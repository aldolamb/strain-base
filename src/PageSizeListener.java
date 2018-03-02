import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;

/**
 * <h1>PageSideListener</h1> Page size change listener for correcting visual
 * proportions. Adjusts proportions based on simple vs advanced search.
 * <p>
 * <b>Note:</b> N/A
 *
 * @author John Aldo Lamberti
 * @version 1.0
 * @since 03-01-2018
 */
public class PageSizeListener implements ChangeListener<Number> {

	public static final float RIGHT_PANE_NORMAL_SETTING = .35f, LEFT_PANE_NORMAL_SETTING = .65f,
			RIGHT_PANE_CONDENSED_SETTING = .2f, LEFT_PANE_CONDENSED_SETTING = .8f;

	SplitPane pane;
	SplitPane split;

	/**
	 * Creates a PageSizeListener for a SplitPane to alter another SplitPane
	 * <p>
	 * 
	 * @param pane
	 *            Search and Navigation SplitPane; detects orientation
	 * @param split
	 *            TabView and SideBar SplitPane; changes divider positions
	 */
	public PageSizeListener(SplitPane pane, SplitPane split) {
		this.pane = pane;
		this.split = split;
	}

	public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		if (pane.getOrientation() == Orientation.VERTICAL) {
			split.setDividerPositions(RIGHT_PANE_CONDENSED_SETTING, LEFT_PANE_CONDENSED_SETTING);
		} else {
			split.setDividerPositions(RIGHT_PANE_NORMAL_SETTING, LEFT_PANE_NORMAL_SETTING);
		}
	}
}
