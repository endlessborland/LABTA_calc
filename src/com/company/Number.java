package com.company;

public class Number extends Base {

    public Number(String number)
    {
        this.number = Double.parseDouble(number);
        this.stringValue = number;
        this.priority = -1;
    }

    public Number(double number)
    {
        this.number = number;
        this.priority = -1;
        this.stringValue = String.valueOf(number);
    }

    public double getNumber()
    {
        return this.number;
    }

    private Double number;

}
