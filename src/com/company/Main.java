package com.company;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        // I STRONGLY DISLIKE CONSOLE IN JAVA
        String input = "";
        try {
            InputStreamReader streamReader = new InputStreamReader(System.in);
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            input = bufferedReader.readLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        // IM TIRED OF WRITING PARSERS
        String modinput = "";
        if (input.startsWith("-")) {
            modinput = "0" + input;
        }
        else {
            if (input.startsWith("(")) {
                modinput = "0+" + input;
            }
            else
                modinput = input;
        }
        modinput = modinput.trim();
        modinput = modinput.replace("(-", "(0-");
        List<String> symbols = new ArrayList<String>();
        // THERE REALLY MIGHT BE AN EASIER WAY 2 DO THIS


        String regex = "(?<=[^0-9.])(?=[0-9.])|(?<=[0-9.])(?=[^0-9.])|" +
                "(?<=\\()(?=\\D)|(?<=\\))(?=\\D)|(?<=\\D)(?=\\()|(?<=\\D)(?=\\))|" +
                "(?<=sin)(?=[0-9.])|(?<=[^0-9.])(?=sin)|" +
                "(?<=!)(?=[^0-9.])|(?<=[0-9.])(?=!)";

//        String regex = "(?<=-)(?=[01])" +
//                "(?<=\\=>)(?=[01])|(?<=[01])(?=\\=>)" +
//                "(?<=\\+\\+)(?=[01])|(?<=[01])(?=\\+\\+)" +
//                "(?<=[^01])(?=[01])|(?<=[01])(?=[^01])";
        for (String a : modinput.split(regex))
            symbols.add(a);
        // IM FINISHED WITH PARSING. NO FOOLPROOF, SRY
        List<Base> parsedSymbols = new ArrayList<>();
        NOBuilder noBuilder = new NOBuilder();
        for (String a : symbols)
            parsedSymbols.add(noBuilder.construct(a));
        // THE ALGORITHM ITSELF. NO FOOLPROOF, SRY
        List<Base> resultString = new ArrayList<>();
        Stack<Base> stack = new Stack<>();
        for (Base a : parsedSymbols)
        {
            // for NUMBER and postfix operation
            if (a instanceof Number || (a instanceof Operation && ((Operation) a).isPostfix())) {
                resultString.add(a);
                continue;
            }
            // for prefix unary operation
            if (a instanceof Operation && ((Operation) a).isPrefix())
            {
                stack.push(a);
                continue;
            }
            // opening bracket
            if (a.getStringValue().equals("(")) {
                stack.push(a);
                continue;
            }
            if (a.getStringValue().equals(")")) {
                Base c = stack.pop();
                while (!c.getStringValue().equals("(")) {
                    resultString.add(c);
                    c = stack.pop();
                }
                continue;
            }
            if (a instanceof Operation && !((Operation) a).isUn())
            {
                if (stack.isEmpty()) {
                    stack.push(a);
                    continue;
                }
                Base peek = stack.peek();
                while (peek instanceof Operation && ( // is an Operation AND
                        (((Operation) peek).IsR() && (((Operation) peek).getPriority() > a.getPriority())) || // is R AND next priority > this priority OR
                            (!((Operation) peek).IsR() && (((Operation) peek).getPriority() >= a.getPriority())) || // is NOT R AND next prior >= this prior OR
                                (((Operation) peek).isPrefix())
                ))
                {
                    resultString.add(stack.pop());
                    if (stack.isEmpty())
                        break;
                    peek = stack.peek();
                }
                stack.push(a);
                continue;
            }
        }
        while (!stack.isEmpty())
            resultString.add(stack.pop());
        // YEP, THAT IS IT
        System.out.println("Reverse Polish Notation: ");
        for (Base a : resultString)
            a.print();
        Stack<Base> outputStack = new Stack<>();
        for (int i = 0; i < resultString.size(); i++)
        {
            if (resultString.get(i) instanceof Number)
            {
                outputStack.push(resultString.get(i));
                continue;
            }
            if (((Operation) resultString.get(i)).isUn())
            {
                double x = ((Number) outputStack.pop()).getNumber();
                outputStack.push(new Number(((Operation) resultString.get(i)).Calculate(x)));
            } else {
                double y = ((Number) outputStack.pop()).getNumber();
                double x = ((Number) outputStack.pop()).getNumber();
                outputStack.push(new Number(((Operation) resultString.get(i)).Calculate(x, y)));
            }
        }
        System.out.println();
        System.out.println("Result is " + outputStack.pop().getStringValue());
    }
}
