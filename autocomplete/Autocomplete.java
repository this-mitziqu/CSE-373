package autocomplete;

public interface Autocomplete {

    /**
     * Returns all terms that start with the given prefix, in descending order of weight.
     * @throws IllegalArgumentException if prefix is null
     */
    Term[] allMatches(String prefix);
}
