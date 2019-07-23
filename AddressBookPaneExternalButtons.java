
//part of Decorator design
public class AddressBookPaneExternalButtons extends AddressBookPaneDecorator {

//------------------------------------------------------------
	// part of Memento design
	private CareTaker careTaker = new CareTaker();
	private Originator originator = new Originator();

	// ��� ��! ����� ���� ��� ���� ���� ���� ��� ������� ������� ����� ����� ����
	// ������ ��� ������ �� �������.
	// ��� ��� ������ ���� ��� �� ������ ������ ����� ��� �� ���� ����� ����� ��
//------------------------------------------------------------

	public AddressBookPaneExternalButtons(AddressBookPaneCore core) {
		super(core);

		CommandButton btAdd = new AddButton();
		CommandButton btUndo = new UndoButton(careTaker, originator);
		CommandButton btRedo = new RedoButton(careTaker, originator);
		addButtons(btAdd, btUndo, btRedo);
	}

}
