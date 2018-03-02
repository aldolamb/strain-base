import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * <h1>Search</h1> Search Bar. Provides simple and advanced search. Runs
 * queries.
 * <p>
 * <b>Note:</b> N/A
 *
 * @author John Aldo Lamberti
 * @version 1.0
 * @since 03-01-2018
 */
public class Search extends GridPane {

	TreeItem<String> tree;
	SplitPane split, sideBar;
	Strain data;
	List<TextField> fields;

	/**
	 * Creates a Search area to add items to the Navigation tree.
	 * <p>
	 * 
	 * @see Navigation
	 * @param tree
	 *            TreeView for outputing results (Navigation)
	 * @param split
	 *            SplitPane accessed for changing divider positions on change of
	 *            search complexity
	 * @param sideBar
	 *            SplitPane accessed for changing divider positions on change of
	 *            search complexity
	 */
	public Search(TreeItem<String> tree, SplitPane split, SplitPane sideBar) {
		this.tree = tree;
		this.split = split;
		this.sideBar = sideBar;
		data = new Strain();
		fields = new ArrayList<TextField>();

		for (String key : data.getKeys()) {
			TextField entry = new TextField(data.get(key));
			entry.setPromptText(key);
			fields.add(entry);
		}

		minimize();
	}

	/**
	 * Minimized search and navigation view.
	 * <p>
	 * Contains simple search view.
	 */
	public void minimize() {
		this.getChildren().clear();

		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(10, 10, 10, 10));

		getColumnConstraints().add(new ColumnConstraints());
		getColumnConstraints().get(0).setHgrow(Priority.ALWAYS);

		TextField searchBox = new TextField("");
		searchBox.setPromptText("Search Database");
		this.add(searchBox, 0, 0);
		searchBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER))
					query(searchBox.getText());
			}
		});

		Button query = new Button("O");
		query.setTooltip(new Tooltip("Search"));
		this.add(query, 1, 0);
		query.setAlignment(Pos.CENTER_RIGHT);
		query.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");

		query.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				query(searchBox.getText());
			}
		});

		Button expand = new Button("+");
		expand.setTooltip(new Tooltip("Advanced Search"));
		this.add(expand, 2, 0);
		expand.setAlignment(Pos.CENTER_RIGHT);
		expand.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");

		expand.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				getColumnConstraints().remove(0, getColumnConstraints().size());
				split.setDividerPositions(0.35f, 0.65f);
				sideBar.setDividerPositions(3.0 / 7, 4.0 / 7);
				SplitPane.setResizableWithParent(sideBar.getItems().get(0), true);
				sideBar.setOrientation(Orientation.HORIZONTAL);
				expand();
			}
		});
	}

	/**
	 * Expanded search and navigation view.
	 * <p>
	 * Contains advanced search view.
	 */
	public void expand() {
		this.getChildren().clear();

		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(10, 10, 10, 10));

		getColumnConstraints().add(new ColumnConstraints());
		getColumnConstraints().get(0).setHgrow(Priority.ALWAYS);

		int r = 0;

		for (TextField entry : fields) {
			entry.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent ke) {
					if (ke.getCode().equals(KeyCode.ENTER)) {
						Strain temp = new Strain();
						for (TextField entry : fields)
							temp.set(entry.getPromptText(), entry.getText());

						advancedQuery(temp);
					}
				}
			});
			if (r == 0)
				add(entry, 0, r++, 1, 1);
			else
				add(entry, 0, r++, 2, 1);
		}

		Button expand = new Button("-");
		expand.setTooltip(new Tooltip("Simple Search"));
		this.add(expand, 1, 0);
		expand.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
		expand.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				getColumnConstraints().remove(0, getColumnConstraints().size());
				split.setDividerPositions(0.2f, 0.8f);
				sideBar.setDividerPositions(0.01f, 0.99f);
				SplitPane.setResizableWithParent(sideBar.getItems().get(0), false);
				sideBar.setOrientation(Orientation.VERTICAL);
				minimize();
			}
		});

		Button search = new Button("Search");
		search.setPrefSize(getWidth(), getHeight());
		this.add(search, 0, r++, 2, 1);
		search.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");

		search.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Strain temp = new Strain();
				for (TextField entry : fields)
					temp.set(entry.getPromptText(), entry.getText());

				advancedQuery(temp);
			}
		});
	}

	/**
	 * Simple query that takes one parameter.
	 * <p>
	 * 
	 * @param s
	 *            String to construct query from.
	 */
	public void query(String s) {
		tree.getChildren().clear();
		for (TreeItem<String> temp : tree.getParent().getChildren())
			temp.setExpanded(false);

		if (!s.isEmpty()) {
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

	/**
	 * Advanced query that takes a Strain.
	 * <p>
	 * 
	 * @param strain
	 *            Strain to construct query from.
	 */
	public void advancedQuery(Strain strain) {
		tree.getChildren().clear();
		for (TreeItem<String> temp : tree.getParent().getChildren())
			temp.setExpanded(false);
		if (!strain.isEmpty()) {
			tree.setExpanded(true);
			Connection connection = null;
			try {
				// create a database connection
				connection = DriverManager.getConnection("jdbc:sqlite:full_records.db");

				Statement statement = connection.createStatement();
				statement.setQueryTimeout(30); // set timeout to 30 sec.

				String query = "SELECT * FROM entry WHERE ";
				boolean first = true;
				for (String key : strain.getKeys()) {
					String value = strain.get(key);
					if (!(value == null || value.isEmpty())) {
						if (first) {
							query += key + " LIKE '%" + value + "%'";
							first = false;
						} else
							query += " AND " + key + " LIKE '%" + value + "%'";
					}
				}

				System.out.println(query);
				ResultSet strains;
				if (first)
					strains = statement.executeQuery("SELECT * FROM entry");
				else
					strains = statement.executeQuery(query);

				ResultSetMetaData rsmd = strains.getMetaData();
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
}