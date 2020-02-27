package autocomplete;

import java.util.Arrays;

public class BinaryRangeSearch implements Autocomplete {
    private Term[] terms;

    /**
     * Validates and stores the given array of terms.
     * Assumes that the given array will not be used externally afterwards (and thus may directly
     * store and mutate it).
     * @throws IllegalArgumentException if terms is null or contains null
     */
    public BinaryRangeSearch(Term[] terms) {
        if (terms == null){
            throw new IllegalArgumentException();
        }
        for (Term term: terms){
            if (term == null){
                throw new IllegalArgumentException();
            }
        }
        Arrays.sort(terms);
        this.terms = terms;
    }

    /**
     * Returns all terms that start with the given prefix, in descending order of weight.
     * @throws IllegalArgumentException if prefix is null
     */
    public Term[] allMatches(String prefix) {
        if (prefix == null){
            throw new IllegalArgumentException();
        }
        int r = prefix.length();
        //1. Sort the terms in lexicographic order.

        //2. Find all query strings that start with the given prefix.
        // You can do this by running two separate binary searches:
        // one to find the first query with the prefix, and another to find the last.

        //terms.length -1 instead of prefix.length() - 1 !!!!
        int firstQuery = binarySearch1(terms, prefix, r, 0, terms.length - 1);
        int lastQuery = binarySearch2(terms, prefix, r, 0, terms.length -1);
        //3. Sort the matching terms in descending order by weight.
        Term[] allMatches = new Term[lastQuery - firstQuery + 1];
        int index = 0;
        for (int i = firstQuery; i <= lastQuery; i++) {
            allMatches[index] = terms[i];
            index++;
        }
        Arrays.sort(allMatches, TermComparators.byReverseWeightOrder());
        return allMatches;
    }

    //check if mid's left is same as mid (if mid is the most left)
    private int binarySearch1(Term[] terms2, String prefix, int r,  int low, int high){
        int mid =  (low + high)/2;
        // prefix compare w/ prefix, not compare the whole word.
        //".queryPrefix(r)"
        //String temp1 = ;
        //String temp2 = terms[mid-1].queryPrefix(r);
        //has to check if reach the front of the array (if mid == 0), overwise there will be bug
        //compare string use "equals()" instead of "=="
        if (terms2[mid].queryPrefix(r).equals(prefix) && (mid == 0 ||
                !terms2[mid].queryPrefix(r).equals(terms2[mid-1].queryPrefix(r)))){
            return mid;
        }

        if (terms2[mid].queryPrefix(r).compareTo(prefix) < 0){
            return binarySearch1(terms2, prefix, r, mid+1, high);
        }else {
            //if (terms[mid].queryPrefix(r).compareTo(prefix) >= 0){
            return binarySearch1(terms2, prefix, r, low, mid-1);
        }
    }

    private int binarySearch2(Term[] terms2, String prefix, int r, int low, int high){
        int mid = (low + high)/2;
        //terms.length -1 instead of r -1
        if (terms2[mid].queryPrefix(r).equals(prefix) && (mid == terms2.length - 1 ||
                !terms2[mid].queryPrefix(r).equals(terms2[mid+1].queryPrefix(r)))){
            return mid;
        }
        if (terms2[mid].queryPrefix(r).compareTo(prefix) <= 0){
            return binarySearch2(terms2, prefix, r, mid +1, high);
        } else {
            //if (terms[mid].queryPrefix(r).compareTo(prefix) > 0){
            return binarySearch2(terms2, prefix, r, low, mid-1);
        }

    }
}
