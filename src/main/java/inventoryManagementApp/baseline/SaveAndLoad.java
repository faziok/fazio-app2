package inventoryManagementApp.baseline;

/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Keven Fazio
 */

import com.google.gson.*;
import javafx.collections.ObservableList;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SaveAndLoad {
    public void saveTSVFile(File file, ObservableList<InventoryItem> list) {
        //try the list and writes to file from file chooser
        try {
            PrintWriter pWriter = new PrintWriter(file);
            //write the file for the entire list of existing items
            for (InventoryItem s : list) {
                pWriter.write(s.getItemSerialNumber() + "\t" + s.getItemName() + "\t" + s.getItemValue() + "\n");
            }
            //close writer
            pWriter.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void saveHTMLFile(File file, ObservableList<InventoryItem> list) {
        //try the list and writes to file from file chooser
        try {
            PrintWriter pWriter = new PrintWriter(file);
            //write the file for the entire list of existing items
            for (InventoryItem s : list) {
                pWriter.write(s.getItemSerialNumber() + "\t" + s.getItemName() + "\t" + s.getItemValue() + "\n");
            }
            //close writer
            pWriter.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void saveJSONFile(File file, ObservableList<InventoryItem> list) throws IOException {

        //Path to file name
        Path path = Paths.get(String.valueOf(file));

        //write json file
        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {

                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                //tree of items
                JsonElement tree = gson.toJsonTree(list);
                gson.toJson(tree, writer);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadTSVFile(File file, ObservableList<InventoryItem> list) throws FileNotFoundException {
        //create BufferReader
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;

        try (br) {
            while ((line = br.readLine()) != null) {
                //read in line of file and split
                String[] newDataArr = line.split("\t");

                //instance variables
                String serialNumber = newDataArr[0];
                String name = newDataArr[1];
                BigDecimal value = new BigDecimal(newDataArr[2]).setScale(2, RoundingMode.HALF_UP);

                //create a new item
                InventoryItem item = new InventoryItem(serialNumber, name, value);

                //add item to list
                list.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadHTMLFile(File file, ObservableList<InventoryItem> list) throws FileNotFoundException {
        //create BufferReader
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;

        try (br) {
            while ((line = br.readLine()) != null) {
                //read in line of file and split
                String[] newDataArr = line.split("\t");

                //instance variables
                String serialNumber = newDataArr[0];
                String name = newDataArr[1];
                BigDecimal value = new BigDecimal(newDataArr[2]);

                //create a new item
                InventoryItem item = new InventoryItem(serialNumber, name, value);

                //add item to list
                list.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadJSONFile(File file, ObservableList<InventoryItem> list) throws IOException {
        Path path = Paths.get(String.valueOf(file));

        try (Reader reader = Files.newBufferedReader(path,
                StandardCharsets.UTF_8)) {

            Gson gson = new Gson();
            InventoryItem[] theItems = gson.fromJson(reader, InventoryItem[].class);

            for (InventoryItem inventoryItem : theItems) {
                //instance variables
                String serialNumber = inventoryItem.getItemSerialNumber();
                String name = inventoryItem.getItemName();
                BigDecimal value = new BigDecimal(inventoryItem.getItemValue().toString()).setScale(2, RoundingMode.HALF_UP);

                //create a new item
                InventoryItem theInventoryItem = new InventoryItem(serialNumber, name, value);

                //add item to list
                list.add(theInventoryItem);
            }
        }
    }
}
