
/**
 *
 * @author Niels
 */
public class HuffmanTree {

    private static HuffmanTree instance;
    private Element root;

    private HuffmanTree() {
        root = null;
    }

    public static HuffmanTree getInstance() {
        if (instance == null) {
            instance = new HuffmanTree();
        }
        return instance;
    }

    private PQ initializeHeap(int[] frequencies) {
        PQ heap = new PQHeap(frequencies.length);
        for (int i = 0; i < frequencies.length; i++) {
            heap.insert(new Element(frequencies[i], new Node(i)));
        }
        return heap;
    }

    public Element getHuffmanTreeRoot(int[] frequencies) {
        if (root == null) {
            PQ heap = initializeHeap(frequencies);
            for (int i = 0; i < frequencies.length - 1; i++) {
                Node node = new Node();

                Element leftChild = heap.extractMin();
                Element rightChild = heap.extractMin();

                node.setLeftChild((Node) leftChild.getData());
                node.setRightChild((Node) rightChild.getData());

                heap.insert(new Element(leftChild.getKey() + rightChild.getKey(), node));
            }
            root = heap.extractMin();
            return root;
        }
        else {
            return root;
        }
    }

}
