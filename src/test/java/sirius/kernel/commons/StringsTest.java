package sirius.kernel.commons;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class StringsTest {

    @Test
    public void isFilled() {
        assertTrue(Strings.isFilled("Test"));
        assertTrue(Strings.isFilled(" "));
        assertFalse(Strings.isFilled(null));
        assertFalse(Strings.isFilled(""));
    }

    @Test
    public void isEmpty() {
        assertFalse(Strings.isEmpty("Test"));
        assertFalse(Strings.isEmpty(" "));
        assertTrue(Strings.isEmpty(null));
        assertTrue(Strings.isEmpty(""));
    }

    @Test
    public void equalIgnoreCase() {
        assertTrue(Strings.equalIgnoreCase("A", "a"));
        assertFalse(Strings.equalIgnoreCase("A", "b"));
        assertTrue(Strings.equalIgnoreCase("", null));
        assertFalse(Strings.equalIgnoreCase(" ", null));
        assertTrue(Strings.equalIgnoreCase(null, null));
    }

    @Test
    public void areEqual() {
        assertTrue(Strings.areEqual("A", "A"));
        assertFalse(Strings.areEqual("a", "A"));
        assertTrue(Strings.areEqual("", null));
        assertFalse(Strings.areEqual(" ", null));
        assertTrue(Strings.areEqual(null, null));
    }

    @Test
    public void toStringMethod() {
        assertEquals("A", Strings.toString("A"));
        assertEquals("", Strings.toString(""));
        assertNull(Strings.toString(null));
    }

    @Test
    public void apply() {
        assertEquals("B A", Strings.apply("%s A", "B"));
        assertEquals("A null", Strings.apply("A %s", (String) null));
    }

    @Test
    public void firstFilled() {
        assertEquals("A", Strings.firstFilled("A"));
        assertEquals("A", Strings.firstFilled("A", "B"));
        assertEquals("A", Strings.firstFilled(null, "A"));
        assertEquals("A", Strings.firstFilled("", "A"));
        assertEquals("A", Strings.firstFilled(null, null, "A"));
        assertNull(Strings.firstFilled());
        assertNull(Strings.firstFilled((String) null));
        assertNull(Strings.firstFilled(""));
    }

    @Test
    public void urlEncode() {
        assertEquals("A%3FTEST%26B%C3%84%C3%96%C3%9C", Strings.urlEncode("A?TEST&BÄÖÜ"));
    }

    @Test
    public void split() {
        assertEquals(Tuple.create("A", "B"), Strings.split("A|B", "|"));
        assertEquals(Tuple.create("A", "&B"), Strings.split("A&&B", "&"));
        assertEquals(Tuple.create("A", "B"), Strings.split("A&&B", "&&"));
        assertEquals(Tuple.create("A", ""), Strings.split("A|", "|"));
        assertEquals(Tuple.create("", "B"), Strings.split("|B", "|"));
        assertEquals(Tuple.create("A&B", null), Strings.split("A&B", "|"));
    }

    @Test
    public void join() {
        assertEquals("A,B,C", Strings.join(",", "A", "B", "C"));
        assertEquals("A,C", Strings.join(",", "A", null, "", "C"));
        assertEquals("A", Strings.join(",", "A"));
        assertEquals("", Strings.join(","));
        assertEquals("ABC", Strings.join("", "A", "B", "C"));
    }

    @Test
    public void toSaneFilename() {
        assertEquals(Strings.toSaneFileName("test.pdf").orElse(""), "test.pdf");
        assertEquals(Strings.toSaneFileName("test").orElse(""), "test");
        assertEquals(Strings.toSaneFileName(".pdf").orElse(""), ".pdf");
        assertEquals(Strings.toSaneFileName("test.").orElse(""), "test.");
        assertEquals(Strings.toSaneFileName("test..").orElse(""), "test_.");
        assertEquals(Strings.toSaneFileName("..test").orElse(""), "_.test");
        assertEquals(Strings.toSaneFileName("Test pdf").orElse(""), "Test_pdf");
        assertEquals(Strings.toSaneFileName("Hallöle").orElse(""), "Halloele");
        assertEquals(Strings.toSaneFileName("test/datei").orElse(""), "test_datei");
        assertEquals(Strings.toSaneFileName("test-datei").orElse(""), "test-datei");
        assertEquals(Strings.toSaneFileName(" test ").orElse(""), "test");
        assertEquals(Strings.toSaneFileName("test.datei.pdf").orElse(""), "test_datei.pdf");
        assertEquals(Strings.toSaneFileName("   "), Optional.<String>empty());
        assertEquals(Strings.toSaneFileName(""), Optional.<String>empty());
    }
}
