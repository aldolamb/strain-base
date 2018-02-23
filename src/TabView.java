import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ContextMenuBuilder;
import javafx.scene.control.MenuItemBuilder;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class TabView extends TabPane {

	public TabView() {
		getTabs().add(new StrainTab());
		
		SingleSelectionModel<Tab> selectionModel = getSelectionModel();

		@SuppressWarnings("deprecation")
		ContextMenu tabContextMenu = ContextMenuBuilder.create()
				.items(MenuItemBuilder.create().text("New").onAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						StrainTab newTab = new StrainTab();
						getTabs().add(newTab);
						selectionModel.select(newTab);
					}
				}).build(), MenuItemBuilder.create().text("Open").onAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {

					}
				}).build(), MenuItemBuilder.create().text("Close").onAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						getTabs().remove(selectionModel.getSelectedIndex());
					}
				}).build(), MenuItemBuilder.create().text("Close All").onAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						getTabs().clear();
					}
				}).build(), MenuItemBuilder.create().text("Cancel").onAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {

						System.out.println(getHeight() + " " + getWidth());
					}
				}).build()).build();

		setContextMenu(tabContextMenu);
	}
}
