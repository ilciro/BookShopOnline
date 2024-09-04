package laptop.view;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.ResourceBundle;


import com.opencsv.exceptions.CsvValidationException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import laptop.controller.ControllerModificaUtente;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;

public class BoundaryModificaUtentePage implements Initializable {
	@FXML
	private Label header;
	@FXML
	private Label tipo;
	@FXML
	private Label nome;
	@FXML
	private Label cognome;
	@FXML
	private Label email;
	@FXML
	private Label pass;
	@FXML
	private Label data;
	@FXML
	private TextField utenteL;
	@FXML
	private TextField nomeL;
	@FXML
	private TextField cognomeL;
	@FXML
	private TextField emailL;
	@FXML
	private PasswordField passL;
	@FXML
	private TextField dataL;
	@FXML
	private ListView<String> ruoli;
	@FXML
	private TextField utenteTF;
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
	@FXML
	private Button buttonM;
	@FXML
	private TextArea descTA;
	
	protected Scene scene;
	
	private ControllerModificaUtente cMU;
	
	
	
	@FXML
	private void ritornaUtente()
	{
		utenteTF.setText(ruoli.getSelectionModel().getSelectedItem());
		 
	}
	
	@FXML
	private void aggiorna() throws SQLException, IOException, ParseException, CsvValidationException, IdException {
		//buttonM
		Date sqlDate;
		java.util.Date utilDate;
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		utilDate = format.parse(dataTF.getText());
	    sqlDate = new Date(utilDate.getTime());

		if(cMU.aggiornaTot( nomeTF.getText(), cognomeTF.getText(), emailTF.getText(),passF.getText(), descTA.getText(), sqlDate.toLocalDate(), utenteTF.getText()))
		{
			Stage stage;
			Parent root;
			stage = (Stage) buttonI.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("visualizzaProfilo.fxml")));
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
		}
		else {
			Stage stage;
			Parent root;
			stage = (Stage) buttonI.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("modificaUtentePage.fxml")));
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
		}


	}

	
	@FXML
	private void prendiDati() throws SQLException, CsvValidationException, IOException, IdException {
		if(ControllerSystemState.getInstance().getTypeOfDb().equals("file")) {

			utenteL.setText(cMU.prendiCsv().getIdRuolo());
			nomeL.setText(cMU.prendiCsv().getNome());
			cognomeL.setText(cMU.prendiCsv().getCognome());
			emailL.setText(cMU.prendiCsv().getEmail());
			passL.setText(cMU.prendiCsv().getPassword());
			dataL.setText(cMU.prendiCsv().getDataDiNascita().toString());
		}else {

			utenteL.setText(cMU.prendi().getIdRuolo());
			nomeL.setText(cMU.prendi().getNome());
			cognomeL.setText(cMU.prendi().getCognome());
			emailL.setText(cMU.prendi().getEmail());
			passL.setText(cMU.prendi().getPassword());
			dataL.setText(cMU.prendi().getDataDiNascita().toString());
		}
		//buttonPrendiDati
		//vedere dati da controller (FARE)
	}
	@FXML
	private void indietro() throws IOException
	{
		Stage stage;
		Parent root;
		stage = (Stage) buttonI.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("visualizzaProfilo.fxml")));
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	
        ruoli.getItems().add("UTENTE");
        ruoli.getItems().add("SCRITTORE");
        ruoli.getItems().add("EDITORE");
        ruoli.getItems().add("ADMIN");

		descTA=new TextArea();
		descTA.setText("descrizione utente");

		cMU=new ControllerModificaUtente();
		
		
	}

}
