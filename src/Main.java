import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
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
		split.setDividerPositions(0.2f, 0.8f);
		
		SplitPane sideBar = new SplitPane();
		sideBar.setOrientation(Orientation.VERTICAL);

		TabView tabs = new TabView();

		SingleSelectionModel<Tab> selectionModel = tabs.getSelectionModel();
		
		TreeItem<String> root = new TreeItem<String>("root");
		// root.setExpanded(true);
		Navigator navigation = new Navigator(root, tabs);
		navigation.setShowRoot(false);
//		TreeView<String> navigation = new TreeView<String>(root);
//
//		navigation.setOnMouseClicked(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent mouseEvent) {
//				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
//					if (mouseEvent.getClickCount() == 2) {
//						Node node = mouseEvent.getPickResult().getIntersectedNode();
//						// Accept clicks only on node cells, and not on empty spaces of the TreeView
//						if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
//							String name = (String) ((TreeItem) navigation.getSelectionModel().getSelectedItem())
//									.getValue();
//
//							Connection connection = null;
//							try {
//								// create a database connection
//								connection = DriverManager.getConnection("jdbc:sqlite:full_records.db");
//
//								Statement statement = connection.createStatement();
//								statement.setQueryTimeout(30); // set timeout to 30 sec.
//
//								ResultSet resultSet = statement
//										.executeQuery("SELECT * FROM entry WHERE strain_name = '" + name + "'");
//
//								StrainTab temp = new StrainTab(new Strain(resultSet));
//								tabs.getTabs().add(temp);
//								selectionModel.select(temp);
//
//							} catch (SQLException e) {
//								System.err.println(e.getMessage());
//							}
//						}
//					}
//				} else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
//					// System.out.println("egg");
//				}
//			}
//
//		});
//
//		@SuppressWarnings("deprecation")
//		ContextMenu rootContextMenu = ContextMenuBuilder.create()
//				.items(MenuItemBuilder.create().text("View").onAction(new EventHandler<ActionEvent>() {
//					@Override
//					public void handle(ActionEvent arg0) {
//						Connection connection = null;
//						try {
//							// create a database connection
//							connection = DriverManager.getConnection("jdbc:sqlite:full_records.db");
//
//							Statement statement = connection.createStatement();
//							statement.setQueryTimeout(30); // set timeout to 30 sec.
//
//							ResultSet resultSet = statement.executeQuery("SELECT * FROM entry WHERE strain_name = '"
//									+ navigation.getSelectionModel().getSelectedItem().getValue() + "'");
//
//							StrainTab temp = new StrainTab(new Strain(resultSet), false);
//							tabs.getTabs().add(temp);
//							selectionModel.select(temp);
//
//						} catch (SQLException e) {
//							System.err.println(e.getMessage());
//						}
//					}
//				}).build(), MenuItemBuilder.create().text("Edit").onAction(new EventHandler<ActionEvent>() {
//					@Override
//					public void handle(ActionEvent arg0) {
//						Connection connection = null;
//						try {
//							// create a database connection
//							connection = DriverManager.getConnection("jdbc:sqlite:full_records.db");
//
//							Statement statement = connection.createStatement();
//							statement.setQueryTimeout(30); // set timeout to 30 sec.
//
//							ResultSet resultSet = statement.executeQuery("SELECT * FROM entry WHERE strain_name = '"
//									+ navigation.getSelectionModel().getSelectedItem().getValue() + "'");
//
//							StrainTab temp = new StrainTab(new Strain(resultSet), true);
//							tabs.getTabs().add(temp);
//							selectionModel.select(temp);
//
//						} catch (SQLException e) {
//							System.err.println(e.getMessage());
//						}
//					}
//				}).build()).build();
//
//		navigation.setContextMenu(rootContextMenu);


		// try {
		// Class.forName("org.sqlite.JDBC");
		// } catch (ClassNotFoundException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

		
		tabs.prefHeightProperty().bind(split.prefHeightProperty());
		ScrollPane scrollTabs = new ScrollPane(tabs);
		scrollTabs.setFitToWidth(true);
		scrollTabs.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

		ScrollPane scrollSideBar = new ScrollPane(navigation);
		scrollSideBar.setFitToWidth(true);
		scrollSideBar.setFitToHeight(true);
		scrollSideBar.prefWidthProperty().bind(sideBar.prefWidthProperty());
		scrollSideBar.prefHeightProperty().bind(sideBar.heightProperty());
		scrollSideBar.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		
		Search search = new Search(navigation.getRoot().getChildren().get(0), split, sideBar);

		// Adds top and bottom to side bar
		sideBar.getItems().add(search);
		sideBar.getItems().add(scrollSideBar);

		sideBar.setDividerPositions(0.01f, 0.99f);
		sideBar.setResizableWithParent(sideBar.getItems().get(0), false);

		// Adds left and right to split
		split.getItems().add(sideBar);
		split.getItems().add(scrollTabs);

		VBox c = new VBox();

		MenuOptions menu = new MenuOptions(tabs);

		c.getChildren().addAll(menu);
		c.getChildren().addAll(split);

		split.setPrefSize(bounds.getMaxX(), bounds.getMaxY());
		Scene scene = new Scene(c, bounds.getMaxX(), bounds.getMaxY());
		
		scene.widthProperty().addListener(new TestListener(sideBar, split));
//		scene.widthProperty().addListener(new ChangeListener<Number>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth,
//					Number newSceneWidth) {
//				split.setDividerPositions(0.3f, 0.7f);
//			}
//		});
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}