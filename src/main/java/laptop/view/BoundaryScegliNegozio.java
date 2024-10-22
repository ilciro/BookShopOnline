package laptop.view;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import laptop.controller.ControllerScegliNegozio;
import laptop.controller.ControllerSystemState;
import laptop.model.Negozio;

public class BoundaryScegliNegozio implements Initializable {
	
	@FXML
	private Label labelL;
	@FXML
	private Pane pane;
	@FXML
	private RadioButton radio1;
	@FXML
	private RadioButton radio2;
	@FXML
	private RadioButton radio3;
	@FXML
	private RadioButton radio4;
	@FXML
	private Button buttonV;
	
	private final ControllerScegliNegozio cSN;
	protected ObservableList<Negozio> listOfNegozi;
	protected String alertTitle = "Ordine ricevuto!";
	protected String alertHeaderTexr = "Il negozio che hai selezionato ha ricevuto il tuo ordine. \n Presentati dopo 3 giorni lavorativi per ritirare il tuo acquisto";
	protected String alertContentText = "Ricordati di presentarti con le credenziali con le quali accedi ed avrai consegnato il tuo ordine";
	protected String warningTitle =" Negozio chiuso o non disponibile per il ritiro";
	protected String warningHeaderText = "Il negozio seleziopnato non � al momento pronto per questo tipo di operazioni";
	private static final  String WARNINGCONTENTTEXT = "Torna indietro e seleziona un'altro negozio fra quelli che ti vengono mostrati ! ";
	private static final String HOMEPAGE = "homePageFinale.fxml";
	private static final String HOMEPAGEA = "homePageAfterLogin.fxml" ;
	private final ControllerSystemState vis=ControllerSystemState.getInstance();
	
	protected Boolean statusA = false ;
	protected Boolean statusB = false ;
	
	protected Scene scene;
	protected Alert alert;
	protected Alert alertE;
	protected FXMLLoader loader ;
	
	
	
	public BoundaryScegliNegozio()
	{
		cSN = new ControllerScegliNegozio();
	}
	
	@FXML
	private void verifica() throws IOException 
	{

			listOfNegozi=cSN.getNegozi();


		ToggleGroup radioGroup=new ToggleGroup();
		radio1.setToggleGroup(radioGroup);
		radio2.setToggleGroup(radioGroup);
		radio3.setToggleGroup(radioGroup);
		radio4.setToggleGroup(radioGroup);
		
		RadioButton selectedRadioButton =(RadioButton) radioGroup.getSelectedToggle();
		
		


		if(selectedRadioButton.getText().equals("Negozio A"))
		{
			checkNegozio1();
		}
		else if(selectedRadioButton.getText().equals("Negozio B"))
		{
			checkNegozio2();
		}
		else if(selectedRadioButton.getText().equals("Negozio C"))
		{
			checkNegozio3();
		}
		else if(selectedRadioButton.getText().equals("Negozio D"))
		{
			checkNegozio4();
		}
		
	}
				
				
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

