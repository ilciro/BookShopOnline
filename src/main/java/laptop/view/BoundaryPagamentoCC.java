package laptop.view;


import com.opencsv.exceptions.CsvValidationException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import laptop.controller.ControllerPagamentoCC;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import laptop.model.CartaDiCredito;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class BoundaryPagamentoCC implements Initializable {
	@FXML
	private Pane pane;
	@FXML
	private Label header;
	@FXML
	private VBox vbox;
	@FXML
	private Label labelN;
	@FXML
	private Label labelC;
	@FXML
	private Label labelCodice;
	@FXML
	private Label labelD;
	@FXML
	private Label labelCiv;
	@FXML
	private VBox vbox2;
	@FXML
	private TextField nomeTF;
	@FXML
	private TextField cognomeTF;
	@FXML
	private TextField codiceTF;
	@FXML
	private TextField scadenzaTF;
	@FXML
	private PasswordField passTF;
	@FXML
	private Button buttonReg;
	@FXML
	private TableView<CartaDiCredito> tableview;
	@FXML
	private TableColumn<String,CartaDiCredito> codiceCC;
	@FXML
	private Label labelNU;
	@FXML
	private TextField nomeInput;
	@FXML
	private RadioButton buttonPrendi;

	@FXML
	private Button buttonI;
	protected Scene scene;
	private ControllerPagamentoCC cPCC;
	private final ControllerSystemState vis=ControllerSystemState.getInstance();

	@FXML
	private void registraCC() throws CsvValidationException, SQLException, IOException, IdException, ParseException {


		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
		java.util.Date date = sdf1.parse(scadenzaTF.getText());
		java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
		cPCC.aggiungiCartaDB(nomeTF.getText(),cognomeTF.getText(),codiceTF.getText(), sqlStartDate,passTF.getText(),vis.getSpesaT());
	}
	@FXML
	private void popolaTabella() throws CsvValidationException, IOException {
		tableview.setItems(cPCC.ritornaElencoCC(nomeInput.getText()));
	}

	@FXML
	private void procediCC() throws IOException, CsvValidationException, SQLException, IdException {

		Stage stage;
		cPCC.pagamentoCC(nomeTF.getText());
		if(vis.getIsPickup())
		{

			Parent root;
			stage = (Stage) buttonI.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("scegliNegozio.fxml")));
			stage.setTitle("Benvenuto nella schermata per annullare fattura");
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}


		Parent root;
		stage = (Stage) buttonI.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("download.fxml")));
		stage.setTitle("Benvenuto nella schermata per annullare fattura");
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}




	@Override
	public void initialize(URL location, ResourceBundle resources) {
        try {
            cPCC=new ControllerPagamentoCC();
        } catch (IOException e) {
            java.util.logging.Logger.getLogger("initialize").log(Level.SEVERE,"error in init");
        }

		if(vis.getIsLogged())
		{
			nomeTF.setText(cPCC.getInfo()[0]);
			cognomeTF.setText(cPCC.getInfo()[1]);
			nomeTF.setEditable(false);
			cognomeTF.setEditable(false);
		}

	}
}
