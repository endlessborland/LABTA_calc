package com.company;


public class NOBuilder {
    public NOBuilder() {};

    public Base construct(String in) {
        if (in.matches("[^0-9]") || in.matches("sin"))
            return new Operation(in);
        return new Number(in);
    }
}
