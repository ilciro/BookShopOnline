package laptop.view;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import laptop.controller.ControllerSystemState;
import laptop.controller.ControllerHomePage;
import laptop.exception.LogoutException;
import laptop.exception.PersistenzaException;

public class BoundaryHomePage implements Initializable{
	@FXML
	private Pane pane;
	@FXML
	private Label header;
	@FXML
	private Label labelScelta;
	@FXML
	private Button buttonL;
	@FXML
	private Button buttonG;
	@FXML
	private Button buttonR;
	@FXML
	private ImageView imageL;
	@FXML
	private ImageView imageG;
	@FXML
	private ImageView imageR;
	@FXML
	private ImageView imageLogin;
	@FXML
	private ImageView imageRicerca;
	@FXML
	private ImageView imageVisualizza;
	@FXML
	private ImageView imageGestione;
	@FXML
	private Button buttonRicerca;
	@FXML
	private Button buttonLogin;
	@FXML
	private Button buttonVisualizza;
	@FXML
	private Button buttonGestione;
	@FXML
	private  HBox hbox1;
	@FXML
	private  HBox hbox2;
	@FXML
	private RadioButton checkDataBase;
	@FXML
	private RadioButton checkFile;
	@FXML
	private TextArea tArea;
	@FXML
	private Button buttonI;
	@FXML
	private ToggleGroup toggleGroup;



	protected Scene scene;
	private static final String COMPRAVENDITA="compravendita.fxml";
	private final ControllerSystemState vis=ControllerSystemState.getInstance();
	private ControllerHomePage cHP;

	@FXML
	private void getListaGiornali() throws IOException {
		if(!getPersistency().equalsIgnoreCase("") || vis.getIsLogged()) {
			vis.setIsSearch(false);
			vis.setTypeAsDaily();
			Stage stage;
			Parent root;
			stage = (Stage) buttonG.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(COMPRAVENDITA)));
			stage.setTitle("Benvenuto nella schermata del riepilogo dei giornali");
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}

	@FXML
	private void getListaRiviste() throws IOException {
		if(!getPersistency().equalsIgnoreCase("") || vis.getIsLogged()) {
			vis.setIsSearch(false);
			vis.setTypeAsMagazine();
			Stage stage;
			Parent root;
			stage = (Stage) buttonR.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(COMPRAVENDITA)));
			stage.setTitle("Benvenuto nella schermata del riepilogo delle riviste");
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}

	@FXML
	private void getListaLibri() throws IOException {
		if(!getPersistency().equalsIgnoreCase("") || vis.getIsLogged()) {
			vis.setIsSearch(false);
			vis.setTypeAsBook();
			Stage stage;
			Parent root;
			stage = (Stage) buttonL.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(COMPRAVENDITA)));
			stage.setTitle("Benvenuto nella schermata del riepilogo dei libri");
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}

	}

	@FXML
	private void login() throws IOException {
		if(!getPersistency().equalsIgnoreCase("")) {
			Stage stage;
			Parent root;
			stage = (Stage) buttonLogin.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("login.fxml")));
			stage.setTitle("Benvenuto nella schermata del login");
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}

	}
	
	@FXML
	private void cerca() throws IOException {
		if(!getPersistency().equalsIgnoreCase("")) {
			vis.setIsSearch(true);
			Stage stage;
			Parent root;
			stage = (Stage) buttonRicerca.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("ricerca.fxml")));
			stage.setTitle("Benvenuto nella schermata della ricerca ");
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}

	}

	private String getPersistency()
	{
		tArea.clear();


		try {

			if (checkDataBase.isSelected()) {
				cHP.dB();
			}
			else if (checkFile.isSelected()) {
				cHP.files();
			}
			else throw new PersistenzaException();
		}catch (PersistenzaException | IOException e)
		{
			tArea.setText(e.toString());
			tArea.lookup(".content").setStyle("-fx-background-color: red;");

		}
        return vis.getTypeOfDb();

	}
	@FXML
	private void ordini() throws IOException {
		Stage stage;
		Parent root;
		stage = (Stage) buttonVisualizza.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("visualizzaOrdini.fxml")));
		stage.setTitle("Benvenuto nella schermata degli ordini ");
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}
	@FXML
	private void gestione() throws IOException {
		Stage stage;
		Parent root;
		stage = (Stage) buttonGestione.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("visualizzaProfilo.fxml")));
		stage.setTitle("Benvenuto nella schermata di visualizzazione/gestione");
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}
	@FXML
	private void indietro() throws IOException, LogoutException {
		if(cHP.logout()) {
			vis.setIsLogged(false);
			Stage stage;
			Parent root;
			stage = (Stage) buttonI.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePageFinale.fxml")));
			stage.setTitle("Benvenuto nella schermata di home page");
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		else throw new LogoutException(" user is still logged in ");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tArea.setEditable(false);
        cHP=new ControllerHomePage();

		tArea.setText(" logged \t " + vis.getIsLogged() +" \t ruolo \t :" + cHP.getRuolo() + " \t id \t" + cHP.getId());


		if(vis.getIsLogged())
		{
			checkDataBase.setVisible(false);
			checkFile.setVisible(false);
			switch (cHP.getRuolo())
			{

				case "SCRITTORE","EDITORE","E","W","S"->
				{
					imageLogin.setVisible(false);
					labelScelta.setVisible(false);
					buttonLogin.setVisible(false);
					imageVisualizza.setVisible(true);
					buttonVisualizza.setVisible(true);
					//per image view funziona
					imageVisualizza.setTranslateX(-120);
					buttonVisualizza.setTranslateX(-120);

					imageGestione.setVisible(true);
					buttonGestione.setVisible(true);

					imageGestione.setTranslateX(-120);
					imageGestione.setTranslateX(-120);

					buttonGestione.setTranslateX(-120);

					buttonI.relocate(750,220);





				}
				case "UTENTE","U"->
				{
					imageVisualizza.setVisible(true);
					buttonVisualizza.setVisible(true);
					imageLogin.setVisible(false);
					buttonLogin.setVisible(false);
					imageGestione.setVisible(false);
					buttonGestione.setVisible(false);
					imageVisualizza.setTranslateX(-120);
					buttonVisualizza.setTranslateX(-120);
					buttonI.relocate(630,220);
				}
				case "N"->
				{
					imageVisualizza.setVisible(false);
					buttonVisualizza.setVisible(false);
					imageGestione.setVisible(false);
					buttonGestione.setVisible(false);
					buttonI.setVisible(false);
				}
				default -> java.util.logging.Logger.getLogger("initialize").log(Level.SEVERE," type of user not correct");
			}
		}
		else {
			imageVisualizza.setVisible(false);
			buttonVisualizza.setVisible(false);
			imageGestione.setVisible(false);
			buttonGestione.setVisible(false);
			buttonI.setVisible(false);
		}






    }

}
