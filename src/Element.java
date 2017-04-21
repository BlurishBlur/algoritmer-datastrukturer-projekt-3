/**
 *
 * @author Niels Heltner (nhelt15) & Antonio Lascari (anlas15)
 */
public class Element {
    
    private int key;
    private Object data;
    
    public Element(int key, Object data) {
        this.key = key;
        this.data = data;
    }

    public Element() {
    }

    public Element(int key) {
        this.key = key;
    }
    
    public Element(Object data) {
        this.data = data;
    }
    
    public int getKey() {
        return key;
    }
    
    public Object getData() {
        return data;
    }
    
    public void setKey(int key) {
        this.key = key;
    }
    
    public void setData(Object data) {
        this.data = data;
    }
    
}
