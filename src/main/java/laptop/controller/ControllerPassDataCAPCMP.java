package laptop.controller;

import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;

import java.time.LocalDate;

public class ControllerPassDataCAPCMP {

    /*
        this class used for reduce duplication
     */
    private final Libro l=new Libro();
    private final Rivista r=new Rivista();

    public Libro getLibro(String [] data)
    {
        l.setTitolo(data[0]);
        l.setCodIsbn(data[1]);
        l.setEditore(data[2]);
        l.setAutore(data[3]);
        l.setLingua(data[4]);
        l.setCategoria(data[5]);
        l.setNrPagine(Integer.parseInt(data[6]));
        l.setDataPubb(LocalDate.parse(data[10]));
        l.setRecensione(data[11]);
        l.setNrCopie(Integer.parseInt(data[7]));
        l.setDesc(data[12]);
        l.setDisponibilita(Integer.parseInt(data[8]));
        l.setPrezzo(Float.parseFloat(data[9]));
        return l;
    }
    public Rivista getRivista(String []info)
    {

        r.setTitolo(info[0]);
        r.setTipologia(info[1]);
        r.setAutore(info[2]);
        r.setLingua(info[3]);
        r.setEditore(info[4]);
        r.setDescrizione(info[5]);
        r.setDataPubb(LocalDate.parse(info[6]));
        r.setDisp(Integer.parseInt(info[7]));
        r.setPrezzo(Float.parseFloat(info[8]));
        r.setCopieRim(Integer.parseInt(info[9]));

        return r;

    }
}
