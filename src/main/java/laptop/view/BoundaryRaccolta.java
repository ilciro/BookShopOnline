package laptop.view;

import com.opencsv.exceptions.CsvValidationException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import laptop.controller.ControllerRaccolta;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import laptop.model.raccolta.Raccolta;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoundaryRaccolta implements Initializable
{
    @FXML
    private Pane pane;
    @FXML
    private Label header;
    @FXML
    private HBox hbox;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private RadioButton radioL;
    @FXML
    private RadioButton radioG;
    @FXML
    private RadioButton radioR;
    @FXML
    private TableView<Raccolta> tableView;
    @FXML
    private TableColumn<Raccolta,Integer>id;
    @FXML
    private TableColumn<Raccolta,String>titolo;
    @FXML
    private TableColumn<Raccolta,Float>prezzo;
    @FXML
    private Button inserisciB;
    @FXML
    private Button modificaB;
    @FXML
    private Button eliminaB;
    @FXML
    private Button indietroB;
    @FXML
    private Button generaB;
    @FXML
    private TextField idTF;
    private Scene scene;
    private  ControllerRaccolta cRacc;
    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final String RACCOLTA="raccolta.fxml";


    @FXML
    private void inserisci() throws IOException {
        vis.setTipoModifica("inserisci");

        if(radioL.isSelected()) vis.setTypeAsBook();
        if (radioG.isSelected()) vis.setTypeAsDaily();
        if(radioR.isSelected()) vis.setTypeAsMagazine();

        try{
            if(vis.getType().isEmpty())
            {
                throw new IOException(" type is wrong!!");
            }
            else {
                Stage stage;
                Parent root;
                stage = (Stage) inserisciB.getScene().getWindow();
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("gestioneOggetto.fxml")));
                stage.setTitle("Benvenuto nella schermata della gestione/inserimento");
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            }
            }catch (IOException e)
        {
            Logger.getLogger("inserisci").log(Level.SEVERE," exeption has occurred !!.",e);
            Stage stage;
            Parent root;
            stage = (Stage) inserisciB.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(RACCOLTA)));
            stage.setTitle("Benvenuto nella schermata della gestione per inserire");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }


    }
    @FXML
    private void modifica() throws IOException {
        vis.setTipoModifica("modifica");
        try{

             if(Integer.parseInt(idTF.getText())<=0 || (Integer.parseInt(idTF.getText())>cRacc.getRaccoltaLista(vis.getType()).size()))
                throw new IdException(" id is null or not in list");
            else{
                vis.setId(Integer.parseInt(idTF.getText()));
                Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("gestioneOggetto.fxml")));
                Stage stage  = (Stage) modificaB.getScene().getWindow();
                stage.setTitle("Benvenuto nella schermata della gestione");
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }catch (IdException |CsvValidationException |NullPointerException e)
        {
            Logger.getLogger("modifica").log(Level.SEVERE, " error in modif .",e);
            Stage stage;
            Parent root;
            stage = (Stage) modificaB.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(RACCOLTA)));
            stage.setTitle("Benvenuto nella schermata della modifica");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }
    @FXML
    private void elimina() throws IOException {
        vis.setId(Integer.parseInt(idTF.getText()));

        try{
            if(Integer.parseInt(idTF.getText())<=0 || Integer.parseInt(idTF.getText())>cRacc.getRaccoltaLista(vis.getType()).size())
                throw new IdException(" id is null or not in list");

            if(cRacc.elimina()) {
                Stage stage;
                Parent root;
                stage = (Stage) eliminaB.getScene().getWindow();
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(RACCOLTA)));
                stage.setTitle("Benvenuto nella schermata della raccolta");
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                Logger.getLogger(" elimina ok").log(Level.INFO, " deleted successfully");
            }
        }catch (IdException | CsvValidationException  | IOException e)
        {
            Logger.getLogger("elimina").log(Level.SEVERE, " error in elimina");
            Stage stage;
            Parent root;
            stage = (Stage) eliminaB.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(RACCOLTA)));
            stage.setTitle("Benvenuto nella schermata della gestione");
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
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("admin.fxml")));
        stage.setTitle("Benvenuto nella schermata di admin");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void genera() throws CsvValidationException, IOException, IdException {
        if(radioL.isSelected()) vis.setTypeAsBook();
        if(radioG.isSelected()) vis.setTypeAsDaily();
        if(radioR.isSelected()) vis.setTypeAsMagazine();
        tableView.setItems(cRacc.getRaccoltaLista(vis.getType()));
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            cRacc=new ControllerRaccolta();
        } catch (IOException e) {
            Logger.getLogger("initialize ").log(Level.SEVERE," exception has occurred !!.",e);
        }
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        titolo.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        prezzo.setCellValueFactory(new PropertyValueFactory<>("prezzo"));

    }
}
