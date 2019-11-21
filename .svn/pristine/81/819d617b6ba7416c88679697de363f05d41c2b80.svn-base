package com.visual.lexML;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import com.visual.lexML.collections.Filter;
import com.visual.lexML.helper.PropertiesImpl;
import com.visual.lexML.items.FieldItem;
import com.visual.lexML.validations.ValidationException;

/**
 * @author icaroafonso
 * Classe que faz a chamada das funções principais da API LexML
 */
public class LexML {


	/**
	 * 
	 * Lista os itens permitidos do vocabulário controlado desejado
	 * @param vocabulario
	 * @return
	 * @throws IOException
	 * @throws ValidationException 
	 */
	public static List<FieldItem> controlledVocabularyList(Filter vocabulario) throws IOException, ValidationException {
		List<FieldItem> listReturn = new ArrayList<FieldItem>();

		if (!vocabulario.getUrnType()) 
			throw new ValidationException("o filtro "+ vocabulario.getValue() +" não é um vocabulário válido");	
		
		URL url = new URL(PropertiesImpl.getValueUrl(vocabulario.getValue()));
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));  
		String line = "";  
		StringBuilder builder = new StringBuilder();

		while ((line = reader.readLine()) != null) { 
			builder.append(line);		  
		}
		reader.close();

		JSONObject jSON =  XML.toJSONObject(builder.toString()).getJSONObject("rdf:RDF");
		JSONArray jSONarray = (JSONArray)jSON.getJSONArray("skos:Concept");

		for (Object object : jSONarray) {			
			FieldItem item = new FieldItem();
			String[] key = ((JSONObject)object).get("rdf:about").toString().split(";");
			item.setChaveURN(key[key.length-1]);	
			
			if (((JSONObject)object).get("skos:prefLabel") instanceof JSONArray) {
				item.setValorExibicao(((JSONObject)(((JSONArray)((JSONObject)object).get("skos:prefLabel")).get(0))).get("content").toString());
				
			} else {
				item.setValorExibicao(((JSONObject)((JSONObject)object).get("skos:prefLabel")).get("content").toString());
			}
			listReturn.add(item);
		}		

		return listReturn;
	}


	/**
	 * 
	 * Retorna String Json com a consulta feita no LexML
	 * @param search objeto {@link Search}  com os campos da pesquisa requisitada setados
	 * @return String Json com o resultado da consulta
	 * @throws IOException
	 * @throws ValidationException 
	 */
	public static String getJsonLexML(Search search) throws IOException, ValidationException {

		URL url = new URL(PropertiesImpl.getValueUrl("urlRaizBusca")+search.parse() );
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));  
		String line = "";  
		StringBuilder builder = new StringBuilder();

		while ((line = reader.readLine()) != null) { 
			builder.append(line);		  
		}

		reader.close();
		return XML.toJSONObject(builder.toString()).toString();
	}
	
	/**
	 * 
	 * Retorna XML com a consulta feita no LexML
	 * @param search objeto {@link Search}  com os campos da pesquisa requisitada setados
	 * @return String XML com o resultado da consulta
	 * @throws IOException
	 * @throws ValidationException 
	 */
	public static String getXmlLexML(Search search) throws IOException, ValidationException {

		URL url = new URL(PropertiesImpl.getValueUrl("urlRaizBusca")+search.parse());
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));  
		String line = "";  
		StringBuilder builder = new StringBuilder();

		while ((line = reader.readLine()) != null) { 
			builder.append(line);		  
		}

		reader.close();
		return builder.toString();
	}
}
