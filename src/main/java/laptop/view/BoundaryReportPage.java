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
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import laptop.controller.ControllerReportPage;

public class BoundaryReportPage implements Initializable {
	
	@FXML
	private Pane pane;
	@FXML
	private Label header;
	@FXML
	private Button totaleB;
	@FXML
	private Button libroB;
	@FXML
	private Button raccoltaB;
	@FXML
	private Button giornaliB;
	@FXML
	private Button buttonI;
	@FXML
	private Button rivisteB;
	@FXML
	private TextArea ta;
	
	private ControllerReportPage cRP;



	protected Scene scene;
	
	
	@FXML
	private void totale() throws NullPointerException,  IOException {
		ta.setText(cRP.reportTotale());
		

	}
	@FXML
	private void reportLibri() throws IOException
	{
		ta.setText(cRP.leggiLibro());

        
    }
		

	
	@FXML
	private void raccolta() throws IOException{
		ta.setText(cRP.reportRaccolta());
        
		
	}
	@FXML
	private void reportGiornali() throws  IOException {
		ta.setText(cRP.leggiGiornale());
		
	}
	@FXML
	private void indietro() throws IOException
	{
		Stage stage;
		Parent root;
		stage = (Stage) buttonI.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("adminPage.fxml")));
		stage.setTitle("Benvenuto nella schermata del riepilogo dei libri");
		scene = new Scene(root);
		stage.setScene(scene);

		stage.show();

	}
	@FXML
	private void reportRiviste() throws IOException
	{
		ta.setText(cRP.leggiRivista());
	}
        
		
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

        try {
            cRP=new ControllerReportPage();
        } catch (IOException e) {
			java.util.logging.Logger.getLogger("Test initialize").log(Level.SEVERE, "eccezione ottenuta",e);

		}

    }

}
