
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Niels
 */
public class Decode {

    private Element createHuffmanTree(int[] c) {
        int n = c.length;
        PQ heap = new PQHeap(c.length);
        for (int i = 0; i < c.length; i++) {
            heap.insert(new Element(c[i], new Node(i)));
        }
        for (int i = 0; i < n - 1; i++) {
            Node node = new Node();

            Element leftChild = heap.extractMin();
            Element rightChild = heap.extractMin();

            node.setLeftChild((Node) leftChild.getData());
            node.setRightChild((Node) rightChild.getData());

            heap.insert(new Element(leftChild.getKey() + rightChild.getKey(), node));
        }
        return heap.extractMin();
    }

    public static void main(String[] args) {
        Decode decode = new Decode();
        int[] frequencies = null;
        BitInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new BitInputStream(new FileInputStream("data/output.txt"));
            out = new FileOutputStream("data/dekodetout.txt");
            frequencies = new int[Encode.BYTE_RANGE];
            int totalBytes = 0;
            for (int i = 0; i < Encode.BYTE_RANGE; i++) {
                frequencies[i] = in.readInt();
                totalBytes += frequencies[i];
            }

            Element huffmanTree = decode.createHuffmanTree(frequencies);
            Node node = null;
            int currentBit;
            int writtenBytes = 0;
            while (writtenBytes < totalBytes) {
                if (node == null) {
                    node = (Node) huffmanTree.getData();
                }
                if (node.isLeaf()) {
                    out.write(node.getKey());
                    writtenBytes++;
                    node = null;
                }
                else {
                    currentBit = in.readBit();
                    if (currentBit == 1) {
                        node = node.getRightChild();
                    }
                    else {
                        node = node.getLeftChild();
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException ex) {
                    Logger.getLogger(Decode.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (out != null) {
                try {
                    out.close();
                }
                catch (IOException ex) {
                    Logger.getLogger(Decode.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

}
