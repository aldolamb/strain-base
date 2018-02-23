import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;

public class StrainTab extends Tab {

	Strain s;

	public StrainTab() {
		this.s = new Strain();
		s.setStrain_name("New Strain");
		
		writable();
	}
	
	public StrainTab(Strain s) {
		this.s = s;
		
		readOnly();
	}
	
	public StrainTab(Strain s, Boolean edit) {
		this.s = s;
		
		if (edit)
			writable();
		else
			readOnly();
	}

	private void readOnly() {
		
		this.setText(s.getStrain_name());
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(30, 30, 30, 30));
		this.setContent(grid);

		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(50);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(50);
		grid.getColumnConstraints().add(0, column1);
		grid.getColumnConstraints().add(1, new ColumnConstraints());
		grid.getColumnConstraints().add(2, column2);

		Text strainName = new Text(s.getStrain_name());
		strainName.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(strainName, 0, 0, 3, 1);

		Label genotype = new Label("Genotype: " + s.getGenotype());
		grid.add(genotype, 0, 1);

//		Label genotypeField = new Label(s.getGenotype());
//		grid.add(genotypeField, 0, 2);

		Label strainBackground = new Label("Strain Background: " + s.getStrain_background());
		grid.add(strainBackground, 2, 1);

//		Label strainBackgroundField = new Label(s.getStrain_background());
//		grid.add(strainBackgroundField, 2, 2);

		Label DNA = new Label("DNA On Array: " + s.getDNA_on_array());
		grid.add(DNA, 0, 3);

//		Label DNAField = new Label(s.getDNA_on_array());
//		grid.add(DNAField, 0, 4);

		Label recordCreationDate = new Label("Record Creation Date: " + s.getRecord_creation_date());
		grid.add(recordCreationDate, 2, 3);

//		Label recordCreationDateField = new Label(s.getRecord_creation_date().toString());
//		grid.add(recordCreationDateField, 2, 4);

		Label strainCreationDate = new Label("Strain Creation Date: " + s.getStrain_creation_date().toString());
		grid.add(strainCreationDate, 0, 5);

//		Label strainCreationDateField = new Label(s.getStrain_creation_date().toString());
//		grid.add(strainCreationDateField, 0, 6);

		Label strainCreatedBy = new Label("Strain Created By: " + s.getStrain_created_by());
		grid.add(strainCreatedBy, 2, 5);

//		Label strainCreatedByField = new Label(s.getStrain_created_by());
//		grid.add(strainCreatedByField, 2, 6);

		Label location = new Label("Location: " + s.getLocation());
		grid.add(location, 0, 7);

//		Label locationField = new Label(s.getLocation());
//		grid.add(locationField, 0, 8);

		Label newLocation = new Label("New Location: " + s.getNew_location());
		grid.add(newLocation, 2, 7);

//		Label newLocationField = new Label(s.getNew_location());
//		grid.add(newLocationField, 2, 8);

		Label lineNumber = new Label("Line Number: " + s.getLine_number());
		grid.add(lineNumber, 0, 9);

//		Label lineNumberField = new Label(s.getLine_number() + "");
//		grid.add(lineNumberField, 0, 10);

		Label notes = new Label("Notes: " + s.getNotes());
		grid.add(notes, 2, 9);

//		Label notesField = new Label(s.getNotes());
//		grid.add(notesField, 2, 10);

		Label remarks = new Label("Remarks: " + s.getRemarks());
		grid.add(remarks, 0, 11);

//		Label remarksField = new Label(s.getRemarks());
//		grid.add(remarksField, 0, 12);

		Label result = new Label("Result: " + s.getResult());
		grid.add(result, 2, 11);

//		Label resultField = new Label(s.getResult());
//		grid.add(resultField, 2, 12);

		Label strainMaintenance = new Label("Strain Maintenance: " + s.getStrain_maintenance());
		grid.add(strainMaintenance, 0, 13);

//		Label strainMaintenanceField = new Label(s.getStrain_maintenance());
//		grid.add(strainMaintenanceField, 0, 14);

