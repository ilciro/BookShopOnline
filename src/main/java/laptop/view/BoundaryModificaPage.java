package laptop.view;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import laptop.controller.ControllerModifPage;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;

public class BoundaryModificaPage implements Initializable {
	@FXML
	private Pane pane;
	@FXML
	private GridPane gridpane ;
	@FXML
	private TextField titoloT;
	@FXML 
	private TextField numeroPagineT;
	@FXML
	private TextField codeIsbnT;
	@FXML
	private TextField editoreT;
	@FXML
	private TextField autoreT;
	@FXML
	private TextField linguaT;
	@FXML
	private ListView<String> categoriaTF ;
	@FXML
	private DatePicker dataP;
	@FXML
	private TextField recensioneT;
	@FXML 
	private TextArea descrizioneT;
	@FXML
	private CheckBox disponibilitaC;
	@FXML
	private TextField prezzoT;
	@FXML
	private TextField copieRimanentiT;
	@FXML
	private Button buttonC;
	@FXML
	private Button buttonA;
	@FXML
	private Label titoloL;
	@FXML
	private Label numeroPagineL;
	@FXML
	private Label codeIsbnL;
	@FXML
	private Label editoreL;
	@FXML
	private Label autoreL;
	@FXML
	private Label linguaL;
	@FXML
	private Label categoriaL;
	@FXML
	private Label dataL;
	@FXML
	private Label recensioneL;
	@FXML
	private Label descrizioneL;
	@FXML
	private Label disponibilitaL;
	@FXML
	private Label prezzoL;
	@FXML
	private Label copieRimanentiL;
	
	@FXML
	private Label labelT;
	@FXML
	private Label labelNP;
	@FXML
	private Label labelCod;
	@FXML
	private Label labelE;
	@FXML
	private Label labelA;
	@FXML
	private Label labelL;
	@FXML
	private Label labelCat;
	@FXML
	private Label labelD;
	@FXML
	private Label labelR;
	@FXML
	private Label labelDesc;
	@FXML
	private Label labelDisp;
	@FXML
	private Label labelP;
	@FXML
	private Label labelCopie;

	private ControllerModifPage cMP;
	private final ControllerSystemState vis= ControllerSystemState.getInstance();
	protected float prezzo ;
	protected int copie;
	protected Scene scene;
	protected int np;
	private final String[] infoGen=new String[7];
	private final String[] infoCostoDisp=new String[7];
	private final String [] info=new String[5];
	private final ObservableList<String> items = FXCollections.observableArrayList();


