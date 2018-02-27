import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.stage.Stage;

public class MenuOptions extends MenuBar{

	public MenuOptions(TabPane tabs, Stage primaryStage) {
        // --- Menu File
        Menu menuFile = new Menu("File");
        
        MenuItem newFile = new MenuItem("New");
        newFile.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            		StrainTab newTab = new StrainTab();
            	 	tabs.getTabs().add(newTab);
            	 	tabs.getSelectionModel().select(newTab);
            }
        });  
        newFile.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCodeCombination.CONTROL_DOWN));
        
        MenuItem openFile = new MenuItem("Open");
        openFile.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	
            }
        });
        openFile.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCodeCombination.CONTROL_DOWN));  
        
        MenuItem closeFile = new MenuItem("Close");
        closeFile.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            		tabs.getTabs().remove(tabs.getSelectionModel().getSelectedIndex());
            }
        });  
        closeFile.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCodeCombination.CONTROL_DOWN));  
        
        MenuItem closeAllFile = new MenuItem("Close All");
        closeAllFile.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            		tabs.getTabs().clear();
            }
        });  
        
        MenuItem saveFile = new MenuItem("Save");
        saveFile.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	
            }
        });  
        saveFile.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCodeCombination.CONTROL_DOWN));  
        
        menuFile.getItems().addAll(newFile, openFile, closeFile, closeAllFile, saveFile);
        
        
        
        
        
        // --- Menu Edit
        Menu menuEdit = new Menu("Edit");
 
        // --- Menu View
        Menu menuWindow = new Menu("Window");
        
        MenuItem minimizeWindow = new MenuItem("Minimize");
        minimizeWindow.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                primaryStage.setIconified(true);
            }
        });  
        
        menuWindow.getItems().addAll(minimizeWindow);
        
        // --- Menu View
        Menu menuHelp = new Menu("Help");
 
        this.getMenus().addAll(menuFile, menuEdit, menuWindow, menuHelp);
	}
}
