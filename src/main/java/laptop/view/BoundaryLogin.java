package laptop.view;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;

import com.opencsv.exceptions.CsvValidationException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import laptop.controller.ControllerLogin;




public class BoundaryLogin implements Initializable {
	@FXML
	private Label labelUser; 
	@FXML
	private Label labelPwd;
	@FXML
	private Label labelB;
	@FXML
	private javafx.scene.layout.GridPane grid;
	@FXML
	private TextField textFieldUsername;
	@FXML
	private PasswordField pwdField;
	@FXML
	private Button buttonI;
	@FXML
	private Button buttonA;
	@FXML
	private Pane panel;
	@FXML
	private ImageView image;
	@FXML
	private Button buttonReg;
	@FXML
	private Button buttonReset;

	
	private ControllerLogin cL;
	protected String ruolo;
	protected Scene scene;



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		cL = new ControllerLogin();

	}

	@FXML
	private void controllaCredenziali() throws IOException, SQLException, CsvValidationException {
		
		String u;
		String p;
		
		u = textFieldUsername.getText();
		p = pwdField.getText();
		

		
		ruolo=cL.getRuoloTempUSer(textFieldUsername.getText());

		if (cL.controlla(u,p)) {

            switch (ruolo) {
                case "e", "E" -> {
                    Stage stage;
                    Parent root;
                    stage = (Stage) buttonI.getScene().getWindow();
                    /*
                     * modificare schermata
                     */
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePageAfterLoginES.fxml")));
                    stage.setTitle("Benvenuto nella schermata di Home page ");

                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                }
                case "w", "W" -> {
                    Stage stage;
                    Parent root;
                    stage = (Stage) buttonI.getScene().getWindow();
                    /*
                     * modificare schermata
                     */
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePageAfterLoginES.fxml")));
                    stage.setTitle("Benvenuto nella schermata di home page dedicata agli editori/ scrittori");

                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();


                }
                case "a", "A" -> {
                    Stage stage;
                    Parent root;
                    stage = (Stage) buttonI.getScene().getWindow();
                    /*
                     * modificare schermata
                     */
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("adminPage.fxml")));
                    stage.setTitle("Benvenuto nella schermata di gestione amministrativa ");

                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();


                }
                case "u", "U" -> {

                    Stage stage;
                    Parent root;
                    stage = (Stage) buttonI.getScene().getWindow();
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePageAfterLogin.fxml")));
                    stage.setTitle("Benvenuto nella schermata di home page ");
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
				default -> 	java.util.logging.Logger.getLogger("Test Controlla").log(Level.SEVERE, "type is wrong");

			}
			

		} 
		else {
			
			java.util.logging.Logger.getLogger("Test login").log(Level.SEVERE,"\n reinserire credenziali ..\n");




		}
	}

	@FXML
	private void annullaCredenziali() throws IOException {
		Stage stage;
		Parent root;
		stage = (Stage) buttonA.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePage.fxml")));
		stage.setTitle("Benvenuto nella schermata del catalogo libri ");
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}

	@FXML
	private void register() throws IOException
	{
		/*
		 * carico scehrmata register
		 */
		Stage stage;
		Parent root;
		stage = (Stage) buttonReg.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("registraUtente.fxml")));
		stage.setTitle("Benvenuto nella schermata del login");
		scene = new Scene(root);
		stage.setScene(scene);

	}
	
	@FXML
	private void azzeraPwd() throws IOException
	{
		
		Stage stage;
		Parent root;
		stage = (Stage) buttonReg.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resetPwd.fxml")));
		stage.setTitle("Benvenuto nella schermata del login");
		scene = new Scene(root);
		stage.setScene(scene);

	}
	 
	 

}
