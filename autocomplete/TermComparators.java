package autocomplete;

import java.util.Comparator;

public class TermComparators {
    public static Comparator<Term> byReverseWeightOrder() {
        return Term::compareToByReverseWeightOrder;
    }

    public static Comparator<Term> byPrefixOrder(int r) {
        return (t1, t2) -> t1.compareToByPrefixOrder(t2, r);
    }
}
