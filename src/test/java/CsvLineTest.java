import be.yildizgames.common.csv.CsvLine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CsvLineTest {

    @Nested
    class Constructor {

        @Test
        void happyFlow() {
            CsvLine line = CsvLine.toLine("a;b;c;", ";");
            Assertions.assertEquals("a", line.getHead());
            Assertions.assertEquals(2, line.getEntries().size());
            Assertions.assertEquals("b", line.getEntries().get(0));
            Assertions.assertEquals("c", line.getEntries().get(1));
            Assertions.assertEquals("a;b;c;", line.getRaw());
            line = CsvLine.toLine("a;b;c", ";");
            Assertions.assertEquals("a", line.getHead());
            Assertions.assertEquals(2, line.getEntries().size());
            Assertions.assertEquals("b", line.getEntries().get(0));
            Assertions.assertEquals("c", line.getEntries().get(1));
            Assertions.assertEquals("a;b;c", line.getRaw());
        }

        @Test
        void nullRaw() {
            Assertions.assertThrows(NullPointerException.class, () -> CsvLine.toLine(null, ";"));
        }

        @Test
        void nullSeparator() {
            Assertions.assertThrows(NullPointerException.class, () -> CsvLine.toLine("a;b;c;", (String)null));
        }

        @Test
        void emptySeparator() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> CsvLine.toLine("a;b;c;", ""));
        }

        @Test
        void wrongSeparator() {
            CsvLine line = CsvLine.toLine("a;b;c;", ":");
            Assertions.assertEquals("a;b;c;", line.getHead());
        }
    }

    @Nested
    class GetEntries {

        @Test
        void ensureImmutable() {
            CsvLine line = CsvLine.toLine("a;b;c;", ";");
            Assertions.assertThrows(UnsupportedOperationException.class, () ->line.getEntries().add("x"));
            Assertions.assertThrows(UnsupportedOperationException.class, () ->line.getEntries().remove(0));
        }
    }

    @Nested
    class toLine {

        @Test
        void happyflow() {
            CsvLine line = CsvLine.toLine("a;b;c;", ";");

        }

    }

}
