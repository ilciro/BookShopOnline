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
import laptop.controller.ControllerCompravendita;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import laptop.model.raccolta.Raccolta;

public class BoundaryCompravendita implements Initializable {
	@FXML
	private Pane panel;
	@FXML
	private Label header;
	@FXML
	private TableView<Raccolta> table;
	@FXML
	private TableColumn<Raccolta, SimpleStringProperty> titolo = new TableColumn<>("Titolo");
	@FXML
	private TableColumn<Raccolta, SimpleStringProperty> editore = new TableColumn<>("Editore");
	@FXML
	private TableColumn<Raccolta, SimpleStringProperty> autore = new TableColumn<>("Autore");
	@FXML
	private TableColumn<Raccolta, SimpleStringProperty> categoria = new TableColumn<>("Categoria");
	@FXML
	private TableColumn<Raccolta, SimpleFloatProperty> prezzo = new TableColumn<>("Prezzo");
	@FXML
	private TableColumn<Raccolta, SimpleIntegerProperty> idLibro = new TableColumn<>("Id Libro");

	@FXML
	private Button buttonL;
	@FXML
	private TextField entryText;
	@FXML
	private Button buttonV;
	@FXML
	private Button buttonA;
	@FXML
	private Button buttonI;

	private ControllerCompravendita cCV;
	private final ControllerSystemState vis = ControllerSystemState.getInstance() ;
	protected Scene scene;
	private static final String TITOLOS="titolo";
	private static final String EDITORES="editore";
	private static final String PREZZOS="prezzo";
	private static final String LIBRO="libro";
	private static final String GIORNALE="giornale";
	private static final String RIVISTA="rivista";



