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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import laptop.controller.ControllerModificaUtente;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;

public class BoundaryModificaUtente implements Initializable {
	
	private ControllerModificaUtente cMU;
	private final ControllerSystemState vis=ControllerSystemState.getInstance();
	@FXML
	private Pane pane;
	@FXML
	private GridPane grid;
	@FXML
	private Label header;
	@FXML
	private Label credV;
	@FXML
	private Label credN;
	@FXML
	private Label nomeL;
	@FXML
	private Label vecchioNL;
	@FXML
	private TextField nuovoNL;
	@FXML
	private Label cognomeL;
	@FXML
	private Label vecchioCL;
	@FXML
	private TextField nuovoCL;
	@FXML
	private Label emailL;
	@FXML
	private Label vecchiaEmailL;
	@FXML
	private TextField nuovaEmailL;
	@FXML
	private Label pwdL;
	@FXML
	private PasswordField vecchiaPwd;
	@FXML
	private PasswordField nuovaPwd;
	@FXML
	private Label descL;
	@FXML
	private Label vecchiaDescL;
	@FXML
	private TextField nuovaDescL;
	@FXML
	private Label dataL;
	@FXML
	private Label vecchiaDNL;
	@FXML
	private DatePicker nuovaDNL;
	@FXML
	private Button buttonV;
	@FXML
	private Button aggiornaB;
	@FXML
	private Button annullaB;
	
	protected Scene scene;
	
	@FXML
	private void visualizza() throws SQLException, CsvValidationException, IOException, IdException {
		if(vis.getTypeOfDb().equals("file"))
		{
			vecchioNL.setText(cMU.prendiCsv().getNome());
			vecchioCL.setText(cMU.prendiCsv().getCognome());
			vecchiaEmailL.setText(cMU.prendiCsv().getEmail());
			vecchiaPwd.setText(cMU.prendiCsv().getPassword());
			vecchiaDescL.setText(cMU.prendiCsv().getDescrizione());
			vecchiaDNL.setText(cMU.prendiCsv().getDataDiNascita().toString());
		}else {
			vecchioNL.setText(cMU.prendi().getNome());
			vecchioCL.setText(cMU.prendi().getCognome());
			vecchiaEmailL.setText(cMU.prendi().getEmail());
			vecchiaPwd.setText(cMU.prendi().getPassword());
			vecchiaDescL.setText(cMU.prendi().getDescrizione());
			vecchiaDNL.setText(cMU.prendi().getDataDiNascita().toString());
		}
		

	}
	@FXML
	private void aggiorna() throws SQLException, CsvValidationException, IOException, IdException {
		if(cMU.aggiornaTot(nuovoNL.getText(),nuovoCL.getText(),
				nuovaEmailL.getText(),nuovaPwd.getText(),
				nuovaDescL.getText(),nuovaDNL.getValue(),vecchiaEmailL.getText()))
		{
			java.util.logging.Logger.getLogger("modifica utente").log(Level.INFO,"\n modifica avventuta con successo");

			

		
		}
		else
		{	
			java.util.logging.Logger.getLogger("errore modifica utente").log(Level.SEVERE,"\n errore nei dati");

			
		}

		
		// scermata  di conferma aggiornamento dati  
		
		
	}
	@FXML
	private void annulla() throws IOException
	{
		//scermata precedente
		Stage stage;
		Parent root;
		stage = (Stage) annullaB.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("visualizzaProfilo.fxml")));
		stage.setTitle("Benvenuto nella schermata del riepilogo ordine");
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		cMU=new ControllerModificaUtente();

	}

	
}
