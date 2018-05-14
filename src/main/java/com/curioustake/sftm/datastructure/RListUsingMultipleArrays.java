package com.curioustake.sftm.datastructure;

import java.util.Arrays;

public class RListUsingMultipleArrays {

    private  final static int INITIAL_CAPACITY = 10;

    int sentinel;
    int nodeCount;

    int freeHead;
    Integer[] next;
    Integer[] previous;
    Integer[] key;

    public RListUsingMultipleArrays() {
        next = new Integer[INITIAL_CAPACITY];
        previous = new Integer[INITIAL_CAPACITY];
        key = new Integer[INITIAL_CAPACITY];

        sentinel = 0;

        for(int i=1; i<next.length; i++) {
            next[i] = i+1;

            if(i == next.length-1)
                next[i] = sentinel;
        }

        next[sentinel] = sentinel;
        previous[sentinel] = sentinel;
        key[sentinel] = -1;

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
        int current = next[sentinel];

        while(current != sentinel) {
            if(key[current].equals(element))
                return true;

            current = next[current];
        }
        return false;
    }

    public boolean add(Integer element) {
        int newNode = alloc();

        next[newNode] = sentinel;
        key[newNode] = element;
        previous[newNode] = previous[sentinel];

        next[previous[sentinel]] = newNode;
        previous[sentinel] = newNode;

        nodeCount++;

        return true;
    }

    public boolean remove(Integer element) {
        int current = next[sentinel];

        while(current != sentinel) {
            if(key[current].equals(element)) {
                next[previous[current]] = next[current];
                previous[next[current]] = previous[current];

                free(current);
                nodeCount--;
                return true;
            }

            current = next[current];
        }

        return false;
    }

    public Integer get(int index) {
        if(index > nodeCount-1)
            throw new RuntimeException("Index out of bound");

        int current = next[sentinel];
        for(int i=0; i<=index; i++) {
            if(i == index)
                return key[current];

            current = next[current];
        }
        return null;
    }

    public Integer set(int index, Integer element) {
        if(index > nodeCount-1)
            throw new RuntimeException("Index out of bound");

        int current = next[sentinel];
        for(int i=0; i<=index; i++) {
            if(i == index) {
                Integer temp = key[current];
                key[current] = element;
                return temp;
            }
            current = next[current];
        }
        return null;
    }

    public Integer[] toArray() {
        Integer[] arrayRepresentation = new Integer[nodeCount];

        int current = next[sentinel];

        for(int i=0; i<nodeCount; i++) {
            arrayRepresentation[i] = key[current];
            current = next[current];
        }

        return arrayRepresentation;
    }

    private int alloc() {
        int temp = freeHead;

        if(next[freeHead] == sentinel) {
            previous = Arrays.copyOf(previous, previous.length*2);
            key = Arrays.copyOf(key, key.length*2);
            next = Arrays.copyOf(next, next.length*2);

            for(int i=next.length/2; i<next.length-1; i++) {
                next[i] = i+1;
            }
            next[next.length-1] = sentinel;
            freeHead = next.length/2;
        } else {
            freeHead = next[freeHead];
        }

        return temp;
    }

    private void free(int index) {
        next[index] = freeHead;
        freeHead = index;
    }
}
