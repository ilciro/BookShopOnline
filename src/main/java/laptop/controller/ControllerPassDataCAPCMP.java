package laptop.controller;

import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;

import java.time.LocalDate;

public class ControllerPassDataCAPCMP {
    private final Libro l=new Libro();
    private final Rivista r=new Rivista();



    public Libro getLibro(String [] data)
    {
        l.setTitolo(data[0]);
        l.setNrPagine(Integer.parseInt(data[1]));
        l.setCodIsbn(data[2]);
        l.setEditore(data[3]);
        l.setAutore(data[4]);
        l.setLingua(data[5]);
        l.setCategoria(data[6]);
        l.setDataPubb(LocalDate.parse(data[7]));
        l.setRecensione(data[8]);
        l.setNrCopie(Integer.parseInt(data[9]));
        l.setDesc(data[10]);
        l.setDisponibilita(Integer.parseInt(data[11]));
        l.setPrezzo(Float.parseFloat(data[12]));
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
