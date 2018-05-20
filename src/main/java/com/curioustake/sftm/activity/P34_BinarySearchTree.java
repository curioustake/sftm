package com.curioustake.sftm.activity;

import com.curioustake.sftm.datastructure.RBinarySearchTree;
import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class P34_BinarySearchTree implements Activity {
    enum OPERATIONS {PUT, GET, REMOVE, SUCCESSOR, PREDECESSOR, CONTAINS_KEY, KEY_SET, IS_EMPTY}

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

        TreeMap<Integer, Integer> control = new TreeMap<>();

        RBinarySearchTree<Integer, Integer> treatment = new RBinarySearchTree<>();

        Random random = new Random();

        for(int i=0; i<original.length;i++) {
            OPERATIONS operation = control.isEmpty() ? OPERATIONS.PUT : OPERATIONS.values()[random.nextInt(OPERATIONS.IS_EMPTY.ordinal()  + 1)];
            int inputValue = original[i];

//            System.out.println(operation + "|" + inputValue);

            switch (operation) {
                case PUT:
                    Integer putControl = control.put(inputValue, inputValue);
                    Integer putTreatment = treatment.put(inputValue, inputValue);

                    if((putControl != null && putTreatment != null) && !putControl.equals(putTreatment))
                        throwException(operation.name(), control, treatment);
//                    i++;
                    break;
                case GET:
                    if((control.get(inputValue) != null && treatment.get(inputValue) != null) &&
                            (!control.get(inputValue).equals(treatment.get(inputValue))))
                        throwException(operation.name(), control, treatment);
                    break;
                case REMOVE:
                    Integer removeControl = control.remove(inputValue);
                    Integer removeTreatment = treatment.remove(inputValue);

                    if((removeControl != null && removeTreatment != null) && !removeControl.equals(removeTreatment))
                        throwException(operation.name(), control, treatment);
                    break;
                case SUCCESSOR:
                    if((control.higherKey(inputValue) != null && treatment.higherKey(inputValue) != null) &&
                            (!control.higherKey(inputValue).equals(treatment.higherKey(inputValue))))
                        throwException(operation.name(), control, treatment);
                    break;
                case PREDECESSOR:
                    if((control.lowerKey(inputValue) != null && treatment.lowerKey(inputValue) != null) &&
                            (!control.lowerKey(inputValue).equals(treatment.lowerKey(inputValue))))
                        throwException(operation.name(), control, treatment);
                    break;
                case CONTAINS_KEY:
                    if(control.containsKey(inputValue) != treatment.containsKey(inputValue))
                        throwException(operation.name(), control, treatment);
                    break;
                case KEY_SET:
                    if(!Arrays.equals(control.keySet().toArray(), treatment.keySet().toArray()))
                        throwException(operation.name(), control, treatment);
                    break;
                case IS_EMPTY:
                    if(control.isEmpty() != treatment.isEmpty())
                        throwException(operation.name(), control, treatment);
                    break;
                default:
                    throw new RuntimeException("Unknown operation for BinarySearchTrees" + operation);
            }

            if(!Arrays.equals(control.keySet().toArray(), treatment.keySet().toArray()))
                throwException(operation.name(), control, treatment);

            int opCount = 1;

            if(operationCount.containsKey(operation))
                opCount = operationCount.get(operation) + 1;

            operationCount.put(operation, opCount);
        }

        System.out.println("\nValidated BinarySearchTree implementation. Operations run....");

        operationCount.entrySet().stream().forEach(k -> System.out.println( k.getKey() + " | " + k.getValue()));
    }

    private void throwException(final String operation, Map<Integer, Integer> control, RBinarySearchTree<Integer, Integer> treatment) {
        System.out.println("\nFAILED OPERATION [" + operation + "]");
        System.out.println("CONTROL" + Arrays.toString(control.keySet().toArray()));
        System.out.println("TREATMENT " + Arrays.toString(treatment.keySet().toArray()) );

        throw new RuntimeException("BinarySearchTree operation " + operation + " not working ");
    }
}