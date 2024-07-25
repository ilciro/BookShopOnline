	package laptop.model.raccolta;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;


	public class Giornale implements Raccolta{

	private String  titolo;
	private String tipologia;
	private String lingua;
	private String editore;
	private LocalDate dataPubb;
	private int copieRimanenti;
	private int disponibilita;
	private float prezzo;
	private int id;

	private final ResourceBundle rbTitoli=ResourceBundle.getBundle("configurations/titles");
	private static final String TITOLOG="titolo13";
	private static final String DSTPATH="dstPath";

	private String[] infoGenerali=new String[6];






	public Giornale()
	{
		super();
	}


	public Giornale(String []info,LocalDate dataPubb,int nrCopie, int disponibilita, float prezzo, int id)
	{
		this.infoGenerali=info;
		this.dataPubb=dataPubb;
		this.copieRimanenti=nrCopie;
		this.disponibilita=disponibilita;
		this.prezzo=prezzo;
		this.id=id;
		this.titolo=info[0];
		this.tipologia=info[1];
		this.editore=info[2];
		this.lingua=info[4];
		



	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getLingua() {
		return lingua;
	}

	public void setLingua(String lingua) {
		this.lingua = lingua;
	}

	public String getEditore() {
		return editore;
	}

	public void setEditore(String editore) {
		this.editore = editore;
	}

	public LocalDate getDataPubb() {
		return dataPubb;
	}

	public void setDataPubb(LocalDate dataPubb) {
		this.dataPubb = dataPubb;
	}

	public int getCopieRimanenti() {
		return copieRimanenti;
	}

	public void setCopieRimanenti(int copieRimanenti) {
		this.copieRimanenti = copieRimanenti;
	}

	public int getDisponibilita() {
		return this.disponibilita;
	}

	public void setDisponibilita(int disponibilita) {
		this.disponibilita = disponibilita;
	}

	public float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private void readPdf() throws IOException, DocumentException {


		Document document = new Document();

		PdfReader reader = new PdfReader(rbTitoli.getString("srcPath") + rbTitoli.getString(TITOLOG));
		PdfCopy copy=new PdfCopy(document,new FileOutputStream(rbTitoli.getString(DSTPATH)+ rbTitoli.getString(TITOLOG)));
		document.open();

		int pages = reader.getNumberOfPages();
		for (int i = 1; i <= pages; i++) {
			copy.addPage(copy.getImportedPage(reader,i));

		}


		reader.close();
		document.close();

	}

	@Override
	public void scarica(int i) throws IOException {

		Document document=new Document();
		try{
			PdfWriter writer=PdfWriter.getInstance(document,new FileOutputStream(rbTitoli.getString(DSTPATH)+rbTitoli.getString(TITOLOG)));
			document.open();
			document.addTitle("Giornale ");
			document.add(new Paragraph("""
                    Giornale/Daily not avalaible.
                    Integer et semper purus,non finibus augue
                    Interpellates habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.
                    Praesent et tincidunt eros.Nunc malesuada ipsum non leo scelerisque molestie.
                    Sed sit amet finibus nulla id ultrices diam.Vestibulum mollis ante eros,vitae accumsan ex lacinia
                    nec.
                    Sed tellus eros, tincidunt eu odio ac, tempor viverra libero.Maecenas id arcu laoreet, tristique felis
                    sit amet,blandit nulla.
                    Maecenas id arcu laoreet, tristique felis sit amet,blandit nulla.Phasellus suscipit sed est ut
                    molestie.
                    Maecenas consequat elit diam, eu semper erat porta nec.Etiam ullamcorper neque vitae mollis
                    cursus."""
			));
			readPdf();
			document.close();
			writer.close();




		}catch (DocumentException | IOException e)
		{
			Logger.getLogger("create pdf").log(Level.SEVERE,"pdf not created");
		}

	}

	@Override
	public void leggi(int i) throws DocumentException, IOException {
		if (Desktop.isDesktopSupported()) {
			new Thread(() -> {
				try {
					Desktop.getDesktop().open(new File(rbTitoli.getString(DSTPATH)+rbTitoli.getString(TITOLOG)));
				} catch (IOException e) {
					Logger.getLogger("open file").log(Level.SEVERE, "open error");				}
			}).start();
		}

	}

	public String[] getInfoGenerali() {
		return infoGenerali;
	}

	public void setInfoGenerali(String[] infoGenerali) {
		this.infoGenerali = infoGenerali;
	}


	
	
}
