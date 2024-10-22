package laptop.view;

import com.opencsv.exceptions.CsvValidationException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import laptop.controller.ControllerSystemState;
import laptop.controller.ControllerUtenti;
import laptop.exception.IdException;
import laptop.model.TempUser;
import laptop.model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoundaryUtenti implements Initializable {
    private  ControllerUtenti cU;
    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    @FXML
    private Pane pane;
    @FXML
    private Label header;
    @FXML
    private TableView <TempUser> tableview;
    @FXML
    private TableColumn<TempUser,Integer> id;
    @FXML
    private TableColumn<TempUser,String> nome;
    @FXML
    private TableColumn<TempUser,Integer> email;
    @FXML
    private VBox vbox;
    @FXML
    private Button buttonGenera;
    @FXML
    private Button inserisciB;
    @FXML
    private Button modificaB;
    @FXML
    private Button cancellaB;
    @FXML
    private Button indietroB;
    @FXML
    private void genera() throws CsvValidationException, SQLException, IOException {
        tableview.setItems(cU.getList());
    }
    @FXML
    private void inserisci() throws IOException {
        vis.setTipoModifica("inserisci");
        Stage stage;
        Parent root;
        stage = (Stage) inserisciB.getScene().getWindow();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("gestioneUtente.fxml")));
        stage.setTitle("Benvenuto nella schermata della gestione");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void modifica() throws IOException,IdException {
        int id=tableview.getSelectionModel().getSelectedItem().getId();
        if(id==0 )throw new IdException("id is wrong");
        vis.setId(id);
        vis.setTipoModifica("modifica");
        Stage stage;
        Parent root;
        stage = (Stage) modificaB.getScene().getWindow();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("gestioneUtente.fxml")));
        stage.setTitle("Benvenuto nella schermata della gestione");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void cancella() throws IOException, IdException, CsvValidationException, SQLException {




        int id=tableview.getSelectionModel().getSelectedItem().getId();


        if(id<=0 ) throw new IdException(" selectd id is null");
        else {
            vis.setId(id);
            Logger.getLogger("cancella ").log(Level.INFO," id : ", vis.getId());
            if (cU.elimina(tableview.getSelectionModel().getSelectedItem().getEmailT()))
            {
                Logger.getLogger("cancella ").log(Level.INFO," deleted successfully");
                Stage stage;
                Parent root;
                stage = (Stage) cancellaB.getScene().getWindow();
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("utenti.fxml")));
                stage.setTitle("Benvenuto nella schermata di utente");
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            }
            else{
                Logger.getLogger("cancella ").log(Level.INFO," deleted unsuccessfully");
                Stage stage;
                Parent root;
                stage = (Stage) cancellaB.getScene().getWindow();
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("utenti.fxml")));
                stage.setTitle("Benvenuto nella schermata di utente");
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            }



        }




    }
    @FXML
    private void indietro() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) indietroB.getScene().getWindow();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("admin.fxml")));
        stage.setTitle("Benvenuto nella schermata di admin");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private Scene scene;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cU=new ControllerUtenti();

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nome.setCellValueFactory(new PropertyValueFactory<>("nomeT"));
        email.setCellValueFactory(new PropertyValueFactory<>("emailT"));

    }
}
