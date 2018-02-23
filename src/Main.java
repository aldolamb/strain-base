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

		TabView tabs = new TabView();
		
		TreeItem<String> root = new TreeItem<String>("Results");
		Navigator navigation = new Navigator(root, tabs);

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

			ResultSet strains = statement.executeQuery("SELECT * FROM entry");
			ResultSetMetaData rsmd = strains.getMetaData();
			int i = 1;
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