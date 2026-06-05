import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.binarytree.BinaryTree;
import components.binarytree.BinaryTree1;


/**
 * JUnit test fixture for {@code BinarySearchTreeMethods}'s static methods
 * isInTree (and removeSmallest).
 */
public final class BinarySearchTreeMethodsTest {

    /**
     * Constructs and return a BST created by inserting the given {@code args}
     * into an empty tree in the order in which they are provided.
     *
     * @param args
     *            the {@code String}s to be inserted in the tree
     * @return the BST with the given {@code String}s
     * @requires [the Strings in args are all distinct]
     * @ensures createBSTFromArgs = [the BST with the given Strings]
     */
    private static BinaryTree<String> createBSTFromArgs(String... args) {
        BinaryTree<String> t = new BinaryTree1<String>();
        for (String s : args) {
            BinaryTreeUtility.insertInTree(t, s);
        }
        return t;
    }

    @Test
    public void sampleTest() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs("b", "a", "c");
        BinaryTree<String> t2 = createBSTFromArgs("b", "a", "c");
        /*
         * Call method under test
         */
        boolean inTree = BinarySearchTreeMethods.isInTree(t1, "a");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, inTree);
        assertEquals(t2, t1);
    }

    @Test
    public void removeSmallestTest() {
    	BinaryTree<String> t1 = createBSTFromArgs("1", "2", "3");
        BinaryTree<String> t2 = createBSTFromArgs( "2", "3");
        String result = BinarySearchTreeMethods.removeSmallest(t1);
        assertEquals(result, "1");
        assertEquals(t2, t1);
    }

    @Test
    public void removeSmallestTestLeftSubtree() {
        BinaryTree<String> t1 = createBSTFromArgs("b", "a", "c");
        BinaryTree<String> t2 = createBSTFromArgs("b", "c");

        String result = BinarySearchTreeMethods.removeSmallest(t1);

        assertEquals("a", result);
        assertEquals(t2, t1);
    }
    
    @Test
    public void removeSmallestTestSingleNode() {
        BinaryTree<String> t1 = createBSTFromArgs("a");
        BinaryTree<String> t2 = createBSTFromArgs();

        String result = BinarySearchTreeMethods.removeSmallest(t1);

        assertEquals("a", result);
        assertEquals(t2, t1);
    }
}
