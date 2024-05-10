package laptop.view;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import laptop.controller.ControllerPassword;

public class BoundaryResetPwd implements Initializable{
	@FXML
	private Pane pane;
	@FXML
	private GridPane grid;
	@FXML
	private Label header;
	@FXML
	private Label emailL;
	@FXML
	private Label vecchiaPL;
	@FXML
	private Label nuovaPL;
	@FXML
	private TextField emailTF;
	@FXML
	private PasswordField vecchiaPF;
	@FXML
	private PasswordField nuovaPF;
	@FXML
	private ImageView image;
	@FXML
	private Button buttonC;
	@FXML
	private Button buttonH;
	
	private ControllerPassword cP;
	protected String email;
	protected String vecchiaP;
	protected String nuovaP;
	protected Scene scene;
	
	@FXML
	private void conferma() throws SQLException
	{
		
		email=emailTF.getText();
		vecchiaP=vecchiaPF.getText();
		nuovaP=nuovaPF.getText();
		cP.aggiornaPass(email,vecchiaP,nuovaP);
		
		
	}
	@FXML
	private void ritorna() throws IOException
	{
		Stage stage;
		Parent root;
		stage = (Stage) buttonH.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePage.fxml")));
		stage.setTitle("Benvenuto nella schermata del login");
		scene = new Scene(root);
		stage.setScene(scene);

	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
				cP=new ControllerPassword();

	}

}
