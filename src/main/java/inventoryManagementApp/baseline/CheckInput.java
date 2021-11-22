package inventoryManagementApp.baseline;

/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Keven Fazio
 */

import javafx.scene.control.Alert;

import java.math.BigDecimal;

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
    }

    public boolean checkSerialNumberDuplicate(String itemSerialNumber, InventoryList itemList){
        //for the size of the InventoryList go through each item and
            //check if the input serialnumber equals an existing one.
        for (int i = 0; i < itemList.getInventoryList().size(); i++){
            InventoryItem test = itemList.getInventoryList().get(i);
            if (test.getItemSerialNumber().equals(itemSerialNumber)){
                return true;
            }
        }

        //test passes
        return false;
    }

    public boolean checkName(String itemName){
        //Test 'Name' input is between 2-256 characters long. Return true if out of bounds
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

        //return true if test fails.
        return (db < 0);
    }

    public void showErrorPopup(String message) {
        //error alert default pop for invalid input
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Follow Directions!");
        alert.setHeaderText("Invalid Input. Try Again.");

        //error message for specific error
        alert.setContentText(message);

        //must exist to continue
        alert.showAndWait();
    }
}
