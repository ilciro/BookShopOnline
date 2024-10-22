package laptop.view;

import com.opencsv.exceptions.CsvValidationException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import laptop.controller.ControllerVisualizzaProfilo;
import laptop.exception.IdException;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoundaryVisualizzaProfilo implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private Label header;
    @FXML
    private TextField ta;
    @FXML
    private HBox hbox;
    @FXML
    private RadioButton ruolo;
    @FXML
    private RadioButton nome;
    @FXML
    private RadioButton cognome;
    @FXML
    private RadioButton email;
    @FXML
    private RadioButton pwd;
    @FXML
    private RadioButton descrizione;
    @FXML
    private RadioButton data;
    @FXML
    private RadioButton tutto;
    @FXML
    private Label labelModifica;
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
    private TextArea  descTA;
    @FXML
    private DatePicker dataN;
    @FXML
    private VBox vbox1;
    @FXML
    private RadioButton radioU;
    @FXML
    private RadioButton radioE;
    @FXML
    private RadioButton radioS;
    @FXML
    private RadioButton radioA;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private ToggleGroup toggleGroup1;
    @FXML
    private VBox vbox2;
    @FXML
    private Button buttonMostra;
    @FXML
    private Button buttonM;
    @FXML
    private Button buttonI;
    private Scene scene;
    private ControllerVisualizzaProfilo cV;



    @FXML
    private void mostra() throws IdException {

        if(ruolo.isSelected()) ruoloM();
        else if(nome.isSelected()) nomeM();
        else if(cognome.isSelected()) cognomeM();
        else if (email.isSelected()) emailM();
        else if(pwd.isSelected()) passwordM();
        else if(descrizione.isSelected()) descrizioneM();
        else if (data.isSelected()) dataM();
        else if(tutto.isSelected()) tuttoM();

        else
        {
            Logger.getLogger("mostra").log(Level.SEVERE,"click one button!!");
            throw new IdException(" campi da modificare non ce ne sono!!");
        }



    }
    @FXML
    private void modifica() throws CsvValidationException, IOException, IdException {
        if(radioU.isSelected()) ruoloTF.setText("UTENTE");
        else if(radioS.isSelected()) ruoloTF.setText("SCRITTORE");
        else if (radioE.isSelected()) ruoloTF.setText("EDITORE");
        else if(radioA.isSelected()) ruoloTF.setText("ADMIN");
        /*
        todo sistemare / finire qui
         */
        if(cV.modifica(ruoloTF.getText(),nomeTF.getText(),cognomeTF.getText(),emailTF.getText(),passTF.getText(),descTA.getText(),dataN.getValue()))
        {
            Stage stage;
            Parent root;
            stage = (Stage) buttonM.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePageFinale.fxml")));
            stage.setTitle("Benvenuto nella home page");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            Logger.getLogger("modif").log(Level.SEVERE," modif not correct!!");
            Stage stage;
            Parent root;
            stage = (Stage) buttonM.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("visualizzaProfilo.fxml")));
            stage.setTitle("Benvenuto nella schermata della modifica");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    private void indietro() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) buttonI.getScene().getWindow();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePageFinale.fxml")));
        stage.setTitle("Benvenuto nella schermata home page");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
















    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cV=new ControllerVisualizzaProfilo();
        ruoloTF.setEditable(false);
        vbox.setVisible(false);
        ta.setText(cV.infoUtente());
        ta.setEditable(false);
        vbox1.setVisible(false);

    }

    private void ruoloM()
    {
        vbox.setVisible(true);
        vbox1.setVisible(true);
        ruoloTF.setVisible(true);
        ruoloTF.setEditable(false);
        nomeTF.setVisible(false);
        cognomeTF.setVisible(false);
        emailTF.setVisible(false);
        passTF.setVisible(false);
        descTA.setVisible(false);
        dataN.setVisible(false);

    }
    private void nomeM()
    {
        vbox.setVisible(true);
        nomeTF.setVisible(true);
        vbox1.setVisible(false);
        ruoloTF.setVisible(false);
        cognomeTF.setVisible(false);
        emailTF.setVisible(false);
        passTF.setVisible(false);
        descTA.setVisible(false);
        dataN.setVisible(false);
    }
    private void cognomeM()
    {
        vbox.setVisible(true);
        cognomeTF.setVisible(true);
        vbox1.setVisible(false);
        ruoloTF.setVisible(false);
        nomeTF.setVisible(false);
        emailTF.setVisible(false);
        passTF.setVisible(false);
        descTA.setVisible(false);
        dataN.setVisible(false);
    }
    private void emailM()
    {
        vbox.setVisible(true);
        emailTF.setVisible(true);
        vbox1.setVisible(false);
        ruoloTF.setVisible(false);
        cognomeTF.setVisible(false);
        nomeTF.setVisible(false);
        passTF.setVisible(false);
        descTA.setVisible(false);
        dataN.setVisible(false);
    }
    private void passwordM()
    {
        vbox.setVisible(true);
        passTF.setVisible(true);
        vbox1.setVisible(false);
        ruoloTF.setVisible(false);
        cognomeTF.setVisible(false);
        emailTF.setVisible(false);
        nomeTF.setVisible(false);
        descTA.setVisible(false);
        dataN.setVisible(false);
    }
    private void descrizioneM()
    {
        vbox.setVisible(true);
        descTA.setVisible(true);
        vbox1.setVisible(false);
        ruoloTF.setVisible(false);
        cognomeTF.setVisible(false);
        emailTF.setVisible(false);
        passTF.setVisible(false);
        nomeTF.setVisible(false);
        dataN.setVisible(false);
    }
    private void dataM()
    {
        vbox.setVisible(true);
        dataN.setVisible(true);
        vbox1.setVisible(false);
        ruoloTF.setVisible(false);
        cognomeTF.setVisible(false);
        emailTF.setVisible(false);
        passTF.setVisible(false);
        descTA.setVisible(false);
        nomeTF.setVisible(false);
    }
    private void tuttoM()
    {
        vbox.setVisible(true);
        vbox1.setVisible(true);
        ruoloTF.setVisible(true);
        nomeTF.setVisible(true);
        cognomeTF.setVisible(true);
        emailTF.setVisible(true);
        passTF.setVisible(true);
        descTA.setVisible(true);
        dataN.setVisible(true);
    }
}
