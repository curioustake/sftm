package com.curioustake.sftm.datastructure;

import java.util.Stack;

public class RStackUsingQueue extends Stack<Integer> {

    RQueue pushQueue;
    RQueue popQueue;

    public RStackUsingQueue() {
        super();
        pushQueue = new RQueue();
        popQueue = new RQueue();
    }

    @Override
    public Integer push(Integer item) {
        while(pushQueue.peek() != null) {
            popQueue.add(pushQueue.remove());
        }
        pushQueue.add(item);
        swapQueue();

        return popQueue.peek();
    }

    private void swapQueue() {
        RQueue tempQueue = popQueue;
        popQueue = pushQueue;
        pushQueue = tempQueue;
    }

    @Override
    public synchronized Integer pop() {
        if(popQueue.peek() == null)
            throw new RuntimeException("POP called on an EMPTY stack");

        Integer temp = popQueue.remove();

        if(popQueue.peek() == null)
            swapQueue();

        return temp;
    }

    @Override
    public synchronized Integer peek() {
        if(popQueue.peek() == null)
            throw new RuntimeException("POP called on an EMPTY stack");

        return popQueue.peek();
    }

    @Override
    public boolean empty() {
        return (pushQueue.peek() == null && popQueue.peek() == null);
    }

    @Override
    public synchronized int search(Object o) {
        throw new RuntimeException("NOT IMPLEMENTED");
    }

}