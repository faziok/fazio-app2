# fazio-app2

*  UCF COP3330 Fall 2021 Application Assignment 2 Solution
*  Copyright 2021 Keven Fazio

 # INVENTORY MANAGER APPLICATION USER GUIDE
 
 1) To add an item to your Inventory, simply type a Serial Number, Name, and Value into the designated text fields. Once the information has been added, click the 'Add Item' button. Your new item will then populate into the list below showing.

 *NOTES for input:
    -The Serial Number must follow a format of A-xxx-xxx-xxx, where 'A' must be a letter, and 'x' may be a letter or number only.
    -The Name must be between 2 and 256 characters long
    -The value must be a number and be equal or greater than 0.
 
 *NOTE: Input fields will be set to default upon adding an item.*
 
 2) There is a 'Clear' button for your convienence. This will set the input fields to their default values if needed.
 
 2) To edit and item, select the row of ONE item you wish to edit and then click the 'Edit Item' button. This will populate the input fields with the information from the row. Simply edit any of the information needed and proceed to click the 'Update' button to submit your changes. You will see the changes immediately in the list.
 
 *NOTE: This will replace any information currently in your input fields.*
 
 3) To delete and item, select the row of ONE item you wish to delete and then click the 'Delete Item' button. Your item will be deleted from the list.
 
 4) To clear the list of all items, click the 'Clear List' button. The list will be cleared of all items.
 
 5) To search your list by Serial Number or Name, simple start typing into the search field. The list will update automatically based off of your input.
 *Example: Typing 'a' will show only item will the letter 'a' (upper or lowercase) in the Serial Number and/or Name.
 
 6) To save your Inventory list click the 'Save' button. A save dialog box will appear where you can choose your file location, file type (.tsv, .html, .json), and file name. Click save and your file will be written.
 
 7) To load a new Inventory list from an existing file, click the 'Load' button. An open dialog box will appear where you can choose the file (.tsv, .html, .json ONLY). Click open and your list WILL REPLACE your current list with the newly loaded list. You may then view, edit, delete, etc. as normal. 
