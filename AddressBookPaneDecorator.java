import javafx.scene.layout.Pane;

public abstract class AddressBookPaneDecorator implements AddressBookPaneCore {

	protected AddressBookPaneCore core;

	public AddressBookPaneDecorator(AddressBookPaneCore core) {		
		this.core= core;		
	}

	@Override
	public void addButtons(CommandButton... buttons) {
		core.addButtons(buttons);
	}

	@Override
	public Pane getPane() {
		return core.getPane();
	}

}
