package laptop.controller;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.GiornaleDao;
import laptop.database.LibroDao;
import laptop.database.RivistaDao;
import laptop.database.csvoggetto.CsvOggettoDao;
import laptop.exception.IdException;
import laptop.exception.PersistenzaException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Raccolta;
import laptop.model.raccolta.Rivista;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerRaccolta {

    private final static ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final String LIBRO = "libro";
    private static final String RIVISTA = "rivista";
    private static final String GIORNALE = "giornale";
    private final LibroDao lD;
    private final GiornaleDao gD;
    private final RivistaDao rD;
    private final Libro l;
    private final Giornale g;
    private final Rivista r;
    private final CsvOggettoDao csv ;
    private static final String REPORTGIORNALE="report/reportGiornale.csv";
    private static final String REPORTLIBRO="report/reportLibro.csv";
    private static final String REPORTRIVISTA="report/reportRivista.csv";


    public ObservableList<Raccolta> getRaccoltaLista(String type) throws IOException, CsvValidationException, IdException, PersistenzaException {

        ObservableList <Raccolta> catalogo= FXCollections.observableArrayList();
        if(vis.getTypeOfDb().equalsIgnoreCase("db")) {
            switch (type) {
                case LIBRO->catalogo.addAll(lD.getLibri());
                case GIORNALE->catalogo.addAll(gD.getGiornali());
                case RIVISTA->catalogo.addAll(rD.getRiviste());
                default->java.util.logging.Logger.getLogger("Test getId db").log(Level.INFO, "error !! list empty");

            }
        }
        if(vis.getTypeOfDb().equalsIgnoreCase("file"))
        {

            switch (type) {
                case LIBRO->catalogo.addAll(csv.retrieveRaccoltaData(new File(REPORTLIBRO)));
                case GIORNALE->	catalogo.addAll(csv.retrieveRaccoltaData(new File(REPORTGIORNALE)));
                case RIVISTA->catalogo.addAll(csv.retrieveRaccoltaData(new File(REPORTRIVISTA)));
                default->java.util.logging.Logger.getLogger("get lista").log(Level.SEVERE, " list is empty");
            }



        }
        return catalogo;
    }

    public ControllerRaccolta() throws IOException {
        lD = new LibroDao();
        gD=new GiornaleDao();
        rD=new RivistaDao();
        csv=new CsvOggettoDao();
        l=new Libro();
        g=new Giornale();
        r=new Rivista();

    }

    public boolean elimina() throws CsvValidationException, IOException {
        boolean status=false;
        if(vis.getTypeOfDb().equals("db"))
        {
            switch (vis.getType())
            {
                case LIBRO -> {
                    l.setId(vis.getId());
                    status=lD.eliminaLibro(l);

                }
                case GIORNALE -> {
                    g.setId(vis.getId());
                    status=gD.eliminaGiornale(g);
                }
                case RIVISTA ->{
                    r.setId(vis.getId());
                    status=rD.eliminaRivista(r);
                }
                default -> Logger.getLogger("elimina con db").log(Level.SEVERE," error with delete in mysql");
            }


        }
        else {
            switch (vis.getType())
            {
                case LIBRO -> {
                    l.setId(vis.getId());
                    status=csv.removeLibroById(l);

                }
                case GIORNALE -> {
                    g.setId(vis.getId());
                    status = csv.removeGiornaleById(g);
                }
                case RIVISTA ->{
                    r.setId(vis.getId());
                    status=csv.removeRivistaById(r);
                }
                default -> Logger.getLogger("elimina con db").log(Level.SEVERE," error with delete in mysql");

            }
        }
        return status;
    }
}
