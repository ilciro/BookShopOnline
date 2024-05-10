package laptop.utilities;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;


public class ConnToDb 
{
	
	protected static Connection conn = null;

	
	private static final String CONNESSIONE="Tentativo di conessione al server..........\\\\n";


	private static final String ERRORE="Errore in mysql..........\\n";


	private static final ResourceBundle rB=ResourceBundle.getBundle("configurations/configDb");

	private static boolean statusConnSys;
	private static boolean statusConnDB;

	public static boolean getStatusConnSys() {
		return statusConnSys;
	}

	public static void setStatusConnSys(boolean statusConnSys) {
		ConnToDb.statusConnSys = statusConnSys;
	}

	public static boolean getStatusConnDB() {
		return statusConnDB;
	}

	public static void setStatusConnDB(boolean statusConnDB) {
		ConnToDb.statusConnDB = statusConnDB;
	}

	public static Connection generalConnection()
	{


		String driver=rB.getString("driver");
		String user=rB.getString("user");
		String pwd=rB.getString("pwdLinux");
		String url=rB.getString("url");
		try
		{
			Class.forName(driver);
			java.util.logging.Logger.getLogger("Test General connection").log(Level.INFO, CONNESSIONE);
			conn = DriverManager.getConnection(url, user,pwd);
			java.util.logging.Logger.getLogger("Test General connection standard").log(Level.INFO, "Connesso standard a sys........\n");
			setStatusConnSys(true);
		}
		catch (SQLException | ClassNotFoundException e1)
		{
			java.util.logging.Logger.getLogger("Test general connection error").log(Level.INFO, ERRORE, e1);
			setStatusConnSys(false);

		}

		return conn;

	}
	public static Connection connectionToDB()
	{


		String driver=rB.getString("driver");
		String user=rB.getString("user");
		String pwd=rB.getString("pwdLinux");
		String url=rB.getString("urlDb");
		try
		{
			Class.forName(driver);
			java.util.logging.Logger.getLogger("Test connection to my db").log(Level.INFO, CONNESSIONE);
			conn = DriverManager.getConnection(url, user,pwd);
			java.util.logging.Logger.getLogger("Test connection to my db").log(Level.INFO, "Connesso standard a ISPW........\n");
			setStatusConnDB(true);
		}
		catch (SQLException | ClassNotFoundException e1)
		{
			java.util.logging.Logger.getLogger("TTest connection to my db error").log(Level.INFO, ERRORE, e1);
			setStatusConnDB(false);

		}

		return conn;

	}



	public static void creaPopolaDb() throws FileNotFoundException {
		ConnToDb.generalConnection();
		ConnToDb.connectionToDB();
		boolean statusSys=ConnToDb.getStatusConnSys();
		boolean statusDb=ConnToDb.getStatusConnDB();
		boolean status=statusSys&&statusDb;
		if(Boolean.FALSE.equals(status)) {
			Reader reader = new BufferedReader(new FileReader("FileSql/export-db.sql"));
			ScriptRunner sr = new ScriptRunner(conn);
			sr.setSendFullScript(false);
			sr.runScript(reader);
		}
		else {
			java.util.logging.Logger.getLogger("creaPopolaDB").log(Level.INFO, "entrambi popolati");

		}
	}
	private ConnToDb(){}



}

