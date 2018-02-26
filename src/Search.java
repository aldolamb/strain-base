import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class Search extends GridPane {

	TreeItem<String> tree;
	SplitPane split, sideBar;
	ArrayList<String> entries = new ArrayList<String>();

	public Search(TreeItem<String> tree, SplitPane split, SplitPane sideBar) {
		this.tree = tree;
		this.split = split;
		this.sideBar = sideBar;
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
				split.setDividerPositions(0.35f, 0.65f);
				sideBar.setDividerPositions(3.0/7, 4.0/7);
				sideBar.setResizableWithParent(sideBar.getItems().get(0), true);
				sideBar.setOrientation(Orientation.HORIZONTAL);
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
				split.setDividerPositions(0.2f, 0.8f);
				sideBar.setDividerPositions(0.01f, 0.99f);
				sideBar.setResizableWithParent(sideBar.getItems().get(0), false);
				sideBar.setOrientation(Orientation.VERTICAL);
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
		
		TextField strainMaintenance = new TextField("");
		strainMaintenance.setPromptText("Expression");
		this.add(strainMaintenance, 0, r++, 2, 1);

		TextField expression = new TextField("");
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
				advancedQuery(temp);
			}
		});
	}

	public void query(String s) {
		tree.getChildren().clear();
		tree.setExpanded(true);

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
				tree.getChildren().add(temp);
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
		tree.getChildren().clear();
		tree.setExpanded(true);
		
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
				if (!(value == null || value.isEmpty())) {
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
				tree.getChildren().add(temp);
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