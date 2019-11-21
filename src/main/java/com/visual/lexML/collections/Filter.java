package com.visual.lexML.collections;

public enum Filter {
	
	// filtos dos tipos campos livres
	TITULO("dc.title",false),
	DESCRICAO("dc.description",false),
	ASSUNTO("dc.subject",false),
	OUTRO("",false),
	
	// filtros do tipo URN
	URN_LOCALIDADES("urlValidaLocalidade",true),
	URN_AUTORIDADES_EMITENTES("urlValidaAutoridade",true),
	URN_TIPO_DE_DOCUMENTO("urlValidaTipoDocumento",true),
	URN_EVENTO("urlValidaEvento",true),
	URN_LINGUA("urlValidaLingua",true),
	URN_TIPO_CONTEUDO("urlValidaTipoConteudo",true);

    private String value;
    private Boolean urnType;

    Filter(String value, Boolean urnType) {
        this.value = value;
        this.urnType = urnType;
    }

    public String getValue() {
        return value;
    }

	public Boolean getUrnType() {
		return urnType;
	}
    
}
