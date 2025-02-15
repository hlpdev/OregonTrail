package hlpdev.oregontrail.util;

/**
 * Pair is an object used to store a pair of two self-defined types
 * (It is currently only used with trade variants but could have a use later.)
 * @param <F> The type of the first element
 * @param <S> The type of the second element
 */
public class Pair<F, S> {

    private final F first;
    private final S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F first() {
        return first;
    }

    public S second() {
        return second;
    }

}
