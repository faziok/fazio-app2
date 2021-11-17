package inventoryManagementApp.baseline;

/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Keven Fazio
 */

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
    public TableColumn<InventoryItem, String> valueCol;

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



    //Initialize
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Max chars for textField
        final int MAX_CHARS = 256;

        //set text formatter for textField at max chars
        itemNameTF.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().length() <= MAX_CHARS ? change : null));

        //set up tableView columns
        serialNumberCol.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));

        //ensure input fields are at default
        //clearFields();
    }
}
