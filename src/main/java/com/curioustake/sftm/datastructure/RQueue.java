package com.curioustake.sftm.datastructure;

import java.util.Arrays;

public class RQueue {

    private static int INITIAL_CAPACITY = 10;

    Integer []data = new Integer[INITIAL_CAPACITY];
    int head = 0;
    int tail = 0;

    public boolean add(Integer input) {
        data[tail] = input;
        updateTail();
        return true;
    }

    public boolean offer(Integer input) {
        data[tail] = input;
        updateTail();
        return true;
    }

    public Integer remove() {
        if(head == tail)
            return null;

        int temp = data[head];
        head = (head + 1) == data.length ? 0 : head + 1;
        return temp;
    }

    public Integer poll() {
        if(head == tail)
            return null;

        int temp = data[head];
        head = (head + 1) == data.length ? 0 : head + 1;
        return temp;
    }

    public Integer element() {
        if(head == tail)
            return null;

        return data[head];
    }

    public Integer peek() {
        if(head == tail)
            return null;

        return data[head];
    }

    private void updateTail() {
        if(tail >= head) {
            if((tail + 1) < data.length) {
                tail++;
            } else if (head != 0) {
                tail = 0;
            } else {
                doubleCapacity();
            }
        } else {
            if((tail + 1) < head) {
                tail++;
            } else {
                doubleCapacity();
            }
        }
    }

    private void doubleCapacity() {
        int newCapacity = data.length * 2;

        Integer []newData = new Integer[newCapacity];

        System.arraycopy(data, head, newData, 0, data.length-head);

        if(tail<head)
            System.arraycopy(data, 0, newData, data.length-head, tail + 1);

        head = 0;
        tail = data.length;

        data = newData;
    }

    @Override
    public String toString() {
        return "RQueue{" +
                "data=" + Arrays.toString(data) +
                ", head=" + head +
                ", tail=" + tail +
                '}';
    }
}

