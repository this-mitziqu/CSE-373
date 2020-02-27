package heap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<T> heap;
    private HashMap<T, Double> priorityMap;
    private HashMap<T, Integer> indexMap;


    public ArrayHeapMinPQ() {
        heap = new ArrayList<>();
        priorityMap = new HashMap<T, Double>();
        indexMap = new HashMap<T, Integer>();
    }

    /*
    Here's a helper method and a method stub that may be useful. Feel free to change or remove
    them, if you wish.
     */

    /**
     * A helper method to create arrays of T, in case you're using an array to represent your heap.
     * You shouldn't need this if you're using an ArrayList instead.
     */
    @SuppressWarnings("unchecked")
    private T[] makeArray(int newCapacity) {
        return (T[]) new Object[newCapacity];
    }

    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private void swap(int a, int b) {
        T tempA = heap.get(a);
        T tempB = heap.get(b);
        heap.set(a, tempB);
        heap.set(b, tempA);
        int temp1 = indexMap.get(tempA);
        int temp2 = indexMap.get(tempB);
        indexMap.put(tempA, temp2);
        indexMap.put(tempB, temp1);
    }

    /**
     * Adds an item with the given priority value.
     * Assumes that item is never null.
     * Runs in O(log N) time (except when resizing).
     * @throws IllegalArgumentException if item is already present in the PQ
     */
    @Override
    public void add(T item, double priority) {
        if (this.contains(item)){
            throw new IllegalArgumentException();
        }
        priorityMap.put(item, priority);
        heap.add(item);
        indexMap.put(item, heap.size() - 1);
        swim(heap.size() - 1); //update heap
    }

    /**
     * Returns true if the PQ contains the given item; false otherwise.
     * Runs in O(log N) time.
     */
    @Override
    public boolean contains(T item) {
        return priorityMap.containsKey(item);
    }

    /**
     * Returns the item with the smallest priority.
     * Runs in O(log N) time.
     * @throws NoSuchElementException if the PQ is empty
     */
    @Override
    public T getSmallest() {
        if (this.isEmpty()){
            throw new NoSuchElementException();
        }
        return heap.get(0);
    }

    /**
     * Removes and returns the item with the smallest priority.
     * Runs in O(log N) time (except when resizing).
     * @throws NoSuchElementException if the PQ is empty
     */
    @Override
    public T removeSmallest() {
        if (this.isEmpty()){
            throw new NoSuchElementException();
        }
        T smallest = getSmallest();
        swap(0, heap.size() - 1);
        heap.remove(heap.size()-1);
        priorityMap.remove(smallest);
        indexMap.remove(smallest);
        sink(0);
        return smallest;
    }

    /**
     * Changes the priority of the given item.
     * Runs in O(log N) time.
     * @throws NoSuchElementException if the item is not present in the PQ
     */
    @Override
    public void changePriority(T item, double priority) {
        if (!this.contains(item)){
            throw new NoSuchElementException();
        }
        //priority larger or smaller, sink or swim
        priorityMap.put(item, priority); //update priorityMap
        //update heap
        swim(indexMap.get(item));
        sink(indexMap.get(item));
    }

    private void swim(int index){
        while ((index >= 1) && (priorityMap.get(heap.get((index - 1) / 2)) > priorityMap.get(heap.get(index)))) {
            swap(index, (index-1)/2);
            index = (index - 1)/2;
        }
    }

    private void sink(int index){
        while (2*index + 1 < heap.size()) {
            int leftChild = 2*index + 1; //left child

            //compare left and right child
            if (leftChild < heap.size() && leftChild+1 < heap.size() && (priorityMap.get(heap.get(leftChild)) >
                    priorityMap.get(heap.get(leftChild + 1)))) {
                leftChild++;
            }
            //compare parent and child
            if (!(priorityMap.get(heap.get(index)) > priorityMap.get(heap.get(leftChild)))){
                break;
            }
            swap(index, leftChild);
            index = leftChild;
        }
    }


    /**
     * Returns the number of items in the PQ.
     * Runs in O(log N) time.
     */
    @Override
    public int size() {
        return heap.size();
    }
}