			radio1.setText(cSN.getNegozi().get(0).getNome());
			radio2.setText(cSN.getNegozi().get(1).getNome());
			radio3.setText(cSN.getNegozi().get(2).getNome());
			radio4.setText(cSN.getNegozi().get(3).getNome());
			
			


	}
	
	public void checkNegozio1() throws IOException
	{
			statusA = listOfNegozi.get(0).getIsOpen();
			statusB =  listOfNegozi.get(0).getIsValid();
			
		
			if( statusA && statusB)
			{
				alert=new Alert(AlertType.CONFIRMATION);
				alert.setTitle(alertTitle);
				alert.setHeaderText(alertHeaderTexr);
				alert.setContentText(alertContentText);
				Optional<ButtonType> result = alert.showAndWait();
				
		        if ((result.isPresent()) && (result.get() == ButtonType.OK))
		        	
		        {
		        	if(vis.getIsLogged())
					{
		            	Stage stage;
		                Parent root;
		                stage = (Stage) buttonV.getScene().getWindow();
		                loader = new FXMLLoader(getClass().getClassLoader().getResource(HOMEPAGEA));
		                root = loader.load();
		                scene = new Scene(root);
		                stage.setScene(scene);
		                stage.show();
		            }
		            else {
		            	Stage stage;
		                Parent root;
		                stage = (Stage) buttonV.getScene().getWindow();
		                loader = new FXMLLoader(getClass().getClassLoader().getResource(HOMEPAGE));
		                root = loader.load();
		                scene = new Scene(root);
		                stage.setScene(scene);
		                stage.show();
		            	}
				
			
		        			
		        }
		        else
				{
				
					alertE = new Alert(AlertType.WARNING);
					alertE.setTitle(warningTitle);
					alertE.setHeaderText(warningHeaderText);
					alertE.setContentText(WARNINGCONTENTTEXT);
				
				}
			}
			
		
		
	}
	public void checkNegozio2() throws IOException
	{
		
			statusA = listOfNegozi.get(1).getIsOpen();
			statusB =  listOfNegozi.get(1).getIsValid();
			
		
			if( statusA && statusB)
			{
				alert=new Alert(AlertType.CONFIRMATION);
				alert.setTitle(alertTitle);
				alert.setHeaderText(alertHeaderTexr);
				alert.setContentText(alertContentText);
				Optional<ButtonType> result = alert.showAndWait();
				
		        if ((result.isPresent()) && (result.get() == ButtonType.OK))
		        	
		        {
		        	if(vis.getIsLogged())
					{
		            	Stage stage;
		                Parent root;
		                stage = (Stage) buttonV.getScene().getWindow();
		                loader = new FXMLLoader(getClass().getClassLoader().getResource(HOMEPAGEA));
		                root = loader.load();
		                scene = new Scene(root);
		                stage.setScene(scene);
		                stage.show();
		            }
		            else {
		            	Stage stage;
		                Parent root;
		                stage = (Stage) buttonV.getScene().getWindow();
		                loader = new FXMLLoader(getClass().getClassLoader().getResource(HOMEPAGE));
		                root = loader.load();
		                scene = new Scene(root);
		                stage.setScene(scene);
		                stage.show();
		            	}
				
			
		        			
		        }
		        else
				{
				
					alertE = new Alert(AlertType.WARNING);
					alertE.setTitle(warningTitle);
					alertE.setHeaderText(warningHeaderText);
					alertE.setContentText(WARNINGCONTENTTEXT);
				
				}
			}
			
		
		
	}
	public void checkNegozio3() throws IOException
	{
			statusA = listOfNegozi.get(2).getIsOpen();
			statusB =  listOfNegozi.get(2).getIsValid();
			
		
			if( statusA && statusB)
			{
				alert=new Alert(AlertType.CONFIRMATION);
				alert.setTitle(alertTitle);
				alert.setHeaderText(alertHeaderTexr);
				alert.setContentText(alertContentText);
				Optional<ButtonType> result = alert.showAndWait();
				
		        if ((result.isPresent()) && (result.get() == ButtonType.OK))
		        	
		        {
		        	if(vis.getIsLogged())
					{
		            	Stage stage;
		                Parent root;
		                stage = (Stage) buttonV.getScene().getWindow();
		                loader = new FXMLLoader(getClass().getClassLoader().getResource(HOMEPAGEA));
		                root = loader.load();
		                scene = new Scene(root);
		                stage.setScene(scene);
		                stage.show();
		            }
		            else {
		            	Stage stage;
		                Parent root;
		                stage = (Stage) buttonV.getScene().getWindow();
		                loader = new FXMLLoader(getClass().getClassLoader().getResource(HOMEPAGE));
		                root = loader.load();
		                scene = new Scene(root);
		                stage.setScene(scene);
		                stage.show();
		            	}
				
			
		        			
		        }
		        else
				{
				
					alertE = new Alert(AlertType.WARNING);
					alertE.setTitle(warningTitle);
					alertE.setHeaderText(warningHeaderText);
					alertE.setContentText(WARNINGCONTENTTEXT);
				
				}
			}
			
		
		
	}
	public void checkNegozio4() throws IOException
	{
		
			statusA = listOfNegozi.get(3).getIsOpen();
			statusB =  listOfNegozi.get(3).getIsValid();
			
		
			if( statusA && statusB)
			{
				alert=new Alert(AlertType.CONFIRMATION);
				alert.setTitle(alertTitle);
				alert.setHeaderText(alertHeaderTexr);
				alert.setContentText(alertContentText);
				Optional<ButtonType> result = alert.showAndWait();
				
		        if ((result.isPresent()) && (result.get() == ButtonType.OK))
		        	
		        {
		        	if(vis.getIsLogged())
					{
		            	Stage stage;
		                Parent root;
		                stage = (Stage) buttonV.getScene().getWindow();
		                loader = new FXMLLoader(getClass().getClassLoader().getResource(HOMEPAGEA));
		                root = loader.load();
		                scene = new Scene(root);
		                stage.setScene(scene);
		                stage.show();
		            }
		            else {
		            	Stage stage;
		                Parent root;
		                stage = (Stage) buttonV.getScene().getWindow();
		                loader = new FXMLLoader(getClass().getClassLoader().getResource(HOMEPAGE));
		                root = loader.load();
		                scene = new Scene(root);
		                stage.setScene(scene);
		                stage.show();
		            	}
				
			
		        			
		        }
		        else
				{
				
					alertE = new Alert(AlertType.WARNING);
					alertE.setTitle(warningTitle);
					alertE.setHeaderText(warningHeaderText);
					alertE.setContentText(WARNINGCONTENTTEXT);
				
				}
			}
			
		
		
	}
	


}
