package setLengthOfLeftStack;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.sequence.Sequence;
import components.sequence.Sequence1L;

public class SequenceTest {

    /**
     * Creates and returns a Sequence with the given entries.
     */
    private static Sequence<Integer> createFromArgs(int... args) {
        Sequence<Integer> s = new Sequence1L<>();

        /*
         * TODO:
         * Add each value from args into s.
         */

        return s;
    }

    /*
     * Constructor tests
     */

    @Test
    public void testConstructorEmpty() {
        Sequence<Integer> s = new Sequence1L<>();

        /*
         * TODO:
         * Check that the new sequence has length 0.
         */
    }

    /*
     * Add tests
     */

    @Test
    public void testAddToEmptyAtZero() {
        Sequence<Integer> s = new Sequence1L<>();

        /*
         * TODO:
         * Add one entry at position 0.
         * Check sequence contents and length.
         */
    }

    @Test
    public void testAddAtFront() {
        Sequence<Integer> s = createFromArgs(2, 3, 4);

        /*
         * TODO:
         * Add an entry at position 0.
         * Check resulting order.
         */
    }

    @Test
    public void testAddAtMiddle() {
        Sequence<Integer> s = createFromArgs(1, 2, 4);

        /*
         * TODO:
         * Add an entry in the middle.
         * Check resulting order.
         */
    }

    @Test
    public void testAddAtEnd() {
        Sequence<Integer> s = createFromArgs(1, 2, 3);

        /*
         * TODO:
         * Add an entry at position s.length().
         * Check resulting order.
         */
    }

    /*
     * Remove tests
     */

    @Test
    public void testRemoveOnlyEntry() {
        Sequence<Integer> s = createFromArgs(10);

        /*
         * TODO:
         * Remove position 0.
         * Check removed value and empty sequence.
         */
    }

    @Test
    public void testRemoveFront() {
        Sequence<Integer> s = createFromArgs(1, 2, 3);

        /*
         * TODO:
         * Remove position 0.
         * Check removed value and remaining order.
         */
    }

    @Test
    public void testRemoveMiddle() {
        Sequence<Integer> s = createFromArgs(1, 2, 3, 4);

        /*
         * TODO:
         * Remove a middle position.
         * Check removed value and remaining order.
         */
    }

    @Test
    public void testRemoveEnd() {
        Sequence<Integer> s = createFromArgs(1, 2, 3);

        /*
         * TODO:
         * Remove position s.length() - 1.
         * Check removed value and remaining order.
         */
    }

    /*
     * Length tests
     */

    @Test
    public void testLengthEmpty() {
        Sequence<Integer> s = new Sequence1L<>();

        /*
         * TODO:
         * Check length is 0.
         */
    }

    @Test
    public void testLengthNonEmpty() {
        Sequence<Integer> s = createFromArgs(1, 2, 3);

        /*
         * TODO:
         * Check length is 3.
         */
    }

    @Test
    public void testLengthAfterAdd() {
        Sequence<Integer> s = createFromArgs(1, 2);

        /*
         * TODO:
         * Add one entry.
         * Check length increased.
         */
    }

    @Test
    public void testLengthAfterRemove() {
        Sequence<Integer> s = createFromArgs(1, 2, 3);

        /*
         * TODO:
         * Remove one entry.
         * Check length decreased.
         */
    }
}