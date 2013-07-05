package com.mCare.geocoderServices;

public class GeocoderInfo {
	
	String result;
	String road;
	String city_district;
	String city;
	String county;
	String state;
	String postcode;
	String country;
	String country_code;
	
	public GeocoderInfo(){
		result = "";
		road = "";
		city_district = "";
		city = "";
		county = "";
		state = "";
		postcode = "";
		country = "";
		country_code = "";
	}
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getRoad() {
		return road;
	}
	
	public void setRoad(String road) {
		this.road = road;
	}
	
	public String getCity_district() {
		return city_district;
	}
	
	public void setCity_district(String city_district) {
		this.city_district = city_district;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCounty() {
		return county;
	}
	
	public void setCounty(String county) {
		this.county = county;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getPostcode() {
		return postcode;
	}
	
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getCountry_code() {
		return country_code;
	}
	
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

}
