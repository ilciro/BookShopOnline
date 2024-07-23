package laptop.model.raccolta;

import java.awt.Desktop;
import java.io.*;
import java.net.URISyntaxException;

import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;


public class Libro implements Raccolta {
	private String titolo;
	private String codIsbn;
	private String editore;
	private String autore;
	private String lingua;
	private String categoria;
	private String descrizione;
	private LocalDate dataPubb;
	private String recensione;

	private int nrPagine;
	private int nrCopie; // numero copie vendute
	private int disponibilita;
	private float prezzo;
	private int id;
	private String[] infoGenerali = new String[7];
	private String[] infoCostiDisp = new String[5];
	private static final String DSTPATH="dstPath";

	private  File f;




	public Libro() {

	}


	public String getTitolo() {
		return this.titolo;
	}

	public String getCodIsbn() {
		return this.codIsbn;
	}

	public String getEditore() {
		return this.editore;
	}

	public String getAutore() {
		return this.autore;
	}

	public String getLingua() {
		return this.lingua;
	}

	public String getCategoria() {
		return this.categoria;
	}

	public LocalDate getDataPubb() {
		return this.dataPubb;
	}

	public String getRecensione() {
		return this.recensione;
	}

	public int getNrCopie() {
		return this.nrCopie;
	}

	public String getDesc() {
		return this.descrizione;
	}

	public int getDisponibilita() {
		return this.disponibilita;
	}

