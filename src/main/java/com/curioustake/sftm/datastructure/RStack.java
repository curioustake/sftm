package com.curioustake.sftm.datastructure;

import java.util.Arrays;
import java.util.Stack;

public class RStack extends Stack<Integer> {

    final static int INITIAL_STACK_SIZE = 10;

    private Integer data[] = new Integer[INITIAL_STACK_SIZE];
    private int head = 0;

    public RStack() {
        super();
    }

    @Override
    public Integer push(Integer item) {
        data[head] = item;
        head++;

        if(head >= data.length)
            data = Arrays.copyOf(data, data.length * 2);

        return data[head-1];
    }

    @Override
    public synchronized Integer pop() {
        if(head <= 0)
            throw new RuntimeException("POP called on an EMPTY stack");
        int temp = data[--head];
        data[head] = null;
        return temp;
    }

    @Override
    public synchronized Integer peek() {
        if(head <= 0)
            throw new RuntimeException("PEEK called on an EMPTY stack");

        return data[head-1];
    }

    @Override
    public boolean empty() {
        return head <= 0;
    }

    @Override
    public synchronized int search(Object o) {
        Integer item = (Integer) o;

        for(int i=head-1; i>=0; i--) {
            if(item.equals(data[i]))
                return head - i;
        }

        return -1;
    }

    @Override
    public String toString() {
        return Arrays.toString(data);
    }
}
