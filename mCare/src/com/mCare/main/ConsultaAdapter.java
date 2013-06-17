package com.mCare.main;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mCare.R;
import com.mCare.consulta.Consulta;

public class ConsultaAdapter extends BaseAdapter {

	private List<Consulta> listConsultas;
	
	
	/**Classe utilizada para instanciar os objetos do XML**/
	private LayoutInflater inflater;
	
	public ConsultaAdapter(Context context, List<Consulta> plistConsultas){
		this.listConsultas = plistConsultas;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	/** final no parametro garante que nao mudemos o objeto item nesse metodo**/
	void addItem(final Consulta item){
		this.listConsultas.add(item);
		/** Atualiza a lista caso seja adicionado**/
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return listConsultas.size();
	}

	@Override
	public Object getItem(int arg0) {
		return listConsultas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		
		/** pega o registro da lista**/
		Consulta con = listConsultas.get(position);
		
		/** utiliza o XML row_consulta para exibir na tela*/		
		convertView = inflater.inflate(R.layout.layout_row_consulta, null);
		
		/** instancia os objetos do XML **/
		ImageView foto = (ImageView) convertView.findViewById(R.id.fotoPaciente);
		TextView nome = (TextView) convertView.findViewById(R.id.nomePaciente);
		TextView bairro = (TextView) convertView.findViewById(R.id.Bairro);
		TextView horario = (TextView) convertView.findViewById(R.id.Horario);
		//FrameLayout ligacao = (FrameLayout) convertView.findViewById(R.id.row_botao_ligacao);
		//FrameLayout maps = (FrameLayout) convertView.findViewById(R.id.row_botao_maps);
		/*
		ligacao.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Uri uri = Uri.parse("tel:"+"986481753");
		        Intent intent = new Intent(Intent.ACTION_CALL,uri);
		        view.getContext().startActivity(intent);
			}
		});
		
		maps.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=rua+falchi+gianini+,+311"));
				view.getContext().startActivity(intent);
			}
		});*/
		
		/** essa parte � quando tivermos j� objetos com informacoes, entao jogamos as informacoes nos objetos
		 * instanciados do XML
		 */
		nome.setText(con.getPaciente().getNome());
		bairro.setText(con.getPaciente().getLogradouro());
		GregorianCalendar gc = con.getHora();
		horario.setText(gc.get(gc.DAY_OF_MONTH)+"/"+gc.get(gc.MONTH)+"/"+gc.get(gc.YEAR)+" às "+gc.get(gc.HOUR_OF_DAY)+":"+gc.get(gc.MINUTE));
		
		LinearLayout barrinha = (LinearLayout) convertView.findViewById(R.id.barrinha);


			barrinha = (LinearLayout) convertView.findViewById(R.id.barrinha);
			barrinha.setBackgroundColor(convertView.getResources().getColor(classificaData(gc)));

		
		return convertView;
	}
	
	public int classificaData(GregorianCalendar gc){
		GregorianCalendar now = new GregorianCalendar(Locale.getDefault());
		int dia = gc.get(GregorianCalendar.DAY_OF_MONTH);
		int mes = gc.get(GregorianCalendar.MONTH);
		int ano = gc.get(GregorianCalendar.YEAR);
		int hora = gc.get(GregorianCalendar.HOUR_OF_DAY);
		int minutos = gc.get(GregorianCalendar.MINUTE);
		
		//ano igual a hoje
		if(ano==now.get(GregorianCalendar.YEAR)){
			//mes igual a hoje
			if(mes==now.get(GregorianCalendar.MONTH)){
				//dia igual a hoje
				if(dia==now.get(GregorianCalendar.DAY_OF_MONTH)){
					//hora igual agora
					if(hora==now.get(GregorianCalendar.HOUR_OF_DAY)){
						//minuto igual agora
						if(minutos==now.get(GregorianCalendar.MINUTE)){
							return R.color.CustomYellow;
						}
						
						if(minutos > now.get(GregorianCalendar.MINUTE)){
							return R.color.CustomYellow;
						}
						
						if(minutos < now.get(GregorianCalendar.MINUTE)){
							return R.color.CustomRed;
						}
						
					}
					//hora maior que agora
					if(hora > now.get(GregorianCalendar.HOUR_OF_DAY)){
						//verifica o periodo e se ha algum atraso
						
					}
					//hora menor que agora
					if(hora < now.get(GregorianCalendar.HOUR_OF_DAY)){
						return R.color.CustomRed;
					}
					
				}
				//dia maior que hoje
				if(dia > now.get(GregorianCalendar.DAY_OF_MONTH)){
					return R.color.CustomBlue;
				}
				//dia menor que hoje
				if(dia < now.get(GregorianCalendar.DAY_OF_MONTH)){
					return R.color.CustomRed;
				}
				
			}
			//mes maior que hoje
			if(mes > now.get(GregorianCalendar.MONTH)){
				return R.color.CustomBlue;
			}
			//mes menor que hoje
			if(mes < now.get(GregorianCalendar.MONTH)){
				return R.color.CustomRed;
			}
			return -1;
		}
		//ano depois de hoje
		if(ano > now.get(GregorianCalendar.YEAR)){
			return R.color.CustomBlue;
		}
		//ano antes de hoje
		if(ano < now.get(GregorianCalendar.YEAR)){
			return R.color.CustomRed;
		}
		return -1;
	}
	
	String enderecoNavigation(String endereco, int numero){
		String endFinal = "";
		String[] separada = endereco.split(" ");
		
		for (String s : separada){
			endFinal += s+"+";
		}
		if(endFinal == ""){
			return null;
		}
		else{
			endFinal+=","+numero;
			return endFinal;
		}
	}
	
}