	@FXML
	private void verifica() throws IOException, SQLException, IdException, CsvValidationException {
		
			String i = entryText.getText();
			

			if(i==null || i.isEmpty())
			{
				try {
					throw new IdException("id null or empty");
					
				}catch(IdException idE)
				{
					java.util.logging.Logger.getLogger("boundary compravendita").log(Level.SEVERE,"\n eccezione ottenuta {0}.",idE.toString());

				}
			}	
			
			
			
			
			
			if( cCV.disponibilita(vis.getType(),i) )
			{

                assert i != null;
                vis.setId(Integer.parseInt(i));
				
			}

			
			Stage stage;
			Parent root;
			stage = (Stage) buttonV.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("visualizzaPage.fxml")));
			stage.setTitle("Benvenuto nella schermata del riepilogo ordine");
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
			
				

			
		
		

	}

	@FXML
	private void procedi() throws IOException, SQLException, IdException, CsvValidationException {
		String i = entryText.getText();
		
		

		if( cCV.disponibilita(vis.getType(),i) )
		{
			
		

			vis.setId(Integer.parseInt(i));
			
		}
		

			Stage stage;
			Parent root;
			stage = (Stage) buttonA.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("acquista.fxml")));
			stage.setTitle("Benvenuto nella schermata del riepilogo ordine");

			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			


	}

	@FXML
	private void vediLista() throws IOException, CsvValidationException, IdException {
		//vedere if anche qui

				table.setItems(cCV.getLista(vis.getType()));

	}

	private String ritornaMessaggio()
	{
		String s=null;
		if(ControllerSystemState.getInstance().getType().equals(LIBRO))
			s="Benvenuto... ecco la lista dei libri nel nostro catalogo...";
		else if(ControllerSystemState.getInstance().getType().equals(GIORNALE))
			s="Benvenuto... ecco la lista dei giornali nel nostro catalogo...";
		else if(ControllerSystemState.getInstance().getType().equals(RIVISTA))
			s="Benvenuto... ecco la lista dele riviste nel nostro catalogo...";
		return s;
	}
	private String popolaBottoneV()
	{
		String s=null;
		if(ControllerSystemState.getInstance().getType().equals(LIBRO))
			s="Mostra Libro";
		else if(ControllerSystemState.getInstance().getType().equals(GIORNALE))
			s="Mostra Giornale";
		else if(ControllerSystemState.getInstance().getType().equals(RIVISTA))
			s="Mostra Rivista";
		return s;
	}
	private String popolaBottoneA()
	{
		String s=null;
		if(ControllerSystemState.getInstance().getType().equals(LIBRO))
			s="Acquista Libro";
		else if(ControllerSystemState.getInstance().getType().equals(GIORNALE))
			s="Acquista Giornale";
		else if(ControllerSystemState.getInstance().getType().equals(RIVISTA))
			s="Acquista Rivista";
		return s;
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {

        try {
            cCV = new ControllerCompravendita();
        } catch (IOException e) {
			java.util.logging.Logger.getLogger("Test initialize").log(Level.SEVERE, "eccezione ottenuta",e);
        }

        buttonV.setText(popolaBottoneV());
		buttonA.setText(popolaBottoneA());
		
		if(ControllerSystemState.getInstance().getType().equals(LIBRO))
		{
			header.setText(ritornaMessaggio());
			titolo.setCellValueFactory(new PropertyValueFactory<>(TITOLOS));
			editore.setCellValueFactory(new PropertyValueFactory<>(EDITORES));
			autore.setCellValueFactory(new PropertyValueFactory<>("autore"));
			categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
			prezzo.setCellValueFactory(new PropertyValueFactory<>(PREZZOS));
			idLibro.setCellValueFactory(new PropertyValueFactory<>("id"));
			
		}
		else if(ControllerSystemState.getInstance().getType().equals(GIORNALE))
		{
			header.setText(ritornaMessaggio());
			titolo.setCellValueFactory(new PropertyValueFactory<>(TITOLOS));
			//same on value editore
			// giornale not have autore attr
			autore.setCellValueFactory(new PropertyValueFactory<>(EDITORES));
			editore.setCellValueFactory(new PropertyValueFactory<>(EDITORES));
			categoria.setCellValueFactory(new PropertyValueFactory<>("tipologia"));
			prezzo.setCellValueFactory(new PropertyValueFactory<>(PREZZOS));
			idLibro.setCellValueFactory(new PropertyValueFactory<>("id"));
		}
		else if(ControllerSystemState.getInstance().getType().equals(RIVISTA))
		{
			header.setText(ritornaMessaggio());
			titolo.setCellValueFactory(new PropertyValueFactory<>(TITOLOS));
			editore.setCellValueFactory(new PropertyValueFactory<>(EDITORES));
			autore.setCellValueFactory(new PropertyValueFactory<>("autore"));
			categoria.setCellValueFactory(new PropertyValueFactory<>("tipologia"));
			prezzo.setCellValueFactory(new PropertyValueFactory<>(PREZZOS));
			idLibro.setCellValueFactory(new PropertyValueFactory<>("id"));
		}
		
		//stampare sttringa opportuuna


	}

	@FXML
	private void torna() throws IOException {
		
		/*
		 * Si vede utente "generico " loggato 
		 * se non � loggato si inposta come tipo "Utente"
		 * qui viene usato utente "UTENtE" come quello che non puo fare nulla (utente base)
		 * per restituire tipo vedere controllerCompravenditaLibri
		 * 
		 * FATTO QUESTO per aggirare il problema del nullPointer EXcoeption di cCV.retTipoUser
		 * 
		 */
		
		if(!vis.getIsLogged())
			cCV.setTipoUser("UTENTE");
		
		String tipoU=cCV.retTipoUser();
		
		if( vis.getIsLogged() &&  tipoU.equalsIgnoreCase("ADMIN")) {
			Stage stage;
			Parent root;
			stage = (Stage) buttonI.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePageAfterLogin.fxml")));
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		else if( vis.getIsLogged() && (tipoU.equalsIgnoreCase("SCRITTORE") || tipoU.equalsIgnoreCase("EDITORE")) ) {
			Stage stage;
			Parent root;
			stage = (Stage) buttonI.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePageAfterLoginES.fxml")));
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}	
		else if((tipoU.equalsIgnoreCase("UTENTE") ) )
		{
			Stage stage;
			Parent root;
			stage = (Stage) buttonI.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePage.fxml")));
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}


	}
}

