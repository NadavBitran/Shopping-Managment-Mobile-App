package com.example.shoppingmanagmentapplication.model;

import java.util.Objects;

public class ShoppingItem {
    private eShoppingType itemType;
    private int amount;
    private String itemName;

    private String itemId;

    public ShoppingItem() {}
    public ShoppingItem(eShoppingType itemType, int amount, String itemName, String itemId) {
        this.itemType = itemType;
        this.amount = amount;
        this.itemName = itemName;
        this.itemId = itemId;
    }

    public ShoppingItem(eShoppingType itemType, int amount, String itemName) {
        this.itemType = itemType;
        this.amount = amount;
        this.itemName = itemName;
    }

    public String getItemId() {return itemId;}
    public void setItemId(String itemId) {this.itemId = itemId;}
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingItem that = (ShoppingItem) o;
        return this.itemId == that.itemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemType, amount, itemName, itemId);
    }

    @Override
    public String toString() {
        return "ShoppingItem{" +
                "itemType=" + itemType +
                ", amount=" + amount +
                ", itemName='" + itemName + '\'' +
                ", itemId='" + itemId + '\'' +
                '}';
    }
}
