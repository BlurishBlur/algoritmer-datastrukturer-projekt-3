/**
 *
 * @author Niels Heltner (nhelt15) & Antonio Lascari (anlas15)
 */
public class Element {
    
    private final int key;
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
