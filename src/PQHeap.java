/**
 * This class represents a priority queue in the form of a min-heap.
 * You can insert an element into the heap, or extract the element with 
 * the currently lowest priority.
 * @author Niels Heltner (nhelt15) & Antonio Lascari (anlas15)
 */
public class PQHeap implements PQ {
    
    /**
     * The heap, represented by an Element array.
     */
    private Element[] heap;
    /**
     * The current amount of objects in the heap.
     */
    private int size;
    
    /**
     * Constructs a priority queue in the form of a min-heap, with a 
     * maximum capacity of the given integer.
     * @param maxElements the maximum capacity of the priority queue.
     */
    public PQHeap(int maxElements) {
        heap = new Element[maxElements];
        size = 0;
    }

    /**
     * Extracts the element with the lowest priority key, and heapifies 
     * the heap afterwards.
     * @return the element with the lowest priority key.
     */
    @Override
    public Element extractMin() {
        Element min = heap[0];
        heap[0] = heap[--size];
        minHeapify(0);
        return min;
    }

    /**
     * Inserts the element into the heap, ensuring heap-shape, 
     * and rearranges the other elements to ensure heap-order.
     * @param element the element to be inserted into the heap.
     */
    @Override
    public void insert(Element element) {
        heap[size++] = element;
        int currentIndex = size - 1;
        while(currentIndex > 0 && 
                heap[getParentIndex(currentIndex)].getKey() > 
                heap[currentIndex].getKey()) {
            swap(getParentIndex(currentIndex), currentIndex);
            currentIndex = getParentIndex(currentIndex);
        }
    }
    
    /**
     * Heapifies the heap, ensuring heap-order.
     * @param index the index of where to start the heapify.
     */
    private void minHeapify(int index) {
        int leftChildIndex = getLeftChildIndex(index);
        int rightChildIndex = getRightChildIndex(index);
        int minIndex;
        if(leftChildIndex < size && 
                heap[leftChildIndex].getKey() < heap[index].getKey()) {
            minIndex = leftChildIndex;
        }
        else {
            minIndex = index;
        }
        if(rightChildIndex < size && 
                heap[rightChildIndex].getKey() < heap[minIndex].getKey()) {
            minIndex = rightChildIndex;
        }
        if(minIndex != index) {
            swap(minIndex, index);
            minHeapify(minIndex);
        }
    }
    
    /**
     * Gets the parent index of a given child index.
     * @param childIndex the index of the child, whose parent's index 
     * needs to be found
     * @return the index of the parent, who has a child with the given index
     */
    private int getParentIndex(int childIndex) {
        return (childIndex - 1) / 2;
    }
    
    /**
     * Gets the left child index of a given parent.
     * @param parentIndex the index of the parent, whose left child's 
     * index needs to be found
     * @return the index of the left child of the parent
     */
    private int getLeftChildIndex(int parentIndex) {
        return (parentIndex * 2) + 1;
    }
    
    /**
     * Gets the right child index of a given parent.
     * @param parentIndex the index of the parent, whose right child's 
     * index needs to be found
     * @return the index of the right child of the parent
     */
    private int getRightChildIndex(int parentIndex) {
        return (parentIndex * 2) + 2;
    }
    
    /**
     * Swaps two elements' position in the heap. After the method call, 
     * the element at index1 will be placed at index2, and the element 
     * previously at index2 will be placed at index1
     * @param index1 the index of the first element to be swapped
     * @param index2 the index of the second element to be swapped
     */
    private void swap(int index1, int index2) {
        Element temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }
    
}
