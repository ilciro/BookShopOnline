package laptop.model;



public class Fattura {

	private String nome;
	private String cognome;
	private String via;
	private String com;
	private float ammontare;
	private int idFattura;

	public int getIdFattura() {
		return idFattura;
	}

	public void setIdFattura(int idFattura) {
		this.idFattura = idFattura;
	}

	public Fattura (){}



	
	public Fattura(String nome, String cognome, String via, String com, float ammontare,int idFattura) {
		this.nome = nome;
		this.cognome = cognome;
		this.via = via;
		this.com = com;

		this.ammontare = ammontare;
		this.idFattura=idFattura;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {

		this.cognome = cognome;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via)  {

		this.via = via;
	}
	public String getCom() {
		return com;
	}
	public void setCom(String com) {


		this.com = com;
	}

	public float getAmmontare() {
		return ammontare;
	}
	public void setAmmontare(float ammontare) {
		this.ammontare = ammontare;
	}

	
	
	@Override
	public String toString() {
		return "Fattura [nome=" + nome + ", cognome=" + cognome + ", via=" + via + ", com=" + com + ", numero=" + idFattura
				+ ", ammontare=" + ammontare + "]";
	}



}
