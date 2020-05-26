/*
 * Copyright (C) Grégory Van den Borre - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Grégory Van den Borre <vandenborre.gregory@hotmail.com> 2019
 */
package be.yildizgames.common.csv;

import be.yildizgames.common.file.ResourceUtil;
import org.apiguardian.api.API;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Represent a line in a CSV document.
 * Immutable class.
 * @author Grégory Van den Borre
 */
public class CsvLine {

    /**
     * This is the line as received.
     */
    private final String raw;

    /**
     * This is the first entry for the line.
     */
    private final String head;

    /**
     * This is all the entries after the head.
     */
    private final List<String> entries;

    private final String separator;

    /**
     * Build a line from a raw line and a separator.
     * @param line Raw line (cannot be null).
     * @param separator Separator between the entries (cannot be null).
     * @throws NullPointerException If any parameter is null.
     * @throws IllegalArgumentException If separator is empty.
     */
    private CsvLine(final String line, final String separator) {
        super();
        if(separator.isEmpty()) {
            throw new IllegalArgumentException("Separator cannot be empty.");
        }
        this.separator = separator;
        this.raw = line;
        String[] values = line.split(separator, -1);
        this.head = values[0];
        this.entries = Arrays.asList(values).subList(1, values.length);
    }

    /**
     * Create a new line from entries and a separator.
     * @param separator Separator to set between the entries (cannot be null).
     * @param values List of entries to set (Cannot be null).
     * @return The created line (never null).
     * @throws NullPointerException If any parameter is null.
     */
    public static CsvLine toLine(final String separator, final Object... values) {
        String result = String.join(separator, Arrays.stream(values).map(e -> checkContent(separator, e)).toArray(String[]::new));
        return new CsvLine(result, separator);
    }

    public static CsvLine toLine(final String raw, final String separator) {
        return new CsvLine(raw, separator);
    }

    /**
     * Append this line to a file.
     * @param file File to use (cannot be null).
     * @throws NullPointerException If any parameter is null.
     */
    @API(status= API.Status.STABLE)
    public final void addToFile(Path file) {
        ResourceUtil.addLine(file, this.raw);
    }

    /**
     * Provide the csv line head.
     * @return The head (never null).
     */
    @API(status= API.Status.STABLE)
    public final String getHead() {
        return this.head;
    }

    /**
     * Provide the csv raw line.
     * @return The raw line (never null).
     */
    @API(status= API.Status.INTERNAL)
    public final String getRaw() {
        return this.raw;
    }

    /**
     * Provide the csv line head.
     * @return The head (never null, immutable collection).
     */
    @API(status= API.Status.STABLE)
    public final List<String> getEntries() {
        return this.entries;
    }

    private static String checkContent(final String separator, final Object input) {
        return input.toString().replace(separator, "%C%").replace("\n", "%L%");
    }

    final String getSeparator() {
        return this.separator;
    }
}
