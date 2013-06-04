package com.mCare.main;

import java.util.GregorianCalendar;

import android.os.Bundle;
import com.mCare.R;
import com.mCare.notificacao.NotificacaoConsulta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;

public class CadastroConsulta extends Activity {
	
	public static int TEMPO_NOTIFICACAO = 1800000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro_consulta);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cadastro_consulta, menu);
		return true;
	}
	
	void createNotification(Context context, CharSequence tickerText,CharSequence title, CharSequence message, GregorianCalendar gc){
		Intent intent = new Intent(context,InfPaciente.class);
		
		NotificacaoConsulta.create(context, tickerText, title, message, R.drawable.dunno, NotificacaoConsulta.notificationId(gc), intent);
	}

}
