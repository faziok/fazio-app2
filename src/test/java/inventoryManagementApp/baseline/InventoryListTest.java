package inventoryManagementApp.baseline;

/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Keven Fazio
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class InventoryListTest {
    InventoryList appTest = new InventoryList();

    //initialize observable for EXPECTED list
    private final ObservableList<InventoryItem> itemListTest = FXCollections.observableArrayList(
            new InventoryItem("A-123-abc-de1", "aName", new BigDecimal(21)),
            new InventoryItem("A-123-abc-de2", "aName", new BigDecimal(22)),
            new InventoryItem("A-123-abc-de3", "aName", new BigDecimal(23)),
            new InventoryItem("A-123-abc-de4", "aName", new BigDecimal(24))
    );

    //setUp list for ACTUAL list
    private void setUpTest5Items (InventoryList appTest){
        //InventoryList appTest = new InventoryList();
        StringBuilder sn = new StringBuilder("A-123-abc-de");

        //for i < 5
        //add a new item
        for (int i = 0; i < 5; i++){
            //increase value by 1 every time
            BigDecimal bd = new BigDecimal(21 + i);
            //append a number 1 higher than 'i' every time
            sn.append(i+1);
            //add to list
            appTest.addItems(sn.toString(), "aName", bd);

            //delete appended number from string builder
            sn.deleteCharAt(12);
        }
    }

    //setUp list for ACTUAL list
    private void setUpTest1024Items (InventoryList appTest){
        //InventoryList appTest = new InventoryList();
        StringBuilder sn = new StringBuilder("A-123-abc-de");

        //for i < 1024
        //add a new item
        for (int i = 0; i < 1024; i++){
            //increase value by 1 every time
            BigDecimal bd = new BigDecimal(21 + i);
            //append a number 1 higher than 'i' every time
            sn.append(i+1);
            //add to list
            appTest.addItems(sn.toString(), "aName", bd);

            //delete appended number from string builder
            sn.deleteCharAt(12);
        }
    }

    @Test
    void addItems() {
        setUpTest1024Items(appTest);

        //get specific item and split to string array for testing
        String[] expected = itemListTest.get(2).toString().split(" ");
        String[] actual = appTest.getInventoryList().get(2).toString().split(" ");

        //test list can hold 1024 items
        assertEquals(1024, appTest.getInventoryList().size());
        //test one item equals the other
        assertArrayEquals(expected, actual);

        //clear appTest list
        appTest.clearList();
    }

    @Test
    void clearList() {
        setUpTest5Items(appTest);

        //CLEAR THE LIST
        appTest.clearList();

        //test list has 0 items
        assertEquals(0, appTest.getInventoryList().size());
    }

    @Test
    void deleteItem() {
        setUpTest5Items(appTest);

        //set selected item as if from tableview
        InventoryItem selectedItem = appTest.getInventoryList().get(4);

        //delete item from list
        appTest.deleteItem(selectedItem);

        //test list contains 4 items instead of 5, one less after deletion.
        assertEquals(4, appTest.getInventoryList().size());

        //test appTest equals itemListTest after deletion
        assertEquals(Arrays.toString(itemListTest.toArray()), Arrays.toString(appTest.getInventoryList().toArray()));

        appTest.clearList();
    }

    @Test
    void filterItems() {
        setUpTest5Items(appTest);

        //create expected list after filter
        final ObservableList<InventoryItem> itemFilterTest = FXCollections.observableArrayList(
                new InventoryItem("A-123-abc-de4", "aName", new BigDecimal(24))
        );

        String searchText = "4";
        appTest.filterItems(searchText);

        assertEquals(Arrays.toString(itemFilterTest.toArray()),
                Arrays.toString(appTest.getFilteredInventoryList().toArray()));

        appTest.clearList();
    }

    @Test
    void updateItem() {
        setUpTest5Items(appTest);

        //create expected list to assert
        final ObservableList<InventoryItem> itemFilterTest = FXCollections.observableArrayList(
                new InventoryItem("A-123-zzz-de3", "ZName", new BigDecimal(100))
        );

        //create variables as if data was changes in text fields
        String snTest = "A-123-zzz-de3";
        String nameTest = "ZName";
        BigDecimal bdTest = new BigDecimal(100);

        //set selected item as if from tableview
        InventoryItem selectedItem = appTest.getInventoryList().get(2);

        appTest.updateItem(snTest, nameTest, bdTest, 2);

        //get specific item and split to string array for testing
        String[] expected = itemFilterTest.get(0).toString().split(" ");
        String[] actual = appTest.getInventoryList().get(2).toString().split(" ");

        //test updated item equals the expected
        assertArrayEquals(expected, actual);

        appTest.clearList();
    }
}