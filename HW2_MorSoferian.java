import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HW2_MorSoferian extends Application {

	public static final int MAX_TOTAL_PANES = 3;
	public static final String TITLE = "AddressBookPane";
	public static final String SINGELTON_MESG_ERR = "Singelton violation. Only " + MAX_TOTAL_PANES
			+ " panes were created";

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Stage[] stages = new Stage[MAX_TOTAL_PANES];
		Scene[] scenes = new Scene[MAX_TOTAL_PANES];
		AddressBookPaneCore[] panes = new AddressBookPaneCore[MAX_TOTAL_PANES];
		try {
			for (int i = 0; true ; i++) { // one more interval to check Exception

				panes[i] = AddressBookPane.getInstance();
				scenes[i] = new Scene(panes[i].getPane());
				stages[i] = new Stage();
				stages[i].setTitle(TITLE);
				stages[i].setScene(scenes[i]);
				stages[i].setResizable(true);
				stages[i].show();
				stages[i].setAlwaysOnTop(true);
				stages[i].setOnCloseRequest(event -> {
				});

			}
		} catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
			System.err.println(SINGELTON_MESG_ERR);
		}
	}
}
