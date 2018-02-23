import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

public class ClickableItem extends TextField {

	String text;

	public ClickableItem(TabPane tabs, String s) {
		text = s;
		this.setText(s);
		this.setOnMouseClicked(e -> {
			try {
				Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db");

				Statement statement = connection.createStatement();
				statement.setQueryTimeout(30); // set timeout to 30 sec.

				ResultSet resultSet = statement.executeQuery("SELECT * FROM entry WHERE strain_name = '" + text + "'");
				
				tabs.getTabs().add(new StrainTab(new Strain(resultSet)));
			} catch (SQLException ex) {
				System.err.println(ex.getMessage());
			}
		});
	}
}