		Label expression = new Label("Expression:" + s.getExpression());
		grid.add(expression, 2, 13);

//		TextArea expressionField = new TextArea(s.getExpression());
//		grid.add(expressionField, 2, 14, 5, 14);
	}

	private void writable() {
		this.setText(s.getStrain_name());
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(30, 30, 30, 30));
		this.setContent(grid);

		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(50);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(50);
		grid.getColumnConstraints().add(0, column1);
		grid.getColumnConstraints().add(1, new ColumnConstraints());
		grid.getColumnConstraints().add(2, column2);

		Text strainName = new Text(s.getStrain_name());
		strainName.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(strainName, 0, 0, 3, 1);

		Label genotype = new Label("Genotype:");
		grid.add(genotype, 0, 1);

		TextField genotypeField = new TextField(s.getGenotype());
		grid.add(genotypeField, 0, 2);

		Label strainBackground = new Label("Strain Background:");
		grid.add(strainBackground, 2, 1);

		TextField strainBackgroundField = new TextField(s.getStrain_background());
		grid.add(strainBackgroundField, 2, 2);

		Label DNA = new Label("DNA On Array:");
		grid.add(DNA, 0, 3);

		TextField DNAField = new TextField(s.getDNA_on_array());
		grid.add(DNAField, 0, 4);

		Label recordCreationDate = new Label("Record Creation Date:");
		grid.add(recordCreationDate, 2, 3);

		TextField recordCreationDateField = new TextField(s.getRecord_creation_date().toString());
		grid.add(recordCreationDateField, 2, 4);

		Label strainCreationDate = new Label("Strain Creation Date:");
		grid.add(strainCreationDate, 0, 5);

		TextField strainCreationDateField = new TextField(s.getStrain_creation_date().toString());
		grid.add(strainCreationDateField, 0, 6);

		Label strainCreatedBy = new Label("Strain Created By:");
		grid.add(strainCreatedBy, 2, 5);

		TextField strainCreatedByField = new TextField(s.getStrain_created_by());
		grid.add(strainCreatedByField, 2, 6);

		Label location = new Label("Location:");
		grid.add(location, 0, 7);

		TextField locationField = new TextField(s.getLocation());
		grid.add(locationField, 0, 8);

		Label newLocation = new Label("New Location:");
		grid.add(newLocation, 2, 7);

		TextField newLocationField = new TextField(s.getNew_location());
		grid.add(newLocationField, 2, 8);

		Label lineNumber = new Label("Line Number: ");
		grid.add(lineNumber, 0, 9);

		TextField lineNumberField = new TextField(s.getLine_number() + "");
		grid.add(lineNumberField, 0, 10);

		Label notes = new Label("Notes:");
		grid.add(notes, 2, 9);

		TextField notesField = new TextField(s.getNotes());
		grid.add(notesField, 2, 10);

		Label remarks = new Label("Remarks:");
		grid.add(remarks, 0, 11);

		TextField remarksField = new TextField(s.getRemarks());
		grid.add(remarksField, 0, 12);

		Label result = new Label("Result:");
		grid.add(result, 2, 11);

		TextField resultField = new TextField(s.getResult());
		grid.add(resultField, 2, 12);

		Label strainMaintenance = new Label("Strain Maintenance:");
		grid.add(strainMaintenance, 0, 13);

		TextField strainMaintenanceField = new TextField(s.getStrain_maintenance());
		grid.add(strainMaintenanceField, 0, 14);

		Label expression = new Label("Expression:");
		grid.add(expression, 2, 13);

		TextArea expressionField = new TextArea(s.getExpression());
		grid.add(expressionField, 2, 14, 5, 14);
		
		// grid.setGridLinesVisible(true);
		// HTMLEditor expressionField = new HTMLEditor();
		// grid.add(expressionField, 3, 7);
		// expressionField.setPrefWidth(10);
		// expressionField.setWrapText(true);

		// Label userName = new Label("User Name:");
		// grid.add(userName, 0, 3);
		//
		// TextField userTextField = new TextField();
		// grid.add(userTextField, 1, 3);
	}
}