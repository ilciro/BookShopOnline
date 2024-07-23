package laptop.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import laptop.controller.ControllerScelta;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class BoundaryScelta implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private Label header;
    @FXML
    private TextField tF;
    @FXML
    private Button buttonP;
    @FXML
    private ImageView dbImage;
    @FXML
    private ImageView fileImage;
    protected Scene scene;
    private ControllerScelta cScelta;


    @FXML
    private void procedi() throws IOException {
        String tipo;
        tipo=tF.getText();

        if(cScelta.getTypeDb(tipo))
        {
            Stage stage;
            Parent root;
            stage = (Stage) buttonP.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePage.fxml")));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            Stage stage;
            Parent root;
            stage = (Stage) buttonP.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("scelta.fxml")));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            cScelta=new ControllerScelta();

    }
}
