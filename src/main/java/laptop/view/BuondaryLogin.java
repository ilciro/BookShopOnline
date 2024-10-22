package laptop.view;

import com.opencsv.exceptions.CsvValidationException;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import laptop.controller.ControllerLogin;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class BuondaryLogin implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private Label header;
    @FXML
    private ImageView imageView;
    @FXML
    private VBox vbox1;
    @FXML
    private Label emailL;
    @FXML
    private Label passL;
    @FXML
    private VBox vbox2;
    @FXML
    private TextField emailTF;
    @FXML
    private PasswordField passTF;
    @FXML
    private VBox vbox3;
    @FXML
    private Button buttonL;
    @FXML
    private Button buttonReg;
    @FXML
    private Button buttonCambio;
    @FXML
    private Button buttonI;

    private  ControllerLogin cL;
    private Scene scene;

    @FXML
    private void login() throws IOException, CsvValidationException, SQLException {



        if(cL.login(emailTF.getText(),passTF.getText()).equals("NONVALIDO"))
            throw new SQLException(" user role not valid!!");
         if(cL.login(emailTF.getText(),passTF.getText()).equals("ADMIN"))
        {

                //caricare scehrmata admin

                    Stage stage;
                    Parent root;
                    stage = (Stage) buttonI.getScene().getWindow();
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("admin.fxml")));
                    stage.setTitle("Benvenuto nella schermata di admin");
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();


        }
        else{

            Stage stage;
            Parent root;
            stage = (Stage) buttonL.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePageFinale.fxml")));
            stage.setTitle("Benvenuto nella home page dopo il login");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

         

    }
    @FXML
    private boolean registra() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) buttonReg.getScene().getWindow();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("registraUtente.fxml")));
        stage.setTitle("Benvenuto nella home page dopo il login");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        return false;
    }
    @FXML
    private void cambia() throws CsvValidationException, SQLException, IOException {
        if(cL.userPresente(emailTF.getText(),passTF.getText()))
        {
            Stage stage;
            Parent root;
            stage = (Stage) buttonCambio.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("aggiornaPassword.fxml")));
            stage.setTitle("Benvenuto nella home page per cambiare credenziali");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        else {
            throw new IOException(" user not found!!");
        }



    }
    @FXML
    private void indietro() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) buttonI.getScene().getWindow();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePageFinale.fxml")));
        stage.setTitle("Benvenuto nella home page dopo il login");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cL=new ControllerLogin();
    }
}
