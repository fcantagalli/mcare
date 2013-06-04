package com.mCare.configuracaoConsulta;

import android.content.Context;
import android.content.Intent;

public class ConsultaController {

	SelecionaCamposView selecionaCampos;
	CriaCampoView criaCampo;
	Context context;
	
	ConsultaController(Context context){

		Intent mostraSelecionaCampos = new Intent(context, SelecionaCamposView.class);
		this.context = context;
		context.startActivity(mostraSelecionaCampos);
	}
	
	public void criaNovoCampo(){
		//CriaCampoView criaCampo = new CriaCampoView(this);
		Intent mostraCriaCampo = new Intent(context, CriaCampoView.class);
		context.startActivity(mostraCriaCampo);
	}
	
	public void campoDefinido(String nome, int tipo){
		selecionaCampos.colocaCampoNaView(nome, tipo);
		Intent mostraCriaCampo = new Intent(context, CriaCampoView.class);
		context.startActivity(mostraCriaCampo);
		criaCampo.finish();
	}
	
}
