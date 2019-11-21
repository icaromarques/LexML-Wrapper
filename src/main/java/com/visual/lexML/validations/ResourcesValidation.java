package com.visual.lexML.validations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import com.visual.lexML.collections.Filter;
import com.visual.lexML.helper.PropertiesImpl;

/**
 * @author icaroafonso
 * Classe feita para validar vocabulários controlados 
 */
public class ResourcesValidation {

	/*
	 *  Valida se o campo que está sendo pesquisado está dentro do vocabulário controlado permitido pelo LexML
	 */
	public static Boolean vocabularyValidation(String field, Filter vocabullary) throws IOException {
		Boolean found = false;
		URL url = new URL(PropertiesImpl.getValueUrl(vocabullary.getValue()));

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
			if (((JSONObject)object).get("rdf:about").toString().contains(field)) {
				found = true;
				break;
			}			  
		}		
		
		return found;
	}
}
