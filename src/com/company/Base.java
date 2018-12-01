package com.company;

public class Base {
    protected String stringValue;
    protected int priority;


    public String getStringValue() {
        return this.stringValue;
    };

    public int getPriority() {
        return this.priority;
    }

    public void print() {
        System.out.print(this.stringValue + " ");
    }
}
