package laptop.model.raccolta;

import java.io.IOException;
import java.net.URISyntaxException;

import com.itextpdf.text.DocumentException;

public interface Raccolta {

	void scarica(int i) throws DocumentException, IOException;//stampo messsaggio libro scaricato
	void leggi(int i) throws IOException, DocumentException, URISyntaxException;

}
