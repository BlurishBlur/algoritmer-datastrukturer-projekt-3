
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
    private int[] frequencies;
    private String[] codeWords;

    public Encode() {
        frequencies = new int[BYTE_RANGE];
        codeWords = new String[BYTE_RANGE];
    }

    private void readFrequencies(String path) {
        try (FileInputStream in = new FileInputStream(path)) {
            int currentByte;
            while ((currentByte = in.read()) != -1) {
                frequencies[currentByte]++;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createCodeWords(Node node, String codeWord) {
        if (node != null) {
            if (node.isLeaf()) {
                codeWords[node.getKey()] = codeWord;
            }
            else {
                createCodeWords(node.getLeftChild(), codeWord + "0");
                createCodeWords(node.getRightChild(), codeWord + "1");
            }
        }
    }

    private void writeOutput(String outputPath, String inputPath) {
        BitOutputStream out = null;
        FileInputStream in = null;
        try {
            out = new BitOutputStream(new FileOutputStream(outputPath));
            for (int frequency : frequencies) {
                out.writeInt(frequency);
            }
            in = new FileInputStream(inputPath);
            int currentByte;
            while ((currentByte = in.read()) != -1) {
                String codeword = codeWords[currentByte];
                for (char character : codeword.toCharArray()) {
                    out.writeBit(Character.getNumericValue(character));
                }
            }
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

    private void start(String inputPath, String outputPath) {
        long start = System.currentTimeMillis();
        System.out.println("Reading frequencies...");
        readFrequencies(inputPath);
        System.out.println("Read frequencies in " + (System.currentTimeMillis() - start) + "ms.");
        start = System.currentTimeMillis();
        System.out.println("Creating huffman tree...");
        Element huffmanTree = HuffmanTree.getInstance().getHuffmanTreeRoot(frequencies);
        System.out.println("Created huffman tree in " + (System.currentTimeMillis() - start) + "ms.");
        start = System.currentTimeMillis();
        System.out.println("Creating codewords...");
        createCodeWords((Node) huffmanTree.getData(), "");
        System.out.println("Created codewords in " + (System.currentTimeMillis() - start) + "ms.");
        start = System.currentTimeMillis();
        System.out.println("Writing output...");
        writeOutput(outputPath, inputPath);
        System.out.println("Wrote output in " + (System.currentTimeMillis() - start) + "ms.");
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            System.out.println("Initializing...");
            new Encode().start(args[0], args[1]);
            System.out.println("Finished.");
        }
        else {
            System.out.println("Illegal syntax.\nUsage: \"nameOfOriginalFile\" \"nameOfCompressedFile\"");
        }

    }

}
