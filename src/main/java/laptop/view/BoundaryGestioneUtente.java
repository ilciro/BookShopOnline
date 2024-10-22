package laptop.view;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import laptop.controller.ControllerGestioneUtente;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoundaryGestioneUtente implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private Label header;
    @FXML
    private VBox vbox;
    @FXML
    private TextField ruoloTF;
    @FXML
    private TextField nomeTF;
    @FXML
    private TextField cognomeTF;
    @FXML
    private TextField emailTF;
    @FXML
    private PasswordField passTF;
    @FXML
    private TextArea descTA;
    @FXML
    private TextField dataTF;
    @FXML
    private VBox vbox1;
    @FXML
    private ListView<String> listView;
    @FXML
    private TextField nomeTF1;
    @FXML
    private TextField cognomeTF1;
    @FXML
    private TextField emailTF1;
    @FXML
    private PasswordField passTF1;
    @FXML
    private TextArea descTA1;
    @FXML
    private DatePicker dataN;
    @FXML
    private VBox vbox2;
    @FXML
    private Button inserisciB;
    @FXML
    private Button modificaB;
    @FXML
    private Button indietroB;

    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private ControllerGestioneUtente cGU;
    private Scene scene;

    @FXML
    private void inserisci() throws IOException, SQLException, CsvValidationException, IdException {

            vbox.setVisible(false);
            modificaB.setVisible(false);






            if(cGU.inserisciUtente(listView.getSelectionModel().getSelectedItem(),nomeTF1.getText(), cognomeTF1.getText(),emailTF1.getText(),passTF1.getText(),descTA1.getText(),dataN.getValue()))
        {
            Stage stage;
            Parent root;
            stage = (Stage) inserisciB.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("utenti.fxml")));
            stage.setTitle("Benvenuto nella schermata di utente");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            Stage stage;
            Parent root;
            stage = (Stage) inserisciB.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("gestioneUtente.fxml")));
            stage.setTitle("Benvenuto nella schermata della gestione utente");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    private void modifica() throws IOException, CsvValidationException, SQLException, IdException {
        inserisciB.setVisible(false);
        if(cGU.modifica(listView.getSelectionModel().getSelectedItem(),nomeTF1.getText(), cognomeTF1.getText(),emailTF1.getText(),passTF1.getText(),descTA1.getText(),dataN.getValue()))
        {
            Stage stage;
            Parent root;
            stage = (Stage) inserisciB.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("utenti.fxml")));
            stage.setTitle("Benvenuto nella schermata di utente");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            Logger.getLogger("modif").log(Level.INFO, "user modified successfully!!");
        }
        else{
            Logger.getLogger("modif").log(Level.SEVERE, "user modified unsuccessfully!!");


            Stage stage;
            Parent root;
            stage = (Stage) modificaB.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("gestioneUtente.fxml")));
            stage.setTitle("Benvenuto nella schermata di utente");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    private void indietro() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) indietroB.getScene().getWindow();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("utenti.fxml")));
        stage.setTitle("Benvenuto nella schermata di utente");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }












    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cGU=new ControllerGestioneUtente();
        ObservableList<String> ruoli= FXCollections.observableArrayList();
        ruoli.add("SCRITTORE");
        ruoli.add("EDITORE");
        ruoli.add("UTENTE");
        ruoli.add("ADMIN");
        listView.setItems(ruoli);

            try {
                switch (vis.getTypeOfDb()) {
                    case "db" -> {
                        ruoloTF.setText(cGU.getDataDao().getIdRuolo());
                        nomeTF.setText(cGU.getDataDao().getNome());
                        cognomeTF.setText(cGU.getDataDao().getCognome());
                        emailTF.setText(cGU.getDataDao().getEmail());
                        passTF.setText(cGU.getDataDao().getPassword());
                        descTA.setText(cGU.getDataDao().getDescrizione());
                        dataTF.setText(String.valueOf(cGU.getDataDao().getDataDiNascita()));
                    }
                    case "file"->
                    {
                        ruoloTF.setText(cGU.getDataCSV().getIdRuolo());
                        nomeTF.setText(cGU.getDataCSV().getNome());
                        cognomeTF.setText(cGU.getDataCSV().getCognome());
                        emailTF.setText(cGU.getDataCSV().getEmail());
                        passTF.setText(cGU.getDataCSV().getPassword());
                        descTA.setText(cGU.getDataCSV().getDescrizione());
                        dataTF.setText(String.valueOf(cGU.getDataDao().getDataDiNascita()));


                    }
                    default -> Logger.getLogger("initialize gestione utente").log(Level.SEVERE," user is empty !!");

                }
            }catch (SQLException |CsvValidationException|IOException e)
            {
                Logger.getLogger("initialize gestione utente").log(Level.SEVERE," exception" , e);
            }
        }
        




}
