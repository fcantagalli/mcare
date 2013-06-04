package com.mCare.locationServices;

import com.mCare.main.MainActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;

public class LocationInfo {

	double latitude;
	double longitude;
	Context context;
	MainActivity main;

	public LocationInfo(Context context, MainActivity main) {
		this.context = context;
		this.main = main;
		latitude = 0;
		longitude = 0;
	}

	// verifica se o gps esta ativado
	public boolean activateGPS() {
		LocationManager service = (LocationManager) context.getSystemService(android.content.Context.LOCATION_SERVICE);

		if (!service.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			// Dialog avisando que o GPS esta desativado
			AlertDialog.Builder builder = new AlertDialog.Builder(main);
			builder.setMessage("Esse aplicativo utiliza sua localização para determinar as condições atuais do tempo").setTitle("O GPS está desativado");
			
			//botao ativar
			builder.setNegativeButton("Desejo ativar o GPS", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					main.startActivity(intent);
					dialog.dismiss();
				}
			});
			builder.setPositiveButton("Fechar",	new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.dismiss();
						}
					});
			AlertDialog dialog = builder.create();
			dialog.show();
		} else {
			return true;
		}
		return false;
	}

	public boolean getLocation() {
		LocationManager locationManager = (LocationManager) context.getSystemService(android.content.Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);

		if (location != null) {
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			return true;
		}
		return false;
	}

	public double[] getLatitudeLongitude() {
		activateGPS();
		double[] information = new double[2];
		if(getLocation()){
			information[0] = latitude;
			information[1] = longitude;
		}else{
			information = null;
		}		
		return information;
	}
}
