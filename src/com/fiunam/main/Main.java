package com.fiunam.main;

import com.fiunam.logger.Logger;

import java.util.Arrays;

public class Main {
    public static void main(String[] args){
        Logger log = new Logger(Main.class);
        try {
            GuiProgram.run();
        }catch(Exception e){
            log.sendError(Arrays.toString(e.getStackTrace()));
        }

    }
}
