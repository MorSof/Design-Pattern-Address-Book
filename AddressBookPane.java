import java.io.IOException;
import java.io.RandomAccessFile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

class AddressBookPane extends GridPane implements AddressBookPaneCore {

	private RandomAccessFile raf;
	// Text fields
	private TextField tfName = new TextField();
	private TextField tfStreet = new TextField();
	private TextField tfCity = new TextField();
	private TextField tfState = new TextField();
	private TextField tfZip = new TextField();
	// Buttons
	private FirstButton btFirst;
	private NextButton btNext;
	private PreviousButton btPrevious;
	private LastButton btLast;
	private FlowPane jpButton;

	public static int paneCounter = 0;
	public static final int MAX_TOTAL_PANES = 3;
	public static final int MAX_EXTERNAL_PANES = 1;

	public EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent arg0) {
			((Command) arg0.getSource()).Execute();
		}
	};

	// Constructor
	private AddressBookPane() { // Open or create a random access file
		try {
			raf = new RandomAccessFile("address.dat", "rw");
		} catch (IOException ex) {
			System.out.print("Error: " + ex);
			System.exit(0);
		}

		tfState.setAlignment(Pos.CENTER_LEFT);
		tfState.setPrefWidth(70);
		tfZip.setPrefWidth(60);
		btFirst = new FirstButton(this, raf);
		btNext = new NextButton(this, raf);
		btPrevious = new PreviousButton(this, raf);
		btLast = new LastButton(this, raf);

		Label state = new Label("State");
		Label zp = new Label("Zip");
		Label name = new Label("Name");
		Label street = new Label("Street");
		Label city = new Label("City");
		// Panel p1 for holding labels Name, Street, and City
		GridPane p1 = new GridPane();
		p1.add(name, 0, 0);
		p1.add(street, 0, 1);
		p1.add(city, 0, 2);
		p1.setAlignment(Pos.CENTER_LEFT);
		p1.setVgap(8);
		p1.setPadding(new Insets(0, 2, 0, 2));
		GridPane.setVgrow(name, Priority.ALWAYS);
		GridPane.setVgrow(street, Priority.ALWAYS);
		GridPane.setVgrow(city, Priority.ALWAYS);
		// City Row
		GridPane adP = new GridPane();
		adP.add(tfCity, 0, 0);
		adP.add(state, 1, 0);
		adP.add(tfState, 2, 0);
		adP.add(zp, 3, 0);
		adP.add(tfZip, 4, 0);
		adP.setAlignment(Pos.CENTER_LEFT);
		GridPane.setHgrow(tfCity, Priority.ALWAYS);
		GridPane.setVgrow(tfCity, Priority.ALWAYS);
		GridPane.setVgrow(tfState, Priority.ALWAYS);
		GridPane.setVgrow(tfZip, Priority.ALWAYS);
		GridPane.setVgrow(state, Priority.ALWAYS);
		GridPane.setVgrow(zp, Priority.ALWAYS);
		// Panel p4 for holding jtfName, jtfStreet, and p3
		GridPane p4 = new GridPane();
		p4.add(tfName, 0, 0);
		p4.add(tfStreet, 0, 1);
		p4.add(adP, 0, 2);
		p4.setVgap(1);
		GridPane.setHgrow(tfName, Priority.ALWAYS);
		GridPane.setHgrow(tfStreet, Priority.ALWAYS);
		GridPane.setHgrow(adP, Priority.ALWAYS);
		GridPane.setVgrow(tfName, Priority.ALWAYS);
		GridPane.setVgrow(tfStreet, Priority.ALWAYS);
		GridPane.setVgrow(adP, Priority.ALWAYS);
		// Place p1 and p4 into jpAddress
		GridPane jpAddress = new GridPane();
		jpAddress.add(p1, 0, 0);
		jpAddress.add(p4, 1, 0);
		GridPane.setHgrow(p1, Priority.NEVER);
		GridPane.setHgrow(p4, Priority.ALWAYS);
		GridPane.setVgrow(p1, Priority.ALWAYS);
		GridPane.setVgrow(p4, Priority.ALWAYS);
		// Set the panel with line border
		jpAddress.setStyle("-fx-border-color: grey;" + " -fx-border-width: 1;" + " -fx-border-style: solid outside ;");
		// Add buttons to a panel
		jpButton = new FlowPane();
		jpButton.setHgap(5);
		jpButton.getChildren().addAll(btFirst, btNext, btPrevious, btLast);
		jpButton.setAlignment(Pos.CENTER);
		GridPane.setVgrow(jpButton, Priority.NEVER);
		GridPane.setVgrow(jpAddress, Priority.ALWAYS);
		GridPane.setHgrow(jpButton, Priority.ALWAYS);
		GridPane.setHgrow(jpAddress, Priority.ALWAYS);
		// Add jpAddress and jpButton to the stage
		this.setVgap(5);
		this.add(jpAddress, 0, 0);
		this.add(jpButton, 0, 1);
		// btAdd.setOnAction(ae);
		btFirst.setOnAction(ae);
		btNext.setOnAction(ae);
		btPrevious.setOnAction(ae);
		btLast.setOnAction(ae);
		btFirst.Execute();

	}

	@Override
	public void addButtons(CommandButton... buttons) {
		jpButton.getChildren().addAll(buttons);
		for (CommandButton commandButton : buttons) {
			commandButton.setPane(this);
			commandButton.setRaf(raf);
			commandButton.setOnAction(ae);
		}
	}

	@Override
	public Pane getPane() {

		return this;
	}

	public static AddressBookPaneCore getInstance() {
		if (paneCounter < MAX_EXTERNAL_PANES) {
			paneCounter++;
			return new AddressBookPaneExternalButtons(new AddressBookPane());
		} else if (paneCounter < MAX_TOTAL_PANES) {
			paneCounter++;
			return new AddressBookPane();
		}
		return null;
	}

	public void actionHandled(ActionEvent e) {
		((Command) e.getSource()).Execute();
	}

	public void SetName(String text) {
		tfName.setText(text);
	}

	public void SetStreet(String text) {
		tfStreet.setText(text);
	}

	public void SetCity(String text) {
		tfCity.setText(text);
	}

	public void SetState(String text) {
		tfState.setText(text);
	}

	public void SetZip(String text) {
		tfZip.setText(text);
	}

	public String GetName() {
		return tfName.getText();
	}

	public String GetStreet() {
		return tfStreet.getText();
	}

	public String GetCity() {
		return tfCity.getText();
	}

	public String GetState() {
		return tfState.getText();
	}

	public String GetZip() {
		return tfZip.getText();
	}
}

