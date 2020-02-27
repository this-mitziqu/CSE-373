package deques.palindrome;

import deques.Deque;
import deques.LinkedDeque;

public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> result = new deques.LinkedDeque<>();
        for (int i = 0; i < word.length(); i++){
            result.addLast(word.charAt(i));
        }
        return result;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> result = wordToDeque(word);
        return isPalindrome(word.length(), result);
    }

    //odd number of characters
    //size decrease by two each call
    @SuppressWarnings("checkstyle:WhitespaceAfter")
    private boolean isPalindrome(int size, Deque<Character> result){
        if (size <= 1){
            return true;
        } else {
           if (result.removeFirst() != result.removeLast()){
               return false;
           }else {
               return isPalindrome(size - 2, result);
           }
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> result = wordToDeque(word);
        return isPalindrome(word.length(), result, cc);
    }

    private boolean isPalindrome(int size, Deque<Character> result, CharacterComparator cc){
        if (size <= 1) {
            return true;
        }else {
            char a = result.removeFirst();
            char b = result.removeLast();
            if (!cc.equalChars(a, b)){
                return false;
            }else {
                return isPalindrome(size - 2, result, cc);
            }
        }
    }
}
