package laptop.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import laptop.model.Pagamento;
import laptop. model.User;
import laptop.utilities.ConnToDb;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PagamentoDao {
	private String query;
	private static final String ECCEZIONE="eccezione ottenuta:";
	public void inserisciPagamento(Pagamento p) throws SQLException {

		query="INSERT INTO PAGAMENTO(metodo,esito,nomeUtente,spesaTotale,eMail,tipoAcquisto,idProdotto) values (?,?,?,?,?,?,?)";

		try(Connection conn=ConnToDb.connectionToDB();
				PreparedStatement prepQ=conn.prepareStatement(query))
		{
			
			
			prepQ.setString(1,p.getMetodo()); // 
			prepQ.setInt(2,p.getEsito());
			prepQ.setString(3,p.getNomeUtente());
			prepQ.setFloat(4,p.getAmmontare());
			prepQ.setString(5, User.getInstance().getEmail());
			prepQ.setString(6,p.getTipo());
			prepQ.setInt(7, p.getIdOggetto());
			prepQ.executeUpdate();
		}catch(SQLException e)
		{
						java.util.logging.Logger.getLogger("insert pagamento").log(Level.INFO, ECCEZIONE, e);
		}
		
		
		}
		
	
	public ObservableList<Pagamento> getPagamenti() throws SQLException  {

			ObservableList<Pagamento> catalogo=FXCollections.observableArrayList();
			query="SELECT idPagamento,metodo,esito,nomeUtente,spesaTotale,tipoAcquisto,idProdotto from PAGAMENTO where eMail=?";
			try(Connection conn=ConnToDb.connectionToDB();
					PreparedStatement prepQ=conn.prepareStatement(query))
			{
				prepQ.setString(1, User.getInstance().getEmail());
				ResultSet rs=prepQ.executeQuery();
			while(rs.next())
			{


				catalogo.add(new Pagamento (rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getString(4),rs.getFloat(5),rs.getString(6)));

			}
			}catch(SQLException e)
			{
							java.util.logging.Logger.getLogger("lista pagamenti").log(Level.INFO, ECCEZIONE, e);
			}
		
		return catalogo;
	}

	public int retUltimoOrdinePag() throws SQLException 
	{
		int id=0;
		query="select count(*) as massimoP from PAGAMENTO";
		try(Connection conn=ConnToDb.connectionToDB();
				PreparedStatement prepQ=conn.prepareStatement(query))
		{
			ResultSet rs=prepQ.executeQuery();
			while ( rs.next() ) {
				id=rs.getInt("massimoP");

			}
		}catch(SQLException e)
		{
						java.util.logging.Logger.getLogger("ultimo ordine").log(Level.INFO, ECCEZIONE, e);
		}
				
		return id;
		
	}

	public boolean annullaOrdinePag(int idC) throws SQLException
	{
		boolean state=false;
		int row=0;
		String query2="delete from PAGAMENTO where idPagamento=?";
		try(Connection conn=ConnToDb.connectionToDB();
				PreparedStatement prepQ=conn.prepareStatement(query2))
		{
			prepQ.setInt(1,idC);
			row=prepQ.executeUpdate();
			if(row==1)
				state=true;
		}catch(SQLException e)
		{
						java.util.logging.Logger.getLogger("annulla ordine").log(Level.INFO, ECCEZIONE, e);
		}
			
			return state;

		}
		

	


}





