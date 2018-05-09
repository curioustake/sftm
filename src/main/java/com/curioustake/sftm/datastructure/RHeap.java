package com.curioustake.sftm.datastructure;

import java.util.Arrays;

public class RHeap {

    public enum HEAP_TYPE{ MIN, MAX}

    private static final int DEFAULT_SIZE = 10;

    Integer[] data = new Integer[DEFAULT_SIZE];
    int size = -1;
    HEAP_TYPE type;

    public RHeap(final HEAP_TYPE type) {
        this.type = type;
    }

    public Integer get() {
        if(size<0)
            return -1;

        return data[0];
    }

    public Integer remove() {
        if(size<0)
            return -1;

        int top = data[0];

        data[0] = data[size];
        size--;

        heapifyChild(0);

        return top;
    }

    public void put(final Integer value) {
        size++;

        if(size > (data.length-1)) {
            data = Arrays.copyOf(data, data.length*2);
        }

        data[size] = value;

        heapifyParent(size);
    }

    public String toString() {
        return Arrays.toString(Arrays.copyOfRange(data,0, size+1));
    }

    private void heapifyParent(final int node) {

        if(node <= 0)
            return;

        Double parentIndex = Math.floor((node - 1)/2);

        if(isValidHeap(data[node], data[parentIndex.intValue()])) {
            swap(parentIndex.intValue(), node);
            heapifyParent(parentIndex.intValue());
        }
    }

    private void heapifyChild(final int node) {
        if(size <= 0)
            return;

        Double lastParentNode = Math.floor((size-1)/2);

        if(node>lastParentNode)
            return;

        int leftChild = ((node*2) + 1) > size ? -1 : (node*2) + 1 ;
        int rigthChild = leftChild + 1 > size ? -1 : leftChild + 1 ;

        if((rigthChild > -1) && isValidHeap(data[rigthChild], data[leftChild]) && isValidHeap(data[rigthChild], data[node])) {
            swap(node, rigthChild);
            heapifyChild(rigthChild);
        } else if(isValidHeap(data[leftChild], data[node])) {
            swap(node, leftChild);
            heapifyChild(leftChild);
        }
    }

    private boolean isValidHeap(final int first, final int second) {
        switch (type) {
            case MAX:
                return first > second;
            case MIN:
                return first < second;
            default:
                throw new RuntimeException("Invalid RHeap Type");
        }
    }

    private void swap(final int first, final int second) {
        Integer temp = data[second];
        data[second] = data[first];
        data[first] = temp;
    }
}
