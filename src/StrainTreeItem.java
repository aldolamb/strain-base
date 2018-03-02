import javafx.scene.control.TreeItem;

/**
 * <h1>StrainTreeItem</h1> Used to identify Strains found in TreeView.
 * <p>
 * <b>Note:</b> Will add more functionality in future if applicable.
 *
 * @author John Aldo Lamberti
 * @version 1.0
 * @since 03-01-2018
 */
public class StrainTreeItem extends TreeItem<String> {

	/**
	 * Creates an empty StrainTreeItem with a Strain.
	 * 
	 * @see Strain
	 */
	public StrainTreeItem(String s) {
		super(s);
	}

}
