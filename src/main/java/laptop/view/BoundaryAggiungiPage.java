package laptop.view;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;

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
import laptop.model.raccolta.Giornale;

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
	private Button buttunC;
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
	
	protected int np;
	protected Scene scene; 
	protected float prezzo;
	protected int copie;
	private final ObservableList<String> items = FXCollections.observableArrayList();
	private final String[] infoGen=new String[6];
	private final String[]infoCostoDisp =new String[6];
	private final String[] info=new String[5];

	@FXML
	private void conferma() throws SQLException
	{
		if(ControllerSystemState.getInstance().getType().equals("libro"))
		{
		
		String t=titoloT.getText();
		np=Integer.parseInt(numeroPagineT.getText());
		String cod=codeIsbnT.getText();
		String ed=editoreT.getText();
		String a=autoreT.getText();
		String l=linguaT.getText();
		String c= categoriaList.getSelectionModel().getSelectedItem();
		LocalDate d=dataP.getValue();
		String r=recensioneT.getText();
		String desc=descrizioneA.getText();
		boolean disp=disponibilitaC.isSelected();	
		if(disp)
		{
			infoCostoDisp[3]=String.valueOf(1);

		}
		else {
			infoCostoDisp[3]=String.valueOf(0);

		}
		prezzo=Float.parseFloat(prezzoT.getText());
		copie=Integer.parseInt(copieRimanentiT.getText());
		
		

		infoGen[0]=t;
		infoGen[2]=a;
		infoGen[3]=l;
		infoGen[4]=ed;
		infoGen[5]=c;

		infoCostoDisp[0]=String.valueOf(np);
		infoCostoDisp[1]=cod;
		infoCostoDisp[4]=String.valueOf(prezzo);
		infoCostoDisp[2]=String.valueOf(copie);// settatodi proprosito
		infoCostoDisp[5]=String.valueOf(50);//settato di proposito
		
		cAP.checkData(infoGen,r,desc,d,infoCostoDisp);
		}
		else if(ControllerSystemState.getInstance().getType().equals("giornale"))
		{
			String title=titoloT.getText();
			String type="Quotidiano";
			String editor=editoreT.getText();
			String language=linguaT.getText();
			LocalDate date=dataP.getValue();
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
			prezzo=Float.parseFloat(prezzoT.getText());
			copie=Integer.parseInt(copieRimanentiT.getText());
			info[0]=title;
			info[1]=type;
			info[2]=language;
			info[4]=editor;
			Giornale giornale = new Giornale(info,date, copie,dispo,prezzo,0);
			cAP.checkDataG(giornale);
			
			
			
		}
		else if(ControllerSystemState.getInstance().getType().equals("rivista"))
		{
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
			
			info[0]=t;
			info[1]=tipologia;
			info[2]=a;
			info[3]=l;
			info[4]=ed;
			if(disp)
			{
				dispo=1;
				//disponibile
			}
			else 
				dispo=0;
			
			
			 cAP.checkDataR(info,data,dispo,prezzo,copie,desc);
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
        if(ControllerSystemState.getInstance().getType().equals("libro"))
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
		else if(ControllerSystemState.getInstance().getType().equals("rivista"))
		{
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
		
	}

	
			
}
