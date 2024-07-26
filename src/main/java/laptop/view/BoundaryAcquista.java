package laptop.view;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import laptop.controller.ControllerAcquista;
import laptop.controller.ControllerSystemState;
import laptop.exception.AcquistaException;
import laptop.exception.IdException;

public class BoundaryAcquista implements Initializable {
	@FXML
	private SplitPane split;
	@FXML
	private AnchorPane ap1;
	@FXML
	private AnchorPane ap2;
	@FXML
	private Label header;
	@FXML
	private Label labelN;
	@FXML
	private Label labelCosto;
	@FXML
	private Label labelQ;
	@FXML
	private Label labelT;
	@FXML
	private Label  nome;
	@FXML
	private Label  copieLabel;
	@FXML
	private Label costo;
	@FXML
	private TextField quantita;
	@FXML
	private CheckBox ritiroN;

	@FXML
	private Label totale;
	@FXML
	private Label labelPag;
	@FXML
	private RadioButton buttonCC;
	@FXML
	private RadioButton buttonCash;
	@FXML
	private Button calcola;
	@FXML
	private Button link;

	protected Scene scene;
	private final ControllerAcquista cA;
	private final ControllerSystemState vis = ControllerSystemState.getInstance() ;

	
	
	@FXML

	private void pagaCC() throws IOException {
		
		if(ritiroN.isSelected()) {
			vis.setPickup(true);
		}
		else if (!ritiroN.isSelected())
		{
			vis.setPickup(false);
		}
		if(Integer.parseInt(quantita.getText())>Integer.parseInt(copieLabel.getText()))
		{
			Stage stage;
			Parent root;
			stage = (Stage) buttonCC.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("acquista.fxml")));
			stage.setTitle("Benvenuto nella schermata di acquisto");
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			java.util.logging.Logger.getLogger("Test pagacc").log(Level.SEVERE,"\n Non vi e sufficiente disponibilita");
		}
		else {
			vis.setMetodoP("cCredito");

			Stage stage;
		Parent root;
		stage = (Stage) buttonCC.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("pagamentoCC.fxml")));
		stage.setTitle("Benvenuto nella schermata dell'acquisto con carta credito");
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		}


	}

	@FXML
	private void pagaCash() throws IOException {
		if(ritiroN.isSelected()) {
			vis.setPickup(true);
		}
		else if (!ritiroN.isSelected())
		{
			vis.setPickup(false);
		}
		if(Integer.parseInt(quantita.getText())>Integer.parseInt(copieLabel.getText()))
		{
			Stage stage;
			Parent root;
			stage = (Stage) buttonCC.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("acquista.fxml")));
			stage.setTitle("Benvenuto nella schermata di acquisto");
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			java.util.logging.Logger.getLogger("Test pagacc").log(Level.SEVERE,"\n Non vi e sufficiente disponibilita");



		}
		else {

			vis.setMetodoP("cash");

		Stage stage;
		Parent root;
		stage = (Stage) buttonCash.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("pagamentoContrassegno.fxml")));
		stage.setTitle("Benvenuto nella schermata dell'acquisto in contanti");

		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		}

	}

	

	@FXML
	private void importo() throws  SQLException, NumberFormatException, IdException, AcquistaException {
		
		if (!nome.getText().isEmpty()) {
			buttonCC.setDisable(false);
			buttonCash.setDisable(false);




			float x = cA.totale1(vis.getType(),labelT.getText(), Integer.parseInt(copieLabel.getText()),Integer.parseInt(quantita.getText()));
			costo.setText(String.valueOf(x));
			float tot;
			tot = x * (Float.parseFloat(quantita.getText()));
			totale.setText(String.valueOf( tot));

			cA.inserisciAmmontare(vis.getType(),Integer.parseInt(quantita.getText()));
			vis.setSpesaT(tot);
			vis.setQuantita(Integer.parseInt(quantita.getText()));
			
			// qui mettere un controllo dal db oer il tipo di prodotto scelto usando l'istanza visualizza


		}
		

	}

	public BoundaryAcquista() throws  IOException {
		cA = new ControllerAcquista();
	}

	@FXML
	private void indietro() throws IOException {
		if( vis.getIsLogged()) {
		Stage stage;
		Parent root;
		stage = (Stage) link.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePageAfterLogin.fxml")));
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		}
		else
		{
			Stage stage;
			Parent root;
			stage = (Stage) link.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePage.fxml")));
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)   {
		
		buttonCC.setDisable(true);
		buttonCash.setDisable(true);
		
			try {
			nome.setText(cA.getNomeById());

			copieLabel.setText(String.valueOf(cA.getDisp(vis.getType())));
		
				costo.setText(String.valueOf(cA.getCosto(vis.getType())));
			} catch (SQLException | IdException e) {
				java.util.logging.Logger.getLogger("Test initialize").log(Level.SEVERE, " eccezione ottenuta {0}.", e.toString());

			}


	}

}
