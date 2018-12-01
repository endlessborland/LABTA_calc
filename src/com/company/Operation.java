package com.company;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class Operation extends  Base{
    private String operation;

    private Calc func;

    private UCalc ufunc;

    private Boolean isR;

    private Boolean isUn;

    private Boolean isPostfix;

    private Boolean isPrefix;


    public Operation(String operation)
    {
        this.operation = operation;
        this.stringValue = operation;
        this.isR = false;
        this.isUn = false;
        this.isPostfix = false;
        this.isPrefix = false;
        switch (operation) {
            case "sin":
                this.priority = 5;
                this.ufunc = x -> Math.sin(x);
                this.isUn = true;
                this.isPrefix = true;
                break;
            case "!":
                this.priority = 5;
                this.ufunc = x -> this.factorial(x);
                this.isPostfix = true;
                this.isUn = true;
                break;
            case "*":
                this.priority = 3;
                this.func = (x, y) -> x * y;
                break;
            case "/":
                this.priority = 3;
                this.func = (x, y) -> x / y;
                break;
            case "+":
                this.func = (x, y) -> x + y;
                this.priority = 2;
                break;
            case "-":
                this.func = (x, y) -> x - y;
                this.priority = 2;
                break;
            case "(":
            case ")":
                this.priority = 1;
                break;
            case "^":
                this.priority = 4;
                this.func = (x, y) -> Math.pow(x, y);
                this.isR = true;
                break;
        }
    }

    public double Calculate(double x, double y)
    {
        return this.func.calculate(x,y);
    }

    public double Calculate(double x) {
        return this.ufunc.calculate(x);
    }

    public Boolean IsR()
    {
        return this.isR;
    }

    public Boolean isPostfix() {
        return isPostfix;
    }

    public Boolean isUn() {
        return isUn;
    }

    public boolean isPrefix() {
        return this.isPrefix;
    }

    private double factorial(double x)
    {
        double result = 1;
        for (int i = 1; i <= (int) x; i++)
        {
            result *= i;
        }
        return result;
    }

}
