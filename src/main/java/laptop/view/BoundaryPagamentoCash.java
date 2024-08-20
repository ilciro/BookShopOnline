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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import laptop.controller.ControllerPagamentoCash;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;

public class BoundaryPagamentoCash implements Initializable{
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
	private Label labelVP;
	@FXML
	private Label labelCom;
	@FXML
	private TextField nomeTF;
	@FXML
	private TextField cognomeTF;
	@FXML
	private TextField viaTF;
	@FXML
	private TextArea eventualiArea;
	@FXML
	private Button buttonI;
	@FXML
	private Button buttonA;

	private ControllerPagamentoCash cPC;

	protected String n; 
	protected String c;
	protected String v;
	protected String com;
	
	protected Scene scene;
	private static final ControllerSystemState vis = ControllerSystemState.getInstance();

	@FXML
	private void procediCash() throws IOException, SQLException, IdException, CsvValidationException {
		
		vis.setMetodoP("cash");


			n = nomeTF.getText();
			c = cognomeTF.getText();
			v = viaTF.getText();
			com = eventualiArea.getText();
			


			if (n.isEmpty() || c.isEmpty() || v.isEmpty()) {
				java.util.logging.Logger.getLogger("procedi cash").log(Level.SEVERE,"\n errore nel pagamento");


				Stage stage;
				Parent root;
				stage = (Stage) buttonI.getScene().getWindow();
				root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("pagamentoContrassegno.fxml")));
				stage.setTitle("Benvenuto nella schermata del riepilogo ordine");
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
				throw new IOException();

			} else {

				cPC.controlla(n, c, v, com);
				
				java.util.logging.Logger.getLogger("pagamento cash").log(Level.INFO,"\n pagamento avvenuto");


				if(vis.getIsPickup()) 
				{
					Stage stage;
					Parent root;
					stage = (Stage) buttonI.getScene().getWindow();
					root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("scegliNegozio.fxml")));
					stage.setTitle("Benvenuto nella schermata per scegliere il negozio");
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
			}


	}

	@FXML
	private void annullaCash() throws IOException {
		Stage stage;
		Parent root;
		stage = (Stage) buttonA.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("acquista.fxml")));
		stage.setTitle("benvenuto nella schermata del riepilogo ordine");

		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
				try {
					cPC = new ControllerPagamentoCash();
				} catch (Exception e) {
					java.util.logging.Logger.getLogger("Test pagacc").log(Level.SEVERE,"\n eccezione ottenuta {0}",e.toString());


					
				}

	}

}
