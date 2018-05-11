package com.curioustake.sftm.activity;

import com.curioustake.sftm.datastructure.RQueue;
import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

public class P27_QueueUsingArrays implements Activity {

    enum OPERATIONS {ADD, OFFER, REMOVE, POLL, ELEMENT, PEEK}

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

        RQueue treatment = new RQueue();

        Queue<Integer> control = new ArrayDeque<>();

        Random random = new Random();

        for(int i=0; i<original.length;) {
            OPERATIONS operation = control.peek() == null ? OPERATIONS.ADD : OPERATIONS.values()[random.nextInt(OPERATIONS.PEEK.ordinal()  + 1)];

            switch (operation) {
                case REMOVE:
                    if(!control.remove().equals(treatment.remove()))
                        throwException(operation.name(), control, treatment);
                    break;
                case POLL:
                    if(!control.poll().equals(treatment.poll()))
                        throwException(operation.name(), control, treatment);
                    break;
                case ELEMENT:
                    if(!control.element().equals(treatment.element()))
                        throwException(operation.name(), control, treatment);
                    break;
                case PEEK:
                    if(!control.peek().equals(treatment.peek()))
                        throwException(operation.name(), control, treatment);
                    break;
                case ADD:
                    control.add(original[i]);
                    treatment.add(original[i]);
                    i++;
                    break;
                case OFFER:
                    control.offer(original[i]);
                    treatment.offer(original[i]);
                    i++;
                    break;
                default:
                    throw new RuntimeException("Unknown operation for Queues" + operation);
            }

            int opCount = 1;

            if(operationCount.containsKey(operation))
                opCount = operationCount.get(operation) + 1;

            operationCount.put(operation, opCount);
        }

        System.out.println("\nValidated Queue implementation. Operations run....");

        operationCount.entrySet().stream().forEach(k -> System.out.println( k.getKey() + " | " + k.getValue()));
    }

    private void throwException(final String operation, Queue control, RQueue treatment) {
        System.out.println("\nFAILED OPERATION [" + operation + "]");
        System.out.println("CONTROL" + Arrays.toString(control.toArray()));
        System.out.println("TREATMENT " + treatment );

        throw new RuntimeException("Queue operation " + operation + " NOT WORKING ");
    }
}
