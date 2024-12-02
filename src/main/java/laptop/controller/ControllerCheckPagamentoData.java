package laptop.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.*;
import laptop.database.csvoggetto.CsvOggettoDao;
import laptop.database.csvpagamento.FatturaPagamentoCCredito;
import laptop.database.csvreport.CsvReport;
import laptop.exception.IdException;
import laptop.model.Pagamento;
import laptop.model.Report;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;

public class ControllerCheckPagamentoData {
	private final ControllerSystemState vis=ControllerSystemState.getInstance();
	private final Libro l;
	private final Giornale g;
	private final  Rivista r;
	private final LibroDao  lD;
	private final RivistaDao rD;
	private final PagamentoDao pagD;
	private final FatturaPagamentoCCredito csvFattura;
	private final CsvOggettoDao csv;
	private final GiornaleDao gD=new GiornaleDao();
	private final CsvReport csvReport;

	public void checkPagamentoData(String nome) throws SQLException, IdException, CsvValidationException, IOException {


		Pagamento p=new Pagamento(0,vis.getMetodoP(),nome,vis.getSpesaT(),null,null, vis.getId());

		Report report=new Report();
		switch (vis.getType())
		{
			case "libro"->
			{
				l.setId(vis.getId());
				if(vis.getTypeOfDb().equalsIgnoreCase("db")) {
					p.setTipo(lD.getLibroByIdTitoloAutoreLibro(l).get(0).getCategoria());
					pagD.inserisciPagamento(p);

				}
				else {
					p.setTipo(csv.getLibroByIdTitoloAutore(l).get(0).getCategoria());
					csvFattura.inserisciPagamento(p);

					report.setIdReport(0);
					report.setTipologiaOggetto(vis.getType());
					report.setTitoloOggetto(csv.getLibroByIdTitoloAutore(l).get(0).getTitolo());
					report.setNrPezzi(vis.getQuantita());
					report.setPrezzo(csv.getLibroByIdTitoloAutore(l).get(0).getPrezzo());
					report.setTotale(csv.getLibroByIdTitoloAutore(l).get(0).getPrezzo()*vis.getQuantita());

					csvReport.inserisciReport(report);

				}
			}
			case "giornale"->
			{
				g.setId(vis.getId());
				if(vis.getTypeOfDb().equalsIgnoreCase("db")) {
					p.setTipo(gD.getGiornaleIdTitoloAutore(g).get(0).getCategoria());
					pagD.inserisciPagamento(p);
				}
				else{
					p.setTipo(vis.getType());
					csvFattura.inserisciPagamento(p);
					report.setIdReport(0);
					report.setTipologiaOggetto(csv.getGiornaleByIdTitoloEditore(g).get(0).getCategoria());
					report.setTitoloOggetto(csv.getGiornaleByIdTitoloEditore(g).get(0).getTitolo());
					report.setNrPezzi(vis.getQuantita());
					report.setPrezzo(csv.getGiornaleByIdTitoloEditore(g).get(0).getPrezzo());
					report.setTotale(csv.getGiornaleByIdTitoloEditore(g).get(0).getPrezzo()*vis.getQuantita());
					csvReport.inserisciReport(report);
				}
			}
			case "rivista"->{
				r.setId(vis.getId());
				if(vis.getTypeOfDb().equalsIgnoreCase("db")) {
					p.setTipo(rD.getRivistaIdTitoloAutore(r).get(0).getCategoria());
					pagD.inserisciPagamento(p);
				}
				else {
					p.setTipo(vis.getType());
					csvFattura.inserisciPagamento(p);
					report.setIdReport(0);
					report.setTipologiaOggetto(csv.getRivistaByIdTitoloEditore(r).get(0).getCategoria());
					report.setTitoloOggetto(csv.getRivistaByIdTitoloEditore(r).get(0).getTitolo());
					report.setNrPezzi(vis.getQuantita());
					report.setPrezzo(csv.getRivistaByIdTitoloEditore(r).get(0).getPrezzo());
					report.setTotale(csv.getRivistaByIdTitoloEditore(r).get(0).getPrezzo()*vis.getQuantita());
					csvReport.inserisciReport(report);
				}
			}
			default -> java.util.logging.Logger.getLogger("pagamento").log(Level.SEVERE," error in payment");
		}

	}

	public ControllerCheckPagamentoData() throws IOException {
		l=new Libro();
		g=new Giornale();
		r=new Rivista();
		lD=new LibroDao();
		rD=new RivistaDao();
		pagD=new PagamentoDao();
		csv=new CsvOggettoDao();
		csvFattura=new FatturaPagamentoCCredito();
		csvReport=new CsvReport();

	}



}
