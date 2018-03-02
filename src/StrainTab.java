import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * <h1>StrainTab</h1> StrainTab is a custom Tab used inside of the TabView to
 * display a given Strain.
 * <p>
 *
 * @author John Aldo Lamberti
 * @version 1.0
 * @since 03-01-2018
 * @see Tab
 * @see TabView
 * @see Strain
 */
public class StrainTab extends Tab {

	Strain s;
	List<String> keys;

	/**
	 * Creates an empty StrainTab with a new Strain titled New Strain.
	 * <p>
	 * Leaves all input fields empty.
	 * 
	 * @see Strain
	 */
	public StrainTab() {
		this.s = new Strain();
		keys = s.getKeys();
		s.set(keys.get(0), "New Strain");

		writable();
	}

	/**
	 * Creates a read only StrainTab from a given strain.
	 * <p>
	 * Fills the tab out with the given strain information.
	 * 
	 * @param strain
	 *            Strain to fill out strain tab.
	 * @see Strain
	 */
	public StrainTab(Strain strain) {
		this.s = strain;
		keys = s.getKeys();

		readOnly();
	}

	/**
	 * Creates a StrainTab from a given strain with a boolean determining whether it
	 * is writable or read only.
	 * <p>
	 * 
	 * @param strain
	 *            Strain to fill out strain tab.
	 * @param edit
	 *            boolean determining whether the tab is writable or read only.
	 * @see Strain
	 */
	public StrainTab(Strain strain, Boolean edit) {
		this.s = strain;
		keys = s.getKeys();

		if (edit)
			writable();
		else
			readOnly();
	}

	/**
	 * Sets the style of the StrianTab to read only.
	 * <p>
	 * Labels instead of TextFields
	 */
	public void readOnly() {
		this.setText(s.get(keys.get(0)));

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

		Text strainName = new Text(s.get(keys.get(0)));
		strainName.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(strainName, 0, 0, 3, 1);

		int x = 0;
		int y = 1;
		for (String key : keys.subList(1, keys.size())) {
			Label temp = new Label(key + ": " + s.get(key));
			if (x == 0) {
				grid.add(temp, x, y);
				x = 2;
			} else {
				grid.add(temp, x, y);
				x = 0;
				y += 2;
			}
		}
	}

	/**
	 * Sets the style of the StrianTab to writable.
	 * <p>
	 * TextFields instead of Labels
	 */
	public void writable() {
		this.setText(s.get(keys.get(0)));

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

		Text strainName = new Text(s.get(keys.get(0)));
		strainName.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(strainName, 0, 0, 3, 1);

		int x = 0;
		int y = 1;
		for (String key : keys.subList(1, keys.size())) {
			Label label = new Label(key);
			TextField field = new TextField(s.get(key));
			if (x == 0) {
				grid.add(label, x, y);
				grid.add(field, x, y + 1);
				x = 2;
			} else {
				grid.add(label, x, y);
				grid.add(field, x, y + 1);
				x = 0;
				y += 2;
			}
		}
	}
}