package com.mCare.main;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mCare.R;
import com.mCare.consulta.Consulta;

public class ConsultaAdapter extends BaseAdapter {

	private List<Consulta> listConsultas;
	private Calendar c1;
	private Calendar c2;
	private Calendar c3;
	private Calendar c4;
	private Calendar c5;
	private Calendar c6;
	/**Classe utilizada para instanciar os objetos do XML**/
	private LayoutInflater inflater;
	private Context context;
	
	public ConsultaAdapter(Context context, List<Consulta> plistConsultas){
		this.listConsultas = plistConsultas;
		this.context = context;
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
		final Consulta con = listConsultas.get(position);
		
		/** utiliza o XML row_consulta para exibir na tela*/		
		convertView = inflater.inflate(R.layout.layout_row_consulta, null);
		convertView.setBackgroundResource(setColorBackground(con));
		/** instancia os objetos do XML **/
		//ImageView foto = (ImageView) convertView.findViewById(R.id.fotoPaciente);
		TextView nome = (TextView) convertView.findViewById(R.id.nomePaciente);
		TextView bairro = (TextView) convertView.findViewById(R.id.Bairro);
		TextView horario = (TextView) convertView.findViewById(R.id.Horario);
		FrameLayout ligacao = (FrameLayout) convertView.findViewById(R.id.callButtonRow);
		//FrameLayout maps = (FrameLayout) convertView.findViewById(R.id.row_botao_maps);
		
		ligacao.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Log.i("tel","numero de telefone: "+con.getPaciente().getTelefone());
				Uri uri = Uri.parse("tel:"+con.getPaciente().getTelefone());
		        Intent intent = new Intent(Intent.ACTION_CALL,uri);
		        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        context.startActivity(intent);
			}
		});
		/*
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
		bairro.setText(con.getPaciente().getBairro());
		GregorianCalendar gc = con.getHora();
		horario.setText(gc.get(gc.DAY_OF_MONTH)+"/"+gc.get(gc.MONTH)+"/"+gc.get(gc.YEAR)+" às "+gc.get(gc.HOUR_OF_DAY)+":"+gc.get(gc.MINUTE));
		if(gc.compareTo(GregorianCalendar.getInstance())<0){
			
		}
		return convertView;
	}
	
	/*private void botaoMapsNavigation(Context context,String endere�o){
		/*Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
		Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=rua+falchi+gianini+,+311"));
		context.startActivity(intent);
	}*/
	
	private void setCalendars(Consulta con){
		GregorianCalendar gc = con.getHora();
		if(c1 == null){
			c1 = new GregorianCalendar(gc.get(gc.YEAR), gc.get(gc.MONTH), gc.get(gc.DAY_OF_MONTH), 6, 30);
			c2 = new GregorianCalendar(gc.get(gc.YEAR), gc.get(gc.MONTH), gc.get(gc.DAY_OF_MONTH), 10, 0);
			c3 = new GregorianCalendar(gc.get(gc.YEAR), gc.get(gc.MONTH), gc.get(gc.DAY_OF_MONTH), 13, 0);
			c4 = new GregorianCalendar(gc.get(gc.YEAR), gc.get(gc.MONTH), gc.get(gc.DAY_OF_MONTH), 17, 0);
			c5 = new GregorianCalendar(gc.get(gc.YEAR), gc.get(gc.MONTH), gc.get(gc.DAY_OF_MONTH), 19, 0);
			c6 = new GregorianCalendar(gc.get(gc.YEAR), gc.get(gc.MONTH), gc.get(gc.DAY_OF_MONTH), 22, 0);
		}	
	}
	
	private int setColorBackground(Consulta con){
		setCalendars(con);
		if(con.getHora().compareTo(c1)==1){
			if(con.getHora().compareTo(c2)==1){
				if(con.getHora().compareTo(c3)==1){
					if(con.getHora().compareTo(c4)==1){
						if(con.getHora().compareTo(c5)==1){
							if(con.getHora().compareTo(c6)==1){
								return R.color.azul;
							}
							return R.color.roxo;
						}
						return R.color.lilas;
					}
					return R.color.vermelho;
				}
				return R.color.laranja;
			}
			return R.color.amarelo;
		}else{
			return R.color.azul;
		}
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
