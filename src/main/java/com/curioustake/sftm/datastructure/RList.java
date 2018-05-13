package com.curioustake.sftm.datastructure;


public class RList {

    Node sentinel;
    int nodeCount;

    public RList() {
        sentinel = new Node();
        nodeCount = 0;
    }

    public int size() {
        return nodeCount;
    }

    public boolean isEmpty() {
        return nodeCount == 0;
    }

    public boolean contains(Integer element) {
        Node current = sentinel.next_;

        while(!current.equals(sentinel)) {
            if(current.key_.equals(element))
                return true;

            current = current.next_;
        }

        return false;
    }

    public boolean add(Integer element) {
        Node newNode = new Node(sentinel, sentinel.previous_, element);

        sentinel.previous_ = newNode;
        newNode.previous_.next_ = newNode;

        nodeCount++;

        return true;
    }

    public boolean remove(Integer element) {
        Node current = sentinel.next_;

        while(!current.equals(sentinel)) {
            if(current.key_.equals(element)) {
                current.previous_.next_ = current.next_;
                current.next_.previous_ = current.previous_;
                nodeCount--;
                return true;
            }

            current = current.next_;
        }

        return false;
    }

    public Integer get(int index) {
        if(index > nodeCount-1)
            throw new RuntimeException("Index out of bound");

        Node current = sentinel.next_;
        for(int i=0; i<=index; i++) {
            if(i == index)
                return current.key_;

            current = current.next_;
        }
        return null;
    }

    public Integer set(int index, Integer element) {
        if(index > nodeCount-1)
            throw new RuntimeException("Index out of bound");

        Node current = sentinel.next_;
        for(int i=0; i<=index; i++) {
            if(i == index) {
                Integer temp = current.key_;
                current.key_ = element;
                return temp;
            }
            current = current.next_;
        }
        return null;
    }

    public Integer[] toArray() {
        Integer[] arrayRepresentation = new Integer[nodeCount];

        Node current = sentinel.next_;

        for(int i=0; i<nodeCount; i++) {
            arrayRepresentation[i] = current.key_;
            current = current.next_;
        }

        return arrayRepresentation;
    }

    class Node {
        Node next_;
        Node previous_;
        Integer key_;

        Node() {
            this.next_ = this;
            this.previous_ = this;
            this.key_ = 0;
        }

        Node(Node next, Node previous, Integer key) {
            this.next_ = next;
            this.previous_ = previous;
            this.key_ = key;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "next_=" + next_ +
                    ", previous_=" + previous_ +
                    ", key_=" + key_ +
                    '}';
        }
    }
}


