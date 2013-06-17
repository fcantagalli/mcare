package com.mCare.notificacao;

import java.util.Date;
import java.util.GregorianCalendar;

import com.mCare.main.MainActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewDebug.FlagToString;

public class NotificacaoConsulta extends BroadcastReceiver {
	
	int hashId(Date date){
		int id = 0;
		
		return id;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent paramIntent) {
		// Request the notification manager
		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		// Create a new intent which will be fired if you click on the notification
		Intent notificationIntent = new Intent(context, MainActivity.class);

		// Attach the intent to a pending intent
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

		// Create the notification

		Bundle b = paramIntent.getExtras();
		String tickerText = b.getString("ticketText");
		String title = b.getString("title");
		String message = b.getString("message");
		int icon = b.getInt("icon");
		int id = b.getInt("id");

		Notification notification = null;
		int apilevel = Build.VERSION.SDK_INT;
		
		if(apilevel >= 11){
			Builder builder = new Notification.Builder(context).setContentTitle(tickerText).setContentText(message).setSmallIcon(icon).setContentIntent(contentIntent).setAutoCancel(true);
			if(apilevel >= 17){
				//Android 4.2
				notification = builder.build();
				notification.defaults = notification.DEFAULT_ALL;
			}else{
				// Android 3.x
				notification = builder.getNotification();
				notification.defaults = notification.DEFAULT_ALL;
			}
		}
		else{
			//Android 2.2
			notification = new Notification(icon,tickerText,id);
			//Informacoes
			notification.setLatestEventInfo(context, title, message, contentIntent);
			notification.defaults = notification.DEFAULT_ALL;
		}
		
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
			.setShowWhen(true).build();
			
		notification.flags |= Notification.FLAG_AUTO_CANCEL;	
		
		nm.notify(id,notification);

		
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
