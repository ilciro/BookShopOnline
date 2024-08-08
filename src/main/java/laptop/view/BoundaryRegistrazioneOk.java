package laptop.view;

import java.io.IOException;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BoundaryRegistrazioneOk {
	@FXML
	private Pane pane;
	@FXML 
	private VBox vbox;
	@FXML
	private Button loginB;
	@FXML
	private Label labelScelta;
	@FXML
	private Button homePageB;
	@FXML
	private Label header;
	@FXML
	private ImageView image;
	
	protected Scene scene ;
	@FXML
	private void vaiLogin() throws IOException
	{
		Stage stage;
		Parent root;
		stage = (Stage) loginB.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("loginPage.fxml")));
		stage.setTitle("Registazione andata a buon fine");

		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}
	@FXML
	private void vaiHome() throws IOException {
		Stage stage;
		Parent root;
		stage = (Stage) homePageB.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePage.fxml")));
		stage.setTitle("Registazione andata a buon fine");

		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

		//roicarico schermata home
	}
	

}
