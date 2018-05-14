package com.curioustake.sftm.datastructure;

public class RSinglyLinkedList {

    Node sentinel;
    int nodeCount;

    public RSinglyLinkedList() {
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
        Node newNode = new Node(sentinel, element);

        Node current = sentinel.next_;

        while(!current.next_.equals(sentinel)) {
            current = current.next_;
        }

        current.next_ = newNode;

        nodeCount++;

        return true;
    }

    public boolean remove(Integer element) {
        Node current = sentinel.next_;
        Node previous = sentinel;

        while(!current.equals(sentinel)) {
            if(current.key_.equals(element)) {
                previous.next_ = current.next_;
                nodeCount--;
                return true;
            }

            previous = current;
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
        Integer key_;

        Node() {
            this.next_ = this;
            this.key_ = 0;
        }

        Node(Node next, Integer key) {
            this.next_ = next;
            this.key_ = key;
        }
    }
}