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
import laptop.controller.ControllerAggiungiPage;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;

public class BoundaryAggiungiPage implements Initializable {
	
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
	private ListView<String> categoriaList ;
	@FXML
	private DatePicker dataP;
	@FXML
	private TextField recensioneT;
	@FXML 
	private TextArea descrizioneA;
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
	
	private ControllerAggiungiPage cAP;
	

	protected Scene scene; 
	protected float prezzo;
	protected int copie;
	private final ObservableList<String> items = FXCollections.observableArrayList();
	private final ControllerSystemState vis=ControllerSystemState.getInstance();


	@FXML
	private void conferma() throws SQLException, CsvValidationException, IOException, IdException {
		switch (vis.getType())
		{
			case "libro"->
			{
				String [] dataL=new String[13];
				String titolo=titoloT.getText();
				String numPag=numeroPagineT.getText();
				String isbn=codeIsbnT.getText();
				String editore=editoreT.getText();
				String autore=autoreT.getText();
				String lingua=linguaT.getText();
				String categoria=categoriaList.getSelectionModel().getSelectedItem();
				LocalDate data=dataP.getValue();
				String recensione=recensioneT.getText();
				String nrCopie=copieRimanentiT.getText();
				String desc=descrizioneA.getText();
				boolean disp=disponibilitaC.isSelected();
				int dispo;
				if(disp)
				{
					dispo=1;

				}
				else {
					dispo=0;

				}
				String prezzo=prezzoT.getText();

				//faccio cosi per evitare sia liste
				//che di passare ogegtto tra boundary e control

				dataL[0]=titolo;
				dataL[1]=numPag;
				dataL[2]=isbn;
				dataL[3]=editore;
				dataL[4]=autore;
				dataL[5]=lingua;
				dataL[6]=categoria;
				dataL[7]= String.valueOf(data);
				dataL[8]=recensione;
				dataL[9]=nrCopie;
				dataL[10]=desc;
				dataL[11]= String.valueOf(dispo);
				dataL[12]=prezzo;


				if(cAP.checkDataL(dataL))

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
			case "giornale"->
			{

				Giornale g=new Giornale();
				g.setTitolo(titoloT.getText());
				g.setEditore(editoreT.getText());
				g.setLingua(linguaT.getText());
				g.setDataPubb(dataP.getValue());
				g.setTipologia(categoriaList.getSelectionModel().getSelectedItem());

				boolean disp=disponibilitaC.isSelected();

				int dispo;

				if(disp)
				{
					dispo=1;
					//disponibile
				}
				else {
					dispo=0;
				}
				g.setDisponibilita(dispo);
				g.setPrezzo(Float.parseFloat(prezzoT.getText()));
				g.setCopieRimanenti(Integer.parseInt(copieRimanentiT.getText()));

				if(cAP.checkDataG(g))
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
			case "rivista"->
			{
				String []dataR=new String[10];
				int dispo;
				String t=titoloT.getText();
				String tipologia=categoriaList.getSelectionModel().getSelectedItem();
				String a=autoreT.getText();
				String l=linguaT.getText();
				String ed=editoreT.getText();
				String desc=descrizioneA.getText();
				LocalDate data=dataP.getValue();
				boolean disp=disponibilitaC.isSelected();
				prezzo=Float.parseFloat(prezzoT.getText());
				copie=Integer.parseInt(copieRimanentiT.getText());

				dataR[0]=t;
				dataR[1]=tipologia;
				dataR[2]=a;
				dataR[3]=l;
				dataR[4]=ed;
				if(disp)
				{
					dispo=1;
				}
				else
					dispo=0;
				dataR[5]=desc;
				dataR[6]= String.valueOf(data);
				dataR[7]= String.valueOf(dispo);
				dataR[8]= String.valueOf(prezzo);
				dataR[9]= String.valueOf(copie);


				if(cAP.checkDataR(dataR))
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
			default -> throw new IOException(" type not correct");

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
        try {
            cAP=new ControllerAggiungiPage();
        } catch (IOException e) {
			java.util.logging.Logger.getLogger("Test initialize").log(Level.SEVERE, "eccezione ottenuta",e);

		}
		switch (vis.getType())
		{
			case "libro"->
			{
				categoriaList.setItems(items);
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
			case "giornale"->
			{
				numeroPagineL.setVisible(false);
				numeroPagineT.setVisible(false);
				codeIsbnL.setVisible(false);
				codeIsbnT.setVisible(false);
				autoreL.setVisible(false);
				autoreT.setVisible(false);
				recensioneL.setVisible(false);
				recensioneT.setVisible(false);
				descrizioneL.setVisible(false);
				descrizioneA.setVisible(false);


				categoriaList.setItems(items);


				items.add("QUOTIDIANO");
			}
			case "rivista"->
			{
				numeroPagineL.setVisible(false);
				numeroPagineT.setVisible(false);
				codeIsbnL.setVisible(false);
				codeIsbnT.setVisible(false);
				recensioneL.setVisible(false);
				recensioneT.setVisible(false);



				categoriaList.setItems(items);
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

			default -> {
            }

		}


	}

	
			
}
