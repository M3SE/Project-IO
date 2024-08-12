package com.simonchang.filemanager.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ExceptionHandler {

    private static final Logger logger = Logger.getLogger(ExceptionHandler.class.getName());

    // Method to handle exceptions
    public static void handle(Exception e, String message) {
        System.err.println(message + ": " + e.getMessage());
        logger.log(Level.SEVERE, message, e);
    }

    // Overloaded method to handle exceptions without a custom message
    public static void handle(Exception e) {
        System.err.println("An error occurred: " + e.getMessage());
        logger.log(Level.SEVERE, "An error occurred", e);
    }
}
