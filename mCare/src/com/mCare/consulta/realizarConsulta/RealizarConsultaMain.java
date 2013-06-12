package com.mCare.consulta.realizarConsulta;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mCare.R;
import com.mCare.exame.Exame;
import com.mCare.media.Midia_Fragment;
import com.mCare.medicamento.ListaMedicamentosPorPaciente;

public class RealizarConsultaMain extends FragmentActivity implements ActionBar.TabListener {
	
	AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    ViewPager mViewPager;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());
		final ActionBar actionBar = getActionBar();
		
		View homeIcon = findViewById(android.R.id.home);
	    ((View) homeIcon.getParent()).setVisibility(View.VISIBLE);
	    ((View) homeIcon).setVisibility(View.VISIBLE);
		
		actionBar.setTitle("Paciente: " + getIntent().getExtras().getString("nome_paciente"));
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
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
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:{
			Toast.makeText(this, "A consulta não foi realizada", Toast.LENGTH_LONG).show();
			onBackPressed();
		}
		break;
		default: return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
	public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {
		
        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0: {
                		return new Consulta_Fragment();
                }
                
                case 1: {
                		return new ListaMedicamentosPorPaciente();
                }
                
                case 2:{
                		return new Midia_Fragment();
                }
                case 3:{
                		return new Midia_Fragment();
                }
            }
        	return new Consulta_Fragment();
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
        	switch(position){
        	case 0: return "Consulta";
        	case 1: return "Medicamentos";
        	case 2: return "Mídia";
        	case 3: return "Exames";
        	}
        	
            return "ERROR";
        }
    }
	
}
