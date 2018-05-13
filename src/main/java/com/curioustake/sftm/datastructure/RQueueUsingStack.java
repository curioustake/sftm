package com.curioustake.sftm.datastructure;

public class RQueueUsingStack {

    RStack pushStack;
    RStack popStack;

    public RQueueUsingStack() {
        pushStack = new RStack();
        popStack = new RStack();
    }

    public boolean add(Integer input) {
        pushStack.push(input);
        return true;
    }

    public boolean offer(Integer input) {
        pushStack.push(input);
        return true;
    }

    public Integer remove() {
        if(popStack.empty() && pushStack.empty())
            return null;

        if(popStack.empty()) {
            while(!pushStack.empty())
                popStack.push(pushStack.pop());
        }

        return popStack.pop();
    }

    public Integer poll() {
        if(popStack.empty() && pushStack.empty())
            return null;

        if(popStack.empty()) {
            while(!pushStack.empty())
                popStack.push(pushStack.pop());
        }

        return popStack.pop();
    }

    public Integer element() {
        if(popStack.empty() && pushStack.empty())
            return null;

        if(popStack.empty()) {
            while(!pushStack.empty())
                popStack.push(pushStack.pop());
        }

        return popStack.peek();
    }

    public Integer peek() {
        if(popStack.empty() && pushStack.empty())
            return null;

        if(popStack.empty()) {
            while(!pushStack.empty())
                popStack.push(pushStack.pop());
        }

        return popStack.peek();
    }
}