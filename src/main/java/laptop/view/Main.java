package laptop.view;

import java.io.*;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import laptop.utilities.ConnToDb;



public class Main  extends Application {


    	// insert a comment
	
	@Override
	public void start(Stage primaryStage) {
		Scene scene;

		try {
			Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("scelta.fxml")));
			scene = new Scene(root);
			primaryStage.setTitle("Benvenuto nella homePage");
			primaryStage.setScene(scene);
			primaryStage.show();
		} 
		catch (Exception e)
		{
			Logger.getLogger("main page").log(Level.SEVERE,"\n eccezione ottenuta .",e);

			
		}

	}

	public static void main(String[] args) throws Exception {



		try {

			ConnToDb.creaPopolaDb();

		} catch (FileNotFoundException eFile) {
			Logger.getLogger("crwa db").log(Level.SEVERE, "\n eccezione ottenuta .", eFile);

		}


			launch(args);




	}


		
	
}
