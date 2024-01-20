package com.techelevator.view;

import java.util.Scanner;

public class ItemList{
    private String itemCode;
    private String itemName;
    private double price;
    private String itemType;
    private int itemCount =5;

    public ItemList(String itemCode, String itemName, double price, String itemType, int itemCount) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.price = price;
        this.itemType = itemType;
        this.itemCount = itemCount;
    }


    public String getItemCode() {
        return itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }

    public String getItemType() {
        return itemType;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    @Override
    public String toString() {
        return String.format("%-3s%-20s%-8s%-10s%-3s",
                itemCode, itemName, String.format("$%.2f", price), itemType, itemCount);
    }
}
