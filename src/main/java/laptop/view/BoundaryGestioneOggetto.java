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
import laptop.controller.ControllerGestione;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoundaryGestioneOggetto implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private Label header;
    @FXML
    private VBox vbox;
    @FXML
    private TextField titoloTF;
    @FXML
    private TextField codiceTF;
    @FXML
    private TextField editoreTF;
    @FXML
    private TextField autoreTF;
    @FXML
    private TextField linguaTF;
    @FXML
    private TextField categoriaTF;
    @FXML
    private TextArea descTF;
    @FXML
    private TextField dataTF;
    @FXML
    private TextArea recensioneTF;
    @FXML
    private TextField pagineTF;
    @FXML
    private TextField copieTF;
    @FXML
    private TextField dispTF;
    @FXML
    private TextField prezzoTF;
    @FXML
    private VBox vbox1;
    @FXML
    private TextField titoloTF1;
    @FXML
    private TextField codiceTF1;
    @FXML
    private TextField editoreTF1;
    @FXML
    private TextField autoreTF1;
    @FXML
    private TextField linguaTF1;
    @FXML
    private ListView<String> categoriaTF1;
    @FXML
    private TextArea descTF1;
    @FXML
    private DatePicker dataN;
    @FXML
    private TextArea recensioneTF1;
    @FXML
    private TextField pagineTF1;
    @FXML
    private TextField copieTF1;
    @FXML
    private TextField dispTF1;
    @FXML
    private TextField prezzoTF1;
    @FXML
    private VBox vbox2;
    @FXML
    private Button buttonIns;
    @FXML
    private  Button modButton;
    @FXML
    private Button indietroB;

    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private Scene scene;
    private  ControllerGestione cG;

    private  static final String LIBRO="libro";
    private  static final String GIORNALE="giornale";
    private  static final String RIVISTA="rivista";
    private static final String RACCOLTA="raccolta.fxml";
    private static final String INFORMATICA="INFORMATICA";
    @FXML
    private void inserisci() throws CsvValidationException, IOException, IdException, SQLException {

        if(cG.inserisci(dati()))
        {
            Stage stage;
            Parent root;
            stage = (Stage) buttonIns.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(RACCOLTA)));
            stage.setTitle("Benvenuto nella schermata per inserire");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else throw new IdException(" id is incorrect");

    }
    @FXML
    private void modifica() throws IOException, IdException, CsvValidationException, SQLException {
        if(cG.modifica(dati()))
        {
            Stage stage;
            Parent root;
            stage = (Stage) modButton.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(RACCOLTA)));
            stage.setTitle("Benvenuto nella schermata della raccolta");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else throw new IdException(" id is incorrect");

    }
    @FXML
    private void indietro() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) indietroB.getScene().getWindow();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(RACCOLTA)));
        stage.setTitle("Benvenuto nella schermata della raccolta");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            cG=new ControllerGestione();
        } catch (IOException e) {
            Logger .getLogger("costruttore").log(Level.SEVERE," exception ",e);
        }
        header.setText(header.getText() + vis.getType());
        ObservableList<String> list= FXCollections.observableArrayList();



        if(vis.getTipoModifica().equalsIgnoreCase("inserisci"))
        {
            modButton.setVisible(false);
            vbox.setVisible(false);

            switch (vis.getType())
            {
                case LIBRO->
                {
                    list.add("ADOLESCENTI_RAGAZZI");
                    list.add("ARTE");
                    list.add("CINEMA_FOTOGRAFIA");
                    list.add("BIOGRAFIE");
                    list.add("DIARI_MEMORIE");
                    list.add("CALENDARI_AGENDE");
                    list.add("DIRITTO");
                    list.add("DIZINARI_OPERE");
                    list.add("ECONOMIA");
                    list.add("FAMIGLIA");
                    list.add("FANTASCIENZA_FANTASY");
                    list.add("FUMETTI_MANGA");
                    list.add("GIALLI_THRILLER");
                    list.add("COMPUTER_GIOCHI");
                    list.add("HUMOR");
                    list.add(INFORMATICA);
                    list.add("WEB_DIGITAL_MEDIA");
                    list.add("LETTERATURA_NARRATIVA");
                    list.add("LIBRI_BAMBINI");
                    list.add("LIBRI_SCOLASTICI");
                    list.add("LIBRI_UNIVERSITARI");
                    list.add("RICETTARI_GENERALI");
                    list.add("LINGUISTICA_SCRITTURA");
                    list.add("POLITICA");
                    list.add("RELIGIONE");
                    list.add("ROMANZI_ROSA");
                    list.add("SCIENZE");
                    list.add("TECNOLOGIA_MEDICINA");
                    list.add("ALTRO");
                    categoriaTF1.setItems(list);


                }
                case GIORNALE->
                {
                    codiceTF1.setVisible(false);
                    autoreTF1.setVisible(false);
                    list.add("QUOTIDIANO");
                    categoriaTF1.setItems(list);
                    descTF1.setVisible(true);
                    recensioneTF1.setVisible(false);
                    pagineTF1.setVisible(false);
                }
                case RIVISTA->
                {
                    codiceTF1.setVisible(false);
                    recensioneTF1.setVisible(false);
                    pagineTF1.setVisible(false);
                    list.add("SETTIMANALE");
                    list.add("BISETTIMANALE");
                    list.add("MENSILE");
                    list.add("BIMESTRALE");
                    list.add("TRIMESTRALE");
                    list.add("ANNUALE");
                    list.add("ESTIVO");
                    list.add("INVERNALE");
                    list.add("SPORTIVO");
                    list.add("CINEMATOGRAFICA");
                    list.add("GOSSIP");
                    list.add("TELEVISIVO");
                    list.add("MILITARE");
                    list.add(INFORMATICA);
                    categoriaTF1.setItems(list);




                }
                default -> Logger.getLogger("initialize").log(Level.SEVERE," type is wrong");
            }

        }
        else {
                buttonIns.setVisible(false);
            try {
                switch (vis.getType()) {
                    case LIBRO -> {

                        titoloTF.setText(cG.libroById().get(0).getTitolo());
                        codiceTF.setText(cG.libroById().get(0).getCodIsbn());
                        editoreTF.setText(cG.libroById().get(0).getEditore());
                        autoreTF.setText(cG.libroById().get(0).getAutore());
                        linguaTF.setText(cG.libroById().get(0).getLingua());
                        categoriaTF.setText(cG.libroById().get(0).getCategoria());
                        descTF.setText(cG.libroById().get(0).getDesc());
                        dataTF.setText(String.valueOf(cG.libroById().get(0).getDataPubb()));
                        recensioneTF.setText(cG.libroById().get(0).getRecensione());
                        pagineTF.setText(String.valueOf(cG.libroById().get(0).getNrPagine()));
                        copieTF.setText(String.valueOf(cG.libroById().get(0).getNrCopie()));
                        dispTF.setText(String.valueOf(cG.libroById().get(0).getDisponibilita()));
                        prezzoTF.setText(String.valueOf(cG.libroById().get(0).getPrezzo()));
                        list.add("ADOLESCENTI_RAGAZZI");
                        list.add("ARTE");
                        list.add("CINEMA_FOTOGRAFIA");
                        list.add("BIOGRAFIE");
                        list.add("DIARI_MEMORIE");
                        list.add("CALENDARI_AGENDE");
                        list.add("DIRITTO");
                        list.add("DIZINARI_OPERE");
                        list.add("ECONOMIA");
                        list.add("FAMIGLIA");
                        list.add("FANTASCIENZA_FANTASY");
                        list.add("FUMETTI_MANGA");
                        list.add("GIALLI_THRILLER");
                        list.add("COMPUTER_GIOCHI");
                        list.add("HUMOR");
                        list.add(INFORMATICA);
                        list.add("WEB_DIGITAL_MEDIA");
                        list.add("LETTERATURA_NARRATIVA");
                        list.add("LIBRI_BAMBINI");
                        list.add("LIBRI_SCOLASTICI");
                        list.add("LIBRI_UNIVERSITARI");
                        list.add("RICETTARI_GENERALI");
                        list.add("LINGUISTICA_SCRITTURA");
                        list.add("POLITICA");
                        list.add("RELIGIONE");
                        list.add("ROMANZI_ROSA");
                        list.add("SCIENZE");
                        list.add("TECNOLOGIA_MEDICINA");
                        list.add("ALTRO");
                        categoriaTF1.setItems(list);


                    }
                    case GIORNALE -> {
                        codiceTF1.setVisible(false);
                        autoreTF1.setVisible(false);
                        list.add("QUOTIDIANO");
                        categoriaTF1.setItems(list);
                        descTF1.setVisible(false);
                        recensioneTF1.setVisible(false);
                        pagineTF1.setVisible(false);

                        codiceTF.setVisible(false);
                        autoreTF.setVisible(false);

                        descTF.setVisible(false);
                        recensioneTF.setVisible(false);
                        pagineTF.setVisible(false);

                        titoloTF.setText(cG.giornaleById().get(0).getTitolo());
                        editoreTF.setText(cG.giornaleById().get(0).getEditore());
                        linguaTF.setText(cG.giornaleById().get(0).getLingua());
                        categoriaTF.setText(cG.giornaleById().get(0).getCategoria());
                        dataTF.setText(String.valueOf(cG.giornaleById().get(0).getDataPubb()));
                        copieTF.setText(String.valueOf(cG.giornaleById().get(0).getCopieRimanenti()));
                        dispTF.setText(String.valueOf(cG.giornaleById().get(0).getDisponibilita()));
                        prezzoTF.setText(String.valueOf(cG.giornaleById().get(0).getPrezzo()));
                    }
                    case RIVISTA -> {
                        codiceTF1.setVisible(false);
                        recensioneTF1.setVisible(false);
                        pagineTF1.setVisible(false);
                        list.add("SETTIMANALE");
                        list.add("BISETTIMANALE");
                        list.add("MENSILE");
                        list.add("BIMESTRALE");
                        list.add("TRIMESTRALE");
                        list.add("ANNUALE");
                        list.add("ESTIVO");
                        list.add("INVERNALE");
                        list.add("SPORTIVO");
                        list.add("CINEMATOGRAFICA");
                        list.add("GOSSIP");
                        list.add("TELEVISIVO");
                        list.add("MILITARE");
                        list.add(INFORMATICA);
                        categoriaTF1.setItems(list);
                        codiceTF.setVisible(false);
                        recensioneTF.setVisible(false);
                        pagineTF.setVisible(false);

                        titoloTF.setText(cG.rivistaById().get(0).getTitolo());
                        editoreTF.setText(cG.rivistaById().get(0).getEditore());
                        autoreTF.setText(cG.rivistaById().get(0).getAutore());
                        linguaTF.setText(cG.rivistaById().get(0).getLingua());
                        categoriaTF.setText(cG.rivistaById().get(0).getCategoria());
                        descTF.setText(cG.rivistaById().get(0).getDescrizione());
                        dataTF.setText(String.valueOf(cG.rivistaById().get(0).getDataPubb()));
                        copieTF.setText(String.valueOf(cG.rivistaById().get(0).getCopieRim()));
                        dispTF.setText(String.valueOf(cG.rivistaById().get(0).getDisp()));
                        prezzoTF.setText(String.valueOf(cG.rivistaById().get(0).getPrezzo()));



                    }
                    default -> Logger.getLogger("modif").log(Level.SEVERE," type is wrong !!");

                }
            } catch (CsvValidationException |IOException|IdException e) {
            Logger.getLogger("modif").log(Level.SEVERE," error in list", e);
        }

        }


    }
    private String[] dati()
    {
        String[] param =new String[14];

        switch (vis.getType())
        {
            case LIBRO -> {
                param[0]=titoloTF1.getText();
                param[1]=codiceTF1.getText();
                param[2]=editoreTF1.getText();
                param[3]=autoreTF1.getText();
                param[4]=linguaTF1.getText();
                param[5]=categoriaTF1.getSelectionModel().getSelectedItem();
                param[6]=descTF1.getText();
                param[7]= String.valueOf(dataN.getValue());
                param[8]=recensioneTF1.getText();
                param[9]=pagineTF1.getText();
                param[10]=copieTF1.getText();
                param[11]=dispTF1.getText();
                param[12]=prezzoTF1.getText();
                //non metto id
            }
            case GIORNALE -> {
                param[0]=titoloTF1.getText();
                param[2]=editoreTF1.getText();
                param[4]=linguaTF1.getText();
                param[5]=categoriaTF1.getSelectionModel().getSelectedItem();
                param[7]= String.valueOf(dataN.getValue());
                param[10]=copieTF1.getText();
                param[11]=dispTF1.getText();
                param[12]=prezzoTF1.getText();
            }
            case RIVISTA -> {
                param[0]=titoloTF1.getText();
                param[2]=editoreTF1.getText();
                param[3]=autoreTF1.getText();
                param[4]=linguaTF1.getText();
                param[5]=categoriaTF1.getSelectionModel().getSelectedItem();
                param[6]=descTF1.getText();
                param[7]= String.valueOf(dataN.getValue());
                param[10]=copieTF1.getText();
                param[11]=dispTF1.getText();
                param[12]=prezzoTF1.getText();
            }
            default -> Logger.getLogger("insert").log(Level.SEVERE, "error in insert");
        }
        return param;
    }



}
