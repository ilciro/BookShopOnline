package laptop.controller;


import laptop.model.User;
import laptop.utilities.ConnToDb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class ControllerHomePage {
    private static final String LIBROP="src/main/resources/csvfiles/libro.csv";
    private static final String GIORNALEP="src/main/resources/csvfiles/giornale.csv";
    private static final String RIVISTAP="src/main/resources/csvfiles/rivista.csv";
    private static final String LIBROFINALE="report/reportLibro.csv";
    private static final String GIORNALEFINALE="report/reportGiornale.csv";
    private static final String RIVISTAFINALE="report/reportRivista.csv";
    private static final String UTENTEP="src/main/resources/csvfiles/utente.csv";
    private static final String UTENTEFINALE="report/reportUtente.csv";
    private static final String FATTURAP="src/main/resources/csvfiles/utente.csv";
    private static final String FATTURAFINALE="report/reportUtente.csv";
    private static final String PAGAMENTOP="src/main/resources/csvfiles/utente.csv";
    private static final String PAGAMENTOFINALE="report/reportUtente.csv";

    private static final ControllerSystemState vis=ControllerSystemState.getInstance();


    public void copiaFiles() throws IOException {

        try {
            File directory=new File("report");

            if(directory.isDirectory())
            {
                String[] files = directory.list();
                assert files != null;

                if (files.length == 0) {
                    throw new IOException(" cartella vuota");
                }
                else
                {
                    Logger.getLogger("copia files").log(Level.INFO, " file already exists!!");

                }
            }


        } catch (IOException eFile) {

            Logger.getLogger("creazione db file").log(Level.INFO, "\n creating files ..");

            Files.copy(Path.of(LIBROP), Path.of(LIBROFINALE), REPLACE_EXISTING);
            Files.copy(Path.of(GIORNALEP), Path.of(GIORNALEFINALE), REPLACE_EXISTING);
            Files.copy(Path.of(RIVISTAP), Path.of(RIVISTAFINALE), REPLACE_EXISTING);
            Files.copy(Path.of(UTENTEP), Path.of(UTENTEFINALE), REPLACE_EXISTING);
            Files.copy(Path.of(FATTURAP), Path.of(FATTURAFINALE), REPLACE_EXISTING);
            Files.copy(Path.of(PAGAMENTOP), Path.of(PAGAMENTOFINALE), REPLACE_EXISTING);

            Logger.getLogger("crea db file").log(Level.SEVERE, "\n eccezione ottenuta nella modalità file.", eFile);
        }
    }

    public void creaDb() throws FileNotFoundException {
        Logger.getLogger("crea db sql").log(Level.INFO, "\n creating tables ..");
        try{
            if(vis.isPopulated())
            {
                Logger.getLogger(" crea db if").log(Level.INFO, " database already populated");
            }
            else {
                ConnToDb.creaPopolaDb();
                vis.setPopulated(true);
            }
        }catch (FileNotFoundException  e)
        {
            Logger.getLogger("crea db ").log(Level.SEVERE, "\n eccezione ottenuta .", e);

        }
    }

    public String getRuolo()
    {
        return User.getInstance().getIdRuolo();
    }


    public String getId() {
        return String.valueOf(User.getInstance().getId());
    }

    public boolean logout()
    {
        boolean status = false;
        User.getInstance().annullaUtente();
        if(User.getInstance().getId()==0)
            status=true;
        return status;
    }

}
