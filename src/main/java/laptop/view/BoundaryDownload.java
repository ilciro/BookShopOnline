package laptop.view;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;

import com.itextpdf.text.DocumentException;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import laptop.controller.ControllerDownload;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;

public class BoundaryDownload implements Initializable {
	@FXML
	private SplitPane split;
	@FXML
	private AnchorPane ap1;
	@FXML
	private Label header;
	@FXML
	private AnchorPane ap2;
	@FXML
	private ImageView image;
	@FXML
	private Button buttonI;
	@FXML
	private Button buttonA;

	private ControllerDownload cD;
	private final ControllerSystemState vis = ControllerSystemState.getInstance() ;
	protected Alert a;
	protected Scene scene;

	@FXML
	private void scarica() throws IOException, DocumentException, URISyntaxException {
		
		a = new Alert(Alert.AlertType.CONFIRMATION);
		a.setTitle("Download effettuato");
		a.setHeaderText("Premere ok per scaricarlo e leggerlo\n");
		Optional<ButtonType> result = a.showAndWait();
		
		
		 if ((result.isPresent()) && (result.get() == ButtonType.OK))
	        	
	        {
				java.util.logging.Logger.getLogger("scarica").log(Level.INFO," all ok...");

	            //passo 0 per evitare il NullPointer


				cD.scarica(vis.getType());


	        }
		Stage stage;
		Parent root;
		stage = (Stage) buttonA.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePageFinale.fxml")));
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void pulisci() throws IOException {



		Stage stage;
		Parent root;
		stage = (Stage) buttonA.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("annullaPagamento.fxml")));
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

		/*

		if( vis.getIsLogged()) 
		{
			Stage stage;
			Parent root;
			stage = (Stage) buttonA.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePageAfterLogin.fxml")));
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		else
		{
			Stage stage;
			Parent root;
			stage = (Stage) buttonA.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePage.fxml")));
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}

		 */
	}

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

        try {
            cD = new ControllerDownload();
        } catch (IOException e) {
			java.util.logging.Logger.getLogger("Test initialize").log(Level.SEVERE, "eccezione ottenuta",e);

		}

    }

}
