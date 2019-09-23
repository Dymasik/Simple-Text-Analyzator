package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        System.out.println("Please, write a path to a file: ");
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String filePath = in.readLine();
            AnalyzatorHelper helper = new AnalyzatorHelper(filePath);
            helper.ShowStatistics();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}
