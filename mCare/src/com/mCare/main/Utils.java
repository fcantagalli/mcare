package com.mCare.main;

import java.util.GregorianCalendar;

public class Utils {
	
	public static String formataHora(GregorianCalendar gc){
		String hora = "" + gc.get(GregorianCalendar.HOUR_OF_DAY);
		String minuto = "" + gc.get(GregorianCalendar.MINUTE);
		adiciona0(hora);
		adiciona0(minuto);		
		
		return adiciona0(hora) + ":" + adiciona0(minuto);
	}
	
	public static String adiciona0(String str){
		if(str.length()==1){
			str = "0" + str;
		}
		return str;
	}
}
