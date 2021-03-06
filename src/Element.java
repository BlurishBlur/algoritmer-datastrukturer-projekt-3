/**
 *
 * @author Niels Heltner (nhelt15) & Antonio Lascari (anlas15)
 */
public class Element {
    
    /**
     * The frequency.
     */
    private final int key;
    
    /**
     * Represents subtrees, generated by the Huffman algorithm.
     */
    private final Object data;
    
    public Element(int key, Object data) {
        this.key = key;
        this.data = data;
    }
    
    public int getKey() {
        return key;
    }
    
    public Object getData() {
        return data;
    }
    
}
