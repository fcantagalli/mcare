package com.mCare.notificacao;

/* FONTE: http://papers.ch/android-schedule-a-notification/ */

import java.util.Calendar;

import com.mCare.main.MainActivity;
import com.mCare.R;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

// The class has to extend the BroadcastReceiver to get the notification from the system
public class Notificacao extends BroadcastReceiver {

	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent paramIntent) {
		// Request the notification manager
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		// Create a new intent which will be fired if you click on the notification
		Intent notificationIntent = new Intent(context, MainActivity.class);

		// Attach the intent to a pending intent
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		// Create the notification
		Context contextoAplicacao = context.getApplicationContext();
		CharSequence contentTitle = "Consulta em 30 minutos!";
		CharSequence contentText = "Voc� tem uma consulta daqui 30 minutos com Bianca Letti na Pra�a da �rvore.";

		int icon = R.drawable.ic_launcher; //ICONE que esta na pasta res
		CharSequence tickerText = "Hello";
		long when = System.currentTimeMillis();

		Notification notification = new Notification(icon, tickerText, when);
		notification.setLatestEventInfo(contextoAplicacao, contentTitle, contentText, contentIntent);

		// Fire the notification
		notificationManager.notify(1, notification);
	}


	
	
	/** Cria uma notificacao no horario informado
	 * 
	 * @param contexto contexto (acho que eh a tela em que o android ta)
	 * @param dias daqui a quantos dias sera a consulta (ex.: em 2 dias => 2)
	 * @param horas em que hora sera a consulta naquele dia (ex.: as 14h => 14)
	 * @param minutos em quantos minutos sera a consulta naquela hora (ex.: 14h30 => 30)
	 */
	public void criaNotificacaoProgramada(Context contexto, int dias, int horas, int minutos)
	{
		// Get new calendar object and set the date to now
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		
		// Add defined amount of days, hours and minutes to the date
		calendar.add(Calendar.HOUR_OF_DAY, (dias * 24) + horas );
		calendar.add(Calendar.MINUTE, minutos );

		// Retrieve alarm manager from the system
		AlarmManager alarmManager = (AlarmManager) contexto.getSystemService(contexto.getApplicationContext().ALARM_SERVICE);
		// Every scheduled intent needs a different ID, else it is just executed once
		int id = (int) System.currentTimeMillis();

		// Prepare the intent which should be launched at the date
		Intent intent = new Intent(contexto, this.getClass()); //this.getClass pega a classe Notificacao, e vai pro metodo onReceive

		// Prepare the pending intent
		PendingIntent pendingIntent = PendingIntent.getBroadcast(contexto.getApplicationContext(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		// Register the alert in the system. You have the option to define if the device has to wake up on the alert or not
		alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
	}
	
	/** Verifica as consultas do dia e cria alarme
	 * @param contexto acho que eh a tela em que o android ta (?)
	 */
	public void criaNotificacoesDoDia(Context contexto) {
		/***** O QUE DEVE SER FEITO ******/
		//VERIFICA NO BANCO DE DADOS QUAIS S�O AS CONSULTAS DO DIA
		
		//CRIA 1 ALARME PARA CADA CONSULTA
			//criaNotificacaoProgramada(Context contexto, int dias, int horas, int minutos)
		
		
		/***** PARA TESTAR ******/
		criaNotificacaoProgramada(contexto, 0, 0, 1); //Cria notificacao em 1 minuto
	}

}
