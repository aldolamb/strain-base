import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SearchWindow extends Stage {

	public SearchWindow(Stage mainStage) {
		this.setTitle("Search Database");

		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		this.setX(bounds.getMinX() + bounds.getWidth() * (1.0 / 4.0));
		this.setY(bounds.getMinY() + bounds.getHeight() * (1.0 / 4.0));
		this.setWidth(bounds.getWidth() * (1.0 / 2.0));
		this.setHeight(bounds.getHeight() * (1.0 / 2.0));
		this.setResizable(false);
		this.initOwner(mainStage);
		this.setAlwaysOnTop(true);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(30, 30, 30, 30));
		Scene scene = new Scene(grid, this.getWidth(), this.getHeight());
		this.setScene(scene);

		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(25);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(25);
		ColumnConstraints column3 = new ColumnConstraints();
		column3.setPercentWidth(25);
		ColumnConstraints column4 = new ColumnConstraints();
		column4.setPercentWidth(25);
		grid.getColumnConstraints().add(0, column1);
		grid.getColumnConstraints().add(1, column2);
		grid.getColumnConstraints().add(2, column3);
		grid.getColumnConstraints().add(3, column4);
		grid.getColumnConstraints().add(0, column1);
		grid.getColumnConstraints().add(1, new ColumnConstraints());
		grid.getColumnConstraints().add(2, column2);
		
		TextField genotypeField = new TextField("");
		genotypeField.setPromptText("Genotype");
		grid.add(genotypeField, 0, 0);

		TextField strainBackgroundField = new TextField("");
		strainBackgroundField.setPromptText("Strain Background");
		grid.add(strainBackgroundField, 0, 1);

		TextField DNAField = new TextField("");
		DNAField.setPromptText("DNA On Array");
		grid.add(DNAField, 0, 2);

		TextField recordCreationDateField = new TextField("");
		recordCreationDateField.setPromptText("Record Creation Date");
		grid.add(recordCreationDateField, 0, 3);

		TextField strainCreationDateField = new TextField("");
		strainCreationDateField.setPromptText("Strain Creation Date");
		grid.add(strainCreationDateField, 0, 4);

		TextField strainCreatedByField = new TextField("");
		strainCreatedByField.setPromptText("Strain Created By");
		grid.add(strainCreatedByField, 0, 5);

		TextField locationField = new TextField("");
		locationField.setPromptText("Location");
		grid.add(locationField, 0, 6);

		TextField newLocationField = new TextField("");
		newLocationField.setPromptText("New Location");
		grid.add(newLocationField, 0, 7);


		TextField lineNumberField = new TextField("");
		lineNumberField.setPromptText("Line Number");
		grid.add(lineNumberField, 0, 8);

		TextField notesField = new TextField("");
		notesField.setPromptText("Notes");
		grid.add(notesField, 0, 9);

		TextField remarksField = new TextField("");
		remarksField.setPromptText("Remarks");
		grid.add(remarksField, 0, 10);

		TextField resultField = new TextField("");
		resultField.setPromptText("Result");
		grid.add(resultField, 0, 11);

//		Label strainMaintenance = new Label("Strain Maintenance:");
//		grid.add(strainMaintenance, 0, 7);
//
//		TextField strainMaintenanceField = new TextField("");
//		grid.add(strainMaintenanceField, 1, 7);
		
//		TextField expressionField = new TextField("");
		TextArea expressionField = new TextArea("");
		expressionField.setPromptText("Expression");
		grid.add(expressionField, 1, 12, 1, 2);

		
		this.show();
	}
}