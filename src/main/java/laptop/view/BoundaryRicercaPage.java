package laptop.view;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;

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
import laptop.controller.ControllerRicercaPage;
import laptop.controller.ControllerVisualizza;
import laptop.exception.IdException;
import laptop.model.raccolta.Raccolta;

public class BoundaryRicercaPage  implements Initializable{
	
	@FXML
	private Pane pane;
	@FXML
	private Label labelT;
	@FXML
	private TextField cercaT;
	@FXML
	private TextField idT;
	@FXML
	private TableView <Raccolta> table;
	@FXML
	private TableColumn<Raccolta, SimpleStringProperty> titolo = new TableColumn<>("Titolo");
	@FXML
	private TableColumn<Raccolta, SimpleStringProperty> autore = new TableColumn<>("Autore");
	@FXML
	private TableColumn<Raccolta, SimpleStringProperty> id = new TableColumn<>("ID");
	@FXML
	private Button buttonC; 
	@FXML
	private Button buttonV; 
	@FXML
	private Button buttonB;
	
	private static final String TITLE = "Benvenuto nella schermata del riepilogo ordine";
    private final ControllerRicercaPage cRP;
	private final ControllerVisualizza cV;
	protected Scene scene;
	
	
	public BoundaryRicercaPage() throws IOException {
		cRP = new ControllerRicercaPage();
		cV=new ControllerVisualizza();
		
	}
	@FXML
	private void cerca() throws SQLException
	{
		// e populo la tabella
		//col controller faccio la ricerca basandomi sul singerlton battona+
		table.setItems( cRP.cercaPerTipo(cercaT.getText()));
	}

	// mostro i dati e le relative schermate
	@FXML
	private void apri() throws IOException
	{
		String i;

		i = idT.getText();
		if(cRP.returnType().equals("libro") || (cRP.returnType().equals("giornale") || (cRP.returnType().equals("rivista"))))
		{
			//cV.setID(i);
			Stage stage;
			Parent root;
			stage = (Stage) buttonV.getScene().getWindow();
            String visualizza = "visualizzaPage.fxml";
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(visualizza)));
			stage.setTitle(TITLE);
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		else
		{
			try {
			throw new IdException("id non valido");
			}catch(IdException e)
			{
				java.util.logging.Logger.getLogger("report libro").log(Level.SEVERE,"\n eccezione ottenuta .",e);

			}
		}
			
		
	}

	@FXML
	private void torna() throws IOException {
		Stage stage;
		Parent root;
		stage = (Stage) buttonB.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("ricercaPerTipo.fxml")));
		stage.setTitle(TITLE);
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		titolo.setCellValueFactory(new PropertyValueFactory<>("titolo"));
		autore.setCellValueFactory(new PropertyValueFactory<>("editore"));
		id.setCellValueFactory(new PropertyValueFactory<>("id"));

	}

	
}
