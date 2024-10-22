package laptop.controller;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.GiornaleDao;
import laptop.database.LibroDao;
import laptop.database.RivistaDao;
import laptop.database.csvoggetto.CsvOggettoDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerRicerca {
    private final LibroDao lD;
    private final GiornaleDao gD;
    private final RivistaDao rD;
    private final CsvOggettoDao csv;
    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private final static String LIBRO="libro";
    private final static String GIORNALE="giornale";
    private final static String RIVISTA="rivista";



    public ControllerRicerca() throws IOException {
        lD=new LibroDao();
        gD=new GiornaleDao();
        rD=new RivistaDao();
        csv=new CsvOggettoDao();
    }

    public int getIdOggetto(String nome) throws CsvValidationException, IOException, IdException {
        int id=0;

        if(vis.getTypeOfDb().equals("db"))
        {
            switch (vis.getType())
            {
                case LIBRO ->
                {
                    Libro l=new Libro();
                    l.setTitolo(nome);
                    l.setEditore(nome);
                    id=lD.getLibroByIdTitoloAutoreLibro(l).get(0).getId();
                }
                case GIORNALE ->
                {
                    Giornale g=new Giornale();
                    g.setTitolo(nome);
                    g.setEditore(nome);
                    id=gD.getGiornaleIdTitoloAutore(g).get(0).getId();
                }
                case RIVISTA -> {
                    Rivista r=new Rivista();
                    r.setTitolo(nome);
                    r.setEditore(nome);
                    id=rD.getRivistaIdTitoloAutore(r).get(0).getId();
                }
                default -> Logger.getLogger("id oggetto db").log(Level.SEVERE," error with data from db!!");
            }
        }
       if (vis.getTypeOfDb().equals("file")){
            switch (vis.getType())
            {
                case LIBRO ->
                {
                    Libro l=new Libro();
                    l.setTitolo(nome);
                    l.setEditore(nome);
                    id=csv.getLibroByIdTitoloAutore(l).get(0).getId();
                }
                case GIORNALE ->
                {
                    Giornale g=new Giornale();
                    g.setTitolo(nome);
                    g.setEditore(nome);
                    id=csv.getGiornaleByIdTitoloEditore(g).get(0).getId();
                }
                case RIVISTA -> {
                    Rivista r=new Rivista();
                    r.setTitolo(nome);
                    r.setEditore(nome);
                    id=csv.getRivistaByIdTitoloEditore(r).get(0).getId();
                }
                default -> Logger.getLogger("id oggetto file").log(Level.SEVERE," error with data from file!!");
            }

        }


        return id;
    }
}
