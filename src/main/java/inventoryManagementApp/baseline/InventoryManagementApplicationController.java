package inventoryManagementApp.baseline;

/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Keven Fazio
 */

import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

public class InventoryManagementApplicationController implements Initializable {
    @FXML
    private TextField itemSerialNumberTF;

    @FXML
    private TextField itemNameTF;

    @FXML
    private TextField itemValueTF;

    @FXML
    private TextField itemSearchTF;

    @FXML
    private TableView<InventoryItem> itemsTableView;

    @FXML
    public TableColumn<InventoryItem, String> serialNumberCol;

    @FXML
    public TableColumn<InventoryItem, String> nameCol;

    @FXML
    public TableColumn<InventoryItem, BigDecimal> valueCol;

    @FXML
    private Button addItemButton;

    @FXML
    private Button updateItemButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button deleteItemButton;

    @FXML
    private Button editItemButton;

    @FXML
    private Button clearListButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button loadButton;

    //------------------------------------

    //Initialize
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //override label in table view when no items are in the list
        itemsTableView.setPlaceholder(new Label("No inventory to display"));

        //create a sorted list to bind to the tableview to allow sorting
        final SortedList<InventoryItem> theSortedItems = new SortedList<>(list.getFilteredInventoryList());

        //bind sortedlist with tableview
        theSortedItems.comparatorProperty().bind(itemsTableView.comparatorProperty());

        //set the tableview to show the filtered list of items
        itemsTableView.setItems(theSortedItems);

