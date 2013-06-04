package com.mCare.main;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mCare.R;
import com.mCare.ServicesListener.InformationServices;
import com.mCare.paciente.ListaPacientes;
import com.mCare.weatherServices.WeatherInfo;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
	
	AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    ViewPager mViewPager;
	private TextView textTemp;
	private ImageView imgTemp;
	
	public static final String TIPO_MEDICO = "tipo_medico";
	public static String tipo_medico;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.action_bar_main);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		View homeIcon = findViewById(android.R.id.home);
		((View) homeIcon.getParent()).setVisibility(View.GONE);
		
		mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });
        
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(actionBar.newTab().setText(mAppSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
        }
		
		textTemp = (TextView) actionBar.getCustomView().findViewById(R.id.textViewTempo);
		imgTemp = (ImageView) actionBar.getCustomView().findViewById(R.id.imageViewTempo);
		
		InformationServices information = new InformationServices(getApplicationContext());
		information.getTemp(this);

		SharedPreferences sp = getSharedPreferences(TIPO_MEDICO, 0);

		String tipo = sp.getString(TIPO_MEDICO, "@");

		if (tipo.equals("@")) {

			// AlertDialog para escolha do tipo de medico que ele ?:
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Bem Vindo ao aplicativo mCare\nEscolha sua area de atuação: ");
			final String[] tiposMedico = getResources().getStringArray(
					R.array.Tipos_de_medico);
			alert.setItems(tiposMedico, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					SharedPreferences sp = getSharedPreferences(TIPO_MEDICO,
							MODE_PRIVATE);
					SharedPreferences.Editor spe = sp.edit();

					spe.putString(TIPO_MEDICO, tiposMedico[arg1]);
					spe.commit();
				}
			});
			alert.create().show();
		} else {
			tipo_medico = tipo;
		}
	}
	
	@Override
	public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void updateWeather(WeatherInfo info){
		textTemp.setText(info.getForecast1TempLowC() + "º - " + info.getForecast1TempHighC() + "º");
		int conditionCode = info.getForecast1Code();
		int refImg = 0;

			switch(conditionCode){
				case 1: refImg = R.drawable.shower2;
						break;
				case 3: refImg = R.drawable.thunder_storm;
						break;
				case 4: refImg = R.drawable.thunder_storm;
						break;
				case 9: refImg = R.drawable.shower1;
						break;
				case 11: refImg = R.drawable.shower2;
						 break;
				case 12: refImg = R.drawable.shower3;
						break;
				case 20: refImg = R.drawable.fog;
						break;
				case 21: refImg = R.drawable.fog;
						break;
				case 26: refImg = R.drawable.cloudy5;
						break;
				case 27: refImg = R.drawable.night_cloudy4;
						break;
				case 28: refImg = R.drawable.cloudy4;
						break;
				case 29: refImg = R.drawable.night_cloudy2;
						break;
				case 30:refImg = R.drawable.cloudy2;
						break;
				case 31:refImg = R.drawable.night_sunny;
						break;
				case 32:refImg = R.drawable.sunny;
						break;
				case 33:refImg =  R.drawable.night_cloudy1;
						break;
				case 34:refImg = R.drawable.cloudy1;
						break;
				case 36:refImg = R.drawable.sunny;
						break;
				case 37:refImg = R.drawable.thunder_storm;
						break;
				case 38:refImg = R.drawable.thunder_storm;
						break;
				case 39:refImg = R.drawable.thunder_storm;
						break;
				case 40:refImg = R.drawable.light_rain;
						break;
				case 44:refImg = R.drawable.cloudy3;
						break;
				case 45:refImg = R.drawable.shower3;
						break;
				case 3200: refImg = R.drawable.dunno;
						break;
			}

		imgTemp.setImageResource(refImg);
		textTemp.setVisibility(View.VISIBLE);
		imgTemp.setVisibility(View.VISIBLE);
	}

	
	public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0: return new Consultas_Fragment();
                
                case 1: return new ListaPacientes();
            }
            return new com.mCare.main.Consultas_Fragment();
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
        	switch(position){
        	case 0: return "Consulta";
        	case 1: return "Contatos";
        	case 2: return "Pacientes";
        	}
        	
            return "ERROR";
        }
    }
	
}
