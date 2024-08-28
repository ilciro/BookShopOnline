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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import laptop.controller.ControllerRicercaPerTipo;
import laptop.controller.ControllerSystemState;

public class BoundaryRicercaPerTipo implements Initializable {
	
	@FXML
	private Pane pane;
	@FXML
	private Label labelC;
	@FXML
	private Button buttonL;
	@FXML
	private Button buttonG;
	@FXML
	private Button buttonR;
	@FXML
	private Button buttonB;
	
	private static final String TITLE = "Benvenuto nella schermata del riepilogo ordine";
	private static final String PAGEFXML = "ricercaPage.fxml";
	private ControllerRicercaPerTipo cRPT;
	protected Scene scene;
	private static final String REPORTLIBRO="report libro";
		
	
	@FXML
	private void torna() throws IOException
	{
		
		if(ControllerSystemState.getInstance().getIsLogged()) {
			Stage stage;
			Parent root;
			stage = (Stage) buttonB.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePageAfterLogin.fxml")));
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			}
			else
			{
				Stage stage;
				Parent root;
				stage = (Stage) buttonB.getScene().getWindow();
				root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePage.fxml")));
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			}
	}
	@FXML
	private void cercaL() throws IOException
	{
		ControllerSystemState.getInstance().setTypeAsBook();

		if(cRPT.setRicerca(ControllerSystemState.getInstance().getType()))
		{
			Stage stage;
			Parent root;
			stage = (Stage) buttonL.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(PAGEFXML)));
			stage.setTitle(TITLE);
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		else
		{
			
			java.util.logging.Logger.getLogger(REPORTLIBRO).log(Level.WARNING,"\n not found");


		}
	}
	@FXML
	private void cercaG() throws IOException
	{
		ControllerSystemState.getInstance().setTypeAsDaily();
		if(cRPT.setRicerca(ControllerSystemState.getInstance().getType()))
		{
			Stage stage;
			Parent root;
			stage = (Stage) buttonG.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(PAGEFXML)));
			stage.setTitle(TITLE);
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		else
		{
			java.util.logging.Logger.getLogger(REPORTLIBRO).log(Level.WARNING,"\n not found ");
		}
	}
	@FXML 
	private void cercaR() throws IOException
	{
		ControllerSystemState.getInstance().setTypeAsMagazine();
		if(cRPT.setRicerca(ControllerSystemState.getInstance().getType()))
		{
			Stage stage;
			Parent root;
			stage = (Stage) buttonR.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(PAGEFXML)));
			stage.setTitle(TITLE);
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		else
		{
			java.util.logging.Logger.getLogger(REPORTLIBRO).log(Level.WARNING,"\n not found");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		cRPT = new  ControllerRicercaPerTipo();
	
	}
}
