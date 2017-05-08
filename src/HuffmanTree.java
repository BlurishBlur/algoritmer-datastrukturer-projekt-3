
/**
 *
 * @author Niels
 */
public class HuffmanTree {

    /**
     * The singleton instance of this class.
     */
    private static HuffmanTree instance;
    
    /**
     * The root of the Huffman tree.
     */
    private Element root;

    /**
     * Private constructor for the singleton.
     */
    private HuffmanTree() {
        root = null;
    }

    /**
     * Static get-method, for the singleton implementation.
     * @return the singleton instance of this class.
     */
    public static HuffmanTree getInstance() {
        if (instance == null) {
            instance = new HuffmanTree();
        }
        return instance;
    }

    /**
     * Initializes the minheap.
     * @param frequencies the frequencies to inhabit the heap with.
     * @return the initialized heap.
     */
    private PQ initializeHeap(int[] frequencies) {
        PQ heap = new PQHeap(frequencies.length);
        for (int i = 0; i < frequencies.length; i++) {
            heap.insert(new Element(frequencies[i], new Node(i)));
        }
        return heap;
    }

    /**
     * Generates the huffmantree and returns the root. If the tree has 
     * already been generated once, it will always return the same root.
     * @param frequencies the frequencies of which to generate the tree from.
     * @return the root of the tree.
     */
    public Element getHuffmanTreeRoot(int[] frequencies) {
        if (root == null) {
            PQ heap = initializeHeap(frequencies);
            for (int i = 0; i < frequencies.length - 1; i++) {
                Node node = new Node();

                Element leftChild = heap.extractMin();
                Element rightChild = heap.extractMin();

                node.setLeftChild((Node) leftChild.getData());
                node.setRightChild((Node) rightChild.getData());

                heap.insert(new Element(leftChild.getKey() + 
                        rightChild.getKey(), node));
            }
            root = heap.extractMin();
            return root;
        }
        else {
            return root;
        }
    }

}
