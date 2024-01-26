package com.techelevator.view;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VendingLogs {
    private static final String LOG_FILE_PATH = "log.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
    public static void logFeedMoney(double amount,double currentBalance){
        log("Feed Money",amount,currentBalance);
    }
    public static void logSelectProduct(String productName, String slot, double price, double currentBalance){
        log(productName+ " " + slot,price,currentBalance);
    }
    public static void logGiveChange(double currentBalance, double change){
        log("Give Change", change, currentBalance);
    }
    private static void log(String action, double amount, double currentBalance){
        String timestamp = DATE_FORMAT.format(new Date());
        String logEntry = String.format("%s %s: $%.2f $%.2f", timestamp, action, amount, currentBalance);
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.println(logEntry);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
