package com.visual.lexML;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.visual.lexML.collections.Filter;
import com.visual.lexML.dto.SearchDTO;
import com.visual.lexML.items.FilterItem;
import com.visual.lexML.validations.ResourcesValidation;
import com.visual.lexML.validations.ValidationException;

/**
 * 
 * Classe que contempla os itens e campos a serem pesquisados no web serviço do LexML
 * @author icaroafonso
 *
 */
public class Search {
	private List<FilterItem> item;

	public static class Builder {

		private List<FilterItem>  item ;

		public Builder() {
			super();
			item = new ArrayList<FilterItem>();
		}

		/**
		 * 
		 * Adiciona objeto de pesquisa, quando o filtro que se está pesquisando é algum dos tipos fixados em {@link Filter}.
		 * Para mais informações, consulte <a href="http://projeto.lexml.gov.br/documentacao/Parte-6-Vocabularios-Controlados.pdf">http://projeto.lexml.gov.br/documentacao/Parte-6-Vocabularios-Controlados.pdf</a>.
		 * 
		 * @param value - Valor a ser pesquisado 
		 * @param type - tipo de filtro pertencente aos valores em {@link Filter}
		 * @return Builder
		 */
		public Builder addFilter(String value, Filter type) {
			this.item.add(new FilterItem(value,type));
			return this;
		}

		/**
		 * 
		 * Adiciona objeto de pesquisa, quando o filtro que se está pesquisando não é nenhum dos tipos fixados em {@link Filter}.
		 * 
		 * @param field - campo a a ser pesquisado no formato em que se encontra no site do projeto lexML
		 * @param value - Valor a ser pesquisado 
		 * @return Builder
		 */
		public Builder addFilter(String field, String value) {
			this.item.add(new FilterItem(field,value));
			return this;
		}

		public Search build() {
			return new Search(this);
		}
	}

	private Search(Builder builder) {
		item = builder.item;
	}




	@Override
	public String toString() {
		return "Search [item=" + item + "]";
	}


	
	/**
	 * Retorna uma String Json com os dados do objeto {@link Search}
	 * @return String Json que representa o objeto
	 */
	public String toJsonString() {

		StringBuilder strFinal = new StringBuilder();
		StringBuilder strAux = new StringBuilder();

		strFinal.append(" [ ");


		for (FilterItem filterItem : item) {
			if (strAux.length() > 0) 
				strAux.append(" , ");

			if (filterItem.getField()!=null) 
				strAux.append("{ \"field\" : \""   +filterItem.getField() +"\", \"value\" : \""+filterItem.getValue()+"\"}");
			else
				strAux.append("{ \"field\" : \""   +filterItem.getType() +"\", \"value\" : \""+filterItem.getValue()+"\"}");
		}
		strFinal.append(strAux);
		strFinal.append(" ] ");
		return strFinal.toString();
	}

	
	/**
	 * 
	 * Transforma os dados recebidos de um DTO que representa um Json para um Objeto Search
	 *  
	 * @param searchDTOList - Lista de dados do tipo {@link SearchDTO} 
	 * @return Objeto {@link Search} preenchido com os devidos filtros
	 * @throws ValidationException
	 */
	public static Search fromJsonString(List<SearchDTO> searchDTOList) throws ValidationException {

		Builder builder = new Search.Builder();
		Search search = null;
		for (SearchDTO searchDTO : searchDTOList) {
				switch (searchDTO.getField()) {
					case "TITULO": {
						builder.addFilter(searchDTO.getValue(),Filter.TITULO);
						break;
					}
					case "ASSUNTO": {
						builder.addFilter(searchDTO.getValue(),Filter.ASSUNTO);
						break;
					}
					case "DESCRICAO": {
						builder.addFilter(searchDTO.getValue(),Filter.DESCRICAO);
						break;
					}
					case "URN_LOCALIDADES": {
						builder.addFilter(searchDTO.getValue(),Filter.URN_LOCALIDADES);
						break;
					}
					case "URN_AUTORIDADES_EMITENTES": {
						builder.addFilter(searchDTO.getValue(),Filter.URN_AUTORIDADES_EMITENTES);
						break;
					}
					case "URN_TIPO_DE_DOCUMENTO": {
						builder.addFilter(searchDTO.getValue(),Filter.URN_TIPO_DE_DOCUMENTO);
						break;
					}
					case "URN_EVENTO": {
						builder.addFilter(searchDTO.getValue(),Filter.URN_EVENTO);
						break;
					}
					case "URN_LINGUA": {
						builder.addFilter(searchDTO.getValue(),Filter.URN_LINGUA);
						break;
					}
					case "URN_TIPO_CONTEUDO": {
						builder.addFilter(searchDTO.getValue(),Filter.URN_TIPO_CONTEUDO);
						break;
					}
					default: {
						builder.addFilter(searchDTO.getField(), searchDTO.getValue());
					}
				}
			}
		
		search = builder.build();
		return search;
	}


	/**
	 * 
	 * Transforma as informações de uma pesquisa em uma query que utiliza do formato SRU, definido pela biblioteca do Congresso Norte-Americano no projeto Search Retrieval via URL (SRU),
	 * Veja mais em <a href="http://www.loc.gov/standards/sru/">http://www.loc.gov/standards/sru/</a>.
	 * @return String com a URL de pesquisa com os arametros escolhidos
	 * @throws IOException
	 * @throws ValidationException
	 */
	public String parse() throws IOException, ValidationException {

		StringBuilder stringOthers = new StringBuilder();
		StringBuilder urnBuilder = new StringBuilder();

		urnBuilder.append("urn=\"");

		for (FilterItem filterItem : item) {
			if (filterItem.getType().getUrnType()) {
				if (!ResourcesValidation.vocabularyValidation(filterItem.getValue(),filterItem.getType())) { 
					throw new ValidationException(filterItem.getValue() + " não é válido para o tipo " + filterItem.getType());
				}
				urnBuilder.append(filterItem.getValue()+" ");
			} else {
				if (stringOthers.length() > 0) 
					stringOthers.append(" AND ");
				if (filterItem.getType().equals(Filter.OUTRO)) {
					stringOthers.append(filterItem.getField() + "=\""+filterItem.getValue()+"\"");
				}
				else {
					stringOthers.append(filterItem.getType().getValue() + "=\""+filterItem.getValue()+"\"");
				}
			}
		}

		if (!urnBuilder.toString().equalsIgnoreCase("=\"")) {
			urnBuilder.append("\"");
			stringOthers.append(" AND ").append(urnBuilder);
		}

		return URLEncoder.encode(stringOthers.toString(),"UTF-8");
	}



}
