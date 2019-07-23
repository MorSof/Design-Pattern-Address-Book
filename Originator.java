
public class Originator {

	private String addressStr;

	//get the address of current address
	public String getCurrentAddressStr() {
		return addressStr;
	}

	//set the address of current address
	public void setCurrentAddressStr(String addressStr) {
		this.addressStr = addressStr;
	}

	//create new memento with current address
	public Memento createMemento() {
		return new Memento(addressStr);
	}

	//get address from memento to current address
	public void getStateFromMemento(Memento memento) {
		if (memento != null) {
			this.addressStr = memento.getAddressStr();
		}else {
			this.addressStr=null;
		}
	}

}
