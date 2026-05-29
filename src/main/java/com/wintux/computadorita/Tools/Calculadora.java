package com.wintux.computadorita.Tools;

import java.util.Stack;

public class Calculadora {

    public static String convertirApostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> pila = new Stack<>();

        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                postfix.append(c);
            } else if (c == '(') {
                pila.push(c);
            } else if (c == ')') {
                while (!pila.isEmpty() && pila.peek() != '(') {
                    postfix.append(pila.pop());
                }
                pila.pop();
            } else {
                while (!pila.isEmpty() && prioridad(c) <= prioridad(pila.peek())) {
                    postfix.append(pila.pop());
                }
                pila.push(c);
            }
        }
        while (!pila.isEmpty()) {
            postfix.append(pila.pop());
        }
        return postfix.toString();
    }

    public static String resolverExpresionPostfix(String postfix) {
        Stack<Double> pila = new Stack<>();
        for (int i = 0; i < postfix.length(); i++) {
            char c = postfix.charAt(i);
            if (Character.isDigit(c)) {
                pila.push((double) (c - '0'));
            } else {
                double b = pila.pop();
                double a = pila.pop();
                switch (c) {
                    case '+': pila.push(a + b); break;
                    case '-': pila.push(a - b); break;
                    case '*': pila.push(a * b); break;
                    case '/':
                        if (b != 0) pila.push(a / b);
                        else return "Error: División por cero";
                        break;
                    default: return "Error: Operador inválido";
                }
            }
        }
        double resultado = pila.pop();
        if (resultado == (int) resultado) return String.valueOf((int) resultado);
        return String.valueOf(resultado);
    }

    private static int prioridad(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        return -1;
    }
}