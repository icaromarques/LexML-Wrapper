package com.visual.lexML.items;



/**
 * @author icaroafonso
 * Classe que contém o item do vocabulário  controlado do lexML. 
 * chaveURN representa o valor encontrado no documento
 * valorExibicao o valor para er exibido em tela
 */
public class FieldItem {
	private String chaveURN;
	private String valorExibicao;
	

	public FieldItem(String chaveURN, String valorExibicao) {
		super();
		this.chaveURN = chaveURN;
		this.valorExibicao = valorExibicao;
	}
	
	public FieldItem() {
		super();
	}
	
	public String getValorExibicao() {
		return valorExibicao;
	}
	public void setValorExibicao(String valorExibicao) {
		this.valorExibicao = valorExibicao;
	}
	public String getChaveURN() {
		return chaveURN;
	}
	public void setChaveURN(String chaveURN) {
		this.chaveURN = chaveURN;
	}
	@Override
	public String toString() {
		return "FieldItem [chaveURN=" + chaveURN + ", valorExibicao=" + valorExibicao + "]\n";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chaveURN == null) ? 0 : chaveURN.hashCode());
		result = prime * result + ((valorExibicao == null) ? 0 : valorExibicao.hashCode());
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
		FieldItem other = (FieldItem) obj;
		if (chaveURN == null) {
			if (other.chaveURN != null)
				return false;
		} else if (!chaveURN.equals(other.chaveURN))
			return false;
		if (valorExibicao == null) {
			if (other.valorExibicao != null)
				return false;
		} else if (!valorExibicao.equals(other.valorExibicao))
			return false;
		return true;
	}
	
}