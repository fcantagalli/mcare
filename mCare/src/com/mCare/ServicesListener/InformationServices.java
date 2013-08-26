package com.mCare.ServicesListener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import com.mCare.R;
import com.mCare.geocoderServices.GeocoderInfo;
import com.mCare.geocoderServices.GeocoderUtils;
import com.mCare.locationServices.LocationInfo;
import com.mCare.main.MainActivity;
import com.mCare.weatherServices.WeatherInfo;
import com.mCare.weatherServices.YahooWeatherUtils;

public class InformationServices {
	
	Context context;
	MainActivity main;
	public static final String CIDADE = "cidade";
	boolean cidadeExiste;
	
	public InformationServices(Context context){
		this.context = context;
	}

	public void gotWeatherInfo(WeatherInfo weatherInfo) {
		//chamada quando pegou as informacoes do tempo
		if(weatherInfo != null){
			main.updateWeather(weatherInfo);
		}else{
			
		}
	}

	public void gotGeocoderInfo(GeocoderInfo geocoderInfo) {
		//chamada quando pegou as informacoes do geocoder
		if(geocoderInfo != null){
			String cityName = geocoderInfo.getCity();
			// caso tenha conseguido pegar a cidade
			if(cityName != null){
				// se nao tiver salvo a cidade ainda, salva ela aqui
				if(cidadeExiste == false){
					SharedPreferences sp = context.getSharedPreferences(MainActivity.TIPO_MEDICO,Context.MODE_PRIVATE);
					SharedPreferences.Editor spe = sp.edit();

					spe.putString(CIDADE, cityName);
					spe.commit();
				}
				YahooWeatherUtils yahooWeatherUtils = YahooWeatherUtils.getInstance();
				yahooWeatherUtils.queryYahooWeather(context, cityName, this);
			}else{
				showProblemAlert("Não foi possível determinar a previsão do tempo, tente novamente mais tarde");
			}
			
		}
	}
	
	public void getTemp(MainActivity main){
		this.main = main;
		LocationInfo location = new LocationInfo(context, main);
		SharedPreferences sp = context.getSharedPreferences(MainActivity.TIPO_MEDICO, 0);

		String cidade = sp.getString(CIDADE, "@");

		if (cidade.equals("@")) {

			// a cidade ainda não está salva, precisa procurar no gps
			cidadeExiste = false;
			double[] latLong = location.getLatitudeLongitude();
			if(latLong != null){
				GeocoderUtils geocoderUtils = new GeocoderUtils();
				geocoderUtils.queryOpenStreetMaps(context, latLong[0], latLong[1], this);
			}else{
				showProblemAlert("Não foi possível determinar sua localização, tente novamente mais tarde");
			}
			
		} else {
			cidadeExiste = true;
			GeocoderInfo gi = new GeocoderInfo();
			gi.setCity(cidade);
			gotGeocoderInfo(gi);
		}
		
	}
	
	public void showProblemAlert(String message){
		AlertDialog.Builder builder = new AlertDialog.Builder(main);
		builder.setMessage(message);
		builder.setIcon(R.drawable.dunno);
		builder.setPositiveButton("Tentar novamente", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				getTemp(main);
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
}
