import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class TabView extends TabPane {

	public TabView() {
		getTabs().add(new StrainTab());
		
		SingleSelectionModel<Tab> selectionModel = getSelectionModel();

		final ContextMenu tabContextMenu = new ContextMenu();
		
		MenuItem tNew = new MenuItem("New");
		tNew.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
				StrainTab newTab = new StrainTab();
				getTabs().add(newTab);
				selectionModel.select(newTab);
		    }
		});
		
		MenuItem tOpen = new MenuItem("Open");
		tOpen.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		        System.out.println("Preferences");
		    }
		});
		
		MenuItem tClose = new MenuItem("Close");
		tClose.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
				getTabs().remove(selectionModel.getSelectedIndex());
		    }
		});
		
		MenuItem tCloseAll = new MenuItem("Close All");
		tCloseAll.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
				getTabs().clear();
		    }
		});
		
		MenuItem tCancel = new MenuItem("Cancel");
		tCancel.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
				getTabs().clear();
		    }
		});
		
		tabContextMenu.getItems().addAll(tNew, tOpen, tClose, tCloseAll, tCancel);
		
		setContextMenu(tabContextMenu);
	}
}
