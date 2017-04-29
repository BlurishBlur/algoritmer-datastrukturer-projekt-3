
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

    private int[] frequencies;

    public Decode() {
        frequencies = new int[Encode.BYTE_RANGE];
    }

    private int readAndSumFrequencies(BitInputStream in) throws IOException {
        int totalBytes = 0;
        for (int i = 0; i < Encode.BYTE_RANGE; i++) {
            frequencies[i] = in.readInt();
            totalBytes += frequencies[i];
        }
        return totalBytes;
    }

    private void decodeCodeWords(int totalBytes, FileOutputStream out, BitInputStream in) throws IOException {
        Element huffmanTree = HuffmanTree.getInstance().getHuffmanTreeRoot(frequencies);
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

    private void writeOutput(String inputPath, String outputPath) {
        BitInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new BitInputStream(new FileInputStream(inputPath));
            out = new FileOutputStream(outputPath);
            decodeCodeWords(readAndSumFrequencies(in), out, in);

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
    
    private void start(String inputPath, String outputPath) {
        new Decode().writeOutput(inputPath, outputPath);
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            System.out.println("Initializing...");
            new Decode().start(args[0], args[1]);
            System.out.println("Finished.");
        }
        else {
            System.out.println("Illegal syntax.\nUsage: \"nameOfCompressedFile\" \"nameOfOriginalFile\"");
        }
    }

}
