package com.visual.lexML.helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
	private Properties propsUrl = null;
	private String propertiesUrl = "addresses.properties";

	protected PropertiesLoader() {
		propsUrl = new Properties();
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(propertiesUrl);
		try{
			propsUrl.load(in);
			in.close();
		} catch(FileNotFoundException e){
		    System.out.println("Não foi possível encontrar o arquivo addresses.properties no caminho ./resources/ > " + e.getMessage());
		    e.printStackTrace();  
		}
		catch(IOException e){
			System.out.println("Arquivo properties inválido > " + e.getMessage());
			e.printStackTrace();
		}
		
	

	}
	
	protected String getValueUrl(String key){
		return (String)propsUrl.getProperty(key);
	}
	
}
