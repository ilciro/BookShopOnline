package laptop.view;

import com.opencsv.exceptions.CsvValidationException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import laptop.controller.ControllerAdmin;
import laptop.controller.ControllerSystemState;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoundaryAdmin implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private Label header;
    @FXML
    private VBox vbox;
    @FXML
    private Button reportB;
    @FXML
    private Button raccoltaB;
    @FXML
    private Button utentiB;
    @FXML
    private Button logoutB;
    protected Scene scene;
    private ControllerAdmin cA;

    private final ControllerSystemState vis=ControllerSystemState.getInstance();

    //caricare le altre schermate
    @FXML
    private void report() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) reportB.getScene().getWindow();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("report.fxml")));
        stage.setTitle("Benvenuto nella schermata del login");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void raccolta() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) raccoltaB.getScene().getWindow();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("raccolta.fxml")));
        stage.setTitle("Benvenuto nella schermata della raccolta");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void utenti() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) utentiB.getScene().getWindow();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("utenti.fxml")));
        stage.setTitle("Benvenuto nella schermata di utente");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void logout() throws CsvValidationException, SQLException, IOException {
        if(cA.logout(vis.getTypeOfDb()))
        {
            Stage stage;
            Parent root;
            stage = (Stage) logoutB.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePageFinale.fxml")));
            stage.setTitle("Benvenuto nella schermata iniziale");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            Logger.getLogger("logout").log(Level.INFO," logout success");

        }
        else
        {
            Stage stage;
            Parent root;
            stage = (Stage) utentiB.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("admin.fxml")));
            stage.setTitle("Benvenuto nella schermata di admin");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            Logger.getLogger("logout").log(Level.SEVERE," error with logoutt!!");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cA=new ControllerAdmin();

    }
}
