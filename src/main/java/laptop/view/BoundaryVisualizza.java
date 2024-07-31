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
import laptop.exception.IdException;

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
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("ricercaPage.fxml")));

			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		i = cV.getID();
		String tipo=vis.getType();
		
		try {
            String indisponibile = "not found";
            switch (tipo) {
                case "libro" -> {

                    labelTitolo.setText(cV.getDataL(i).getTitolo());
                    labelNumeroPagine.setText(String.valueOf(cV.getDataL(i).getNrPagine()));
                    labelCodiceISBN.setText(cV.getDataL(i).getCodIsbn());
                    labelEditore.setText(cV.getDataL(i).getEditore());
                    labelAutore.setText(cV.getDataL(i).getAutore());
                    labelLingua.setText(cV.getDataL(i).getLingua());
                    labelCategoria.setText(cV.getDataL(i).getCategoria());
                    labelDate.setText(String.valueOf(cV.getDataL(i).getDataPubb()));
                    labelRecensione.setText(cV.getDataL(i).getRecensione());
                    labelDescrizione.setText(cV.getDataL(i).getDesc());
                    labelDisp.setText(String.valueOf(cV.getDataL(i).getDisponibilita()));
                    labelPrezzo.setText(String.valueOf(cV.getDataL(i).getPrezzo()));
                    labelCopieRimanenti.setText(String.valueOf(cV.getDataL(i).getNrCopie()));
                }
                case "giornale" -> {
                    labelTitolo.setText(cV.getDataG(i).getTitolo());
                    labelNumeroPagine.setText(String.valueOf(0));
                    labelCodiceISBN.setText(indisponibile);
                    labelEditore.setText(cV.getDataG(i).getEditore());
                    labelAutore.setText(indisponibile);
                    labelLingua.setText(cV.getDataG(i).getLingua());
                    labelCategoria.setText(indisponibile);
                    labelDate.setText(String.valueOf(cV.getDataG(i).getDataPubb()));
                    labelRecensione.setText(indisponibile);
                    labelDescrizione.setText(indisponibile);
                    labelDisp.setText(String.valueOf(cV.getDataG(i).getDisponibilita()));
                    labelPrezzo.setText(String.valueOf(cV.getDataG(i).getPrezzo()));
                    labelCopieRimanenti.setText(String.valueOf(0));
                }
                case "rivista" -> {
                    labelTitolo.setText(cV.getDataR(i).getTitolo());
                    labelNumeroPagine.setText(String.valueOf(0));
                    labelCodiceISBN.setText(String.valueOf(0));
                    labelEditore.setText(cV.getDataR(i).getEditore());
                    labelAutore.setText(cV.getDataR(i).getAutore());
                    labelLingua.setText(cV.getDataL(i).getLingua());
                    labelCategoria.setText(indisponibile);
                    labelDate.setText(String.valueOf(cV.getDataR(i).getDataPubb()));
                    labelRecensione.setText(indisponibile);
                    labelDescrizione.setText(cV.getDataR(i).getDescrizione());
                    labelDisp.setText(String.valueOf(cV.getDataL(i).getDisponibilita()));
                    labelPrezzo.setText(String.valueOf(cV.getDataL(i).getPrezzo()));
                    labelCopieRimanenti.setText(String.valueOf(cV.getDataL(i).getNrCopie()));
                }
				default -> java.util.logging.Logger.getLogger("Test initialize").log(Level.SEVERE, "type is wrong");

			}
		} catch (SQLException |CsvValidationException|IOException |IdException e) {
			e.getMessage();

			
		}


    }
	

}
