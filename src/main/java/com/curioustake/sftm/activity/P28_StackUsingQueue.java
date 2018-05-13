package com.curioustake.sftm.activity;

import com.curioustake.sftm.datastructure.RStackUsingQueue;
import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

public class P28_StackUsingQueue extends Stack<Integer> implements Activity {

    enum OPERATIONS {PUSH, POP, PEEK, EMPTY}

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

        RStackUsingQueue treatment = new RStackUsingQueue();

        Stack<Integer> control = new Stack<>();

        Random random = new Random();

        for(int i=0; i<original.length;) {
            OPERATIONS operation = treatment.empty() ?
                    OPERATIONS.PUSH : OPERATIONS.values()[random.nextInt(OPERATIONS.EMPTY.ordinal()  + 1)];

            switch (operation) {
                case POP:
                    if(!control.pop().equals(treatment.pop()))
                        throwException(operation.name(), control, treatment);
                    break;
                case PEEK:
                    if(!control.peek().equals(treatment.peek()))
                        throwException(operation.name(), control, treatment);
                    break;
                case EMPTY:
                    if(control.empty() != treatment.empty())
                        throwException(operation.name(), control, treatment);
                    break;
//                case SEARCH:
//                    if(control.search(original[i]) != treatment.search(original[i]))
//                        throwException(operation.name(), control, treatment);
//                    break;
                case PUSH:
                    control.push(original[i]);
                    treatment.push(original[i]);
                    i++;
                    break;
                default:
                    throw new RuntimeException("Unknown operation for Stacks" + operation);
            }

            int opCount = 1;

            if(operationCount.containsKey(operation))
                opCount = operationCount.get(operation) + 1;

            operationCount.put(operation, opCount);
        }

        System.out.println("\nValidated Stack implementation. Operations run....");

        operationCount.entrySet().stream().forEach(k -> System.out.println( k.getKey() + " | " + k.getValue()));
    }

    private void throwException(final String operation, Stack control, RStackUsingQueue treatment) {
        System.out.println("\nFAILED OPERATION [" + operation + "]");
        System.out.println("CONTROL" + Arrays.toString(control.toArray()));
        System.out.println("TREATMENT " + treatment );

        throw new RuntimeException("Stack operation " + operation + " NOT WORKING ");
    }
}
