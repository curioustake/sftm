package com.curioustake.sftm.activity;

import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class P37_MatrixParenthesization implements Activity {

    public void invoke(String[] args) {
        System.out.println( "Execute => " + Arrays.toString(args) );

        final int count = Integer.parseInt(args[1]);
        final int size = Integer.parseInt(args[2]);
        final boolean printResult = Boolean.parseBoolean(args[3]);

        if(count <= 1) {
            System.out.println("INPUT SIZE TO SMALL ");
            return;
        }

        Integer[] dimensionsList = RandomDataGenerator.getRandomIntegerArray(count, 1, size, printResult);

        Matrix []input = new Matrix[dimensionsList.length-1];
        for(int i=0; i<dimensionsList.length-1; i++) {
            Matrix m = new Matrix();
            m.row_ = dimensionsList[i];
            m.column_ = dimensionsList[i+1];
            input[i] = m;
        }

//        Arrays.stream(input).forEach(i -> System.out.println(i));

//        MatrixChain bruteForce = parenthesizeBruteForce(input, 0, input.length-1);
//        System.out.println("\nBruteForce Optimal Solution");
//        System.out.println(bruteForce);

        MatrixChain dpMemoized = parenthesizeDPMemoized(input, 0, input.length-1, new HashMap<>());
        System.out.println("\nDPMemoized Optimal Solution");
        System.out.println(dpMemoized);

        MatrixChain dpBottomUp = parenthesizeDPBottomUp(input);
        System.out.println("\nDPBottomUp Optimal Solution");
        System.out.println(dpBottomUp);

        if(!dpBottomUp.expression_.equals(dpMemoized.expression_) ||
                dpBottomUp.cost_ != dpMemoized.cost_)
            throw new RuntimeException("Something got screwed in the DP implementation");

    }

    private MatrixChain parenthesizeBruteForce(final Matrix[] input, final int start, final int end) {
        MatrixChain optimalMC = null;

        if(start == end) {
            optimalMC = new MatrixChain();
            optimalMC.result_ = input[start];
            optimalMC.expression_ = input[start].toString();
            optimalMC.cost_ = 0;
        }

        for(int i=start; i<end; i++) {
            MatrixChain prefix = parenthesizeBruteForce(input, start, i);
            MatrixChain suffix = parenthesizeBruteForce(input, i+1, end);

            MatrixChain mc = new MatrixChain();

            Matrix result = new Matrix();
            result.row_ = prefix.result_.row_;
            result.column_ = suffix.result_.column_;
            mc.result_ =  result;

            mc.expression_ = "(" + prefix.expression_ + " * " + suffix.expression_ + ")";

            mc.cost_ = prefix.cost_ + suffix.cost_ + (prefix.result_.row_ * suffix.result_.column_ * suffix.result_.row_);

            if(optimalMC == null || optimalMC.cost_ > mc.cost_)
                optimalMC = mc;
        }

        return optimalMC;
    }

    private MatrixChain parenthesizeDPMemoized(final Matrix[] input, final int start, final int end, final Map<String, MatrixChain> optimizedChains) {
        MatrixChain optimalMC = null;

        if(start == end) {
            optimalMC = new MatrixChain();
            optimalMC.result_ = input[start];
            optimalMC.expression_ = input[start].toString();
            optimalMC.cost_ = 0;
        }

        for(int i=start; i<end; i++) {
            final String prefixKey = start + "-" + i;
            if(!optimizedChains.containsKey(prefixKey))
                optimizedChains.put(prefixKey, parenthesizeDPMemoized(input, start, i, optimizedChains));
            MatrixChain prefix = optimizedChains.get(prefixKey);

            final String suffixKey = (i+1) + "-" + end;
            if(!optimizedChains.containsKey(suffixKey))
                optimizedChains.put(suffixKey, parenthesizeDPMemoized(input, i+1, end, optimizedChains));
            MatrixChain suffix = optimizedChains.get(suffixKey);

            MatrixChain mc = new MatrixChain();

            Matrix result = new Matrix();
            result.row_ = prefix.result_.row_;
            result.column_ = suffix.result_.column_;
            mc.result_ =  result;

            mc.expression_ = "(" + prefix.expression_ + " * " + suffix.expression_ + ")";

            mc.cost_ = prefix.cost_ + suffix.cost_ + (prefix.result_.row_ * suffix.result_.column_ * suffix.result_.row_);

            if(optimalMC == null || optimalMC.cost_ > mc.cost_)
                optimalMC = mc;
        }

        final String chainKey = start + "-" + end;
        optimizedChains.put(chainKey, optimalMC);
        return optimalMC;
    }

    private MatrixChain parenthesizeDPBottomUp(final Matrix[] input) {
        final Map<String, MatrixChain> optimizedChains = new HashMap<>();

        for(int size=0; size<input.length; size++) {
            for(int start=0; start<input.length; start++) {
                int end = start + size;
                if(end > input.length-1)
                    break;

                MatrixChain optimalMC = null;

                if (start == end) {
                    optimalMC = new MatrixChain();
                    optimalMC.result_ = input[start];
                    optimalMC.expression_ = input[start].toString();
                    optimalMC.cost_ = 0;
                }

                for (int i = start; i < end; i++) {
                    final String prefixKey = start + "-" + i;
                    MatrixChain prefix = optimizedChains.get(prefixKey);

                    final String suffixKey = (i + 1) + "-" + end;
                    MatrixChain suffix = optimizedChains.get(suffixKey);

                    MatrixChain mc = new MatrixChain();

                    Matrix result = new Matrix();
                    result.row_ = prefix.result_.row_;
                    result.column_ = suffix.result_.column_;
                    mc.result_ = result;

                    mc.expression_ = "(" + prefix.expression_ + " * " + suffix.expression_ + ")";

                    mc.cost_ = prefix.cost_ + suffix.cost_ + (prefix.result_.row_ * suffix.result_.column_ * suffix.result_.row_);

                    if (optimalMC == null || optimalMC.cost_ > mc.cost_)
                        optimalMC = mc;
                }

                final String chainKey = start + "-" + end;
                optimizedChains.put(chainKey, optimalMC);
            }
        }
        return optimizedChains.get(0 +"-"+ (input.length-1));
    }

    private class Matrix {
        int row_;
        int column_;

        @Override
        public String toString() {
            return "["+ row_ + " x " + column_ + "]";
        }
    }

    private class MatrixChain {
        Matrix result_;
        String expression_;
        int cost_;

        @Override
        public String toString() {
            return "MatrixChain { " +
                    "\n\tresult = " + result_ +
                    "\n\texpression = " + expression_ +
                    "\n\tcost = " + cost_ +
                    "\n}";
        }
    }
}
