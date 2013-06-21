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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mCare.R;
import com.mCare.media.Midia_Fragment;
import com.mCare.medicamento.ListaMedicamentosPorPaciente;

public class RealizarConsultaMain extends FragmentActivity implements ActionBar.TabListener {
	
	AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    ViewPager mViewPager;
    static Consulta_Fragment consulta_fragment = null;
    static Midia_Fragment midia_fragment = null;
    static ListaMedicamentosPorPaciente lista_medicamentos_paciente = null;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());
		final ActionBar actionBar = getActionBar();
		
		View homeIcon = findViewById(android.R.id.home);
	    ((View) homeIcon.getParent()).setVisibility(View.VISIBLE);
	    ((View) homeIcon).setVisibility(View.VISIBLE);
		
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.action_bar_realizar_consulta);
		
		//titulo da activity
		TextView titulo = (TextView) actionBar.getCustomView().findViewById(R.id.textViewTitulo);
		Log.i("RealizarConsultaMain", "referencia de titulo: " + titulo);
		titulo.setText("Paciente: " + getIntent().getExtras().getString("nome_paciente"));
		
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		//botao de finalizar da actionbar
		Button finalizar = (Button) actionBar.getCustomView().findViewById(R.id.buttonFinalizar);
		finalizar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				salvaDados();
			}
		});
		
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
	
	private void salvaDados(){
		consulta_fragment.salvaInformacoes();
		lista_medicamentos_paciente.salvaDados();
		Toast.makeText(this, "Consulta realizada com sucesso!", Toast.LENGTH_LONG).show();
	//	ListaMedicamentosPorPaciente listaMed = (ListaMedicamentosPorPaciente) fm.findFragmentById();
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
                	if(consulta_fragment==null){
                		consulta_fragment = new Consulta_Fragment();
                	}
                	return consulta_fragment;
                }
                
                case 1: {
                	if(lista_medicamentos_paciente==null){
                		lista_medicamentos_paciente = new ListaMedicamentosPorPaciente();
                	}
                	return lista_medicamentos_paciente;
                }
                
                case 2:{
                	if(midia_fragment==null){
                		midia_fragment = new Midia_Fragment();
                	}
                	return midia_fragment;
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
        	case 1: return "Dados Adicionais";
        	case 2: return "Mídia";
        	case 3: return "Exames";
        	}
        	
            return "ERROR";
        }
    }
	
}
