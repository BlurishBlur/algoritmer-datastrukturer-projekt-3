
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
        try (FileInputStream in = new FileInputStream(path)) {
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

    public void createCodeWords(String[] codeWords, Node node, String codeWord) {
        if (node != null) {
            if (node.isLeaf()) {
                codeWords[node.getKey()] = codeWord;
            }
            else {
                createCodeWords(codeWords, node.getLeftChild(), codeWord + "0");
                createCodeWords(codeWords, node.getRightChild(), codeWord + "1");
            }
        }
    }

    public static void main(String[] args) {
        Encode encode = new Encode();
        long start = System.currentTimeMillis();
        int[] frequencies = encode.getFrequencies("data/input.txt");
        System.out.println("Read frequencies: " + (System.currentTimeMillis() - start) + "ms");
        start = System.currentTimeMillis();
        Element huffmanTree = encode.createHuffmanTree(frequencies);
        System.out.println("Create huffman tree: " + (System.currentTimeMillis() - start) + "ms");
        start = System.currentTimeMillis();
        String[] codeWords = new String[BYTE_RANGE];
        encode.createCodeWords(codeWords, (Node) huffmanTree.getData(), "");
        System.out.println("Create codewords: " + (System.currentTimeMillis() - start) + "ms");
        start = System.currentTimeMillis();

        BitOutputStream out = null;
        FileInputStream in = null;
        try {
            out = new BitOutputStream(new FileOutputStream("data/output.txt"));
            for (int frequency : frequencies) {
                out.writeInt(frequency);
            }
            System.out.println("Write frequencies: " + (System.currentTimeMillis() - start) + "ms");
            start = System.currentTimeMillis();
            in = new FileInputStream("data/input.txt");
            int currentByte;
            while ((currentByte = in.read()) != -1) {
                String codeword = codeWords[currentByte];
                for (char character : codeword.toCharArray()) {
                    out.writeBit(Character.getNumericValue(character));
                }
            }
            System.out.println("Write codewords: " + (System.currentTimeMillis() - start) + "ms");
            start = System.currentTimeMillis();
        }
        catch (FileNotFoundException e) {
            System.err.println("Kunne ikke finde stien til output fil");
            e.printStackTrace();
        }
        catch (IOException e) {
            Logger.getLogger(Encode.class.getName()).log(Level.SEVERE, null, e);
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException e) {
                    System.err.println("Fejl ved lukning af inputstream");
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.writeBit(0);
                    out.writeBit(1);
                    out.close();
                }
                catch (IOException e) {
                    System.err.println("Fejl ved lukning af outputstream");
                    e.printStackTrace();
                }
            }
        }
    }

}
