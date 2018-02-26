import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ContextMenuBuilder;
import javafx.scene.control.MenuItemBuilder;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class Navigator extends TreeView<String> {

	public Navigator(TreeItem<String> ti, TabView tabs) {
		super(ti);

		TreeItem<String> searchResults = new TreeItem<String>("Search Results");
		TreeItem<String> fullRecords = new TreeItem<String>("Full Records");
		TreeItem<String> labMembers = new TreeItem<String>("Lab Members");
		ti.getChildren().addAll(searchResults, fullRecords, labMembers);

		Connection connection = null;
		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:full_records.db");

			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.

			ResultSet strains = statement.executeQuery("SELECT * FROM entry");
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
				fullRecords.getChildren().add(temp);
			}

			ResultSet uniqueLab = statement.executeQuery("SELECT DISTINCT strain_created_by FROM entry");
			while (uniqueLab.next()) {
				TreeItem<String> member = new TreeItem<String>(uniqueLab.getString(1));

				Statement statement2 = connection.createStatement();
				statement2.setQueryTimeout(30); // set timeout to 30 sec.
				String test = "SELECT * FROM entry WHERE strain_created_by = '" + uniqueLab.getString(1) + "'";
				ResultSet memberInfo = statement2
						.executeQuery(test);
				ResultSetMetaData rsmd2 = memberInfo.getMetaData();
				while (memberInfo.next()) {
					TreeItem<String> currStrain = new TreeItem<String>(memberInfo.getString(1));
					int r = 1;
					while (r++ < rsmd2.getColumnCount()) {
						String columnData = memberInfo.getString(r);
						if (columnData != null)
							if (!columnData.isEmpty())
								currStrain.getChildren()
										.add(new TreeItem<String>(rsmd2.getColumnName(r) + ": " + columnData));
						// && !rsmd2.getColumnLabel(r).equals("strain_created_by")
					}
					member.getChildren().add(currStrain);
				}
				labMembers.getChildren().add(member);
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

		SingleSelectionModel<Tab> selectionModel = tabs.getSelectionModel();

		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {
						Node node = mouseEvent.getPickResult().getIntersectedNode();
						// Accept clicks only on node cells, and not on empty spaces of the TreeView
						if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
							String name = (String) ((TreeItem) getSelectionModel().getSelectedItem()).getValue();

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
									+ ((TreeItem) getSelectionModel().getSelectedItem()).getValue() + "'");

							Strain result = new Strain(resultSet);
							String resultName = result.get(result.getKeys().get(0));
							boolean found = false;
							for (Tab tab : tabs.getTabs()) {
								if (tab.getText().equals(resultName)) {
									found = true;
									((StrainTab) tab).readOnly();
									selectionModel.select(tab);
								}
							}

							if (!found) {
								StrainTab newTab = new StrainTab(result, false);
								tabs.getTabs().add(newTab);
								selectionModel.select(newTab);
							}
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
									+ ((TreeItem) getSelectionModel().getSelectedItem()).getValue() + "'");

							Strain result = new Strain(resultSet);
							String resultName = result.get(result.getKeys().get(0));
							boolean found = false;
							for (Tab tab : tabs.getTabs()) {
								if (tab.getText().equals(resultName)) {
									found = true;
									((StrainTab) tab).writable();
									selectionModel.select(tab);
								}
							}

							if (!found) {
								StrainTab newTab = new StrainTab(result, true);
								tabs.getTabs().add(newTab);
								selectionModel.select(newTab);
							}

						} catch (SQLException e) {
							System.err.println(e.getMessage());
						}
					}
				}).build()).build();

		setContextMenu(rootContextMenu);
	}

}
