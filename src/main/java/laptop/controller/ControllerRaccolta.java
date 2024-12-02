package laptop.controller;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.GiornaleDao;
import laptop.database.LibroDao;
import laptop.database.RivistaDao;
import laptop.database.csvoggetto.CsvOggettoDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Raccolta;
import laptop.model.raccolta.Rivista;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerRaccolta {

    private static final  ControllerSystemState vis=ControllerSystemState.getInstance();
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


    public ObservableList<Raccolta> getRaccoltaLista(String type) throws IOException, CsvValidationException, IdException {

        ObservableList <Raccolta> catalogo= FXCollections.observableArrayList();

        switch (type) {
            case LIBRO->
            {
                if(vis.getTypeOfDb().equalsIgnoreCase("db"))
                    catalogo.addAll(lD.getLibri());
                else catalogo.addAll(csv.retrieveRaccoltaData(new File(REPORTLIBRO)));

            }
            case GIORNALE->
            {
                if(vis.getTypeOfDb().equalsIgnoreCase("db"))
                    catalogo.addAll(gD.getGiornali());
                else catalogo.addAll(csv.retrieveRaccoltaData(new File(REPORTGIORNALE)));
            }
            case RIVISTA->
            {
                if(vis.getTypeOfDb().equalsIgnoreCase("db"))
                    catalogo.addAll(rD.getRiviste());
                else catalogo.addAll(csv.retrieveRaccoltaData(new File(REPORTRIVISTA)));

            }
            default->Logger.getLogger("Test getId db").log(Level.INFO, "error !! list empty");

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
        boolean status = false;

            switch (vis.getType())
            {
                case LIBRO -> {
                    l.setId(vis.getId());
                    if(vis.getTypeOfDb().equals("db"))
                        status=lD.eliminaLibro(l);
                    else status=csv.removeLibroById(l);

                }
                case GIORNALE -> {
                    g.setId(vis.getId());
                    if(vis.getTypeOfDb().equals("db"))
                        status=gD.eliminaGiornale(g);
                    else status=csv.removeGiornaleById(g);
                }
                case RIVISTA ->{
                    r.setId(vis.getId());
                    if(vis.getTypeOfDb().equals("db"))
                        status=rD.eliminaRivista(r);
                    else  status=csv.removeRivistaById(r);
                }
                default -> Logger.getLogger("elimina con db").log(Level.SEVERE," error with delete in mysql");
            }





        return status;
    }
}
