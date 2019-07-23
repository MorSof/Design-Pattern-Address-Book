
//part of Decorator design
public class AddressBookPaneExternalButtons extends AddressBookPaneDecorator {

//------------------------------------------------------------
	// part of Memento design
	private CareTaker careTaker = new CareTaker();
	private Originator originator = new Originator();

	// היי חן! באופן אישי אני חושב שאין צורך כאן במחלקות שציינתי למעלה ואפשר פשוט
	// להגדיר כאן ישירות את המחסנית.
	// בכל זאת העדפתי ללכת לפי מה שמוגדר בספרות למרות שזה לא יעיל לדעתי במקרה זה
//------------------------------------------------------------

	public AddressBookPaneExternalButtons(AddressBookPaneCore core) {
		super(core);

		CommandButton btAdd = new AddButton();
		CommandButton btUndo = new UndoButton(careTaker, originator);
		CommandButton btRedo = new RedoButton(careTaker, originator);
		addButtons(btAdd, btUndo, btRedo);
	}

}
