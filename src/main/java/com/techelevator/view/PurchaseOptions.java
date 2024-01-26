package com.techelevator.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PurchaseOptions {
    Scanner userInput = new Scanner(System.in);
    Map<String, ItemList> itemsMap = new HashMap<>();

    public double feedMoney(double currentBalance){
        System.out.println("Please enter a money value for your purchase");
        double amount = userInput.nextDouble();
        userInput.nextLine();
        currentBalance += amount;
        String formatBalance = String.format("%.2f", currentBalance);
        System.out.println("Current Balance: $" + formatBalance);
        VendingLogs.logFeedMoney(amount,currentBalance);
        return currentBalance;
    }
    public String selectProduct(double currentBalance) {
        displayItems();
        System.out.println("Please enter item code");
        String userChoice = userInput.nextLine().toUpperCase();
        if (itemsMap.containsKey(userChoice)) {
            ItemList selectedItem = itemsMap.get(userChoice);
            if (selectedItem.getItemCount() > 0) {
                if (currentBalance >= selectedItem.getPrice()) {
                    selectedItem.setItemCount(selectedItem.getItemCount() - 1);
                    currentBalance -= selectedItem.getPrice();
                    itemSounds(selectedItem);
                    VendingLogs.logSelectProduct(selectedItem.getItemName(), userChoice, selectedItem.getPrice(), currentBalance);
                } else {
                    System.out.println("Sorry insufficient funds");
                }
            } else {
                System.out.println("Sorry, that item is Sold Out");
            }
        } else {
            System.out.println("Invalid item code. Please try again.");
        }
        return String.format("%.2f", currentBalance);
    }
//    public void finishTransaction(){
//        System.out.println("Thank you for using the vending machine");
//        System.out.println("Your change is [change]");
//
//    }
    public void displayItems() {
        File itemFile = new File("vendingmachine.csv");
        try {
            Scanner readFile = new Scanner(itemFile);
            while (readFile.hasNextLine()) {
                String itemLine = readFile.nextLine();
                String[] parts = itemLine.split("\\|");
                if (parts.length == 4) {
                    String itemCode = parts[0];
                    String itemName = parts[1];
                    String itemType = parts[3];
                    double price = Double.parseDouble(parts[2]);
                    if (itemsMap.containsKey(itemCode)) {
                        ItemList itemList = itemsMap.get(itemCode);
                        System.out.println(itemList);
                    } else {
                        int initialItemCount = 5;
                        ItemList itemList = new ItemList(itemCode, itemName, price, itemType, initialItemCount);
                        itemsMap.put(itemCode, itemList);
                        System.out.println(itemList);
                    }
                } else {
                    System.out.println("Invalid input in file: " + itemLine);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void displayBalance(double currentBalance) {
        String formatBalance = String.format("%.2f", currentBalance);
        System.out.println("Current Balance: $" + formatBalance);
    }
    public void itemSounds(ItemList selectedItem){
        String itemType = selectedItem.getItemType();
        if (itemType.equals("Chip")){
            System.out.println("Crunch Crunch, Yum!");
        }else if (itemType.equals("Candy")){
            System.out.println("Munch Munch, Yum!");
        }else if(itemType.equals("Drink")){
            System.out.println("Glug Glug, Yum!");
        }else if(itemType.equals("Gum")){
            System.out.println("Chew Chew, Yum!");
        }
    }
    public void getChange(double currentBalance){
        int quarters = (int) (currentBalance/0.25);
        currentBalance %= 0.25;
        int dimes = (int) (currentBalance/ 0.10);
        currentBalance %= 0.10;
        int nickels = (int) (currentBalance/ 0.05);
        VendingLogs.logGiveChange(currentBalance,quarters * 0.25 + dimes * 0.10 + nickels * 0.05);
        System.out.println("Thank you for using this vending machine here is your change");
        System.out.println("Quarters: " + quarters);
        System.out.println("Dimes: "+ dimes);
        System.out.println("Nickels: "+ nickels);


    }
    public void exitVendingMachine(){
        System.out.println("Thank you for using this vending machine!");
        System.out.println("Have a great day");
    }

}
