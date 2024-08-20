package laptop.view;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;


import com.opencsv.CSVReader;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import laptop.controller.ControllerCancellaUser;
import laptop.controller.ControllerSystemState;
import laptop.controller.ControllerUserPage;

public class BoundaryUserPage implements Initializable {
	@FXML
	private Pane pane;
	@FXML
	private Label header;
	@FXML
	private Button buttonA;
	@FXML
	private Button buttonM;
	@FXML
	private Button buttonDel;
	@FXML
	private Button buttonC;
	@FXML
	private Button indietro;
	@FXML
	private Button buttonP;
	@FXML
	private TextArea elencoUtenti;
	@FXML
	private Label idL;
	@FXML
	private TextField utenteTF;
	
	private ControllerUserPage cUP;
	private ControllerCancellaUser cCU;

	
	private final ControllerSystemState vis=ControllerSystemState.getInstance();
	protected Scene scene ;
	


	@FXML
	private void aggiungi() throws IOException
	{
		Stage stage;
		Parent root;
		stage = (Stage) buttonA.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("aggiungiUtente.fxml")));
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

		
	}
	@FXML
	private void modifica() throws IOException
	{
		vis.setId(Integer.parseInt(utenteTF.getText()));
		if(vis.getId() >= 0  )
		
		{
			Stage stage;
			Parent root;
			stage = (Stage) buttonA.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("modificaUtentePage.fxml")));
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}

	}
	@FXML
	private void cancella() throws NumberFormatException, SQLException, IOException, CsvValidationException {
		 boolean state;

		vis.setId(Integer.parseInt(utenteTF.getText()));
		if(vis.getId()>=0)
		{
			state=cCU.cancellaUser();
			if(state)
			{
				Stage stage;
				Parent root;
				stage = (Stage) buttonA.getScene().getWindow();
				root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("adminPage.fxml")));
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			}
			else {
				Stage stage;
				Parent root;
				stage = (Stage) buttonA.getScene().getWindow();
				root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("userPage.fxml")));
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			}
		}
			
		
	}
	@FXML
	private void torna() throws IOException
	{
		Stage stage;
		Parent root;
		stage = (Stage) buttonA.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("adminPage.fxml")));
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}
	@FXML
	private void prendi() throws IOException,NullPointerException {




		if(vis.getTypeOfDb().equals("file"))
		{
			elencoUtenti.clear();
			elencoUtenti.setText(cUP.getCsvData());
		}
		else {

			elencoUtenti.clear();
			cUP.getUtenti();



			try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/riepilogoUtenti.txt"))) {
				String line = reader.readLine();


				while (line != null) {

					elencoUtenti.appendText(line.concat("\n"));
					line = reader.readLine();

				}
			}

		}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		cUP=new ControllerUserPage();
		cCU=new ControllerCancellaUser();
	}
	
	
	
	
	
	
	
	
	
	}
