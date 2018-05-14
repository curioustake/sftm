package com.curioustake.sftm.datastructure;

import java.util.Arrays;

public class RListUsingSingleArray {

    private  final static int INITIAL_CAPACITY = 10;

    int sentinel;
    int nodeCount;

    int freeHead;
    Integer[] data;

    public RListUsingSingleArray() {
        data = new Integer[INITIAL_CAPACITY * 3];
        sentinel = 0;

        setPrevious(sentinel,sentinel);
        setKey(sentinel,-1);
        setNext(sentinel,sentinel);

        for(int i=1; i<data.length/3; i++) {
            setNext(i, i+1);

            if(i == (data.length/3)-1)
                setNext(i, sentinel);
        }

        freeHead = 1;
        nodeCount = 0;
    }

    public int size() {
        return nodeCount;
    }

    public boolean isEmpty() {
        return nodeCount == 0;
    }

    public boolean contains(Integer element) {
        int current = getNext(sentinel);

        while(current != sentinel) {
            if(getKey(current).equals(element))
                return true;

            current = getNext(current);
        }
        return false;
    }

    public boolean add(Integer element) {
        int newNode = alloc();

        setNext(newNode, sentinel);
        setKey(newNode, element);
        setPrevious(newNode, getPrevious(sentinel));

        setNext(getPrevious(sentinel), newNode);
        setPrevious(sentinel, newNode);

        nodeCount++;

        return true;
    }

    public boolean remove(Integer element) {
        int current = getNext(sentinel);

        while(current != sentinel) {
            if(getKey(current).equals(element)) {
                setNext(getPrevious(current), getNext(current));
                setPrevious(getNext(current), getPrevious(current));

                free(current);
                nodeCount--;
                return true;
            }

            current = getNext(current);
        }

        return false;
    }

    public Integer get(int index) {
        if(index > nodeCount-1)
            throw new RuntimeException("Index out of bound");

        int current = getNext(sentinel);
        for(int i=0; i<=index; i++) {
            if(i == index)
                return getKey(current);

            current = getNext(current);
        }
        return null;
    }

    public Integer set(int index, Integer element) {
        if(index > nodeCount-1)
            throw new RuntimeException("Index out of bound");

        int current = getNext(sentinel);
        for(int i=0; i<=index; i++) {
            if(i == index) {
                Integer temp = getKey(current);
                setKey(current, element);
                return temp;
            }
            current = getNext(current);
        }
        return null;
    }

    public Integer[] toArray() {
        Integer[] arrayRepresentation = new Integer[nodeCount];

        int current = getNext(sentinel);

        for(int i=0; i<nodeCount; i++) {
            arrayRepresentation[i] = getKey(current);
            current = getNext(current);
        }

        return arrayRepresentation;
    }

    private int alloc() {
        int temp = freeHead;

        if(getNext(freeHead) == sentinel) {
            data = Arrays.copyOf(data, data.length*2);

            for(int i=(data.length/3)/2; i<(data.length/3); i++) {
                setNext(i, i+1);

                if(i == (data.length/3)-1)
                    setNext(i, sentinel);
            }

            freeHead = (data.length/3)/2;
        } else {
            freeHead = getNext(freeHead);
        }

        return temp;
    }

    private void free(int index) {
        setNext(index, freeHead);
        freeHead = index;
    }

    private void setPrevious(int index, int previous) {
        data[index * 3] = previous;
    }

    private int getPrevious(int index) {
        return data[index * 3];
    }

    private void setKey(int index, Integer key) {
        data[(index * 3) + 1] = key;
    }

    private Integer getKey(int index) {
        return data[(index * 3) + 1];
    }

    private void setNext(int index, int next) {
        data[(index * 3) + 2] = next;
    }

    private int getNext(int index) {
         return data[(index * 3) + 2];
    }
}