        //Max chars for name textField
        final int MAX_CHARS = 256;
        //set text formatter for name textField at max chars
        itemNameTF.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().length() <= MAX_CHARS ? change : null));

        //Max chars for serial number textField
        final int SN_MAX_CHARS = 13;
        //set text formatter for serial number textField at max chars
        itemSerialNumberTF.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().length() <= SN_MAX_CHARS ? change : null));

        //set up tableView columns
        serialNumberCol.setCellValueFactory(new PropertyValueFactory<>("itemSerialNumber"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        valueCol.setCellValueFactory(new PropertyValueFactory<>("itemValue"));

        //create listener for searchTF and send text to filterItems method
        itemSearchTF.textProperty().addListener((observable, oldValue, newValue) ->
                list.filterItems(newValue)
        );

        //set extension for txt and show saved dialog box
        fc.getExtensionFilters().add(extFilterTSV);
        fc.getExtensionFilters().add(extFilterHTML);
        fc.getExtensionFilters().add(extFilterJSON);
        fc.getExtensionFilters().add(extFilterALL);

        //ensure input fields are at default
        clearFields();
    }

    //--------------------------------------

    //Observable list object
    InventoryList list = new InventoryList();

    //create variable for selected index of the list
    int listIndex;

    //create variable for selected row
    InventoryItem selectedIndex = null;

    //save object
    SaveAndLoad saveAndLoad = new SaveAndLoad();
    //create file chooser
    FileChooser fc = new FileChooser();

    //create extensions for file types
    FileChooser.ExtensionFilter extFilterTSV = new FileChooser.ExtensionFilter("TSV files (*.tsv)", "*.tsv");
    FileChooser.ExtensionFilter extFilterHTML = new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html");
    FileChooser.ExtensionFilter extFilterJSON = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
    FileChooser.ExtensionFilter extFilterALL = new FileChooser.ExtensionFilter("ALL files (*.*)", "*.*");

    //---------------------------------------

    @FXML
    private void addItem(ActionEvent e) {
        //get input from fields
        String itemSerialNumber = itemSerialNumberTF.getText();
        String itemName = itemNameTF.getText();
        String itemValueTest = itemValueTF.getText();

        //object to check input
        CheckInput check = new CheckInput();

        //if any of the tests fail, throw error for incorrect input and clear corresponding field
        if(check.checkSerialNumber(itemSerialNumber)){
            String snFormatError = "Please enter Serial Number: A-xxx-xxx-xxx\n" +
                    "(where 'A' is a letter and 'x' is a letter or number)";
            check.showErrorPopup(snFormatError);
        } else if (check.checkSerialNumberDuplicate(itemSerialNumber, list)){
            String snDuplicateError = "Serial Number entered is already in use.";
            check.showErrorPopup(snDuplicateError);
        } else if (check.checkName(itemName)){
            String nameError = "Please enter a name between 2-256 characters long.";
            check.showErrorPopup(nameError);
        } else if (check.checkValue(itemValueTest)){
            String valueError = "Value may only be a positive dollar amount.";
            check.showErrorPopup(valueError);
        } else {

            BigDecimal itemValue = new BigDecimal(itemValueTest).setScale(2, RoundingMode.HALF_UP);

            list.addItems(itemSerialNumber, itemName, itemValue);

            //clear item input fields
            clearFields();
        }
    }

    @FXML
    private void clear(ActionEvent e) {
        //clear input fields
        clearFields();
    }

    @FXML
    private void clearList(ActionEvent e) {
        //clear list of items
        list.clearList();
    }

    @FXML
    private void deleteItem(ActionEvent e) {
        //get selected item from tableview selection row
        InventoryItem tableIndex = itemsTableView.getSelectionModel().getSelectedItem();

        //delete selected item
        list.deleteItem(tableIndex);
    }

    @FXML
    private void editItem(ActionEvent e) {
        //get selected row from tableview and get list index from that
        selectedIndex = itemsTableView.getSelectionModel().getSelectedItem();
        listIndex = list.getInventoryList().indexOf(selectedIndex);

        //pull selected item info to fields
        if (selectedIndex != null){
            //Populate description textArea
            itemSerialNumberTF.setText(selectedIndex.getItemSerialNumber());

            //Populate NameTF
            itemNameTF.setText(selectedIndex.getItemName());

            //Populate ValueTF
            itemValueTF.setText(String.valueOf(selectedIndex.getItemValue()));
        }
    }

    @FXML
    private void update(ActionEvent e) {
        //get input from fields
        String itemSerialNumber = itemSerialNumberTF.getText();
        String itemName = itemNameTF.getText();
        String itemValueTest = itemValueTF.getText();

        //object to check input
        CheckInput check = new CheckInput();

        if(selectedIndex != null){
            //if any of the tests fail, throw error for incorrect input and clear corresponding field
            if(check.checkSerialNumber(itemSerialNumber)){
                String snFormatError = "Please enter Serial Number: A-xxx-xxx-xxx\n" +
                        "(where 'A' is a letter and 'x' is a letter or number)";
                check.showErrorPopup(snFormatError);
            } else if (check.checkSerialNumberDuplicate(itemSerialNumber, list)){
                String snDuplicateError = "Serial Number is already in use.";
                check.showErrorPopup(snDuplicateError);
            } else if (check.checkName(itemName)){
                String nameError = "Please enter a name between 2-256 characters long.";
                check.showErrorPopup(nameError);
            } else if (check.checkValue(itemValueTest)){
                String valueError = "Please enter a positive dollar amount.";
                check.showErrorPopup(valueError);
            } else {
                BigDecimal itemValue = new BigDecimal(itemValueTest);

                list.updateItem(itemSerialNumber, itemName, itemValue, listIndex);

                //clear item input fields
                clearFields();
            }
        } else {
            clearFields();
        }
    }


    @FXML
    private void saveList(ActionEvent e) {
        //create file and open save dialog box
        File file = fc.showSaveDialog(null);

        //variables for extension matching
        String extTSV = "tsv";
        String extHTML = "html";
        String extJSON = "json";
        String extension;
        String fileName;
        int index;

        if (file != null) {
            fileName = file.toString();
            //index of last "." in file name
            index = fileName.lastIndexOf('.');
            //if index > 0 get the substring following the "." which would be the file extension
            if (index > 0) {
                extension = fileName.substring(index + 1);

                //if extension matches, save the file as that extension
                if (extension.equals(extTSV)) {
                    saveAndLoad.saveTSVFile(file, list.getInventoryList());
                } else if (extension.equals(extHTML)) {
                    saveAndLoad.saveHTMLFile(file, list.getInventoryList());
                } else if (extension.equals(extJSON)) {
                    saveAndLoad.saveJSONFile(file, list.getInventoryList());
                }
            }
        }
    }


    @FXML
    private void loadList(ActionEvent e) throws IOException {
        //create file and open load dialog box
        File file = fc.showOpenDialog(null);

        //variables for extension matching
        String extTSV = "tsv";
        String extHTML = "html";
        String extJSON = "json";
        String extension;
        String fileName;
        int index;

        if (file != null){
            fileName = file.toString();
            //index of last "." in file name
            index = fileName.lastIndexOf('.');
            //if index > 0 get the substring following the "." which would be the file extension
            if(index > 0) {
                extension = fileName.substring(index + 1);

                //clear current list
                list.clearList();

                //if extension matches, load the file as that extension
                if (extension.equals(extTSV)){
                    //load
                    saveAndLoad.loadTSVFile(file, list.getInventoryList());
                } else if (extension.equals(extHTML)){
                    //load
                    saveAndLoad.loadHTMLFile(file, list.getInventoryList());
                } else if (extension.equals(extJSON)){
                    //load
                    saveAndLoad.loadJSONFile(file, list.getInventoryList());
                }
            }
        }
    }

    //method to clear input fields when needed
    private void clearFields () {
        //clear item input fields
        itemNameTF.clear();
        itemSerialNumberTF.clear();
        itemValueTF.clear();
        itemSerialNumberTF.requestFocus();
    }
}
