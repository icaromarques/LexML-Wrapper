package com.visual.lexML;

import static com.google.common.truth.Truth.assertThat;

import java.io.IOException;
import java.util.List;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.visual.lexML.collections.Filter;
import com.visual.lexML.items.FieldItem;
import com.visual.lexML.validations.ValidationException;

@RunWith(JUnit4.class)
public class LexMLTest {


	@Test
	public void testControlledVocabularyList() throws IOException, ValidationException {
		List<FieldItem> lista = LexML.controlledVocabularyList(Filter.URN_LINGUA);
		assertThat(lista).contains(new FieldItem("pt-br","Português"));
	}

	@Test(expected=ValidationException.class)
	public void testControlledVocabularyListException() throws IOException, ValidationException {
		LexML.controlledVocabularyList(Filter.ASSUNTO);
	}

	@Test
	public void testGetJsonLexML() throws IOException, ValidationException {
		Search search = new Search.Builder()
				.addFilter("Lei",Filter.TITULO)
				.addFilter("EXTINGUE O IMPOSTO DE", Filter.DESCRICAO)
				.addFilter("EXTINÇÃO", Filter.ASSUNTO)
				.addFilter("minas.gerais",Filter.URN_LOCALIDADES)
				.addFilter("lei", Filter.URN_TIPO_DE_DOCUMENTO)
				.addFilter("estadual", Filter.URN_AUTORIDADES_EMITENTES)
				.addFilter("dc.type", "html")
				.addFilter("dc.identifier", "005037311")
				.build();

		JSONObject obj = new JSONObject(LexML.getJsonLexML(search));

		assertThat(obj).isInstanceOf(JSONObject.class);
		assertThat(obj.getJSONObject("srw:searchRetrieveResponse").get("srw:numberOfRecords")).isEqualTo(1);
		
	}
	
	
}
