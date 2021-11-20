package inventoryManagementApp.baseline;

/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Keven Fazio
 */

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    public TableColumn<String, Integer> serialNumberCol;

    @FXML
    public TableColumn<String, Integer> nameCol;

    @FXML
    public TableColumn<String, Integer> valueCol;

    @FXML
    private Button addItemButton;

    @FXML
    private Button updateItemButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button searchItemButton;

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
        itemsTableView.setPlaceholder(new Label("No inventory to display"));

        //Max chars for textField
        final int MAX_CHARS = 256;
        //set text formatter for textField at max chars
        itemNameTF.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().length() <= MAX_CHARS ? change : null));


        //set up tableView columns
        serialNumberCol.setCellValueFactory(new PropertyValueFactory<>("itemSerialNumber"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        valueCol.setCellValueFactory(new PropertyValueFactory<>("itemValue"));

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


    //Save save = new Save();


    /*
    //create file chooser
    FileChooser fc = new FileChooser();
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
     */

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
            //**************Throw error message*************
            itemSerialNumberTF.clear();
        } else if (check.checkName(itemName)){
            //**************Throw error message*************
            itemNameTF.clear();
        } else if (check.checkValue(itemValueTest)){
            //**************Throw error message*************
            itemValueTF.clear();
        } else {
            BigDecimal itemValue = new BigDecimal(itemValueTest);

            list.addItems(itemSerialNumber, itemName, itemValue);

            //send list data to tableview
            itemsTableView.setItems(list.getFilteredInventoryList());

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
                //**************Throw error message*************
                itemSerialNumberTF.clear();
            } else if (check.checkName(itemName)){
                //**************Throw error message*************
                itemNameTF.clear();
            } else if (check.checkValue(itemValueTest)){
                //**************Throw error message*************
                itemValueTF.clear();
            } else {
                BigDecimal itemValue = new BigDecimal(itemValueTest);

                list.updateItem(itemSerialNumber, itemName, itemValue, listIndex);

                //send list data to tableview
                itemsTableView.setItems(list.getFilteredInventoryList());

                //clear item input fields
                clearFields();
            }
        } else {
            //*******************Throw error message*********************
            clearFields();
        }
    }

    /*
    @FXML
    private void filterItems(ActionEvent e) {
        //get value from comboBox
        String val = filter.getValue();

        //send value to filter method from to-do list
        list.filterList(val);

        //set the tableview to show the filtered list of items
        itemsTableView.setItems(list.getFilteredTodoList());
    }

    @FXML
    private void saveList(ActionEvent e) {
        //set extension for txt and show saved dialog box
        fc.getExtensionFilters().add(extFilter);
        File file = fc.showSaveDialog(null);

        //if a file name and location is chosen, save the file
        if (file != null) {
            save.saveTxtFile(file, list.getItemList());
        }
    }

    @FXML
    private void loadList(ActionEvent e) throws IOException {
        //set extension for txt and show open dialog box
        fc.getExtensionFilters().add(extFilter);
        File file = fc.showOpenDialog(null);

        String line;

        if (file != null) {
            //clear current list
            list.clearList();

            //create scanner
            BufferedReader br = new BufferedReader(new FileReader(file));

            try (br) {
                while ((line = br.readLine()) != null) {
                    //read in line of file and split
                    String[] newDataArr = line.split(",");

                    //instance variables
                    String description = newDataArr[0];

                    //set local date
                    //if input from file is "" set to null
                    //else set to input
                    LocalDate date;
                    if (newDataArr[1].equals("")) {
                        date = null;
                    } else {
                        date = LocalDate.parse(newDataArr[1]);
                    }

                    //if input is 'Completed' set status to true
                    //else false
                    boolean status;
                    status = newDataArr[2].equals(SHOW_COMPLETED);

                    //add item to list
                    list.addItems(description, date, status);
                }
            }

            //update tableview
            itemsTableView.setItems(list.getFilteredTodoList());
            filter.setValue(SHOW_ALL);
        }
    }
     */

    //method to clear input fields when needed
    private void clearFields () {
        //clear item input fields
        itemNameTF.clear();
        itemSerialNumberTF.clear();
        itemValueTF.clear();
    }
}
