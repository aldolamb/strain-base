import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;

public class MenuOptions extends MenuBar{

	public MenuOptions(TabPane tabs) {
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
        
        MenuItem openFile = new MenuItem("Open");
        openFile.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	
            }
        });  
        
        MenuItem closeFile = new MenuItem("Close");
        closeFile.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            		tabs.getTabs().remove(tabs.getSelectionModel().getSelectedIndex());
            }
        });  
        
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
        
        menuFile.getItems().addAll(newFile, openFile, closeFile, closeAllFile, saveFile);
        
        
        
        
        
        // --- Menu Edit
        Menu menuEdit = new Menu("Edit");
 
        // --- Menu View
        Menu menuView = new Menu("View");
        
        // --- Menu View
        Menu menuHelp = new Menu("Help");
 
        this.getMenus().addAll(menuFile, menuEdit, menuView, menuHelp);
	}
}
