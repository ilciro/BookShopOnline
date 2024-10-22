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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import laptop.controller.ControllerSystemState;
import laptop.controller.ControllerVisualizza;
import laptop.database.GiornaleDao;
import laptop.database.LibroDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;

public class BoundaryVisualizza implements Initializable {
	
	@FXML
	private Pane pane;
	@FXML
	private GridPane gridpane ;
	@FXML
	private Label labelTitolo;
	@FXML 
	private Label labelNumeroPagine;
	@FXML
	private Label labelCodiceISBN;
	@FXML
	private Label labelEditore;
	@FXML
	private Label labelAutore;
	@FXML
	private Label labelLingua;
	@FXML
	private Label labelCategoria ;
	@FXML
	private Label labelDate;
	@FXML
	private Label labelRecensione;
	@FXML 
	private Label labelDescrizione;
	@FXML
	private Label labelDisp;
	@FXML
	private Label labelPrezzo;
	@FXML
	private Label labelCopieRimanenti;
	@FXML
	private Button buttonBack;
	@FXML
	private Button buttonA;
	@FXML
	private Label titoloL;
	@FXML
	private Label numeroPagineL;
	@FXML
	private Label codeIsbnL;
	@FXML
	private Label editoreL;
	@FXML
	private Label autoreL;
	@FXML
	private Label linguaL;
	@FXML
	private Label categoriaL;
	@FXML
	private Label dataL;
	@FXML
	private Label recensioneL;
	@FXML
	private Label descrizioneL;
	@FXML
	private Label disponibbilitaL;
	@FXML
	private Label prezzoL;
	@FXML
	private Label copieRimanenteL;
	
	private final ControllerVisualizza cV;
	protected int i;
	private final ControllerSystemState vis = ControllerSystemState.getInstance() ;


    public BoundaryVisualizza() throws IOException {
		cV = new ControllerVisualizza();
	}
	
	@FXML
	private void acquista() throws IOException
	{
		Stage stage;
		Parent root;
		stage = (Stage) buttonA.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("acquista.fxml")));
		stage.setTitle("Benvenuto nella schermata del riepilogo ordine");
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	@FXML
	private void annulla() throws IOException
	{
		if (!vis.getIsSearch()) {
		Stage stage;
		Parent root;
		stage = (Stage) buttonBack.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("compravendita.fxml")));

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		}
		else
		{
			Stage stage;
			Parent root;
			stage = (Stage) buttonBack.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("ricerca.fxml")));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {


		i = cV.getID();
		String tipo=vis.getType();
		String indisponibile="";

		try {
			switch (tipo)
			{
				case "libro"->
				{
					labelTitolo.setText(cV.getListLibro().get(0).getTitolo());
					labelNumeroPagine.setText(String.valueOf(cV.getListLibro().get(0).getNrPagine()));
					labelCodiceISBN.setText(cV.getListLibro().get(0).getCodIsbn());
					labelEditore.setText(cV.getListLibro().get(0).getEditore());
					labelAutore.setText(cV.getListLibro().get(0).getAutore());
					labelLingua.setText(cV.getListLibro().get(0).getLingua());
					labelCategoria.setText(cV.getListLibro().get(0).getCategoria());
					labelDate.setText(String.valueOf(cV.getListLibro().get(0).getDataPubb()));
					labelRecensione.setText(cV.getListLibro().get(0).getRecensione());
					labelDescrizione.setText(cV.getListLibro().get(0).getDesc());
					labelDisp.setText(String.valueOf(cV.getListLibro().get(0).getDisponibilita()));
					labelPrezzo.setText(String.valueOf(cV.getListLibro().get(0).getPrezzo()));
					labelCopieRimanenti.setText(String.valueOf(cV.getListLibro().get(0).getNrCopie()));

				}
				case "giornale"->
				{

					labelTitolo.setText(cV.getListGiornale().get(0).getTitolo());
					labelNumeroPagine.setText(String.valueOf(0));
					labelCodiceISBN.setText(indisponibile);
					labelEditore.setText(cV.getListGiornale().get(0).getEditore());
					labelAutore.setText(indisponibile);
					labelLingua.setText(cV.getListGiornale().get(0).getLingua());
					labelCategoria.setText(indisponibile);
					labelDate.setText(String.valueOf(cV.getListGiornale().get(0).getDataPubb()));
					labelRecensione.setText(indisponibile);
					labelDescrizione.setText(indisponibile);
					labelDisp.setText(String.valueOf(cV.getListGiornale().get(0).getDisponibilita()));
					labelPrezzo.setText(String.valueOf(cV.getListGiornale().get(0).getPrezzo()));
					labelCopieRimanenti.setText(String.valueOf(0));
				}
				case "rivista" -> {
					labelTitolo.setText(cV.getListRivista().get(0).getTitolo());
					labelNumeroPagine.setText(String.valueOf(0));
					labelCodiceISBN.setText(String.valueOf(0));
					labelEditore.setText(cV.getListRivista().get(0).getEditore());
					labelAutore.setText(cV.getListRivista().get(0).getAutore());
					labelLingua.setText(cV.getListRivista().get(0).getLingua());
					labelCategoria.setText(cV.getListRivista().get(0).getCategoria());
					labelDate.setText(String.valueOf(cV.getListRivista().get(0).getDataPubb()));
					labelRecensione.setText(indisponibile);
					labelDescrizione.setText(cV.getListRivista().get(0).getDescrizione());
					labelDisp.setText(String.valueOf(cV.getListRivista().get(0).getDisp()));
					labelPrezzo.setText(String.valueOf(cV.getListRivista().get(0).getPrezzo()));
					labelCopieRimanenti.setText(String.valueOf(cV.getListRivista().get(0).getCopieRim()));
				}
				default -> java.util.logging.Logger.getLogger("initialize").log(Level.SEVERE," type is not correct");
			}


		}catch (IOException|IdException|CsvValidationException e)
		{
			java.util.logging.Logger.getLogger("initialize").log(Level.SEVERE," eccezione ottenuta");
		}







		}





	

}
