package laptop.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import laptop.model.Fattura;
import laptop.model.Pagamento;
import laptop. model.User;
import laptop.utilities.ConnToDb;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PagamentoDao {
	private String query;
	private static final String ECCEZIONE="eccezione ottenuta:";

	public void inserisciPagamento(Pagamento p) throws SQLException {

		query="INSERT INTO PAGAMENTO(metodo,nomeUtente,spesaTotale,eMail,tipoAcquisto,idProdotto) values (?,?,?,?,?,?)";

		try(Connection conn=ConnToDb.connectionToDB();
				PreparedStatement prepQ=conn.prepareStatement(query))
		{
			
			
			prepQ.setString(1,p.getMetodo()); // 
			prepQ.setString(2,p.getNomeUtente());
			prepQ.setFloat(3,p.getAmmontare());
			prepQ.setString(4, User.getInstance().getEmail());
			prepQ.setString(5,p.getTipo());
			prepQ.setInt(6, p.getIdOggetto());
			prepQ.executeUpdate();
		}catch(SQLException e)
		{
			java.util.logging.Logger.getLogger("insert pagamento").log(Level.INFO, ECCEZIONE, e);
		}
		
		
		}

	public Pagamento ultimoPagamento()
	{
		Pagamento p=new Pagamento();
		query="select * from PAGAMENTO order by idPagamento desc limit 1";
		try(Connection conn=ConnToDb.connectionToDB();
			PreparedStatement prepQ=conn.prepareStatement(query)){

			ResultSet rs=prepQ.executeQuery();
			while (rs.next())
			{
				p.setIdPag(rs.getInt(1));
				p.setMetodo(rs.getString(2));
				p.setNomeUtente(rs.getString(3));
				p.setAmmontare(rs.getFloat(4));
				p.setEmail(rs.getString(5));
				p.setTipo(rs.getString(6));
				p.setIdOggetto(rs.getInt(7));
			}

		}catch(SQLException e)
		{
			java.util.logging.Logger.getLogger("return pagamento").log(Level.INFO, ECCEZIONE, e);
		}
		return p;

	}




	public boolean cancellaPagamento(Pagamento p) throws SQLException
	{
		boolean state=false;
		int row;
		 query="delete from PAGAMENTO where idPagamento=?";
		try(Connection conn=ConnToDb.connectionToDB();
				PreparedStatement prepQ=conn.prepareStatement(query))
		{
			prepQ.setInt(1,p.getIdPag());
			row=prepQ.executeUpdate();
			if(row==1)
				state=true;
		}catch(SQLException e)
		{
			java.util.logging.Logger.getLogger("annulla ordine").log(Level.INFO, ECCEZIONE, e);
		}
			
			return state;

		}


	public ObservableList<Pagamento> listPagamenti(Pagamento pag)
	{
		ObservableList<Pagamento> list=FXCollections.observableArrayList();

		query="select  idPagamento,metodo,spesaTotale,tipoAcquisto,idProdotto from PAGAMENTO where email=?";
		try(Connection conn=ConnToDb.connectionToDB();
			PreparedStatement prepQ=conn.prepareStatement(query)){
			prepQ.setString(1,pag.getEmail());

			ResultSet rs=prepQ.executeQuery();
			while (rs.next())
			{
				Pagamento p=new Pagamento();
				p.setIdPag(rs.getInt(1));
				p.setMetodo(rs.getString(2));
				p.setAmmontare(rs.getFloat(3));
				p.setTipo(rs.getString(4));
				p.setIdOggetto(rs.getInt(5));
				list.add(p);
			}

		}catch(SQLException e)
		{
			java.util.logging.Logger.getLogger("return pagamento").log(Level.INFO, ECCEZIONE, e);
		}
		return list;

	}
		

	


}





