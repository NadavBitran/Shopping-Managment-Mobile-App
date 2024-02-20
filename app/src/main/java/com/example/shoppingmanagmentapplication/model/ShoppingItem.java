package com.example.shoppingmanagmentapplication.model;

public class ShoppingItem {
    private eShoppingType itemType;
    private int amount;
    private String itemName;
    public ShoppingItem(eShoppingType itemType, int amount, String itemName) {
        this.itemType = itemType;
        this.amount = amount;
        this.itemName = itemName;
    }

    public eShoppingType getItemType() {
        return itemType;
    }

    public void setItemType(eShoppingType itemType) {
        this.itemType = itemType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
