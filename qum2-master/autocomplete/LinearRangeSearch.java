package autocomplete;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class LinearRangeSearch implements Autocomplete {
    private Term[] terms;

    /**
     * Validates and stores the given array of terms.
     * Assumes that the given array will not be used externally afterwards (and thus may directly
     * store and mutate it).
     * @throws IllegalArgumentException if terms is null or contains null
     */
    public LinearRangeSearch(Term[] terms) {
        if (terms == null){
            throw new IllegalArgumentException();
        }
        for (Term term:terms){
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
        List<Term> temp = new ArrayList<Term>();
        for (int i = 0; i < terms.length; i++){
           if (terms[i].queryPrefix(r).equals(prefix)){
               temp.add(terms[i]);
           }
        }
        Term[] allMatches = new Term[temp.size()];
        for (int i =0; i < temp.size(); i++) {
            allMatches[i] = temp.get(i);
        }
        Arrays.sort(allMatches, TermComparators.byReverseWeightOrder());
        return allMatches;
    }
}


