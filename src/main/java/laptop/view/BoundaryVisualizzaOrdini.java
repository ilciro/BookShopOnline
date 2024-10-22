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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import laptop.controller.ControllerVisualizzaOrdini;
import laptop.model.Pagamento;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoundaryVisualizzaOrdini implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private Label header;
    @FXML
    private TableView<Pagamento> tableview;
    @FXML
    private TableColumn<Pagamento,String> idPagamento;
    @FXML
    private TableColumn<Pagamento,String> metodo;
    @FXML
    private TableColumn<Pagamento,Float> spesaTotale;
    @FXML
    private TableColumn<Pagamento,String> tipoAcquisto;
    @FXML
    private TableColumn<Pagamento,Integer> idProdotto;
    @FXML
    private  HBox vbox;
    @FXML
    private Button generaB;
    @FXML
    private Button eliminaB;
    @FXML
    private Button indietroB;
    private ControllerVisualizzaOrdini cV;
    protected Scene scene;

    @FXML
    private void genera() throws CsvValidationException, IOException {
        tableview.setItems(cV.getLista());
    }
    @FXML
    private void elimina() throws CsvValidationException, SQLException, IOException {
        int id=tableview.getSelectionModel().getSelectedItem().getIdPag();
        if(cV.cancellaPagamento(id))
        {
            Stage stage;
            Parent root;
            stage = (Stage) eliminaB.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePageFinale.fxml")));
            stage.setTitle("Benvenuto nella home page");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            Logger.getLogger("elimina").log(Level.SEVERE," deleted payment failed");
            Stage stage;
            Parent root;
            stage = (Stage) indietroB.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("visualizzaOrdini.fxml")));
            stage.setTitle("Benvenuto nella schermata degli ordini");
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
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePageFinale.fxml")));
        stage.setTitle("Benvenuto nella home page");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            cV=new ControllerVisualizzaOrdini();
        } catch (IOException e) {
            Logger.getLogger("initialize").log(Level.SEVERE," costrunctor not created");
        }
        header.setText(header.getText()+"\t"+ cV.getEmail());

        idPagamento.setCellValueFactory(new PropertyValueFactory<>("idPag"));
        metodo.setCellValueFactory(new PropertyValueFactory<>("metodo"));
        spesaTotale.setCellValueFactory(new PropertyValueFactory<>("ammontare"));
        tipoAcquisto.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        idProdotto.setCellValueFactory(new PropertyValueFactory<>("idOggetto"));

    }
}
