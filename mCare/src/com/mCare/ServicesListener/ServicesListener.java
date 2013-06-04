package com.mCare.ServicesListener;

import com.mCare.geocoderServices.GeocoderInfo;
import com.mCare.weatherServices.WeatherInfo;

public interface ServicesListener {
	
	public void gotWeatherInfo(WeatherInfo weatherInfo);

	public void gotGeocoderInfo(GeocoderInfo result);
	
}
