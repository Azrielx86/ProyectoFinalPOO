package com.fiunam.main;

public class Main {
    public static void main(String[] args){
        try {
            GuiProgram.run();
        }catch(Exception e){
            e.getStackTrace();
        }

    }
}