class CommandButton extends Button implements Command {
	public final static int UNICODE_SIZE = 2;
	public final static int START_POS = 0;
	public final static int NAME_SIZE = 32;
	public final static int STREET_SIZE = 32;
	public final static int CITY_SIZE = 20;
	public final static int STATE_SIZE = 10;
	public final static int ZIP_SIZE = 5;
	public final static int RECORD_SIZE = (NAME_SIZE + STREET_SIZE + CITY_SIZE + STATE_SIZE + ZIP_SIZE);
	// RECORD_SIZE=99

	protected AddressBookPane p;
	protected RandomAccessFile raf;

	public CommandButton(AddressBookPane pane, RandomAccessFile r) {
		super();
		p = pane;
		raf = r;

	}

	public CommandButton() {
		super();
	}

	public void Execute() {
	}

	/** Write a record at the end of the file */
	public void writeAddress() {
		try {
			raf.seek(raf.length());
			FixedLengthStringIO.writeFixedLengthString(p.GetName(), NAME_SIZE, raf);
			FixedLengthStringIO.writeFixedLengthString(p.GetStreet(), STREET_SIZE, raf);
			FixedLengthStringIO.writeFixedLengthString(p.GetCity(), CITY_SIZE, raf);
			FixedLengthStringIO.writeFixedLengthString(p.GetState(), STATE_SIZE, raf);
			FixedLengthStringIO.writeFixedLengthString(p.GetZip(), ZIP_SIZE, raf);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/** Read a record at the specified position */
	public void readAddress(long position) throws IOException {
		raf.seek(position);
		String name = FixedLengthStringIO.readFixedLengthString(NAME_SIZE, raf);
		String street = FixedLengthStringIO.readFixedLengthString(STREET_SIZE, raf);
		String city = FixedLengthStringIO.readFixedLengthString(CITY_SIZE, raf);
		String state = FixedLengthStringIO.readFixedLengthString(STATE_SIZE, raf);
		String zip = FixedLengthStringIO.readFixedLengthString(ZIP_SIZE, raf);
		setToTextFields(name, street, city, state, zip);
	}

	public void setToTextFields(String name, String street, String city, String state, String zip) {
		p.SetName(name);
		p.SetStreet(street);
		p.SetCity(city);
		p.SetState(state);
		p.SetZip(zip);
	}

	public void writeToEndFile(String str) {

		try {
			FixedLengthStringIO.writeFixedLengthString(str, RECORD_SIZE, raf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public AddressBookPane getPane() {
		return p;
	}

	public void setPane(AddressBookPane p) {
		this.p = p;
	}

	public RandomAccessFile getRaf() {
		return raf;
	}

	public void setRaf(RandomAccessFile raf) {
		this.raf = raf;
	}

}

class AddButton extends CommandButton {

	private final String ADD = "Add";

	public AddButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText(ADD);
	}

	public AddButton() {
		super();
		this.setText(ADD);
	}

	@Override
	public void Execute() {
		writeAddress();
	}
}

class UndoButton extends CommandButton {

	private CareTaker careTaker = new CareTaker();
	private Originator originator = new Originator();
	private final String UNDO = "Undo";

	public UndoButton(CareTaker careTaker, Originator originator) {
		super();
		setText(UNDO);
		this.careTaker = careTaker;
		this.originator = originator;
	}

	@Override
	public void Execute() {
		try {
			if (raf.length() > 0) {
				raf.seek(raf.length() - RECORD_SIZE * 2);
				String addressStr = FixedLengthStringIO.readFixedLengthString(RECORD_SIZE, raf);// reading last added
																								// address
				originator.setCurrentAddressStr(addressStr); // setting current originator address
				careTaker.addMemento(originator.createMemento());// adding memento to stack
				raf.setLength(raf.length() - RECORD_SIZE * 2); // delete last address on file
				if (raf.length() > 0) {
					readAddress(START_POS);
				} else {
					setToTextFields("", "", "", "", "");
				}

				System.out.println("raf length: " + raf.length());
				System.out.println("raf pointer: " + raf.getFilePointer());

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class RedoButton extends CommandButton {

	private CareTaker careTaker = new CareTaker();
	private Originator originator = new Originator();
	private final String REDO = "Redo";

	public RedoButton(CareTaker careTaker, Originator originator) {
		super();
		this.careTaker = careTaker;
		this.originator = originator;
		setText(REDO);

	}

	@Override
	public void Execute() {

		try {

			originator.getStateFromMemento(careTaker.getMemento());// setting new current address from stack
			String redoStr = originator.getCurrentAddressStr(); // getting the current address
			if (redoStr != null) {// if the stack is not empty or originator current string was initialized
				raf.setLength(raf.length() + RECORD_SIZE * 2);
				raf.seek(raf.length() - RECORD_SIZE * 2);
				writeToEndFile(redoStr); // writing Redo address at the end of the file
				readAddress(raf.length() - RECORD_SIZE * 2); // setting the Redo address on text fields
				System.out.println("raf length: " + raf.length());
				System.out.println("raf pointer: " + raf.getFilePointer());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

class NextButton extends CommandButton {

	private final String NEXT = "Next";

	public NextButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText(NEXT);
	}

	@Override
	public void Execute() {
		try {

			long currentPosition = raf.getFilePointer();
			if (currentPosition < raf.length()) {
				readAddress(currentPosition);
			} else if (raf.length() == 0) {
				setToTextFields("", "", "", "", "");
				raf.seek(START_POS);
			} else {
				raf.seek(raf.length() - RECORD_SIZE * 2);
				readAddress(raf.getFilePointer());
			}
			System.out.println("raf length: " + raf.length());
			System.out.println("raf pointer: " + raf.getFilePointer());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}

class PreviousButton extends CommandButton {

	private final String PREVIOUS = "Previous";

	public PreviousButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText(PREVIOUS);
	}

	@Override
	public void Execute() {
		try {
			long currentPosition = raf.getFilePointer();
			if (currentPosition - 2 * 2 * RECORD_SIZE >= 0)
				readAddress(currentPosition - 2 * 2 * RECORD_SIZE);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}

class LastButton extends CommandButton {

	private final String LAST = "Last";

	public LastButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText(LAST);
	}

	@Override
	public void Execute() {
		try {
			long lastPosition = raf.length();
			if (lastPosition > 0)
				readAddress(lastPosition - 2 * RECORD_SIZE);
			else
				setToTextFields("", "", "", "", "");

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}

class FirstButton extends CommandButton {

	private final String FIRST = "First";

	public FirstButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText(FIRST);
	}

	@Override
	public void Execute() {
		try {
			if (raf.length() > 0)
				readAddress(0);
			else
				setToTextFields("", "", "", "", "");

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
