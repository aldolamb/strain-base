import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TestListener implements ChangeListener<Number> {

	public static final float RIGHT_PANE_NORMAL_SETTING = .3f, LEFT_PANE_NORMAL_SETTING = .8f,
			RIGHT_PANE_CONDENSED_SETTING = .2f, LEFT_PANE_CONDENSED_SETTING = .8f;

	SplitPane pane;
	SplitPane split;

	public TestListener(SplitPane pane, SplitPane split) {
		this.pane = pane;
		this.split = split;
	}

	@Override
	public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		if (pane.getOrientation() == Orientation.VERTICAL) {
			split.setDividerPositions(RIGHT_PANE_CONDENSED_SETTING, LEFT_PANE_CONDENSED_SETTING);
		} else {
			split.setDividerPositions(RIGHT_PANE_NORMAL_SETTING, LEFT_PANE_NORMAL_SETTING);
		}
	}
}
