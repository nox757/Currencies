package com.chibisovap.console;

import com.chibisovap.data.TypeCurrency;

import java.util.Arrays;
import java.util.Scanner;

public class Console implements Iinput {

    private final Scanner scanner = new Scanner(System.in);
    private TypeCurrency from = null;
    private TypeCurrency to = null;

    @Override
    public boolean getNewInput() {
        from = null;
        to = null;
        try {

            System.out.print("\nEnter from currency:\n");
            from = TypeCurrency.valueOf(scanner.next().toUpperCase().trim());
            System.out.print("Enter to currency:\n");
            to = TypeCurrency.valueOf(scanner.next().toUpperCase().trim());

        } catch (IllegalArgumentException e) {
            System.err.println("Input has mistake, checking supported Symbols: " + Arrays.toString(TypeCurrency.values()));
            return false;
        }
        return true;
    }

    @Override
    public String getFrom(){
        return from == null ? "" : from.toString();
    }

    @Override
    public String getTo(){
        return to == null ? "" : to.toString();
    }

}
