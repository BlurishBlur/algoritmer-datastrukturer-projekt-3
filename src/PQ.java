/**
 *
 * @author Niels Heltner (nhelt15) & Antonio Lascari (anlas15)
 */
public interface PQ {
    
    /**
     * Extracts the minimum priority of the heap.
     * @return the element with the lowest priority.
     */
    Element extractMin();
    
    /**
     * Inserts an element into the heap.
     * @param element the element to be inserted into the heap.
     */
    void insert(Element element);
    
}
