package com.curioustake.sftm.activity;

import com.curioustake.sftm.datastructure.RHeap;
import com.curioustake.sftm.utils.DataValidator;
import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.Arrays;


public class P17_HeapDatastructure implements Activity {

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

        RHeap heap = new RHeap(sortOrder.equals(SORT_ORDER.ASCENDING) ? RHeap.HEAP_TYPE.MIN : RHeap.HEAP_TYPE.MAX);

        Arrays.stream(input).forEach(i->{heap.put(i);});

        for(int i=0; i<input.length; i++) {
            input[i] = heap.remove();
        }
    }
}
