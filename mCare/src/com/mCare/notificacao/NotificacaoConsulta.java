package com.mCare.notificacao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.mCare.main.MainActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class NotificacaoConsulta extends BroadcastReceiver {
	
	int hashId(Date date){
		int id = 0;
		
		return id;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent paramIntent) {

		Bundle b = paramIntent.getExtras();
		String tickerText = b.getString("ticketText");
		String title = b.getString("title");
		String message = b.getString("message");
		int icon = b.getInt("icon");
		int id = b.getInt("id");

		//Pending Intent para executar a intent ao selecionar a notificacao
		
				NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

				// Create a new intent which will be fired if you click on the notification
				Intent notificationIntent = new Intent(context, MainActivity.class);

				// Attach the intent to a pending intent
				PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

				// Create the notification

				Notification notification = null;
				
				notification = new Notification.Builder(context)
				.setTicker(tickerText)
				.setContentTitle(title)
				.setContentText(message)
				.setSmallIcon(icon)
				.setContentIntent(contentIntent)
				.setAutoCancel(true)
				.build();
					
				notification.flags |= Notification.FLAG_AUTO_CANCEL;	
				
				nm.notify(id,notification);
	}
	
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public void create(Context contexto,long quandoIraAparecer,CharSequence tickerText, CharSequence title, CharSequence message, int icon){
		//Pending Intent para executar a intent ao selecionar a notificacao
		
		NotificationManager nm = (NotificationManager) contexto.getSystemService(Context.NOTIFICATION_SERVICE);

		// Create a new intent which will be fired if you click on the notification
		Intent notificationIntent = new Intent(contexto, MainActivity.class);

		// Attach the intent to a pending intent
		PendingIntent contentIntent = PendingIntent.getActivity(contexto, 0, notificationIntent, 0);

		// Create the notification

		Notification notification = null;
		
		int id = (int) quandoIraAparecer;
		
			notification = new Notification.Builder(contexto)
			.setContentTitle(tickerText)
			.setContentText(message)
			.setSmallIcon(icon).
			setContentIntent(contentIntent)
			.setAutoCancel(true)
			.setWhen(quandoIraAparecer)
			.build();
			
		notification.flags |= Notification.FLAG_AUTO_CANCEL;	
		
		nm.notify(id,notification);
		
		/*
		AlarmManager gerenciador = (AlarmManager) contexto.getSystemService(Context.ALARM_SERVICE);
		Calendar cal = Calendar.getInstance();
		
		cal.setTimeInMillis(quandoIraAparecer);
		
		Intent i = new Intent(contexto, MainActivity.class);
		i.putExtra("ticketText", tickerText);
		i.putExtra("title", title);
		i.putExtra("message",message);
		i.putExtra("icon", icon);
		i.putExtra("id",quandoIraAparecer);
		
		PendingIntent p = PendingIntent.getBroadcast(contexto, 0, i, 0);
		
		gerenciador.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), p);
		*/
	}
	
	public void cancell(Context contexto, int id){
		NotificationManager nm = (NotificationManager) contexto.getSystemService(Activity.NOTIFICATION_SERVICE);
		nm.cancel(id);
	}
	
	public int notificationId(GregorianCalendar gc){
		
		int id = (int) gc.getTimeInMillis();
		return id;
	}

}
