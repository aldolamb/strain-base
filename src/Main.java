import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("JavaFX Welcome");

		// Get screen dimensions, set program to fullscreen
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		primaryStage.setX(bounds.getMinX());
		primaryStage.setY(bounds.getMinY());
		primaryStage.setWidth(bounds.getWidth());
		primaryStage.setHeight(bounds.getHeight());

		// Potential login, will require username and password
		// - will also take database choice
		// new Login();

		SplitPane split = new SplitPane();
		split.setDividerPositions(0.2f, 0.8f);

		SplitPane sideBar = new SplitPane();
		sideBar.setOrientation(Orientation.VERTICAL);

		TabView tabs = new TabView();

		Navigator navigation = new Navigator(tabs);
		navigation.setShowRoot(false);

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
		SplitPane.setResizableWithParent(sideBar.getItems().get(0), false);

		// Adds left and right to split
		split.getItems().add(sideBar);
		split.getItems().add(scrollTabs);

		VBox c = new VBox();

		MenuOptions menu = new MenuOptions(tabs, primaryStage);

		c.getChildren().addAll(menu);
		c.getChildren().addAll(split);

		split.setPrefSize(bounds.getMaxX(), bounds.getMaxY());
		Scene scene = new Scene(c, bounds.getMaxX(), bounds.getMaxY());

		scene.widthProperty().addListener(new PageSizeListener(sideBar, split));

		final KeyCombination keyCombinationShiftC = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
		final KeyCombination keyCombinationShiftN = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (keyCombinationShiftC.match(event))
					tabs.getTabs().remove(tabs.getSelectionModel().getSelectedIndex());
				else if (keyCombinationShiftN.match(event)) {
					StrainTab newTab = new StrainTab();
					tabs.getTabs().add(newTab);
					tabs.getSelectionModel().select(newTab);
				}
			}
		});

		primaryStage.setScene(scene);
		primaryStage.show();
	}
}