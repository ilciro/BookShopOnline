package laptop.view;

import com.opencsv.exceptions.CsvValidationException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import laptop.controller.ControllerAnnullaPagamento;
import laptop.controller.ControllerSystemState;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuondaryAnnullaPagamento implements Initializable {
    private  ControllerAnnullaPagamento cannP;

    @FXML
    private Pane pane;
    @FXML
    private TextArea tAPagamento;
    @FXML
    private TextArea tAFattura;
    @FXML
    private Button cancFattura;
    @FXML
    private Button cancPagamento;
    @FXML
    private Button buttonI;
    @FXML
    private TextField idFattura;
    @FXML
    private TextField idPagamento;

    private final ControllerSystemState vis= ControllerSystemState.getInstance();
    private static final String CASH="cash";
    private  static final String CCREDITO="cCredito";
    protected Scene scene;


    @FXML
    private void cancellaFattura() throws SQLException, CsvValidationException, IOException {
        if(cannP.cancellaFattura(idFattura.getText())) {
            Logger.getLogger("cancella Pagamento ok ").log(Level.INFO, "payment deleted with success!!");
            tAFattura.clear();
        }
    }
    @FXML
    private void cancellaPagamento() throws SQLException, CsvValidationException, IOException {
        if(cannP.cancellaPagamento(idPagamento.getText())) {
            Logger.getLogger("cancella Pagamento ok ").log(Level.INFO, "payment deleted with success!!");
            tAPagamento.clear();
        }
    }
    @FXML
    private void indietro() throws IOException {
        boolean status = false;
        switch (vis.getMetodoP())
        {
            case CASH -> {

                if(tAPagamento.getText().isEmpty() && tAFattura.getText().isEmpty())
                    status=true;

            }
            case CCREDITO ->
            {
                if (tAPagamento.getText().isEmpty()) status=true;
            }
            default ->  Logger.getLogger("indietro ").log(Level.INFO," textareas not empty !!!");

        }
        if(status)
        {
            Stage stage;
            Parent root;
            stage = (Stage) buttonI.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePageFinale.fxml")));
            stage.setTitle("Benvenuto nella schermata del riepilogo ordine");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {


            cannP = new ControllerAnnullaPagamento();


            switch (vis.getMetodoP()) {
                case CASH -> {

                        tAFattura.setText(cannP.getFattura());
                        tAPagamento.setText(cannP.getPagamento());

                }
                case CCREDITO ->

                        tAPagamento.setText(cannP.getPagamento());


                default -> Logger.getLogger("initialize ").log(Level.INFO, "payment type not correct!");


            }
        }catch (CsvValidationException | IOException e)
        {

           Logger.getLogger("initialize exception ").log(Level.INFO, " exception ", e);

        }


    }
}
