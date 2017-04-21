
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

    public static void main(String[] args) {
        int[] array = new int[256];
        try {
            FileInputStream in = new FileInputStream("src/t.txt");
            boolean running = true;
            int b;
            try {
                while ((b = in.read()) != -1) {
                    array[b]++;
                    System.out.println(Arrays.toString(array));
                }
            }
            catch (IOException ex) {
                Logger.getLogger(Encode.class.getName()).log(Level.SEVERE, null, ex);
            }
            Element element = huffman(array);
            int[] ar = ((Dict) element.data).orderedTraversal();
            System.out.println(Arrays.toString(ar));
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(Encode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static Element huffman(int[] c) {
        int n = c.length;
        PQ heap = new PQHeap(c.length);
        for (int i = 0; i < c.length; i++) {
            heap.insert(new Element(c[i]));
        }
        for (int i = 0; i < n - 1; i++) {
            Element e = new Element();

            Element first = heap.extractMin();
            Element second = heap.extractMin();

            e.data = new DictBinTree();
            ((Dict) e.data).insert(first.key);
            ((Dict) e.data).insert(second.key);
            e.key = ((Dict) e.data).getFrequency();

            heap.insert(e);
        }
        return heap.extractMin();
    }

}
