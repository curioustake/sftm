package com.curioustake.sftm.activity;

import com.curioustake.sftm.datastructure.RListUsingMultipleArrays;
import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class P32_ListUsingMultipleArrays implements Activity {

    enum OPERATIONS {SIZE, IS_EMPTY, CONTAINS, ADD, REMOVE, GET, SET}

    Map<OPERATIONS, Integer> operationCount = new HashMap();

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

        List<Integer> control = new LinkedList<>();

        RListUsingMultipleArrays treatment = new RListUsingMultipleArrays();

        Random random = new Random();

        for(int i=0; i<original.length;) {
            OPERATIONS operation = control.isEmpty() ? OPERATIONS.ADD : OPERATIONS.values()[random.nextInt(OPERATIONS.SET.ordinal()  + 1)];

            switch (operation) {
                case SIZE:
                    if(control.size() != (treatment.size()))
                        throwException(operation.name(), control, treatment);
                    break;
                case IS_EMPTY:
                    if(control.isEmpty() != treatment.isEmpty())
                        throwException(operation.name(), control, treatment);
                    break;
                case CONTAINS:
                    if(control.contains(original[i]) != treatment.contains(original[i]))
                        throwException(operation.name(), control, treatment);
                    break;
                case ADD:
                    control.add(original[i]);
                    treatment.add(original[i]);
                    i++;
                    break;
                case REMOVE:
                    if(control.remove(original[i]) != treatment.remove(original[i]))
                        throwException(operation.name(), control, treatment);
                    break;
                case GET:
                    int getIndex = random.nextInt(control.size());
                    if(!control.get(getIndex).equals(treatment.get(getIndex)))
                        throwException(operation.name(), control, treatment);
                    break;
                case SET:
                    int setIndex = random.nextInt(control.size());
                    if(!control.set(setIndex, original[i]).equals(treatment.set(setIndex, original[i])))
                        throwException(operation.name(), control, treatment);
                    break;
                default:
                    throw new RuntimeException("Unknown operation for Lists" + operation);
            }

            int opCount = 1;

            if(operationCount.containsKey(operation))
                opCount = operationCount.get(operation) + 1;

            operationCount.put(operation, opCount);
        }

        System.out.println("\nValidated List implementation. Operations run....");

        operationCount.entrySet().stream().forEach(k -> System.out.println( k.getKey() + " | " + k.getValue()));
    }

    private void throwException(final String operation, List<Integer> control, RListUsingMultipleArrays treatment) {
        System.out.println("\nFAILED OPERATION [" + operation + "]");
        System.out.println("CONTROL" + Arrays.toString(control.toArray()));
        System.out.println("TREATMENT " + Arrays.toString(treatment.toArray()) );

        throw new RuntimeException("List operation " + operation + " not working ");
    }
}