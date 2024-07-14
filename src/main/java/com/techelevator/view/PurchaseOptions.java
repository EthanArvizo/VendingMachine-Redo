package com.techelevator.view;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PurchaseOptions {
    private TreeMap<String, ItemList> itemsMap = new TreeMap<>();

    public double feedMoney(double currentBalance, double amount) {
        currentBalance += amount;
        String formatBalance = String.format("%.2f", currentBalance);
        System.out.println("Current Balance: $" + formatBalance);
        VendingLogs.logFeedMoney(amount, currentBalance);
        return currentBalance;
    }

    public String selectProduct(double currentBalance, String userChoice) {
        if (itemsMap.containsKey(userChoice)) {
            ItemList selectedItem = itemsMap.get(userChoice);
            if (selectedItem.getItemCount() > 0) {
                if (currentBalance >= selectedItem.getPrice()) {
                    selectedItem.setItemCount(selectedItem.getItemCount() - 1);
                    currentBalance -= selectedItem.getPrice();
                    itemSounds(selectedItem);
                    VendingLogs.logSelectProduct(selectedItem.getItemName(), userChoice, selectedItem.getPrice(), currentBalance);
                } else {
                    JOptionPane.showMessageDialog(null, "Sorry insufficient funds", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Sorry, that item is Sold Out", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid item code. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return String.format("%.2f", currentBalance);
    }

    public void displayItems() {
        File itemFile = new File("vendingmachine.csv");  // Use the uploaded file path
        if (itemsMap.isEmpty()) { // Load items only if the map is empty
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
                        int initialItemCount = 5;
                        ItemList itemList = new ItemList(itemCode, itemName, price, itemType, initialItemCount);
                        itemsMap.put(itemCode, itemList);
                    } else {
                        System.out.println("Invalid input in file: " + itemLine);
                    }
                }
                readFile.close(); // Close the scanner after use
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Error reading vending machine data.", "Error", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException(e);
            }
        }

        String itemsDisplay = "";
        for (Map.Entry<String, ItemList> entry : itemsMap.entrySet()) {
            String itemCode = entry.getKey();
            ItemList item = entry.getValue();
            // Format the price to two decimal places for proper currency display
            String formattedPrice = String.format("%.2f", item.getPrice());
            itemsDisplay += itemCode + " - " + item.getItemName() + " - $" + formattedPrice +
                    " - " + item.getItemType() + " - Quantity: " + item.getItemCount() + "\n";
        }

        JOptionPane.showMessageDialog(null, itemsDisplay, "Vending Machine Items", JOptionPane.INFORMATION_MESSAGE);
    }

    public void getChange(double currentBalance) {
        int quarters = (int) (currentBalance / 0.25);
        currentBalance %= 0.25;
        int dimes = (int) (currentBalance / 0.10);
        currentBalance %= 0.10;
        int nickels = (int) (currentBalance / 0.05);
        VendingLogs.logGiveChange(currentBalance, quarters * 0.25 + dimes * 0.10 + nickels * 0.05);
        JOptionPane.showMessageDialog(null, "Thank you for using this vending machine. Here is your change:\n" +
                "Quarters: " + quarters + "\nDimes: " + dimes + "\nNickels: " + nickels, "Change", JOptionPane.INFORMATION_MESSAGE);
    }

    public void exitVendingMachine() {
        JOptionPane.showMessageDialog(null, "Thank you for using this vending machine! Have a great day!", "Exit", JOptionPane.INFORMATION_MESSAGE);
    }

    private void itemSounds(ItemList selectedItem) {
        String itemType = selectedItem.getItemType();
        switch (itemType) {
            case "Chip":
                JOptionPane.showMessageDialog(null, "Crunch Crunch, Yum!", "Sound", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "Candy":
                JOptionPane.showMessageDialog(null, "Munch Munch, Yum!", "Sound", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "Drink":
                JOptionPane.showMessageDialog(null, "Glug Glug, Yum!", "Sound", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "Gum":
                JOptionPane.showMessageDialog(null, "Chew Chew, Yum!", "Sound", JOptionPane.INFORMATION_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Yum!", "Sound", JOptionPane.INFORMATION_MESSAGE);
                break;
        }
    }
}
