import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Put your name here
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static final class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected =
                this.createFromArgsRef(ORDER, true, "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    /*
     * Tests for add.
     */

    @Test
    public final void testAddNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green");
        SortingMachine<String> mExpected =
                this.createFromArgsRef(ORDER, true, "green", "blue");

        m.add("blue");

        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddSeveral() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "delta", "alpha", "charlie", "bravo");

        m.add("delta");
        m.add("alpha");
        m.add("charlie");
        m.add("bravo");

        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddDuplicates() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "apple");
        SortingMachine<String> mExpected =
                this.createFromArgsRef(ORDER, true, "apple", "apple");

        m.add("apple");

        assertEquals(mExpected, m);
    }

    /*
     * Tests for changeToExtractionMode.
     */

    @Test
    public final void testChangeToExtractionModeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected =
                this.createFromArgsRef(ORDER, false);

        m.changeToExtractionMode();

        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionModeNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "delta", "alpha", "charlie", "bravo");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "delta", "alpha", "charlie", "bravo");

        m.changeToExtractionMode();

        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionModeAlreadySortedInput() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "alpha", "bravo", "charlie", "delta");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "alpha", "bravo", "charlie", "delta");

        m.changeToExtractionMode();

        assertEquals(mExpected, m);
    }

    /*
     * Tests for removeFirst.
     */

    @Test
    public final void testRemoveFirstSingle() {
        SortingMachine<String> m =
                this.createFromArgsTest(ORDER, false, "green");
        SortingMachine<String> mExpected =
                this.createFromArgsRef(ORDER, false);

        String removed = m.removeFirst();

        assertEquals("green", removed);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveFirstMany() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "delta", "alpha", "charlie", "bravo");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "bravo", "charlie", "delta");

        String removed = m.removeFirst();

        assertEquals("alpha", removed);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveFirstTwice() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "delta", "alpha", "charlie", "bravo");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "charlie", "delta");

        String removed1 = m.removeFirst();
        String removed2 = m.removeFirst();

        assertEquals("alpha", removed1);
        assertEquals("bravo", removed2);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveFirstWithDuplicates() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "banana", "apple", "apple", "cherry");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "apple", "banana", "cherry");

        String removed = m.removeFirst();

        assertEquals("apple", removed);
        assertEquals(mExpected, m);
    }

    /*
     * Tests for isInInsertionMode.
     */

    @Test
    public final void testIsInInsertionModeConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);

        assertEquals(true, m.isInInsertionMode());
    }

    @Test
    public final void testIsInInsertionModeAfterAdd() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "alpha", "bravo");

        assertEquals(true, m.isInInsertionMode());
    }

    @Test
    public final void testIsInInsertionModeAfterChangeToExtractionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "alpha", "bravo");

        m.changeToExtractionMode();

        assertEquals(false, m.isInInsertionMode());
    }

    /*
     * Tests for order.
     */

    @Test
    public final void testOrderConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);

        assertEquals(ORDER, m.order());
    }

    @Test
    public final void testOrderAfterAdd() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "alpha", "bravo");

        assertEquals(ORDER, m.order());
    }

    @Test
    public final void testOrderAfterChangeToExtractionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "delta", "alpha", "charlie", "bravo");

        assertEquals(ORDER, m.order());
    }

    /*
     * Tests for size.
     */

    @Test
    public final void testSizeEmpty() {
        SortingMachine<String> m = this.constructorTest(ORDER);

        assertEquals(0, m.size());
    }

    @Test
    public final void testSizeInsertionModeNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "alpha", "bravo", "charlie");

        assertEquals(3, m.size());
    }

    @Test
    public final void testSizeExtractionModeNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "alpha", "bravo", "charlie");

        assertEquals(3, m.size());
    }

    @Test
    public final void testSizeAfterRemoveFirst() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "delta", "alpha", "charlie", "bravo");

        m.removeFirst();

        assertEquals(3, m.size());
    }

    @Test
    public final void testSizeAfterRemoveFirstTwice() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "delta", "alpha", "charlie", "bravo");

        m.removeFirst();
        m.removeFirst();

        assertEquals(2, m.size());
    }

}
