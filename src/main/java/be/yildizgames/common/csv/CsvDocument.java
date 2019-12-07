package be.yildizgames.common.csv;

import be.yildizgames.common.file.ResourceUtil;
import org.apiguardian.api.API;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A collection of csv lines.
 */
public class CsvDocument implements Iterable<CsvLine> {

    private final List<CsvLine> lines;

    private CsvDocument(List<CsvLine> lines) {
        if(!lines.isEmpty()) {
            String s = lines.get(0).getSeparator();
            for(CsvLine l : lines) {
                if(!s.equals(l.getSeparator())) {
                    throw new IllegalArgumentException("The line "
                            + l.getRaw() + " has not the expected separator, should be " + s);
                }
            }
        }
        this.lines = Collections.unmodifiableList(lines);
    }

    /**
     * Read all line in a file and extract them as CSV lines.
     * @param path File to use (cannot be null).
     * @param separator Separator for the CSV (cannot be null).
     * @return A collection of CSV lines extracted from the file.
     * @throws NullPointerException If any parameter is null.
     */
    @API(status= API.Status.STABLE)
    public static CsvDocument fromFile(Path path, String separator) {
        return new CsvDocument(ResourceUtil.readAllLines(path)
                .stream()
                .map(s -> CsvLine.toLine(s, separator))
                .collect(Collectors.toList()));
    }

    /**
     * Read all line in a file and extract them as CSV lines.
     * @param path File to use (cannot be null).
     * @param separator Separator for the CSV (cannot be null).
     * @param transform Transform the input before adding it (cannot be null).
     * @return A collection of CSV lines extracted from the file.
     * @throws NullPointerException If any parameter is null.
     */
    public static CsvDocument fromFile(final Path path, final String separator, final Transform transform) {
        return new CsvDocument(ResourceUtil.readAllLines(path)
                .stream()
                .map(transform::to)
                .map(s -> CsvLine.toLine(s, separator))
                .collect(Collectors.toList()));
    }

    @API(status= API.Status.STABLE)
    public final void toFile(Path path) {
        ResourceUtil.writeAllLines(path, this.lines.stream().map(CsvLine::getRaw).collect(Collectors.toList()));
    }

    @API(status= API.Status.STABLE)
    public static CsvDocument fromLines(List<CsvLine> lines) {
        return new CsvDocument(lines);
    }

    @API(status= API.Status.INTERNAL)
    @Override
    public final Iterator<CsvLine> iterator() {
        return this.lines.iterator();
    }

    @API(status= API.Status.INTERNAL)
    @Override
    public final void forEach(Consumer<? super CsvLine> action) {
        this.lines.forEach(action);
    }

    @API(status= API.Status.INTERNAL)
    @Override
    public final Spliterator<CsvLine> spliterator() {
        return this.lines.spliterator();
    }

    public final Stream<CsvLine> stream() {
        return this.lines.stream();
    }
}
