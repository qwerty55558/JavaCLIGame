package myUtil;

import java.util.Scanner;

public class MyScanner {
    private static final Scanner INSTANCE = new Scanner(System.in);

    private MyScanner(){}

    public static Scanner getInstance(){
        return INSTANCE;
    }
}
