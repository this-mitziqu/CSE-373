package deques.palindrome;

import deques.Deque;
import org.junit.Test;

import static org.junit.Assert.*;

public class PalindromeTest {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque<Character> d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
       assertFalse(palindrome.isPalindrome("apple"));
       assertTrue(palindrome.isPalindrome("abcddcba"));
       assertTrue(palindrome.isPalindrome("ABA"));
       assertTrue(palindrome.isPalindrome("abcdcba"));
       assertTrue(palindrome.isPalindrome("a"));
       assertTrue(palindrome.isPalindrome("aa"));
       assertTrue(palindrome.isPalindrome(""));
       assertFalse(palindrome.isPalindrome("Bb"));
       assertFalse(palindrome.isPalindrome("aBA"));
       assertFalse(palindrome.isPalindrome("ABa"));
       assertTrue(palindrome.isPalindrome("  "));
    }

    @Test
    public void testIsPalindrome2() {
        deques.palindrome.CharacterComparator cc = new OffByOne();
        assertTrue(palindrome.isPalindrome("flake", cc));
        assertTrue(palindrome.isPalindrome("ab", cc));
        assertTrue(palindrome.isPalindrome("cbacb", cc));
        assertTrue(palindrome.isPalindrome(" ", cc));
        assertFalse(palindrome.isPalindrome("   ", cc));
        assertFalse(palindrome.isPalindrome("flaae", cc));
        assertFalse(palindrome.isPalindrome("palindrome", cc));
        assertFalse(palindrome.isPalindrome("Aba", cc));
        assertFalse(palindrome.isPalindrome("*~!", cc));

    }
}
