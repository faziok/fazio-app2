package inventoryManagementApp.baseline;

/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Keven Fazio
 */

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CheckInputTest {
    InventoryList appTest = new InventoryList();
    CheckInput checkTest = new CheckInput();

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

    @Test
    void checkSerialNumber() {
        //Test should be false if sn is in the correct format
        String snTest1 = "A-123-abc-456";

        //Tests should be true if sn is in the incorrect format
        String snTest2 = "A-123-abc456";
        String snTest3 = "1-123-abc-456";
        String snTest4 = "A-1@3-abc-456";


        assertFalse(checkTest.checkSerialNumber(snTest1));
        assertTrue(checkTest.checkSerialNumber(snTest2));
        assertTrue(checkTest.checkSerialNumber(snTest3));
        assertTrue(checkTest.checkSerialNumber(snTest4));
    }

    @Test
    void checkSerialNumberDuplicate() {
        setUpTest5Items(appTest);

        //Test should be false if not a duplicate
        String snTest1 = "A-123-abc-456";

        //Tests should be true if a duplicate
        String snTest2 = "A-123-abc-de1";
        String snTest3 = "A-123-abc-de2";
        String snTest4 = "A-123-abc-de3";


        assertFalse(checkTest.checkSerialNumberDuplicate(snTest1, appTest));
        assertTrue(checkTest.checkSerialNumberDuplicate(snTest2, appTest));
        assertTrue(checkTest.checkSerialNumberDuplicate(snTest3, appTest));
        assertTrue(checkTest.checkSerialNumberDuplicate(snTest4, appTest));

        appTest.clearList();
    }

    @Test
    void checkName() {
        //name with less than 2 characters should be true
        String nameTest0 = "A";

        //name with more than 256 characters should be true
        String nameTest257 = "B".repeat(257);

        //name with 256 characters should be false
        String nameTest256 = "C".repeat(256);

        //name with 2 or more characters should be false
        String nameTest2 = "MyName";


        assertTrue(checkTest.checkName(nameTest0));
        assertTrue(checkTest.checkName(nameTest257));
        assertFalse(checkTest.checkName(nameTest256));
        assertFalse(checkTest.checkName(nameTest2));
    }

    @Test
    void checkValue() {
        //Must be a number not a letter, return
        String valueTest1 = "A";

        //name with more than 256 characters should be true
        String valueTest2 = "-1";

        //name with 256 characters should be false
        String valueTest3 = "0.000000";

        //name with 2 or more characters should be false
        String valueTest4 = "2008.65";

        //name with 2 or more characters should be false
        String valueTest5 = "57";

        assertTrue(checkTest.checkValue(valueTest1));
        assertTrue(checkTest.checkValue(valueTest2));
        assertFalse(checkTest.checkValue(valueTest3));
        assertFalse(checkTest.checkValue(valueTest4));
        assertFalse(checkTest.checkValue(valueTest5));

    }
}