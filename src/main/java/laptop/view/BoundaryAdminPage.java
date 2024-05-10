package laptop.view;

import java.io.IOException;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class BoundaryAdminPage {
	
	// Finita !
	@FXML
	private Pane pane;
	@FXML
	private Label header ;
	@FXML
	private Button buttonR;
	@FXML
	private Button raccoltaB;
	@FXML 
	private Button buttonU;
	@FXML
	private Button buttonL;
	
	protected Scene scene;
	
	@FXML
	private void logout() throws IOException
	{
		Stage stage;
		Parent root;
		stage = (Stage) buttonL.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePage.fxml")));
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML 
	private void  raccolta() throws IOException
	{
		/*flusso di report qui*/

		Stage stage;
		Parent root;
		stage = (Stage) raccoltaB.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("raccoltaPage.fxml")));
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void utenti() throws IOException
	{
		Stage stage;
		Parent root;
		stage = (Stage) buttonU.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("userPage.fxml")));
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	@FXML
	private void report() throws IOException
	{
		Stage stage;
		Parent root;
		stage = (Stage) buttonR.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("reportPage.fxml")));
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
}
