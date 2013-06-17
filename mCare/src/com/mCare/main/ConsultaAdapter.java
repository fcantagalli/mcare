package com.mCare.main;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
		final Consulta con = listConsultas.get(position);
		
		/** utiliza o XML row_consulta para exibir na tela*/		
		convertView = inflater.inflate(R.layout.layout_row_consulta, null);
		
		/** instancia os objetos do XML **/
		ImageView foto = (ImageView) convertView.findViewById(R.id.fotoPaciente);
		TextView nome = (TextView) convertView.findViewById(R.id.textViewPaciente);
		TextView horario = (TextView) convertView.findViewById(R.id.Horario);
		FrameLayout ligacao = (FrameLayout) convertView.findViewById(R.id.row_botao_ligacao);
		//FrameLayout maps = (FrameLayout) convertView.findViewById(R.id.row_botao_maps);

		ligacao.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Uri uri = Uri.parse("tel:"+ con.getPaciente().getTelefone());
		        Intent intent = new Intent(Intent.ACTION_CALL,uri);
		        view.getContext().startActivity(intent);
			}
		});
		
		/** essa parte � quando tivermos j� objetos com informacoes, entao jogamos as informacoes nos objetos
		 * instanciados do XML
		 */
		nome.setText(con.getPaciente().getNome());
		GregorianCalendar gc = con.getHora();
		horario.setText(gc.get(gc.DAY_OF_MONTH)+"/"+gc.get(gc.MONTH)+"/"+gc.get(gc.YEAR)+" às "+gc.get(gc.HOUR_OF_DAY)+":"+gc.get(gc.MINUTE));
		
		FrameLayout barrinha = (FrameLayout) convertView.findViewById(R.id.barrinha);
		barrinha.setBackgroundColor(convertView.getResources().getColor(R.color.CustomRed));

		return convertView;
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
