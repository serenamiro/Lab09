package it.polito.tdp.borders.model;

public class Country {
	
	private int cod;
	private String nome;
	private String abbr;
	
	public Country(int cod, String nome, String abbr) {
		this.cod = cod;
		this.nome = nome;
		this.abbr = abbr;
	}

	@Override
	public String toString() {
		return cod+" "+nome+" "+abbr+"\n";
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cod;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (cod != other.cod)
			return false;
		return true;
	}
	
	
	

}
