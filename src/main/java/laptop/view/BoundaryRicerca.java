package laptop.view;

import com.opencsv.exceptions.CsvValidationException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import laptop.controller.ControllerRicerca;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BoundaryRicerca implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private Label header;
    @FXML
    private RadioButton ricercaL;
    @FXML
    private RadioButton ricercaG;
    @FXML
    private RadioButton ricercaR;
    @FXML
    private Label labelRicerca;
    @FXML
    private TextField ricercaTA;
    @FXML
    private VBox vbox;
    @FXML
    private Button cercaB;
    @FXML
    private Button mostraB;
    @FXML
    private Button buttonI;
    @FXML
    private Label idL;
    @FXML
    private TextField idTF;
   @FXML
   private ToggleGroup toggleGroup;
   protected Scene scene;
   private final ControllerSystemState vis=ControllerSystemState.getInstance();
   private ControllerRicerca cR;


    @FXML
    private void cerca() throws CsvValidationException, IOException, IdException {


        if(ricercaL.isSelected()) vis.setTypeAsBook();
        if(ricercaG.isSelected()) vis.setTypeAsDaily();
        if(ricercaR.isSelected()) vis.setTypeAsMagazine();
        idTF.setText(String.valueOf(cR.getIdOggetto(ricercaTA.getText())));





    }
    @FXML
    private void mostra() throws IOException {

        vis.setId(Integer.parseInt(idTF.getText()));
        Stage stage;
        Parent root;
        stage = (Stage) mostraB.getScene().getWindow();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("visualizzaPage.fxml")));
        stage.setTitle("Benvenuto nella schermata del riepilogo di oggetto");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }
    @FXML
    private void indietro() throws IOException {
        /*
        todo vedere se user logged
         */
        Stage stage;
        Parent root;
        stage = (Stage) buttonI.getScene().getWindow();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePageFinale.fxml")));
        stage.setTitle("Benvenuto nella schermata della home Page");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            cR=new ControllerRicerca();
        } catch (IOException e) {
            Logger.getLogger("initialize").log(Level.SEVERE, "error in init .",e);
        }
    }
}
