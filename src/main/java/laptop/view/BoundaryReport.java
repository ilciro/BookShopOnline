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
import laptop.controller.ControllerReport;
import laptop.controller.ControllerSystemState;
import laptop.model.Report;
import laptop.model.TempUser;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class BoundaryReport implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private Label header;
    @FXML
    private HBox hbox;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private RadioButton totaleRadio;
    @FXML
    private RadioButton libriRadio;
    @FXML
    private RadioButton giornaliRadio;
    @FXML
    private RadioButton rivisteRadio;
    @FXML
    private RadioButton utentiRadio;
    @FXML
    private Button generaButton;
    @FXML
    private TableView<Report> tableViewReport;
    @FXML
    private TableColumn<Report,String> idReport;
    @FXML
    private TableColumn<Report,String> titoloOggetto;
    @FXML
    private TableColumn<Report,String> tipologiaOggetto;
    @FXML
    private TableColumn<Report,Float> totale;
    @FXML
    private TableView<TempUser> tableViewUtenti;
    @FXML
    private TableColumn<Integer,TempUser> idUser;
    @FXML
    private TableColumn<String,TempUser> email;
    @FXML
    private TableColumn<LocalDate,TempUser> dataN;


    @FXML
    private Button buttonI;
    protected Scene scene;

    private ControllerReport cR;
    private final ControllerSystemState vis=ControllerSystemState.getInstance();







    @FXML
    private void genera() throws SQLException, CsvValidationException, IOException {


        if(totaleRadio.isSelected()) {
            tableViewReport.setItems(cR.reportTotale());
            tableViewUtenti.setItems(cR.reportUser());




        }
        if(libriRadio.isSelected())
        {
            vis.setTypeAsBook();
            tableViewUtenti.setEditable(false);
            tableViewReport.setItems(cR.reportLGR());
        }
        if(giornaliRadio.isSelected())
        {
            vis.setTypeAsDaily();
            tableViewUtenti.setEditable(false);
            tableViewReport.setItems(cR.reportLGR());
        }
        if(rivisteRadio.isSelected())
        {
            vis.setTypeAsMagazine();
            tableViewUtenti.setEditable(false);
            tableViewReport.setItems(cR.reportLGR());
        }
        if(utentiRadio.isSelected())
        {
            tableViewReport.setEditable(false);
            tableViewUtenti.setItems(cR.reportUser());

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cR=new ControllerReport();
        idReport.setCellValueFactory(new PropertyValueFactory<>("idReport"));
        tipologiaOggetto.setCellValueFactory(new PropertyValueFactory<>("tipologiaOggetto"));
        titoloOggetto.setCellValueFactory(new PropertyValueFactory<>("titoloOggetto"));

        totale.setCellValueFactory(new PropertyValueFactory<>("totale"));

        idUser.setCellValueFactory(new PropertyValueFactory<>("id"));
        email.setCellValueFactory(new PropertyValueFactory<>("emailT"));
        dataN.setCellValueFactory(new PropertyValueFactory<>("dataDiNascitaT"));


    }
}
