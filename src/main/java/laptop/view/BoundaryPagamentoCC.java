package laptop.view;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;

import com.opencsv.exceptions.CsvValidationException;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import laptop.controller.ControllerPagamentoCC;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import laptop.model.CartaDiCredito;

public class BoundaryPagamentoCC implements Initializable {

	@FXML
	private Pane panel;
	@FXML
	private GridPane grid;
	@FXML
	private Label header;
	@FXML
	private Label labelN;
	@FXML
	private Label labelC;
	@FXML
	private Label cartaC;
	@FXML
	private Label labelS;
	@FXML
	private TextField nomeTF;
	@FXML
	private TextField cognomeTF;
	@FXML
	private TextField codiceTF;
	@FXML
	private TextField scadTF;
	@FXML
	private Button buttonI;
	@FXML
	private Button buttonA;

	@FXML
	private Label labelCiv;
	@FXML
	private Button buttonReg;

	@FXML
	private TextField nomeInput;
	@FXML
	private RadioButton buttonPrendi;

	@FXML
	private PasswordField codiceTFCiv;

	@FXML
	private TableView<CartaDiCredito> tableCC;
	@FXML
	private TableColumn<CartaDiCredito, SimpleStringProperty> codiceCC = new TableColumn<>("CodiceCarta");
	@FXML
	private Label labelNU;

	private ControllerPagamentoCC cPCC;
	protected Boolean esito;
	protected Scene scene;
	
	private static final ControllerSystemState vis = ControllerSystemState.getInstance();

	@FXML
	private void procediCC() throws IOException, SQLException, IdException, CsvValidationException {
		vis.setMetodoP("cCredito");

		String cod = codiceTF.getText();
		String civ=codiceTFCiv.getText();
		
		
		esito = cPCC.controllaPag(scadTF.getText(),cod,civ);
		   
		
		if (Boolean.TRUE.equals(esito)) {
			//insrrire pagamento cc
			//vedasi inserimento fattura
			cPCC.pagamentoCC(nomeTF.getText());
			vis.setIdOggetto(vis.getId());
			if(vis.getIsPickup()) 
			{
				Stage stage;
				Parent root;
				stage = (Stage) buttonI.getScene().getWindow();
				root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("scegliNegozio.fxml")));
				stage.setTitle("Benvenuto nella schermata per il download");
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();	
			}
			else
			{
				 
			Stage stage;
			Parent root;
			stage = (Stage) buttonI.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("download.fxml")));
			stage.setTitle("Benvenuto nella schermata per il download");
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			}
		} else {
			java.util.logging.Logger.getLogger("procedi pagacc").log(Level.INFO,"\n riprovare");

			Stage stage;
			Parent root;
			stage = (Stage) buttonI.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("PagamentoCC.fxml")));

			stage.setTitle("Benvenuto nella schermata per il pagamento");

			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		
		

	}

	@FXML
	private void annullaCC() throws IOException {
		Stage stage;
		Parent root;
		stage = (Stage) buttonA.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("acquista.fxml")));
		stage.setTitle("benvenuto nella schermata del riepilogo ordine");

		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public BoundaryPagamentoCC()  {
		try {
			cPCC = new ControllerPagamentoCC();
		} catch (Exception e) {
			java.util.logging.Logger.getLogger("Test pagacc").log(Level.SEVERE,"\n eccezione ottenuta .",e);

		}
		
	}

	@FXML
	public void registraCC() throws ParseException, SQLException, IdException, CsvValidationException, IOException {
		

		String nome = nomeTF.getText();

		String cognome = cognomeTF.getText();
		String codice = codiceTF.getText();
		String d = scadTF.getText();
		String civ=codiceTFCiv.getText();
		
		Date sqlDate = null;
		java.util.Date utilDate;
		//qui passo stringa per comodita..
		
		
		 SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

		    try {
		         utilDate = format.parse(d);
		         sqlDate = new Date(utilDate.getTime());
		    } catch (ParseException e) {
				java.util.logging.Logger.getLogger("Test pagacc").log(Level.SEVERE,"\n eccezione ottenuta {0}",e.toString());

		    }
		cPCC.aggiungiCartaDB(nome, cognome, codice, sqlDate, civ, vis.getSpesaT());


		
		 
		 
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		codiceCC.setCellValueFactory(new PropertyValueFactory<>("numeroCC"));
		if(!vis.getIsLogged())
		{
			buttonPrendi.setDisable(true);
			buttonReg.setDisable(true);
		}


	}

	@FXML
	private void popolaTabella() throws SQLException {
		try {

			String nomeUt = nomeInput.getText();
			if (nomeUt.isEmpty()) {
				buttonPrendi.setDisable(true);
				throw new IOException();
			}
			else {
				buttonPrendi.setDisable(false);
				tableCC.setItems(cPCC.ritornaElencoCC(nomeUt));
			}
		} catch (IOException| CsvValidationException |IdException e) {
			e.getMessage();
		}
		buttonPrendi.setDisable(false);
	}

	@FXML
	private void prova() throws Exception
	{
		nomeTF.setText(cPCC.tornaDalDb(tableCC.getSelectionModel().getSelectedItem().getNumeroCC()).getNomeUser());
		cognomeTF.setText(cPCC.tornaDalDb(tableCC.getSelectionModel().getSelectedItem().getNumeroCC()).getCognomeUser());
		codiceTF.setText(cPCC.tornaDalDb(tableCC.getSelectionModel().getSelectedItem().getNumeroCC()).getNumeroCC());
		scadTF.setText(cPCC.tornaDalDb(tableCC.getSelectionModel().getSelectedItem().getNumeroCC()).getScadenza().toString());


	}
}
