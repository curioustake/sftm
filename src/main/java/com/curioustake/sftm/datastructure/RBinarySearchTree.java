package com.curioustake.sftm.datastructure;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class RBinarySearchTree <K extends Comparable<K>, V> {

    Node root;
    int size;
    Node sentinel;

    public <K, V> RBinarySearchTree() {
        root = null;
        size = 0;
        sentinel = new Node();
    }

    public V put(K key, V value) {
        Node newNode = new Node(key, value);

        V previous = null;

        if(root == null) {
            root = newNode;
        } else {
            previous = put(root, newNode);
        }

        size++;
        return previous;
    }

    private V put(Node subTreeRoot, Node newNode) {
        V previous = null;

        if(subTreeRoot.key_.compareTo(newNode.key_) == 0) {
            previous = subTreeRoot.value_;
            subTreeRoot.value_ = newNode.value_;
        } else if (subTreeRoot.key_.compareTo(newNode.key_) > 0) {
            if(subTreeRoot.left_ == sentinel) {
                newNode.parent_ = subTreeRoot;
                subTreeRoot.left_ = newNode;
            }
            else
                return put(subTreeRoot.left_, newNode);
        } else {
            if(subTreeRoot.right_ == sentinel) {
                newNode.parent_ = subTreeRoot;
                subTreeRoot.right_ = newNode;
            }
            else
                return put(subTreeRoot.right_, newNode);
        }

        return previous;
    }

    public V get(K key) {
        if(isEmpty())
            return null;

        Node node = search(root, key);
        return node == null ? null : node.value_;
    }

    public V remove(K key) {
        if(isEmpty())
            return null;

        Node node = search(root, key);

        if(node == null)
            return null;

        if(node.right_ == sentinel || node.left_ == sentinel) {
            return removeLeafOrSingleChildNode(node);
        }

        Node successor = higherNode(node);

        if(successor == null) {
            throw new RuntimeException("Code cannot be reached");
        }

        V temp = node.value_;

        node.key_ = successor.key_;
        node.value_ = successor.value_;

        removeLeafOrSingleChildNode(successor);

        return temp;
    }

    private V removeLeafOrSingleChildNode(Node node) {
        Node child = sentinel;

        if(node.right_ != sentinel)
            child = node.right_;
        else if(node.left_ != sentinel)
            child = node.left_;

        if(node != root) {
            if(node.parent_.right_ == node)
                node.parent_.right_ = child;
            else
                node.parent_.left_ = child;

            if(child != sentinel)
                child.parent_ = node.parent_;
        } else {
            root = child == sentinel ? null : child;
        }

        size--;
        return node.value_;
    }

    private Node search(Node subTreeRoot, K key) {
        if(subTreeRoot == sentinel)
            return null;

        if(subTreeRoot.key_.compareTo(key) == 0) {
            return subTreeRoot;
        } else if(subTreeRoot.key_.compareTo(key) > 0) {
            return search(subTreeRoot.left_, key);
        } else {
            return search(subTreeRoot.right_, key);
        }
    }

    public K higherKey(K key) {
        Node node = search(root, key);
        return higherNode(node) == null ? null : higherNode(node).key_;
    }

    private Node higherNode(Node node) {
        if(node == null)
            return null;

        if (node.right_ != sentinel) {
            return firstNode(node.right_);
        }

        Node parent = node.parent_;

        while (parent != null && parent.right_ == node) {
            node = parent;
            parent = parent.parent_;
        }

        return parent;
    }

    private Node firstNode(Node node) {
        while(node.left_ != sentinel) {
            node = node.left_;
        }

        return node;
    }

    public K lowerKey(K key) {
        Node node = search(root, key);
        return lowerNode(node) == null ? null : lowerNode(node).key_;
    }

    private Node lowerNode(Node node) {
        if(node == null)
            return null;

        if(node.left_ != sentinel) {
            return lastNode(node.left_);
        }

        Node parent = node.parent_;

        while(parent != null && parent.left_ == node){
            node = parent;
            parent = parent.parent_;
        }

        return parent;
    }

    private Node lastNode(Node node) {
        while(node.right_ != sentinel) {
            node = node.right_;
        }

        return node;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public Set<K> keySet() {
        return new LinkedHashSet<>(keySet(root));
    }

    private List<K> keySet(Node node) {
        List<K> keys = new LinkedList<>();

        if(node == null && node != sentinel)
            return keys;

        if(node.left_ != sentinel)
            keys.addAll(keySet(node.left_));

        keys.add(node.key_);

        if(node.right_ != sentinel)
            keys.addAll(keySet(node.right_));

        return keys;
    }

    public boolean isEmpty() {
        return size <= 0;
    }

    class Node {
        Node parent_;
        Node left_;
        Node right_;

        K key_;
        V value_;

        Node(){
            parent_ = null;
            left_ = null;
            right_ = null;
            key_ = null;
            value_ = null;
        }

        Node(K key, V value) {
            parent_ = null;
            left_ = sentinel;
            right_ = sentinel;
            key_ = key;
            value_ = value;
        }
    }
}
