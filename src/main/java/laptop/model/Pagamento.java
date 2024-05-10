package laptop.model;


import laptop.exception.IdException;


public class Pagamento {

	private int id;
	private String metodo;
	private int esito; //0 ok 1 fallito
	private String nomeUtente;
	private float ammontare;
	private String tipo;
	private int idOggetto;





	public Pagamento(int id, String metodo, int esito, String nomeUtente, float ammontare, String tipo) {
		super();
		this.id = id;
		this.metodo = metodo;
		this.esito = esito;
		this.nomeUtente = nomeUtente;
		this.ammontare = ammontare;
		this.tipo = tipo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) throws IdException {

		if (id<0)
		{
			throw new IdException("id incorrect");
		}
		this.id = id;
	}
	public String getMetodo() {
		return metodo;
	}
	public void setMetodo(String metodo)
    {
		this.metodo = metodo;
	}
	public int getEsito() {
		return esito;
	}
	public void setEsito(int esito)  {

		this.esito = esito;
	}
	public String getNomeUtente() {
		return nomeUtente;
	}
	public void setNomeUtente(String nomeUtente) {

		this.nomeUtente = nomeUtente;
	}
	public float getAmmontare() {
		return ammontare;
	}
	public void setAmmontare(float ammontare)  {

		this.ammontare = ammontare;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {

		this.tipo = tipo;
	}
	
	
	@Override
	public String toString() {
		return "Pagamento [id=" + id + ", metodo=" + metodo + ", esito=" + esito + ", nomeUtente=" + nomeUtente
				+ ", ammontare=" + ammontare + ", tipo=" + tipo + ", idOggetto="+idOggetto+"]";
	}
	
	public int getIdOggetto() {
		return idOggetto;
	}
	public void setIdOggetto(int idOggetto) {
		this.idOggetto = idOggetto;
	}
	//usato per stampare roba in cronologia ordini
	public Pagamento(int id, String metodo, int esito, String nomeUtente, float ammontare, String tipo,int idOggetto) {
	super();
	this.id = id;
	this.metodo = metodo;
	this.esito = esito;
	this.nomeUtente = nomeUtente;
	this.ammontare = ammontare;
	this.tipo = tipo;
	this.idOggetto=idOggetto;
}
	public Pagamento() {
		
	}

	
	
	
}
