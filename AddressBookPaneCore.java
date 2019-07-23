import javafx.scene.layout.Pane;

public interface AddressBookPaneCore {

	void addButtons(CommandButton... buttons);
	Pane getPane();
}
