package laptop.view;

import java.io.IOException;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import laptop.controller.ControllerSystemState;

public class BoundaryHomePage {
	@FXML
	private Pane pane;
	@FXML
	private GridPane grid;
	@FXML
	private Label header;
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
	private ImageView imageU;
	@FXML
	private ImageView imageC;
	@FXML
	private Button buttonLogin;
	@FXML
	private Button buttonC;

	protected Scene scene;
	private static final String COMPRAVENDITA="compravendita.fxml";
	private final ControllerSystemState vis=ControllerSystemState.getInstance();

	@FXML
	private void getListaGiornali() throws IOException {
		vis.setIsSearch(false);
		vis.setTypeAsDaily();
		Stage stage;
		Parent root;
		stage = (Stage) buttonL.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(COMPRAVENDITA)));
		stage.setTitle("Benvenuto nella schermata del riepilogo dei giornali");
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void getListaRiviste() throws IOException {
		vis.setIsSearch(false);
		vis.setTypeAsMagazine();
		Stage stage;
		Parent root;
		stage = (Stage) buttonL.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(COMPRAVENDITA)));
		stage.setTitle("Benvenuto nella schermata del riepilogo delle riviste");
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void getListaLibri() throws IOException {
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

	@FXML
	private void login() throws IOException {
		Stage stage;
		Parent root;
		stage = (Stage) buttonL.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("loginPage.fxml")));
		stage.setTitle("Benvenuto nella schermata del login");
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}
	
	@FXML
	private void cerca() throws IOException {
		vis.setIsSearch(true);
		Stage stage;
		Parent root;
		stage = (Stage) buttonC.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("ricercaPerTipo.fxml")));
		scene = new Scene(root);
		stage.setScene(scene);

		stage.show();

	}

}
