package laptop.view;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;

import com.opencsv.exceptions.CsvValidationException;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import laptop.controller.ControllerGestionePage;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import laptop.model.raccolta.Raccolta;

public class BoundaryGestionePage implements Initializable {
	@FXML
	private Pane pane;
	@FXML
	private Label header;
	@FXML
	private TableView<Raccolta>table=new TableView<>();
	@FXML
	private TableColumn<Raccolta, SimpleStringProperty> titolo = new TableColumn<>("Titolo");
	@FXML
	private TableColumn<Raccolta, SimpleStringProperty> tipologia = new TableColumn<>("Tipologia");
	@FXML
	private TableColumn<Raccolta, SimpleStringProperty> editore = new TableColumn<>("Editore");
	@FXML
	private TableColumn<Raccolta, SimpleStringProperty> autore = new TableColumn<>("Autore");
	@FXML
	private TableColumn<Raccolta, SimpleFloatProperty> prezzo = new TableColumn<>("Prezzo");
	@FXML
	private TableColumn<Raccolta, SimpleIntegerProperty> id = new TableColumn<>("ID ProdottoTitolo");
	@FXML
	private Button buttonG;
	@FXML
	private Button buttonAdd;
	@FXML
	private Button modB;
	@FXML
	private Button buttonDel;
	@FXML
	private Button buttonB;
	@FXML
	private TextField idL;
	private ControllerGestionePage cGP;
	private final ControllerSystemState vis=ControllerSystemState.getInstance();
	protected Scene scene;

	
	
	
	
	@FXML
	private void genera() throws CsvValidationException, IOException, IdException {

		table.setItems(null);

		table.setItems(cGP.getLista(vis.getType()));
		
	}
	@FXML
	private void aggiungi() throws IOException
	{
		Stage stage;
		Parent root;
		stage = (Stage) buttonAdd.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("aggiungiOggettoPage.fxml")));
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	@FXML
	private void modifica() throws IOException {
		vis.setId(Integer.parseInt(idL.getText()));
		Stage stage;
		Parent root;
		stage = (Stage) modB.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("modificaOggettoPage.fxml")));
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		
	}
	@FXML
	private void cancella() throws SQLException, CsvValidationException, IOException {

		vis.setId(Integer.parseInt(idL.getText()));
		if(cGP.cancella(Integer.parseInt(idL.getText())))
		{
			Stage stage;
			Parent root;
			stage = (Stage) buttonDel.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("paginaGestione.fxml")));
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}

		
	}
	@FXML
	private void indietro() throws IOException
	{
		Stage stage;
		Parent root;
		stage = (Stage) buttonB.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("adminPage.fxml")));
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {


		try {
            cGP=new ControllerGestionePage();
			table.setItems(null);

		} catch (IOException e) {
			java.util.logging.Logger.getLogger("Test initialize").log(Level.SEVERE, "eccezione ottenuta",e);

		}

		header.setText(cGP.settaHeader(vis.getType()));

		titolo.setCellValueFactory(new PropertyValueFactory<>("titolo"));
		tipologia.setCellValueFactory(new PropertyValueFactory<>("categoria"));
		editore.setCellValueFactory(new PropertyValueFactory<>("editore"));
		autore.setCellValueFactory(new PropertyValueFactory<>("autore"));
		prezzo.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
		id.setCellValueFactory(new PropertyValueFactory<>("id"));

		if(vis.getType().equals("giornale")) {
			tipologia.setCellValueFactory(new PropertyValueFactory<>("tipologia"));
			autore.setCellValueFactory(new PropertyValueFactory<>("editore"));
		}
		if(vis.getType().equals("rivista"))
		{
			tipologia.setCellValueFactory(new PropertyValueFactory<>("tipologia"));

		}


		
	}

}
