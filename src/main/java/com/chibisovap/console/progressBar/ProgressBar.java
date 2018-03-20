package com.chibisovap.console.progressBar;

import java.util.Timer;

public class ProgressBar implements IProgressBar{

    @Override
    public void printProgress(int percentage){
        int num = percentage / 10;
        System.out.print("\r");
        for (int i = 0; i < num; i++) {
            System.out.print("*");
        }

    }

}
