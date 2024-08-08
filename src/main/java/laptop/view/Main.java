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
	private static final String LIBROP="src/main/resources/csvfiles/libro.csv";
	private static final String GIORNALEP="src/main/resources/csvfiles/giornale.csv";
	private static final String RIVISTAP="src/main/resources/csvfiles/rivista.csv";
	private static final String LIBROFINALE="report/reportLibro.csv";
	private static final String GIORNALEFINALE="report/reportGiornale.csv";
	private static final String RIVISTAFINALE="report/reportRivista.csv";
	private static final String UTENTEP="src/main/resources/csvfiles/utente.csv";
	private static final String UTENTEFINALE="report/reportUtente.csv";



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


			Files.copy(Path.of(LIBROP), Path.of(LIBROFINALE),REPLACE_EXISTING);
			Files.copy(Path.of(GIORNALEP), Path.of(GIORNALEFINALE),REPLACE_EXISTING);
			Files.copy(Path.of(RIVISTAP), Path.of(RIVISTAFINALE),REPLACE_EXISTING);
			Files.copy(Path.of(UTENTEP), Path.of(UTENTEFINALE),REPLACE_EXISTING);







		} catch (FileNotFoundException eFile) {
			Logger.getLogger("crwa db").log(Level.SEVERE, "\n eccezione ottenuta .", eFile);

		}




		launch(args);




	}

}
