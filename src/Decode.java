
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

    /**
     * The frequencies of bits.
     */
    private int[] frequencies;

    /**
     * Initializes the freuqncies array.
     */
    public Decode() {
        frequencies = new int[Encode.BYTE_RANGE];
    }

    /**
     * Reads the file from the bitinputstream, and sums all the frequencies, 
     * to ensure correct length of the output file.
     * @param in the bitinputstream to read fom.
     * @return the total amount of bytes.
     * @throws IOException 
     */
    private int readAndSumFrequencies(BitInputStream in) throws IOException {
        int totalBytes = 0;
        for (int i = 0; i < Encode.BYTE_RANGE; i++) {
            frequencies[i] = in.readInt();
            totalBytes += frequencies[i];
        }
        return totalBytes;
    }

    /**
     * Decodes the codewords back into bits.
     * @param totalBytes the total amount of bytes to process.
     * @param out the output file
     * @param in the input file.
     * @throws IOException 
     */
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

    /**
     * Writes the decompressed file to a desired output location.
     * @param inputPath the input file of the compressed file
     * @param outputPath the output file of the decompressed file
     */
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
    
    /**
     * Initializes the decoding process.
     * @param inputPath the input file of the compressed file
     * @param outputPath the output file of the decompressed file
     */
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
