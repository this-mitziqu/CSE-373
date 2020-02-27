package deques.palindrome;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OffByOneTest {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();



    @SuppressWarnings("checkstyle:WhitespaceAfter")
    @Test
    public void testOffByOne() {
       assertFalse(offByOne.equalChars('a', 'a'));
        assertTrue(offByOne.equalChars('a', 'b'));
       assertFalse(offByOne.equalChars('A', 'a'));
       assertFalse(offByOne.equalChars('*', 'a'));
       assertFalse(offByOne.equalChars('*', 'A'));
       assertFalse(offByOne.equalChars('!', '^'));
       assertFalse(offByOne.equalChars('i', 'c'));
       assertFalse(offByOne.equalChars('C', 'c'));
       assertFalse(offByOne.equalChars('a', 'c'));
       assertTrue(offByOne.equalChars('O', 'P'));
       assertTrue(offByOne.equalChars('O', 'N'));
       assertFalse(offByOne.equalChars('0', 'a'));
        assertFalse(offByOne.equalChars(' ', ' '));
        assertFalse(offByOne.equalChars('0', ' '));
        assertFalse(offByOne.equalChars('0', 'A'));
        assertFalse(offByOne.equalChars('0', '*'));
       assertFalse(offByOne.equalChars('*', 'A'));
       assertFalse(offByOne.equalChars('*', ' '));
        assertFalse(offByOne.equalChars('a', ' '));
        assertFalse(offByOne.equalChars(' ', 'A'));
        assertFalse(offByOne.equalChars('*', 'b'));
        assertFalse(offByOne.equalChars('*', 'a'));
       assertFalse(offByOne.equalChars('B', 'c'));
       assertFalse(offByOne.equalChars('b', 'C'));

    }


}
