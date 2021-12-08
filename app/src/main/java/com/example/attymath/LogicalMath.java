package com.example.attymath;

import java.util.Random;

public class LogicalMath {
    private int generatedNum1;
    private int generatedNum2;
    private int maxNumber = 200;
    private int maxNumberMultiplication = 12;
    private int maxNumberDivision = 200;
    private char mathOperator;

    public LogicalMath(char mathOperator) {
        this.mathOperator = mathOperator;
    }

    public float doMath() {
        Random rand = new Random();
        if (mathOperator == 'x') {
            maxNumber = maxNumberMultiplication;
            generatedNum1 = rand.nextInt(maxNumber);
            generatedNum2 = rand.nextInt(maxNumber);
        } else if (mathOperator == '/') {
            maxNumber = maxNumberDivision;
            generatedNum1 = rand.nextInt(maxNumber);
            generatedNum2 = rand.nextInt(maxNumber);
        } else {
            generatedNum1 = rand.nextInt(maxNumber);
            generatedNum2 = rand.nextInt(maxNumber);
        }


        float calculatedAnswer = -1;

        switch (mathOperator) {
            case '+':
                calculatedAnswer = generatedNum1 + generatedNum2;
                break;
            case '-':
                checkNumbers();
                calculatedAnswer = generatedNum1 - generatedNum2;
                break;
            case 'x':
                calculatedAnswer = generatedNum1 * generatedNum2;
                break;
            case '/':
                while (generatedNum2 == 0) {
                    generatedNum2 = rand.nextInt(maxNumber);
                }
                int dividend = Math.max(generatedNum1, generatedNum2);
                int divisor = Math.min(generatedNum1, generatedNum2);
                generatedNum1 = (dividend - (dividend % divisor));
                generatedNum2 = divisor;

                calculatedAnswer = generatedNum1 / generatedNum2;

        }
        return calculatedAnswer;
    }

    private void checkNumbers() {
        if (generatedNum1 < generatedNum2) {
            int hold = generatedNum1;
            generatedNum1 = generatedNum2;
            generatedNum2 = hold;
        }
    }

    public int[] getNumbers() {
        return new int[]{generatedNum1, generatedNum2};
    }
}
