package com.mCare.ServicesListener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

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
			if(cityName != null){
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
		double[] latLong = location.getLatitudeLongitude();
		if(latLong != null){
			GeocoderUtils geocoderUtils = new GeocoderUtils();
			geocoderUtils.queryOpenStreetMaps(context, latLong[0], latLong[1], this);
		}else{
			showProblemAlert("Não foi possível determinar sua localização, tente novamente mais tarde");
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
