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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import laptop.controller.ControllerAggiornaPassword;
import laptop.exception.IdException;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class BoundaryAggiornaPassword implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private Label header;
    @FXML
    private TextField vecchiaMTF;
    @FXML
    private PasswordField vecchiaPTF;
    @FXML
    private PasswordField nuovaPTF;
    @FXML
    private Button buttonA;
    @FXML
    private Button buttonI;
    private Scene scene;

    @FXML
    private void aggiorna() throws IOException, CsvValidationException, SQLException, IdException {
        if(cAP.aggiorna(nuovaPTF.getText()))
        {
            Stage stage;
            Parent root;
            stage = (Stage) buttonI.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("login.fxml")));
            stage.setTitle("Benvenuto nella schermata del login");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            throw new SQLException(" not updated");
        }
    }

    @FXML
    private void indietro() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) buttonI.getScene().getWindow();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("login.fxml")));
        stage.setTitle("Benvenuto nella schermata del login");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    private ControllerAggiornaPassword cAP;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cAP=new ControllerAggiornaPassword();

        try {
            vecchiaMTF.setText(cAP.getEmail());
            vecchiaPTF.setText(cAP.getPass());
        } catch (SQLException | CsvValidationException |IOException e) {
            java.util.logging.Logger.getLogger("iniitialize").log(Level.SEVERE," credentials are wrong !!");
        }

    }
}
