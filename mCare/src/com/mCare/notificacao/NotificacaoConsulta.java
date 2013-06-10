package com.mCare.notificacao;

import java.util.Date;
import java.util.GregorianCalendar;

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

public class NotificacaoConsulta {
	
	int hashId(Date date){
		int id = 0;
		
		return id;
	}
	
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static void create(Context contexto,long quandoIraAparecer,CharSequence tickerText, CharSequence title, CharSequence message, int icon){
		//Pending Intent para executar a intent ao selecionar a notificacao
		
		Intent intent = new Intent("NOTIFICACAO");
		intent.putExtra("ticketText", tickerText);
		intent.putExtra("title",title);
		intent.putExtra("message", message);
		intent.putExtra("icon", icon);
		int id = (int) quandoIraAparecer;
		intent.putExtra("id",(int) id);
		PendingIntent p = PendingIntent.getActivity(contexto, 0, intent, 0);
		
		AlarmManager alarm = (AlarmManager) contexto.getSystemService(contexto.ALARM_SERVICE);
		alarm.set(AlarmManager.RTC_WAKEUP,quandoIraAparecer,p);
		
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
