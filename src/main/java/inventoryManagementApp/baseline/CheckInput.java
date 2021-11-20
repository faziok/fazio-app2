package inventoryManagementApp.baseline;

/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Keven Fazio
 */

import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CheckInput {

    public boolean checkSerialNumber (String itemSerialNumber){
        //Test 'SerialNumber' is entered in the correct format A-xxx-xxx-xxx
        //('A' is any letter, 'x' can be a letter or number)
        //break string into char Array and boolean check = false
        char[] charSN = itemSerialNumber.toCharArray();
        //if charSN is 10 characters long and starts with a letter continue checking. Return false if fails
        if(charSN.length == 13 && Character.isLetter(charSN[0])){
            //check charSN index 1, 5, 9 is a '-', return false is fails
            for(int i = 1; i < charSN.length; i += 4){
                if(charSN[i] != '-' ||
                        !Character.isLetter(charSN[i + 1]) && !Character.isDigit(charSN[i + 1]) ||
                        !Character.isLetter(charSN[i + 2]) && !Character.isDigit(charSN[i + 2]) ||
                        !Character.isLetter(charSN[i + 3]) && !Character.isDigit(charSN[i + 3])){
                    return true;
                }
            }
        } else {
            return true;
        }
        //test passes
        return false;
    }aqwsz2ezd33d

    public boolean checkSerialNumberDuplicate(String itemSerialNumber, ObservableList<InventoryItem> itemList){
        for (int i = 0; i < itemList.size(); i++){
            if(itemList.equals(itemSerialNumber)){
                return true;
            }
        }
        return false;
    }

    public boolean checkName(String itemName){
        //Test 'Name' input is between 2-256 characters long. Return false if fails
        return itemName.length() < 2 || itemName.length() > 256;
    }

    public boolean checkValue(String itemValueTest){
        //Test 'Value' input is a double
        try {
            new BigDecimal(itemValueTest);
        } catch (NumberFormatException err) {
            return true;
        }

        //parse to a double to check if greater or equal to 0.
        double db = Double.parseDouble(itemValueTest);

        return (db < 0);

        //return true if test passes.
    }
}