	public float getPrezzo() {
		return this.prezzo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public void setCodIsbn(String codIsbn) {
		this.codIsbn = codIsbn;
	}

	public void setEditore(String editore) {
		this.editore = editore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public void setLingua(String lingua) {
		this.lingua = lingua;
	}

	public void setDataPubb(LocalDate dataPubb) {
		this.dataPubb = dataPubb;
	}

	public void setRecensione(String recensione) {
		this.recensione = recensione;
	}

	public void setNrCopie(int nrCopie) {
		this.nrCopie = nrCopie;
	}

	public void setDesc(String desc) {
		this.descrizione = desc;
	}

	public void setDisponibilita(int disponibilita) {
		this.disponibilita = disponibilita;
	}

	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

	private final ResourceBundle rbTitoli=ResourceBundle.getBundle("configurations/titles");

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCategoria(String categoria) {
		switch (categoria) {
			case "ADOLESCENTI_RAGAZZI":
				this.categoria = CategorieLibro.ADOLESCENTI_RAGAZZI.toString();
				break;
			case "ARTE":
				this.categoria = CategorieLibro.ARTE.toString();
				break;
			case "CINEMA_FOTOGRAFIA":
				this.categoria = CategorieLibro.CINEMA_FOTOGRAFIA.toString();
				break;
			case "BIOGRAFIE":
				this.categoria = CategorieLibro.BIOGRAFIE.toString();
				break;
			case "DIARI_MEMORIE":
				this.categoria = CategorieLibro.DIARI_MEMORIE.toString();
				break;
			case "CALENDARI_AGENDE":
				this.categoria = CategorieLibro.CALENDARI_AGENDE.toString();
				break;
			case "DIRITTO":
				this.categoria = CategorieLibro.DIRITTO.toString();
				break;
			case "DIZINARI_OPERE":
				this.categoria = CategorieLibro.DIZINARI_OPERE.toString();
				break;
			case "ECONOMIA":
				this.categoria = CategorieLibro.ECONOMIA.toString();
				break;
			case "FAMIGLIA":
				this.categoria = CategorieLibro.FAMIGLIA.toString();
				break;
			case "SALUTE_BENESSERE":
				this.categoria = CategorieLibro.SALUTE_BENESSERE.toString();
				break;
			case "FANTASCIENZA_FANTASY":
				this.categoria = CategorieLibro.FANTASCIENZA_FANTASY.toString();
				break;
			case "FUMETTI_MANGA":
				this.categoria = CategorieLibro.FUMETTI_MANGA.toString();
				break;
			case "GIALLI_THRILLER":
				this.categoria = CategorieLibro.GIALLI_THRILLER.toString();
				break;
			case "COMPUTER_GIOCHI":
				this.categoria = CategorieLibro.COMPUTER_GIOCHI.toString();
				break;
			case "HUMOR":
				this.categoria = CategorieLibro.HUMOR.toString();
				break;
			case "INFORMATICA":
				this.categoria = CategorieLibro.INFORMATICA.toString();
				break;
			case "WEB_DIGITAL_MEDIA":
				this.categoria = CategorieLibro.WEB_DIGITAL_MEDIA.toString();
				break;
			case "LETTERATURA_NARRATIVA":
				this.categoria = CategorieLibro.LETTERATURA_NARRATIVA.toString();
				break;
			case "LIBRI_BAMBINI":
				this.categoria = CategorieLibro.LIBRI_BAMBINI.toString();
				break;
			case "LIBRI_SCOLASTICI":
				this.categoria = CategorieLibro.LIBRI_SCOLASTICI.toString();
				break;
			case "LIBRI_UNIVERSITARI":
				this.categoria = CategorieLibro.LIBRI_UNIVERSITARI.toString();
				break;
			case "RICETTARI_GENERALI":
				this.categoria = CategorieLibro.RICETTARI_GENERALI.toString();
				break;
			case "LINGUISTICA_SCRITTURA":
				this.categoria = CategorieLibro.LINGUISTICA_SCRITTURA.toString();
				break;
			case "POLITICA":
				this.categoria = CategorieLibro.POLITICA.toString();
				break;
			case "RELIGIONE":
				this.categoria = CategorieLibro.RELIGIONE.toString();
				break;
			case "ROMANZI_ROSA":
				this.categoria = CategorieLibro.ROMANZI_ROSA.toString();
				break;
			case "SCIENZE":
				this.categoria = CategorieLibro.SCIENZE.toString();
				break;
			case "TECNOLOGIA_MEDICINA":
				this.categoria = CategorieLibro.TECNOLOGIA_MEDICINA.toString();
				break;
			case "ALTRO":
				this.categoria = CategorieLibro.ALTRO.toString();
				break;

			default:
				this.categoria = null;
				break;
		}
	}

	private void createPDF(String name)
	{
		//create a pdf with a paragraph
		Document document=new Document();
		try{
			PdfWriter writer=PdfWriter.getInstance(document,new FileOutputStream(rbTitoli.getString(DSTPATH)+name));
			document.open();
			document.addTitle("Libro ");
			document.add(new Paragraph("""
                    libro/book not avalaible.Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                    Curabitur tempus tincidunt justo eget eleifend.
                    Maecenas nisl nulla, lobortis quis semper quis, scelerisque nec purus.
                    Mauris laoreet ac tellus molestie varius. Maecenas scelerisque, nibh ac pellentesque luctus, quam nibh congue tortor, vitae placerat dui lorem eget erat.
                    Proin non porta odio, ac porta nisl. Donec vitae commodo sem.
                    Fusce sit amet urna nec quam mattis tristique.
                    Nam non neque a nunc rhoncus ullamcorper eu in ex.
                    Proin et orci lacus. Cras rutrum lorem erat, nec ultrices urna efficitur sed.
                    Praesent auctor non lorem at bibendum.
                    Proin tincidunt mauris a velit pulvinar posuere.
                    Suspendisse posuere ex sed enim sollicitudin, sit amet ullamcorper nunc mollis.
                    Quisque sagittis aliquet ipsum vitae facilisis.
                    Etiam id ligula sed sem viverra cursus.
                    Aenean suscipit molestie posuere.
                    Duis id ante ut nibh suscipit gravida.
                    Cras egestas risus ac tellus varius, et rhoncus enim porttitor.
                    Nam malesuada in massa eu venenatis."""));
			document.close();
			writer.close();

			readPdf(name);


		}catch (DocumentException | IOException e)
		{
			Logger.getLogger("create pdf").log(Level.SEVERE,"pdf not created:{0} ", name);
		}
    }

	private void readPdf(String name) throws IOException, DocumentException {

		Document document = new Document();

        PdfReader reader = new PdfReader(rbTitoli.getString("srcPath") +name);
		PdfCopy copy=new PdfCopy(document,new FileOutputStream(rbTitoli.getString(DSTPATH)+name));
		document.open();

		int pages = reader.getNumberOfPages();
		for (int i = 1; i <= pages; i++) {
			copy.addPage(copy.getImportedPage(reader,i));

		}


		reader.close();
		document.close();



	}
	@Override
	public void scarica(int i) throws DocumentException, IOException {

		//here is copy of file

			switch (i) {
				case 1 ->createPDF(rbTitoli.getString("titolo1"));
				case 2 -> createPDF(rbTitoli.getString("titolo2"));
				case 3-> createPDF(rbTitoli.getString("titolo3"));
				case 4-> createPDF(rbTitoli.getString("titolo4"));
				case 5->createPDF(rbTitoli.getString("titolo5"));
				case 6->createPDF(rbTitoli.getString("titolo6"));
				case 7-> createPDF(rbTitoli.getString("titolo7"));
				case 8->createPDF(rbTitoli.getString("titolo8"));
				case 9-> createPDF(rbTitoli.getString("titolo9"));
				case 10->createPDF(rbTitoli.getString("titolo10"));
				case 11->createPDF(rbTitoli.getString("titolo11"));
				case 12->createPDF(rbTitoli.getString("titolo12"));

				default -> 	Logger.getLogger("Test scarica").log(Level.SEVERE, "download error");




			}

}

	@Override
	public void leggi(int i) throws IOException, DocumentException, URISyntaxException {



		switch (i) {
			case 1 -> f=new File(rbTitoli.getString(DSTPATH) + rbTitoli.getString("titolo1"));
			case 2->f=new File(rbTitoli.getString(DSTPATH) + rbTitoli.getString("titolo2"));
			case 3->f=new File(rbTitoli.getString(DSTPATH) + rbTitoli.getString("titolo3"));
			case 4->f=new File(rbTitoli.getString(DSTPATH) + rbTitoli.getString("titolo4"));
			case 5->f=new File(rbTitoli.getString(DSTPATH) + rbTitoli.getString("titolo5"));
			case 6->f=new File(rbTitoli.getString(DSTPATH) + rbTitoli.getString("titolo6"));
			case 7->f=new File(rbTitoli.getString(DSTPATH) + rbTitoli.getString("titolo7"));
			case 8->f=new File(rbTitoli.getString(DSTPATH) + rbTitoli.getString("titolo8"));
			case 9->f=new File(rbTitoli.getString(DSTPATH) + rbTitoli.getString("titolo9"));
			case 10->f=new File(rbTitoli.getString(DSTPATH) + rbTitoli.getString("titolo10"));
			case 11->f=new File(rbTitoli.getString(DSTPATH) + rbTitoli.getString("titolo11"));

			case 12->f=new File(rbTitoli.getString(DSTPATH) + rbTitoli.getString("titolo12"));

			default -> 	Logger.getLogger("Test leggi").log(Level.SEVERE, "read error");
		}
		if (Desktop.isDesktopSupported()) {
			new Thread(() -> {
				try {
					Desktop.getDesktop().open(f);
				} catch (IOException e) {
					Logger.getLogger("open file").log(Level.SEVERE, "open error");				}
			}).start();
		}
	}

	/*
	 * provo a levare i codeSmells > 7 paramentri
	 */
	public Libro(String[] info, LocalDate dataPubb,  String recensione, String descrizione,String [] prezzi) {
		this.infoGenerali=info;
		this.infoCostiDisp=prezzi;
		this.descrizione=descrizione;

		this.recensione=recensione;
		this.dataPubb = dataPubb;

		this.titolo=info[0];
		this.codIsbn=info[1];
		this.editore=info[2];
		this.autore=info[3];
		this.lingua=info[4];
		this.categoria=info[5];


		this.nrPagine=Integer.parseInt(prezzi[0]);
		this.nrCopie=Integer.parseInt(prezzi[1]);
		this.disponibilita=Integer.parseInt(prezzi[2]);
		this.prezzo=Float.parseFloat(prezzi[3]);
		this.id=Integer.parseInt(prezzi[4]);



	}

	public String[] getInfoGenerali() {
		return infoGenerali;
	}

	public void setInfoGenerali(String[] infoGenerali) {
		this.infoGenerali = infoGenerali;
	}




	public String[] getInfoCostiDisp() {
		return infoCostiDisp;
	}

	public void setInfoCostiDisp(String[] infoCostiDisp) {
		this.infoCostiDisp = infoCostiDisp;
	}


	public int getNrPagine() {
		return nrPagine;
	}

	public void setNrPagine(int nrPagine) {
		this.nrPagine = nrPagine;
	}
}
