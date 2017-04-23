
/**
 *
 * @author Niels
 */
public interface IBinaryTree {
    
    void insert(int key);
    
    int[] orderedTraversal(boolean print);
    
    int getFrequency();
    
    Node getRoot();
    
}
