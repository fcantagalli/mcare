package com.mCare.notificacao;

import java.util.Date;
import java.util.GregorianCalendar;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class NotificacaoConsulta {
	
	int hashId(Date date){
		int id = 0;
		
		return id;
	}
	
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static void create(Context contexto,long quandoIraAparecer,CharSequence tickerText, CharSequence title, CharSequence message, int icon, int id, Intent intent){
		//Pending Intent para executar a intent ao selecionar a notificacao
		PendingIntent p = PendingIntent.getActivity(contexto, 0, intent, 0);
		
		Notification notification = null;
		int apilevel = Build.VERSION.SDK_INT;
		
		if(apilevel >= 11){
			Builder builder = new Notification.Builder(contexto).setContentTitle(tickerText).setContentText(message).setSmallIcon(icon).setContentIntent(p).setAutoCancel(true).setWhen(quandoIraAparecer);
			builder.setAutoCancel(true);
			builder.setWhen(quandoIraAparecer);
			if(apilevel >= 17){
				//Android 4.2
				notification = builder.build();
				notification.defaults = notification.DEFAULT_ALL;
				notification.when = quandoIraAparecer;
			}else{
				// Android 3.x
				notification = builder.getNotification();
				notification.defaults = notification.DEFAULT_ALL;
				notification.when = quandoIraAparecer;
			}
		}
		else{
			//Android 2.2
			notification = new Notification(icon,tickerText,quandoIraAparecer);
			//Informacoes
			notification.setLatestEventInfo(contexto, title, message, p);
			notification.defaults = notification.DEFAULT_ALL;
		}
		
		NotificationManager nm = (NotificationManager) contexto.getSystemService(Activity.NOTIFICATION_SERVICE);
		nm.notify(id,notification);
		
	}
	
	public static void cancell(Context contexto, int id){
		NotificationManager nm = (NotificationManager) contexto.getSystemService(Activity.NOTIFICATION_SERVICE);
		nm.cancel(id);
	}
	
	public static int notificationId(GregorianCalendar gc){
		
		int id = (int) gc.getTimeInMillis();
		return id;
	}
}
