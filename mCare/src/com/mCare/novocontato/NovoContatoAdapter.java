package com.mCare.novocontato;

import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mCare.R;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class NovoContatoAdapter extends BaseAdapter{
	
	/**Classe utilizada para instanciar os objetos do XML**/
	private LayoutInflater inflater;
	private List<Tel> telefones;
	private ListView lv;
	private NovoContato activity;
	private TextView baixo;
	private TextView cima;
	
	/** construtor para receber o context **/
	public NovoContatoAdapter(NovoContato activity, List<Tel> telefones, ListView lv, TextView baixo, TextView cima){
		this.telefones = telefones;
		this.lv = lv;
		inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.baixo = baixo;
		this.cima = cima;
		this.activity = activity;
	}
	
	/** final no parametro garante que nao mudemos o objeto item nesse metodo**/
	void addItem(final Tel item){
		this.telefones.add(item);
		/** Atualiza a lista caso seja adicionado**/
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return telefones.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return telefones.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public View getView(int position, View convertView, ViewGroup viewGroup) {
		/** pega o registro da lista**/
		
		Tel tel = telefones.get(position);
		
		/** utiliza o XML row_consulta para exibir na tela*/		
		
		
		/** instancia os objetos do XML **/
		RelativeLayout restoContainer = (RelativeLayout) activity.findViewById(R.id.restoContainer);
		LinearLayout geral = (LinearLayout) activity.findViewById(R.id.geralContainer);
		LinearLayout telefonesContainer = (LinearLayout) activity.findViewById(R.id.telefonesContainer);

		
		Log.i("phil", "tamanho da lista: " + activity.findViewById(R.id.listViewContatos).getHeight());
		Log.i("phil", "tamanho do container da lista: " + telefonesContainer.getHeight());
		//geral.removeView(restoContainer);
		
		convertView = inflater.inflate(R.layout.row_novocontato, null);
		//telefonesContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		Spinner tipo = (Spinner) convertView.findViewById(R.id.spinner1);
		EditText telefone = (EditText) convertView.findViewById(R.id.editText1);
		ImageView delet = (ImageView) convertView.findViewById(R.id.imageView1);
		
		tel.campoTexto = telefone;
		tel.tipo = tipo;
		
		ArrayAdapter<CharSequence> tiposCel = ArrayAdapter.createFromResource(convertView.getContext(), R.array.tiposTel, android.R.layout.simple_list_item_1);
		tipo.setAdapter(tiposCel);
		/** essa parte � quando tivermos j� objetos com informacoes, entao jogamos as informacoes nos objetos
		 * instanciados do XML
		 */
		//geral.addView(restoContainer);		
		return convertView;
	}

}
