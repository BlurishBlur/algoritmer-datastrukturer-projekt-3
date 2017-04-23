
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Niels Heltner (nhelt15) & Antonio Lascari (anlas15)
 */
public class Encode {

    public static final int BYTE_RANGE = 256;

    public int[] getFrequencies(String path) {
        int[] frequencies = null;
        try (FileInputStream in = new FileInputStream("src/t.txt")) {
            frequencies = new int[BYTE_RANGE];
            int currentByte;
            while ((currentByte = in.read()) != -1) {
                frequencies[currentByte]++;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return frequencies;
    }

    private Element createHuffmanTree(int[] c) {
        int n = c.length;
        PQ heap = new PQHeap(c.length);
        for (int i = 0; i < c.length; i++) {
            if (c[i] > 0) {
                heap.insert(new Element(c[i]));
            }
        }
        for (int i = 0; i < 5; i++) {
            Element e = new Element(new BinaryTree());

            Element first = heap.extractMin();
            Element second = heap.extractMin();

            if (first.getData() != null) {
                int[] f = ((IBinaryTree) first.getData()).orderedTraversal(false);
                for (int j = 0; j < f.length; j++) {
                    ((IBinaryTree) e.getData()).insert(f[j]);
                }
            }
            else {
                ((IBinaryTree) e.getData()).insert(first.getKey());
            }
            if (second.getData() != null) {
                int[] s = ((IBinaryTree) second.getData()).orderedTraversal(false);
                for (int j = 0; j < s.length; j++) {
                    ((IBinaryTree) e.getData()).insert(s[j]);
                }
            }
            else {
                ((IBinaryTree) e.getData()).insert(second.getKey());
            }

            //((IBinaryTree) e.getData()).insert(first.getKey());
            //((IBinaryTree) e.getData()).insert(second.getKey());
            e.setKey(((IBinaryTree) e.getData()).getFrequency());
            //System.out.println("run " + i + ": " + e.getKey());

            heap.insert(e);
        }
        return heap.extractMin();
    }

    public void createCodeWords(String[] codeWords, Node node, String codeWord) {
        if (node != null) {
            System.out.println(node.getKey());
            if (node.isLeaf()) {
                codeWords[node.getKey()] = codeWord;
                System.out.println("new codeword");
            }
            else {
                createCodeWords(codeWords, node.getLeftChild(), codeWord + "0");
                createCodeWords(codeWords, node.getRightChild(), codeWord + "1");
            }
        }
    }

    public static void main(String[] args) {
        Encode encode = new Encode();
        int[] frequencies = encode.getFrequencies("src/t.txt");
        //System.out.println(encode.createHuffmanTree(frequencies).getKey());
        //int[] ar = ((IBinaryTree) encode.createHuffmanTree(frequencies).getData()).orderedTraversal(false);
        IBinaryTree huffmanTree = ((IBinaryTree) encode.createHuffmanTree(frequencies).getData());
        String[] c = new String[BYTE_RANGE];
        encode.createCodeWords(c, huffmanTree.getRoot(), "");
        System.out.println(Arrays.toString(c));

        //System.out.println(Arrays.toString(ar));
//encode.createCodewords(new String[BYTE_RANGE], ar, "");
    }

}
