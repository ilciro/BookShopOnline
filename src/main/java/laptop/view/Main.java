package laptop.view;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


public class Main  extends Application {




	// insert a comment
	
	@Override
	public void start(Stage primaryStage) {
		Scene scene;

		try {
			Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePageFinale.fxml")));
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






		launch(args);




	}

}
