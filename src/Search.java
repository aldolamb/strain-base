import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Search extends GridPane {

	TreeView<String> tree;

	public Search(TreeView<String> t) {
		tree = t;
		minimize();
	}

	public void minimize() {
		this.getChildren().clear();

		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(10, 10, 10, 10));

		getColumnConstraints().add(new ColumnConstraints());
		getColumnConstraints().get(0).setHgrow(Priority.ALWAYS);

		int r = 0;

		TextField strainName = new TextField("");
		strainName.setPromptText("Strain Name");
		this.add(strainName, 0, r++, 1, 1);
		strainName.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER))
					query(strainName.getText());
			}
		});

		Button query = new Button("O");
		this.add(query, 1, 0);
		query.setAlignment(Pos.CENTER_RIGHT);
		query.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");

		query.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				query(strainName.getText());
			}
		});

		Button expand = new Button("+");
		this.add(expand, 2, 0);
		expand.setAlignment(Pos.CENTER_RIGHT);
		expand.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");

		expand.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				getColumnConstraints().remove(0, getColumnConstraints().size());
				expand();
			}
		});
	}

	public void expand() {
		this.getChildren().clear();

		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(10, 10, 10, 10));

		getColumnConstraints().add(new ColumnConstraints());
		getColumnConstraints().get(0).setHgrow(Priority.ALWAYS);

		int r = 0;

		TextField strainName = new TextField("");
		strainName.setPromptText("Strain Name");
		this.add(strainName, 0, r++, 1, 1);
		strainName.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				Strain temp = new Strain();
				temp.setStrain_name(strainName.getText());
				if (ke.getCode().equals(KeyCode.ENTER))
					advancedQuery(temp);
			}
		});

		Button expand = new Button("-");
		this.add(expand, 1, 0);
		expand.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
		expand.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				getColumnConstraints().remove(0, getColumnConstraints().size());
				minimize();
			}
		});

		TextField genotype = new TextField("");
		genotype.setPromptText("Genotype");
		this.add(genotype, 0, r++, 2, 1);

		TextField strainBackground = new TextField("");
		strainBackground.setPromptText("Strain Background");
		this.add(strainBackground, 0, r++, 2, 1);

		TextField DNA = new TextField("");
		DNA.setPromptText("DNA On Array");
		this.add(DNA, 0, r++, 2, 1);

		TextField recordCreationDate = new TextField("");
		recordCreationDate.setPromptText("Record Creation Date");
		this.add(recordCreationDate, 0, r++, 2, 1);

		TextField strainCreationDate = new TextField("");
		strainCreationDate.setPromptText("Strain Creation Date");
		this.add(strainCreationDate, 0, r++, 2, 1);

		TextField strainCreatedBy = new TextField("");
		strainCreatedBy.setPromptText("Strain Created By");
		this.add(strainCreatedBy, 0, r++, 2, 1);

		TextField location = new TextField("");
		location.setPromptText("Location");
		this.add(location, 0, r++, 2, 1);

		TextField newLocation = new TextField("");
		newLocation.setPromptText("New Location");
		this.add(newLocation, 0, r++, 2, 1);

		TextField lineNumber = new TextField("");
		lineNumber.setPromptText("Line Number");
		this.add(lineNumber, 0, r++, 2, 1);

		TextField notes = new TextField("");
		notes.setPromptText("Notes");
		this.add(notes, 0, r++, 2, 1);

		TextField remarks = new TextField("");
		remarks.setPromptText("Remarks");
		this.add(remarks, 0, r++, 2, 1);

		TextField result = new TextField("");
		result.setPromptText("Result");
		this.add(result, 0, r++, 2, 1);
		
		TextArea strainMaintenance = new TextArea("");
		strainMaintenance.setPromptText("Expression");
		this.add(strainMaintenance, 0, r++, 2, 1);

		TextArea expression = new TextArea("");
		expression.setPromptText("Expression");
		this.add(expression, 0, r++, 2, 1);

		Button search = new Button("Search");
		search.setPrefSize(getWidth(), getHeight());
		this.add(search, 0, r++, 2, 1);
		search.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");

		search.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Strain temp;
//				if (!lineNumber.getText().isEmpty()) {
				temp = new Strain(strainName.getText(),
										genotype.getText(),
										strainBackground.getText(),
										DNA.getText(),
										recordCreationDate.getText(),
										strainCreationDate.getText(),
										strainCreatedBy.getText(),
										location.getText(),
										newLocation.getText(),
										Integer.parseInt(lineNumber.getText()),
										notes.getText(),
										remarks.getText(),
										result.getText(),
										strainMaintenance.getText(),
										expression.getText());
//				} else {
//					temp = new Strain(strainName.getText(),
//							genotype.getText(),
//							strainBackground.getText(),
//							DNA.getText(),
//							recordCreationDate.getText(),
//							strainCreationDate.getText(),
//							strainCreatedBy.getText(),
//							location.getText(),
//							newLocation.getText(),
//							-1,
//							notes.getText(),
//							remarks.getText(),
//							result.getText(),
//							strainMaintenance.getText(),
//							expression.getText());
//				}
				advancedQuery(temp);
			}
		});
	}

	public void query(String s) {
		tree.getRoot().getChildren().clear();

		Connection connection = null;
		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:full_records.db");

			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.

			ResultSet strains;
			if (s.isEmpty())
				strains = statement.executeQuery("SELECT * FROM entry");
			else
				strains = statement.executeQuery("SELECT * FROM entry WHERE strain_name LIKE '%" + s + "%'");

			// while (strains.next())
			// tree.getRoot().getChildren().add(new TreeItem<String>(strains.getString(i)));

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
				tree.getRoot().getChildren().add(temp);
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
	}
	
	public void advancedQuery(Strain s) {
		tree.getRoot().getChildren().clear();
		
		Connection connection = null;
		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:full_records.db");

			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.

			String query = "SELECT * FROM entry WHERE ";
			boolean first = true;
			Iterator<String> it1 = s.getColumns().iterator();
			Iterator<String> it2 = s.getValues().iterator();
			
			while (it1.hasNext() && it2.hasNext()) {
				String column = it1.next(),
						value = it2.next();
				if (value != null || !value.isEmpty()) {
					System.out.println(value + " " + value.isEmpty());
					if (first) {
						query += column + " LIKE '%" + value + "%'";
						first = false;
					}
					else
						query += " AND " + column + " LIKE '%" + value + "%'";
				}
			}
			
			System.out.println(query);
			ResultSet strains;
			if (first) 
				strains = statement.executeQuery("SELECT * FROM entry");
			else
				strains = statement.executeQuery(query);

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
				tree.getRoot().getChildren().add(temp);
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
	}
}