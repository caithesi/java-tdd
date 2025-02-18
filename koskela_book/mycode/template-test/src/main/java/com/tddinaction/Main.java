package com.tddinaction;

public class Main {
    public static void main(String[] args) {
        test(new int[]{1, 2, 3});
    }

    private static void test(int... x) {
        for (var i : x) {
            System.out.println(i);
        }
    }
}