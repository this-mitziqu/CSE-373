package autocomplete;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TermTest {
    @Test
    public void testSimpleCompareTo() {
        Term a = new Term("autocomplete", 0);
        Term b = new Term("me", 0);
        assertTrue(a.compareTo(b) < 0); // "autocomplete" < "me"
    }

    // Write more unit tests below.
}
