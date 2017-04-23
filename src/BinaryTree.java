
/**
 *
 * @author Niels
 */
public class BinaryTree implements IBinaryTree {

    private Node root;
    private int pointer;
    private int size;

    public BinaryTree() {
        root = null;
        size = 0;
    }

    @Override
    public void insert(int key) {
        root = insert(root, key);
        size++;
    }

    private Node insert(Node node, int key) {
        if (node == null) {
            node = new Node(key);
        }
        else if (node.getRightChild() == null) {
            node.setRightChild(insert(node.getRightChild(), key));
        }
        else {
            node.setLeftChild(insert(node.getLeftChild(), key));
        }
        return node;
    }
    
    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public int[] orderedTraversal(boolean print) {
        int[] array = new int[size];
        pointer = 0;
        if(print)
        System.out.println("root key: " + root.getKey());
        inorderTreeWalk(root, array);
        return array;
    }
    
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
        int[] a = orderedTraversal(false);
        for (int i = 0; i < a.length; i++) {
            total += a[i];
        }
        return total;
    }

}