	@FXML
	private void aggiorna() throws SQLException, NullPointerException, CsvValidationException, IOException, IdException {
        switch (vis.getType()) {
            case "libro" -> {
                String []dataL=new String[13];
                String t = titoloT.getText();
                np = Integer.parseInt(numeroPagineT.getText());
                String cod = codeIsbnT.getText();
                String ed = editoreT.getText();
                String a = autoreT.getText();
                String l = linguaT.getText();
                String c = categoriaTF.getSelectionModel().getSelectedItem();
                LocalDate d = dataP.getValue();
                String r = recensioneT.getText();
                int dispo=0;
                boolean disp = disponibilitaC.isSelected();
                if(disp)
                    dispo=1;

                String desc = descrizioneT.getText();


                prezzo = Float.parseFloat(prezzoT.getText());
                copie = Integer.parseInt(copieRimanentiT.getText());

                dataL[0]=t;
                dataL[1]= String.valueOf(np);
                dataL[2]=cod;
                dataL[3]=ed;
                dataL[4]=a;
                dataL[5]=l;
                dataL[6]=c;
                dataL[7]= String.valueOf(d);
                dataL[8]=r;
                dataL[9]= String.valueOf(copie);
                dataL[10]=desc;
                dataL[11]= String.valueOf(dispo);
                dataL[12]= String.valueOf(prezzo);

				if(cMP.checkDataL(dataL))
				{
                    Stage stage;
                    Parent root;
                    stage = (Stage) buttonC.getScene().getWindow();
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("paginaGestione.fxml")));
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

				}
                else
                {
                    java.util.logging.Logger.getLogger("Test modif book").log(Level.SEVERE,"\n book not modified ");

                }
            }
            case "giornale" -> {
                String []dataG=new String[8];
                String t = titoloT.getText();
                String tipo = "QUOTIDIANO";
                String ed = editoreT.getText();
                String l = linguaT.getText();
                LocalDate d = dataP.getValue();
                boolean disp = disponibilitaC.isPressed();
                int dispo;
                if (disp) {
                    dispo = 1;
                } else {
                    dispo = 0;
                }
                prezzo = Float.parseFloat(prezzoT.getText());
                copie = Integer.parseInt(copieRimanentiT.getText());

               dataG[0]=t;
               dataG[1]=tipo;
               dataG[2]=ed;
                dataG[3]=l;
                dataG[4]= String.valueOf(d);
                dataG[5]= String.valueOf(dispo);
                dataG[6]= String.valueOf(prezzo);
                dataG[7]= String.valueOf(copie);


                if(cMP.checkDataG(dataG))
                {
                    Stage stage;
                    Parent root;
                    stage = (Stage) buttonC.getScene().getWindow();
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("paginaGestione.fxml")));
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
                else
                {
                    java.util.logging.Logger.getLogger("Test modif daily").log(Level.SEVERE,"\n daily not modified ");

                }

            }
            case "rivista" -> {
                String [] info=new String[10];
                String t = titoloT.getText();
                String tipologia = categoriaTF.getSelectionModel().getSelectedItem();
                String autore = autoreT.getText();
                String l = linguaT.getText();
                String e = editoreT.getText();
                String desc = descrizioneT.getText();
                LocalDate d = dataP.getValue();
                boolean disp = disponibilitaC.isPressed();


                int dispo;

                if (disp) {
                    dispo = 1;
                    //disponibile
                } else {
                    dispo = 0;
                }
                prezzo = Float.parseFloat(prezzoT.getText());
                copie = Integer.parseInt(copieRimanentiT.getText());

                info[0] = t;
                info[1] = tipologia;
                info[2] = autore;
                info[3] = l;
                info[4] = e;
                info[5] = desc;
                info[6] = String.valueOf(d);
                info[7] = String.valueOf(dispo);
                info[8] = String.valueOf(prezzo);
                info[9] = String.valueOf(copie);

               if( cMP.checkDataR(info))
               {
                   Stage stage;
                   Parent root;
                   stage = (Stage) buttonC.getScene().getWindow();
                   root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("paginaGestione.fxml")));
                   scene = new Scene(root);
                   stage.setScene(scene);
                   stage.show();
               }


            }
            default -> 	java.util.logging.Logger.getLogger("Test aggiorna").log(Level.SEVERE, "type is worng");

        }
	}
		
		
	@FXML
	private void annulla() throws IOException
	{
		Stage stage;
		Parent root;
		stage = (Stage) buttonA.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("paginaGestione.fxml")));
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/* settare valori textArea*/




        try {
			cMP=new ControllerModifPage();

            switch (vis.getType()) {
                case "libro" -> {
                    labelT.setText(cMP.getLibriById(vis.getId()).get(0).getTitolo());
                    labelNP.setText(String.valueOf(cMP.getLibriById(vis.getId()).get(0).getNrPagine()));
                    labelCod.setText(cMP.getLibriById(vis.getId()).get(0).getCodIsbn());
                    labelE.setText(cMP.getLibriById(vis.getId()).get(0).getEditore());
                    labelA.setText(cMP.getLibriById(vis.getId()).get(0).getAutore());
                    labelL.setText(cMP.getLibriById(vis.getId()).get(0).getLingua());
                    labelCat.setText(String.valueOf(cMP.getLibriById(vis.getId()).get(0).getCategoria()));
                    labelR.setText(cMP.getLibriById(vis.getId()).get(0).getRecensione());
                    labelP.setText(String.valueOf(cMP.getLibriById(vis.getId()).get(0).getPrezzo()));
                    labelCopie.setText(String.valueOf(cMP.getLibriById(vis.getId()).get(0).getNrCopie()));
                    labelD.setText(cMP.getLibriById(vis.getId()).get(0).getDataPubb().toString());
                    labelDesc.setText(cMP.getLibriById(vis.getId()).get(0).getDesc());
                    labelDisp.setText(String.valueOf(cMP.getLibriById(vis.getId()).get(0).getDisponibilita()));

                    categoriaTF.setItems(items);
                    items.add("ADOLESCENTI_RAGAZZI");
                    items.add("ARTE");
                    items.add("CINEMA_FOTOGRAFIA");
                    items.add("BIOGRAFIE");
                    items.add("DIARI_MEMORIE");
                    items.add("CALENDARI_AGENDE");
                    items.add("DIRITTO");
                    items.add("DIZINARI_OPERE");
                    items.add("ECONOMIA");
                    items.add("FAMIGLIA");
                    items.add("SALUTE_BENESSERE");

                    items.add("FANTASCIENZA_FANTASY");
                    items.add("FUMETTI_MANGA");
                    items.add("GIALLI_THRILLER");
                    items.add("COMPUTER_GIOCHI");
                    items.add("HUMOR");
                    items.add("INFORMATICA");
                    items.add("WEB_DIGITAL_MEDIA");
                    items.add("LETTERATURA_NARRATIVA");
                    items.add("LIBRI_BAMBINI");
                    items.add("LIBRI_SCOLASTICI");
                    items.add("LIBRI_UNIVERSITARI");
                    items.add("RICETTARI_GENERALI");

                    items.add("LINGUISTICA_SCRITTURA");
                    items.add("POLITICA");
                    items.add("RELIGIONE");
                    items.add("ROMANZI_ROSA");

                    items.add("SCIENZE");
                    items.add("TECNOLOGIA_MEDICINA");
                }
                case "giornale" -> {


                    numeroPagineL.setVisible(false);
                    labelNP.setVisible(false);
                    numeroPagineT.setVisible(false);
                    codeIsbnL.setVisible(false);
                    labelCod.setVisible(false);
                    codeIsbnT.setVisible(false);
                    autoreL.setVisible(false);
                    labelA.setVisible(false);
                    autoreT.setVisible(false);
                    labelT.setText(cMP.getGiornaliById(vis.getId()).get(0).getTitolo());
                    labelE.setText(cMP.getGiornaliById(vis.getId()).get(0).getEditore());
                    labelL.setText(cMP.getGiornaliById(vis.getId()).get(0).getLingua());
                    labelCat.setText("QUOTIDIANO");
                    categoriaTF.setItems(items);
                    items.add("QUOTIDIANO");
                    recensioneL.setVisible(false);
                    labelR.setVisible(false);
                    recensioneT.setVisible(false);
                    descrizioneL.setVisible(false);
                    labelDesc.setVisible(false);
                    descrizioneT.setVisible(false);
                    labelP.setText(String.valueOf(cMP.getGiornaliById(vis.getId()).get(0).getPrezzo()));
                    labelCopie.setText(String.valueOf(cMP.getGiornaliById(vis.getId()).get(0).getCopieRimanenti()));
                    labelD.setText(cMP.getGiornaliById(vis.getId()).get(0).getDataPubb().toString());
                    labelDisp.setText(String.valueOf(cMP.getGiornaliById(vis.getId()).get(0).getDisponibilita()));

                }
                case "rivista"-> {

                    numeroPagineL.setVisible(false);
                    labelNP.setVisible(false);
                    numeroPagineT.setVisible(false);
                    codeIsbnL.setVisible(false);
                    labelCod.setVisible(false);
                    codeIsbnT.setVisible(false);
                    recensioneL.setVisible(false);
                    labelR.setVisible(false);
                    recensioneT.setVisible(false);
                    labelT.setText(cMP.getRivistaById(vis.getId()).get(0).getTitolo());
                    labelE.setText(cMP.getRivistaById(vis.getId()).get(0).getEditore());
                    labelA.setText(cMP.getRivistaById(vis.getId()).get(0).getAutore());
                    labelL.setText(cMP.getRivistaById(vis.getId()).get(0).getLingua());
                    labelCat.setText(String.valueOf(cMP.getRivistaById(vis.getId()).get(0).getTipologia()));
                    labelP.setText(String.valueOf(cMP.getRivistaById(vis.getId()).get(0).getPrezzo()));
                    labelCopie.setText(String.valueOf(cMP.getRivistaById(vis.getId()).get(0).getCopieRim()));
                    labelD.setText(cMP.getRivistaById(vis.getId()).get(0).getDataPubb().toString());
                    labelDesc.setText(cMP.getRivistaById(vis.getId()).get(0).getDescrizione());
                    labelDisp.setText(String.valueOf(cMP.getRivistaById(vis.getId()).get(0).getDisp()));

                    categoriaTF.setItems(items);
                    items.add("SETTIMANALE");
                    items.add("BISETTIMANALE");
                    items.add("MENSILE");
                    items.add("BIMESTRALE");
                    items.add("TRIMESTRALE");
                    items.add("ANNUALE");
                    items.add("ESTIVO");
                    items.add("INVERNALE");
                    items.add("SPORTIVO");
                    items.add("CINEMATOGRAFIA");
                    items.add("GOSSIP");
                    items.add("TELEVISIVO");
                    items.add("MILITARE");
                    items.add("INFORMATICA");
                }
                default -> 	java.util.logging.Logger.getLogger("Test initialize").log(Level.SEVERE, "categories ot matched");

            }



		} catch (SQLException | IOException | CsvValidationException | IdException e)
		{
			java.util.logging.Logger.getLogger("Test initialize").log(Level.SEVERE,"\n eccezione ottenuta {0}",e.toString());

		} 
		
		


	}

	
			


}
