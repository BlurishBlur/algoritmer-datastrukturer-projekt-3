/**
 * This class represents a node, contained in a binary search tree.
 * @author Niels Heltner (nhelt15) & Antonio Lascari (anlas15)
 */
public class Node {
    
    /**
     * The key is the integer value, representing the node.
     */
    private int key;
    /**
     * The pointer to the left child of this node.
     */
    private Node leftChild;
    /**
     * The pointer to the right child of this node.
     */
    private Node rightChild;
    
    /**
     * Instantiates a new Node object, with the key value of 0, and both
     * child pointers pointing to null.
     */
    public Node() {
    }
    
    /**
     * Instantiates a new node, with the given key value, and both
     * child pointers pointing to null.
     * @param key the key value of the new node.
     */
    public Node(int key) {
        this.key = key;
    }
    
    /**
     * Returns the key value of this node.
     * @return the key value of this node.
     */
    public int getKey() {
        return key;
    }
    
    /**
     * Returns the left child node of this node.
     * @return the left child node of this node.
     */
    public Node getLeftChild() {
        return leftChild;
    }
    
    /**
     * Returns the right child node of this node.
     * @return the right child node of this node.
     */
    public Node getRightChild() {
        return rightChild;
    }
    
    /**
     * Sets the left child node of this node, to the given node.
     * @param leftChild the new left child of this node.
     */
    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }
    
    /**
     * Sets the right child node of this node, to the given node.
     * @param rightChild the new right child of this node.
     */
    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }
    
}
