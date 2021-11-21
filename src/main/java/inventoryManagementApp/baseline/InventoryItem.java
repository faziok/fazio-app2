package inventoryManagementApp.baseline;

/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Keven Fazio
 */

import java.math.BigDecimal;

public class InventoryItem {
    private String itemSerialNumber;
    private String itemName;
    private BigDecimal itemValue;

    //default constructor
    public InventoryItem (){
        itemSerialNumber = null;
        itemName = null;
        itemValue = BigDecimal.valueOf(0);
    }

    //Parameterized constructor
    public InventoryItem(String itemSerialNumber, String itemName, BigDecimal itemValue){
        this.itemSerialNumber = itemSerialNumber;
        this.itemName = itemName;
        this.itemValue = itemValue;
    }

    public String getItemSerialNumber(){
        //get description
        return itemSerialNumber;
    }

    public String getItemName(){
        //get description
        return itemName;
    }

    public BigDecimal getItemValue(){
        //get item value
        return itemValue;
    }

    public void setItemSerialNumber(String itemSerialNumber){
        //set description
        this.itemSerialNumber = itemSerialNumber;
    }

    public void setItemName(String itemName) {
        //set status
        this.itemName = itemName;
    }

    public void setItemValue(BigDecimal itemValue) {
        //set status
        this.itemValue = itemValue;
    }

    //override data to a string
    @Override
    public String toString(){
        return getItemSerialNumber() + " " + getItemName() + " " + getItemValue();
    }
}
