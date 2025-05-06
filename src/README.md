# Source Files
Contains all the files for the gui, logic, saving, loading, etc.
## Overview
All files that allow user to actually use the program.

### DnD5eSheet
Handles the getting of the .fxml file in the res folder and setting that as the main window.

### Controller
Controls the functionality of the character sheet main window. Calls button manager to handle external (save, load, etc.) funcitons from other classes. This is considered the Display Layer.

### Character
Holds the instance character stats and other values, these are called from and used in external equations and for the ability to save/load to the instance sheet. 

### Inventory
External class that holds the characters inventory. It was just easier to make its own class as it would be to large to put into Character class.

### Character Manager
Hanldes the character files. Mainly handles the deletion and renaming of characters if the user so desires.

### File Manager
Handles the saving and loading of characters and user settings from a .json file.

### Button Manager
Handles what to do and call when a unique button is pressed on the main window.

### Settings
Hanldes and sets hard coded or multi-used variables across different classes. Can be used to change path locations, inventory size, etc.

### User Settings
Handles user set settings however mainly just the character save directory for now.

### Notification
Handles the showing of notifications to the screen so a user is aware of any warnings or erros and to confirm to the user that stuff when successfully.
