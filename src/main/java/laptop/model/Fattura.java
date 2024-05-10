package laptop.model;



public class Fattura {

	private String nome;
	private String cognome;
	private String via;
	private String com;
	private String numero;
	private float ammontare;

	public Fattura() {
		super();
 
	}
	
	public Fattura(String nome, String cognome, String via, String com, String numero, float ammontare) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.via = via;
		this.com = com;
		this.numero = numero;
		this.ammontare = ammontare;
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
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public float getAmmontare() {
		return ammontare;
	}
	public void setAmmontare(float ammontare) {
		this.ammontare = ammontare;
	}

	
	
	@Override
	public String toString() {
		return "Fattura [nome=" + nome + ", cognome=" + cognome + ", via=" + via + ", com=" + com + ", numero=" + numero
				+ ", ammontare=" + ammontare + "]";
	}

	

	
	
}
