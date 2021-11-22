package inventoryManagementApp.baseline;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SaveAndLoadTest {
    InventoryList appTest = new InventoryList();
    InventoryList loadTest = new InventoryList();
    SaveAndLoad saveAndLoad = new SaveAndLoad();

    //setUp list for ACTUAL list
    private void setUpTest5Items (InventoryList appTest){
        //InventoryList appTest = new InventoryList();
        StringBuilder sn = new StringBuilder("A-123-abc-de");

        //for i < 5
        //add a new item
        for (int i = 0; i < 5; i++){
            //increase value by 1 every time
            BigDecimal bd = new BigDecimal(21 + i).setScale(2, RoundingMode.HALF_UP);
            //append a number 1 higher than 'i' every time
            sn.append(i+1);
            //add to list
            appTest.addItems(sn.toString(), "aName", bd);

            //delete appended number from string builder
            sn.deleteCharAt(12);
        }
    }


    @Test
    void saveTSVFile() {
        //create list
        setUpTest5Items(appTest);

        //create file name
        File fileTSV = new File ("./data/index.tsv");

        //create file
        saveAndLoad.saveTSVFile(fileTSV, appTest.getInventoryList());

        //assert it exists
        assertTrue(fileTSV.exists());

        appTest.clearList();
    }

    @Test
    void saveHTMLFile() {
        //create list
        setUpTest5Items(appTest);

        //create file name
        File fileHTML = new File ("./data/index.html");

        //create file
        saveAndLoad.saveHTMLFile(fileHTML, appTest.getInventoryList());

        //assert it exists
        assertTrue(fileHTML.exists());

        appTest.clearList();
    }

    @Test
    void saveJSONFile() throws IOException {
        //create list
        setUpTest5Items(appTest);

        //create file name
        File fileJSON = new File ("./data/index.json");

        //create file
        saveAndLoad.saveJSONFile(fileJSON, appTest.getInventoryList());

        //assert it exists
        assertTrue(fileJSON.exists());

        appTest.clearList();
    }

    @Test
    void loadTSVFile() throws FileNotFoundException {
        //create list
        setUpTest5Items(appTest);

        //create file name
        File fileTSV = new File ("./data/index.tsv");

        //load list to new InventoryList
        saveAndLoad.loadTSVFile(fileTSV, loadTest.getInventoryList());

        //test appTest equals itemListTest after deletion
        assertEquals(Arrays.toString(appTest.getFilteredInventoryList().toArray()),
                Arrays.toString(loadTest.getInventoryList().toArray()));

        loadTest.clearList();
        appTest.clearList();
    }

    @Test
    void loadHTMLFile() throws IOException {
        //create list
        setUpTest5Items(appTest);

        //create file name
        File fileHTML = new File ("./data/index.html");

        //load list to new InventoryList
        saveAndLoad.loadHTMLFile(fileHTML, loadTest.getInventoryList());

        //test appTest equals itemListTest after deletion
        assertEquals(Arrays.toString(appTest.getFilteredInventoryList().toArray()),
                Arrays.toString(loadTest.getInventoryList().toArray()));

        loadTest.clearList();
        appTest.clearList();
    }

    @Test
    void loadJSONFile() throws IOException {
        //create list
        setUpTest5Items(appTest);

        //create file name
        File fileJSON = new File ("./data/index.json");

        //load list to new InventoryList
        saveAndLoad.loadJSONFile(fileJSON, loadTest.getInventoryList());

        //test appTest equals itemListTest after deletion
        assertEquals(Arrays.toString(appTest.getFilteredInventoryList().toArray()),
                Arrays.toString(loadTest.getInventoryList().toArray()));

        loadTest.clearList();
        appTest.clearList();
    }
}