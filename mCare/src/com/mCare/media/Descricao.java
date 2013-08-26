package com.mCare.media;

import java.util.GregorianCalendar;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.sax.TextElementListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mCare.R;
import com.mCare.db.DbHelperMedia;

public class Descricao extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_descricao);
		getActionBar().setTitle("Descrição da foto:");

		String caminho = getIntent().getExtras().getString("caminho_foto");
		Log.i("midia", "Caminho: " + caminho);

		ImageView myImage = (ImageView) findViewById(R.id.imageViewFoto);
		Bitmap foto = (Bitmap) getIntent().getExtras().get("foto");
		myImage.setImageBitmap(foto);
		
		Button finalizar = (Button) findViewById(R.id.buttonFinalizaDescricao);
		finalizar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gravaBanco();
			}
		});
	}
	
	public void gravaBanco(){
		EditText edit = (EditText) findViewById(R.id.editTextDescricao);
		String descricao = edit.getText().toString();
		if(descricao.length()!=0){
			String caminho = getIntent().getExtras().getString("caminho_foto");
			Log.i("midia", caminho);
			DbHelperMedia dbMidia = new DbHelperMedia(this);
			GregorianCalendar now = new GregorianCalendar();
					
			dbMidia.insereMedia(dbMidia.FOTO,getIntent().getExtras().getInt("id_paciente") ,caminho, descricao, now);	
		}else{
			Toast.makeText(this, "Insira uma descricao", Toast.LENGTH_LONG).show();
			return;
		}
		Toast.makeText(this, "Foto salva com sucesso!", Toast.LENGTH_LONG).show();
		onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.descricao, menu);
		return true;
	}

}
