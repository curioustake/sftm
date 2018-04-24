package com.curioustake.sftm.activity;

import com.curioustake.sftm.utils.DataValidator;
import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.Arrays;

public class P15_HeapSortRecursive implements Activity {

    enum SORT_ORDER { ASCENDING, DESCENDING}
    
    public void invoke(String[] args) {
        System.out.println( "Execute => " + Arrays.toString(args) );

        final int count = Integer.parseInt(args[1]);
        final int max = Integer.parseInt(args[2]);
        final boolean printResults = Boolean.parseBoolean(args[3]);

        Integer[] original = RandomDataGenerator.getRandomIntegerArray(count, max, printResults);

        if(count <= 1) {
            System.out.println("INPUT SIZE TO SMALL ");
            return;
        }

        // HEAP SORT ASCENDING
        Integer[] inputAsc = original.clone();
        sort(inputAsc, SORT_ORDER.ASCENDING);
        System.out.println("\n###################### HEAP SORT validateSortAscending ##############################");
        System.out.println("\nHEAP SORT ASCENDING SUCCESSFUL? [" + DataValidator.validateSortAscending(original, inputAsc, printResults) + "]\n");
        System.out.println("############################################################################################\n");

        // HEAP SORT DESCENDING
        Integer[] inputDesc = original.clone();
        sort(inputDesc, SORT_ORDER.DESCENDING);
        System.out.println("\n###################### HEAP SORT validateSortDescending #############################");
        System.out.println("\nHEAP SORT DESCENDING SUCCESSFUL? [" + DataValidator.validateSortDescending(original, inputDesc, printResults) + "]\n");
        System.out.println("############################################################################################\n");

    }

    private void sort(Integer[] input, SORT_ORDER sortOrder) {
        buildHeap(input, input.length-1, sortOrder);
        swap(input, 0, input.length-1);

        for(int i=input.length-2; i>0; i--) {
            Double lastParentNode = Math.floor(((i) - 1)/2);
            heapifyNode(input, 0, lastParentNode.intValue(), i, sortOrder);
            swap(input, 0, i);
        }
    }

    private void buildHeap(Integer[] input, int size, SORT_ORDER sortOrder) {
        Double lastParentNode = Math.floor((size - 1)/2);

        for(int i=lastParentNode.intValue(); i>=0; i--) {
            heapifyNode(input, i, lastParentNode.intValue(), size, sortOrder);
        }
    }

    private void heapifyNode(final Integer[] input, final int node, final int lastParentNode, final int heapSize, final SORT_ORDER sortOrder) {
        if(node > lastParentNode)
            return;

        int leftChild = ((node*2) + 1) > heapSize ? - 1 : ((node*2) + 1);

        int rightChild = (leftChild + 1) > heapSize ? - 1 : (leftChild + 1);

        int maxNode = node;

        if(rightChild > -1 && swap(input[leftChild], input[rightChild], sortOrder)  && swap(input[node], input[rightChild], sortOrder) ) {
            maxNode = rightChild;
        } else if(swap(input[node], input[leftChild], sortOrder)) {
            maxNode = leftChild;
        }

        if(maxNode != node) {
            swap(input, maxNode, node);
            heapifyNode(input, maxNode, lastParentNode, heapSize, sortOrder);
        }
    }

    private void swap(final Integer[] input, final int first, final int second) {
        int temp = input[second];
        input[second] = input[first];
        input[first] = temp;
    }

    private boolean swap(final Integer first, final Integer second, SORT_ORDER sortOrder) {
        switch (sortOrder) {
            case ASCENDING:
                return (first < second);
            case DESCENDING:
                return (first > second);
            default:
                throw new RuntimeException("Invalid sort Order");
        }
    }
}
