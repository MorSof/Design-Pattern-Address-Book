import java.util.Stack;

public class CareTaker {
	private Stack<Memento> mementoStack = new Stack<Memento>();

	//add momento to stack
	public void addMemento(Memento memento) {
		if (memento != null) {
			mementoStack.push(memento);
		}
	}

	//get momento from stack
	public Memento getMemento() {
		if (!mementoStack.isEmpty()) {
			return mementoStack.pop();
		}
		return null;
	}
}
