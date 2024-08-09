package laptop.view;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
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
import javafx.stage.Stage;
import laptop.controller.ControllerAggiungiUtente;
import laptop.exception.IdException;

public class BoundaryAggiungiUtente implements Initializable {
	@FXML
	private Label header;
	@FXML
	private Label nomeL;
	@FXML
	private Label cognomeL;
	@FXML
	private Label emailL;
	@FXML
	private Label passL;
	@FXML
	private Label dataL;
	@FXML
	private TextField nomeTF;
	@FXML
	private TextField cognomeTF;
	@FXML
	private TextField emailTF;
	@FXML
	private PasswordField passF;
	@FXML
	private TextField dataTF;
	@FXML
	private Button buttonI;
	@FXML
	private Button buttonA;
	protected Scene scene;
	
	private ControllerAggiungiUtente cAU;
	
	@FXML
	private void inserisci() throws ParseException, SQLException, IOException, CsvValidationException, IdException {
		if(cAU.checkData(nomeTF.getText(),cognomeTF.getText(),emailTF.getText(),passF.getText(),dataTF.getText()))
		{
			Stage stage;
			Parent root;
			stage = (Stage) buttonI.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("adminPage.fxml")));
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		else {
			java.util.logging.Logger.getLogger("inserisci").log(Level.SEVERE,"errore nei dati");
			Stage stage;
			Parent root;
			stage = (Stage) buttonA.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("aggiungiUtente.fxml")));
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();

		}
	}
	@FXML
	private void annulla() throws IOException
	{
		Stage stage;
		Parent root;
		stage = (Stage) buttonA.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("userPage.fxml")));
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cAU=new ControllerAggiungiUtente();

	}

}
