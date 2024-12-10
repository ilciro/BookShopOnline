package laptop.model;




public class Pagamento {

	public int getIdPag() {
		return idPag;
	}

	public void setIdPag(int idPag) {
		this.idPag = idPag;
	}

	private int idPag;
	private String metodo;
	private String nomeUtente;
	private float ammontare;
	private String email;
	private String tipo;
	private int idOggetto;

	public float getAmmontare() {
		return ammontare;
	}

	public void setAmmontare(float ammontare) {
		this.ammontare = ammontare;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIdOggetto() {
		return idOggetto;
	}

	public void setIdOggetto(int idOggetto) {
		this.idOggetto = idOggetto;
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public String getNomeUtente() {
		return nomeUtente;
	}

	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "Pagamento [id=" + idPag + ", metodo=" + metodo + ", nomeUtente=" + nomeUtente
				+ ", ammontare=" + ammontare + ", tipo=" + tipo + ", idOggetto="+idOggetto+"]";
	}

	public Pagamento(int idPag,String metodo,String nomeUtente,float spesaTotale,String email,String tipoAcquisto,int idProdotto)
	{
		this.idPag=idPag;
		this.metodo=metodo;
		this.nomeUtente=nomeUtente;
		this.ammontare=spesaTotale;
		this.email=email;
		this.tipo=tipoAcquisto;
		this.idOggetto=idProdotto;
	}
	public Pagamento(){}



	
	
	
}
