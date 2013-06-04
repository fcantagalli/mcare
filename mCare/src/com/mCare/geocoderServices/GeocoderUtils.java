package com.mCare.geocoderServices;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.mCare.ServicesListener.InformationServices;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class GeocoderUtils {
	
	private double latitude;
	private double longitude;
	private InformationServices informationServices;
	
	public void queryOpenStreetMaps(Context context, double latitude, double longitude, InformationServices listener) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.informationServices = listener;
		GeocoderQueryTask task = new GeocoderQueryTask();
		task.setContext(context);
		String cityName = "";
		task.execute(new String[]{cityName});
	}
	
	private String getGeocoderString(Context context) {
		String qResult = "";
		String queryString = "http://nominatim.openstreetmap.org/reverse?format=xml&lat=" + latitude + "&lon=" + longitude + "&zoom=18&addressdetails=1";

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(queryString);

		try {
			HttpEntity httpEntity = httpClient.execute(httpGet).getEntity();
			
			if (httpEntity != null) {
				InputStream inputStream = httpEntity.getContent();
				Reader in = new InputStreamReader(inputStream);
				BufferedReader bufferedreader = new BufferedReader(in);
				StringBuilder stringBuilder = new StringBuilder();

				String stringReadLine = null;

				while ((stringReadLine = bufferedreader.readLine()) != null) {
					stringBuilder.append(stringReadLine + "\n");
				}

				qResult = stringBuilder.toString();
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
		}
		return qResult;
	}
	
	private Document convertStringToDocument(Context context, String src) {
		Document dest = null;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser;

		try {
			parser = dbFactory.newDocumentBuilder();
			dest = parser.parse(new ByteArrayInputStream(src.getBytes()));
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
			Toast.makeText(context, e1.toString(), Toast.LENGTH_LONG).show();
		} catch (SAXException e) {
			e.printStackTrace();
			Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
		}

		return dest;
	}
	
	private GeocoderInfo parseGeocoderInfo(Context context, Document doc) {
		GeocoderInfo geocoderInfo = new GeocoderInfo();
		try {
			
			geocoderInfo.setCity(doc.getElementsByTagName("city").item(0).getTextContent());
			
			/*
			 * Informacoes que nao serao utilizadas
			geocoderInfo.setState(doc.getElementsByTagName("state").item(0).getTextContent());
			geocoderInfo.setResult(doc.getElementsByTagName("result").item(0).getTextContent());
			geocoderInfo.setCity(doc.getElementsByTagName("city").item(0).getTextContent());
			geocoderInfo.setCity_district(doc.getElementsByTagName("city_district").item(0).getTextContent());
			geocoderInfo.setCountry(doc.getElementsByTagName("country").item(0).getTextContent());
			geocoderInfo.setCountry_code(doc.getElementsByTagName("country_code").item(0).getTextContent());
			geocoderInfo.setCounty(doc.getElementsByTagName("county").item(0).getTextContent());
			geocoderInfo.setPostcode(doc.getElementsByTagName("postcode").item(0).getTextContent());
			geocoderInfo.setRoad(doc.getElementsByTagName("road").item(0).getTextContent());
			geocoderInfo.setState(doc.getElementsByTagName("state").item(0).getTextContent());
			*/
			
		} catch (NullPointerException e) {
			e.printStackTrace();
			Toast.makeText(context, "Parse XML failed - Unrecognized Tag", Toast.LENGTH_SHORT).show();
			geocoderInfo = null;
		}
		
		return geocoderInfo;
	}
	
private class GeocoderQueryTask extends AsyncTask<String, Void, GeocoderInfo> {
		
		private Context mContext;
		
		public void setContext(Context context) {
			mContext = context;
		}

		@Override
		protected GeocoderInfo doInBackground(String... cityName) {
			// TODO Auto-generated method stub
			String geocoderString = getGeocoderString(mContext);
			if(geocoderString != ""){
				Document geocoderDoc = convertStringToDocument(mContext, geocoderString);
				GeocoderInfo geocoderInfo = parseGeocoderInfo(mContext, geocoderDoc);
				return geocoderInfo;
			}
			else return null;
		}

		@Override
		protected void onPostExecute(GeocoderInfo result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result != null){
				informationServices.gotGeocoderInfo(result);
			}
			else Log.w("T", "não foi possível retornar o nome da cidade");
		}
		
	}

}
