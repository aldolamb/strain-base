import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("JavaFX Welcome");

		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		primaryStage.setX(bounds.getMinX());
		primaryStage.setY(bounds.getMinY());
		primaryStage.setWidth(bounds.getWidth());
		primaryStage.setHeight(bounds.getHeight());

		SplitPane split = new SplitPane();

		VBox sideBar = new VBox();

		TabPane tabs = new TabPane();
		
		tabs.getTabs().add(new StrainTab());

		SingleSelectionModel<Tab> selectionModel = tabs.getSelectionModel();

		@SuppressWarnings("deprecation")
		ContextMenu tabContextMenu = ContextMenuBuilder.create()
				.items(MenuItemBuilder.create().text("New").onAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						StrainTab newTab = new StrainTab();
						tabs.getTabs().add(newTab);
						tabs.getSelectionModel().select(newTab);
					}
				}).build(), MenuItemBuilder.create().text("Open").onAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {

					}
				}).build(), MenuItemBuilder.create().text("Close").onAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						tabs.getTabs().remove(tabs.getSelectionModel().getSelectedIndex());
					}
				}).build(), MenuItemBuilder.create().text("Close All").onAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						tabs.getTabs().clear();
					}
				}).build(), MenuItemBuilder.create().text("Cancel").onAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {

						System.out.println(tabs.getHeight() + " " + tabs.getWidth());
					}
				}).build()).build();

		tabs.setContextMenu(tabContextMenu);
//		tabContextMenu.prefWidthProperty().bind(split.prefWidthProperty());
//		tabContextMenu.prefHeightProperty().bind(split.heightProperty());
		
		TreeItem<String> root = new TreeItem<String>("Results");
		// root.setExpanded(true);
		TreeView<String> navigation = new TreeView<String>(root);

		navigation.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {
						Node node = mouseEvent.getPickResult().getIntersectedNode();
						// Accept clicks only on node cells, and not on empty spaces of the TreeView
						if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
							String name = (String) ((TreeItem) navigation.getSelectionModel().getSelectedItem())
									.getValue();

							Connection connection = null;
							try {
								// create a database connection
								connection = DriverManager.getConnection("jdbc:sqlite:full_records.db");

								Statement statement = connection.createStatement();
								statement.setQueryTimeout(30); // set timeout to 30 sec.

								ResultSet resultSet = statement
										.executeQuery("SELECT * FROM entry WHERE strain_name = '" + name + "'");

								StrainTab temp = new StrainTab(new Strain(resultSet));
								tabs.getTabs().add(temp);
								selectionModel.select(temp);

							} catch (SQLException e) {
								System.err.println(e.getMessage());
							}
						}
					}
				} else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
					// System.out.println("egg");
				}
			}

		});

		@SuppressWarnings("deprecation")
		ContextMenu rootContextMenu = ContextMenuBuilder.create()
				.items(MenuItemBuilder.create().text("View").onAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						Connection connection = null;
						try {
							// create a database connection
							connection = DriverManager.getConnection("jdbc:sqlite:full_records.db");

							Statement statement = connection.createStatement();
							statement.setQueryTimeout(30); // set timeout to 30 sec.

							ResultSet resultSet = statement.executeQuery("SELECT * FROM entry WHERE strain_name = '"
									+ navigation.getSelectionModel().getSelectedItem().getValue() + "'");

							StrainTab temp = new StrainTab(new Strain(resultSet), false);
							tabs.getTabs().add(temp);
							selectionModel.select(temp);

						} catch (SQLException e) {
							System.err.println(e.getMessage());
						}
					}
				}).build(), MenuItemBuilder.create().text("Edit").onAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						Connection connection = null;
						try {
							// create a database connection
							connection = DriverManager.getConnection("jdbc:sqlite:full_records.db");

							Statement statement = connection.createStatement();
							statement.setQueryTimeout(30); // set timeout to 30 sec.

							ResultSet resultSet = statement.executeQuery("SELECT * FROM entry WHERE strain_name = '"
									+ navigation.getSelectionModel().getSelectedItem().getValue() + "'");

							StrainTab temp = new StrainTab(new Strain(resultSet), true);
							tabs.getTabs().add(temp);
							selectionModel.select(temp);

						} catch (SQLException e) {
							System.err.println(e.getMessage());
						}
					}
				}).build()).build();

		navigation.setContextMenu(rootContextMenu);

		split.setDividerPositions(0.2f, 0.8f);

		// try {
		// Class.forName("org.sqlite.JDBC");
		// } catch (ClassNotFoundException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

		Connection connection = null;
		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:full_records.db");

			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.

			int i = 0;
//			ResultSet resultSet = statement.executeQuery("SELECT * FROM entry");
//
//			while (resultSet.next()) {
//				if (i++ == 10)
//					break;
//				Strain s = new Strain(resultSet);
//				StrainTab temp = new StrainTab(s);
//				tabs.getTabs().add(temp);
//			}

			ResultSet strains = statement.executeQuery("SELECT * FROM entry");
			ResultSetMetaData rsmd = strains.getMetaData();
			i = 1;
			while (strains.next()) {
				TreeItem<String> temp = new TreeItem<String>(strains.getString(1));
				int r = 1;
				while (r++ < rsmd.getColumnCount()) {
					String columnData = strains.getString(r);
					if (columnData != null)
						if (!columnData.isEmpty())
							temp.getChildren().add(new TreeItem<String>(rsmd.getColumnName(r) + ": " + columnData));
				}
				navigation.getRoot().getChildren().add(temp);
			}
		}

		catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) { // Use SQLException class instead.
				System.err.println(e);
			}
		}

		ScrollPane scrollTabs = new ScrollPane(tabs);
		scrollTabs.setFitToWidth(true);
		scrollTabs.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

		ScrollPane scrollSideBar = new ScrollPane(navigation);
		scrollSideBar.setFitToWidth(true);
		scrollSideBar.setFitToHeight(true);
		scrollSideBar.prefWidthProperty().bind(sideBar.prefWidthProperty());
		scrollSideBar.prefHeightProperty().bind(sideBar.heightProperty());
		scrollSideBar.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

		Search search = new Search(navigation);

		// Adds top and bottom to side bar
		sideBar.getChildren().add(search);
		sideBar.getChildren().add(scrollSideBar);

		// Adds left and right to split
		split.getItems().add(sideBar);
		split.getItems().add(scrollTabs);

		VBox c = new VBox();

		MenuOptions menu = new MenuOptions(tabs);

		c.getChildren().addAll(menu);
		c.getChildren().addAll(split);

		split.setPrefSize(bounds.getMaxX(), bounds.getMaxY());
		Scene scene = new Scene(c, bounds.getMaxX(), bounds.getMaxY());

		scene.widthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth,
					Number newSceneWidth) {
				split.setDividerPositions(0.2f, 0.8f);
			}
		});

		primaryStage.setScene(scene);
		primaryStage.show();
	}
}