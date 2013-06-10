package com.mCare.notificacao;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificacaoAoLigar extends BroadcastReceiver {
	@Override
	public void onReceive(Context contexto, Intent intent) {
		//Cria alarmes do dia
		//Notificacao n = new Notificacao();
		//n.criaNotificacoesDoDia(contexto);

		//Programa um alarme para verifica
		voltaDiaSeguinte(contexto);
	}


	/** Usa um alarmManager para se programar e voltar no dia seguinte para calcular as consultas do dia
	 * 
	 */
	public void voltaDiaSeguinte(Context contexto) {
		// Get new calendar object and set the date to now
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());

		// Add defined amount of days, hours and minutes to the date
		calendar.add(Calendar.DATE, Calendar.DATE + 1 );
		calendar.add(Calendar.HOUR_OF_DAY, 0 );

		// Retrieve alarm manager from the system
		AlarmManager alarmManager = (AlarmManager) contexto.getSystemService(contexto.getApplicationContext().ALARM_SERVICE);
		// Every scheduled intent needs a different ID, else it is just executed once
		int id = (int) System.currentTimeMillis();

		// Prepare the intent which should be launched at the date
		Intent intent = new Intent(contexto, this.getClass());  //this.getClass pega a classe NotificacaoAoLigar, e vai pro metodo onReceive

		// Prepare the pending intent
		PendingIntent pendingIntent = PendingIntent.getBroadcast(contexto.getApplicationContext(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		// Register the alert in the system. You have the option to define if the device has to wake up on the alert or not
		alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
	}
}