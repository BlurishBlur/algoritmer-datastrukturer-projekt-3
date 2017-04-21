/**
 * This class represents a binary search tree.
 * @author Niels Heltner (nhelt15) & Antonio Lascari (anlas15)
 */
public class DictBinTree implements Dict {
    
    /**
     * The root of the tree.
     */
    private Node root;
    /**
     * The total amount of elements in the tree.
     */
    private int size;
    /**
     * The pointer used for doing an ordered traversal.
     */
    private int pointer;
    
    /**
     * Instantiates the tree, with a size of 0.
     */
    public DictBinTree() {
        size = 0;
    }

    /**
     * Inserts a key into the tree, first by comparing the key to the 
     * other nodes in the tree, starting from the root.
     * @param key The key wished to be inserted into the tree.
     */
    @Override
    public void insert(int key) {
        Node y = null;
        Node x = root;
        Node z = new Node(key);
        while(x != null) {
            y = x;
            if(z.getKey() < x.getKey()) {
                x = x.getLeftChild();
            }
            else {
                x = x.getRightChild();
            }
        }
        if(y == null) {
            root = z;
        }
        else if(z.getKey() < y.getKey()) {
            y.setLeftChild(z);
        }
        else {
            y.setRightChild(z);
        }
        size++;
    }

    /**
     * Traverses through the tree, returning an integer array with all
     * elements in order.
     * @return all elements in order.
     */
    @Override
    public int[] orderedTraversal() {
        int[] array = new int[size];
        pointer = 0;
        inorderTreeWalk(root, array);
        return array;
    }

    /**
     * Method to recursively traverse through the tree, and insert the
     * keys, in order, into the array.
     * @param x the starting node, from which to traverse.
     * @param array the array in which to insert the result.
     */
    private void inorderTreeWalk(Node x, int[] array) {
        if(x != null) {
            inorderTreeWalk(x.getLeftChild(), array);
            array[pointer++] = x.getKey();
            inorderTreeWalk(x.getRightChild(), array);
        }
    }
    
    @Override
    public int getFrequency() {
        int total = 0;
        int[] a = orderedTraversal();
        for (int i = 0; i < a.length; i++) {
            total += a[i];
        }
        return total;
    }

    /**
     * Searches for a given key, and returns whether or not the key is 
     * contained in the tree. Starting at the root, it compares the key
     * to that node. If the key is smaller, it continues down the left
     * part of the tree, and if it's larger, it continues down the right
     * part of the tree.
     * @param key the key to be checked whether is contained in the tree
     * or not.
     * @return 
     */
    @Override
    public boolean search(int key) {
        Node x = root;
        while(x != null) {
            if(key == x.getKey()) {
                return true;
            }
            if(key < x.getKey()) {
                x = x.getLeftChild();
            }
            else {
                x = x.getRightChild();
            }
        }
        return false;
    }
    
}
