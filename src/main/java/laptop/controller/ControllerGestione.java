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
import laptop.model.raccolta.Rivista;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerGestione {
    private final Libro l;
    private final Giornale g;
    private final Rivista r;
    private final LibroDao lD;
    private final GiornaleDao gD;
    private final RivistaDao rD;
    private final CsvOggettoDao csv;
    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final String LIBRO="libro";
    private static final String GIORNALE="giornale";
    private static final String RIVISTA="rivista";


    public boolean inserisci(String[] param) throws CsvValidationException, IOException, SQLException {
        boolean status = false;
        vis.setTipoModifica("insert");

        switch (vis.getType())
        {
            case LIBRO -> {
                setLibro(param);

                if(vis.getTypeOfDb().equals("db"))
                {
                    status=lD.inserisciLibro(l);
                }
                else status=csv.inserisciLibro(l);
            }
            case GIORNALE -> {
                setGiornale(param);

                if(vis.getTypeOfDb().equals("db"))
                {
                    status=gD.creaGiornale(g);
                }
                else status=csv.inserisciGiornale(g);
            }
            case RIVISTA -> {
                setRivista(param);

                if(vis.getTypeOfDb().equals("db")) status=rD.creaRivista(r);
                else status=csv.inserisciRivista(r);
            }
            default -> Logger.getLogger("inserisci").log(Level.SEVERE,"insert is wrong");


        }
        return status;
    }

    public ControllerGestione() throws IOException {
        l=new Libro();
        g=new Giornale();
        r=new Rivista();
        lD=new LibroDao();
        gD=new GiornaleDao();
        rD=new RivistaDao();
        csv=new CsvOggettoDao();
    }

    public ObservableList<Libro>libroById() throws CsvValidationException, IOException, IdException {
        ObservableList<Libro> list= FXCollections.observableArrayList();
        l.setId(vis.getId());
        if(vis.getTypeOfDb().equals("db")) list.add(lD.getLibroByIdTitoloAutoreLibro(l).get(0));
        else list.add(csv.getLibroByIdTitoloAutore(l).get(0));
        return list;
    }
    public ObservableList<Giornale> giornaleById() throws CsvValidationException, IOException, IdException {
        ObservableList<Giornale> list= FXCollections.observableArrayList();
        g.setId(vis.getId());
        if(vis.getTypeOfDb().equals("db")) list.add(gD.getGiornaleIdTitoloAutore(g).get(0));
        else list.add(csv.getGiornaleByIdTitoloEditore(g).get(0));
        return list;
    }
    public ObservableList<Rivista> rivistaById() throws CsvValidationException, IOException, IdException {
        ObservableList<Rivista> list= FXCollections.observableArrayList();
        r.setId(vis.getId());
        if(vis.getTypeOfDb().equals("db")) list.add(rD.getRivistaIdTitoloAutore(r).get(0));
        else list.add(csv.getRivistaByIdTitoloEditore(r).get(0));
        return list;
    }

    public boolean modifica(String[] dati) throws CsvValidationException, IOException, IdException, SQLException {
       boolean status=false;
       vis.setTipoModifica("modifica");

       switch (vis.getType())
       {
           case LIBRO -> {
               setLibro(dati);
               l.setId(vis.getId());
               Logger.getLogger("modifLibro").log(Level.INFO,"id libro da modif:{0}",l.getId());
                if (vis.getTypeOfDb().equals("db")) status=lD.modifLibro(l);
                else{
                    Libro l2=csv.retrieveLibroData(l).get(0);
                    csv.removeLibroById(l2);
                    status=csv.inserisciLibro(l);
                }
           }
           case GIORNALE ->
           {
               setGiornale(dati);
               g.setId(vis.getId());
               Logger.getLogger("modifGiornale").log(Level.INFO,"id giornale da modif:{0}",g.getId());
               if (vis.getTypeOfDb().equals("db")) status=gD.aggiornaGiornale(g);
               else{
                   Giornale g2=csv.retriveGiornaleData(g).get(0);
                   csv.removeGiornaleById(g2);
                   status=csv.inserisciGiornale(g);
               }

           }
           case RIVISTA -> {
               setRivista(dati);
               r.setId(vis.getId());
               Logger.getLogger("modifRivista").log(Level.INFO,"id rivista da modif:{0}",r.getId());
               if (vis.getTypeOfDb().equals("db")) status=rD.aggiornaRivista(r);
               else{
                   Rivista r2=csv.retrieveRivistaData(r).get(0);
                   csv.removeRivistaById(r2);
                   status=csv.inserisciRivista(r);
               }

           }
           default -> Logger.getLogger("modif").log(Level.SEVERE, "error in modif");
       }


        return status;
    }

    private void setLibro(String []param)
    {
        l.setTitolo(param[0]);
        l.setCodIsbn(param[1]);
        l.setEditore(param[2]);
        l.setAutore(param[3]);
        l.setLingua(param[4]);
        l.setCategoria(param[5]);
        l.setDesc(param[6]);
        l.setDataPubb(LocalDate.parse(param[7]));
        l.setRecensione(param[8]);
        l.setNrPagine(Integer.parseInt(param[9]));
        l.setNrCopie(Integer.parseInt(param[10]));
        l.setDisponibilita(Integer.parseInt(param[11]));
        l.setPrezzo(Float.parseFloat(param[12]));


    }
    private void setGiornale(String[] param)
    {
        g.setTitolo(param[0]);
        g.setEditore(param[2]);
        g.setLingua(param[4]);
        g.setCategoria(param[5]);
        g.setDataPubb(LocalDate.parse(param[7]));
        g.setCopieRimanenti(Integer.parseInt(param[10]));
        g.setDisponibilita(Integer.parseInt(param[11]));
        g.setPrezzo(Float.parseFloat(param[12]));
    }
    private void setRivista(String []param)
    {
        r.setTitolo(param[0]);
        r.setEditore(param[2]);
        r.setAutore(param[3]);
        r.setLingua(param[4]);
        r.setCategoria(param[5]);
        r.setDescrizione(param[6]);
        r.setDataPubb(LocalDate.parse(param[7]));
        r.setCopieRim(Integer.parseInt(param[10]));
        r.setDisp(Integer.parseInt(param[11]));
        r.setPrezzo(Float.parseFloat(param[12]));
    }
}
