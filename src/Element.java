/**
 *
 * @author Niels Heltner (nhelt15) & Antonio Lascari (anlas15)
 */
public class Element {
    
    public int key;
    public Object data;
    
    public Element(int key, Object data) {
        this.key = key;
        this.data = data;
    }

    public Element() {
    }

    public Element(int key) {
        this.key = key;
    }
    
}
