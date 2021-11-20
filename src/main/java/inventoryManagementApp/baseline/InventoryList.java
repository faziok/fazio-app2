package inventoryManagementApp.baseline;

/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Keven Fazio
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.math.BigDecimal;

public class InventoryList {
    //initialize observable and filtered list
    private final ObservableList<InventoryItem> itemList = FXCollections.observableArrayList();
    private final FilteredList<InventoryItem> filteredItems = new FilteredList<>(itemList, p -> true);


    public void addItems(String itemSerialNumber, String itemName, BigDecimal itemValue){
        //create TodoListItem object and add it to list
        InventoryItem item = new InventoryItem(itemSerialNumber, itemName, itemValue);

        //Add item to list
        itemList.add(item);
    }

    public ObservableList<InventoryItem> getInventoryList(){
        //get filtered list
        return itemList;
    }

    public ObservableList<InventoryItem> getFilteredInventoryList(){
        //get filtered items
        return filteredItems;
    }

    public void clearList(){
        //clear list
        itemList.clear();
    }

    public void deleteItem(InventoryItem item){
        //remove item from list
        itemList.remove(item);
    }

    /*
    public void filterList(String val){
        //set predicate for filter filter
        //*****************************************************************************************
    }
     */


    public void updateItem(String itemSerialNumber, String itemName, BigDecimal itemValue, int listIndex){
        //create an InventoryItem object and add it to list
        InventoryItem item = new InventoryItem(itemSerialNumber, itemName, itemValue);

        //set updated information to selected index in list.
        getInventoryList().set(listIndex, item);
    }
}
