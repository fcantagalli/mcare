package com.mCare.media;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

import com.mCare.R;

public class Descricao extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_descricao);
		getActionBar().setTitle("Descrição da foto:");

		String caminho = getIntent().getExtras().getString("caminho_foto");
		Log.i("midia", "Caminho: " + caminho);

		ImageView myImage = (ImageView) findViewById(R.id.imageViewFoto);
		
		Cursor cursor = MediaStore.Images.Thumbnails.query(getContentResolver(), Uri.parse(caminho), null);
		if (cursor != null) {
		    cursor.moveToFirst();
		    int imageID = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
		    Uri uri = Uri.withAppendedPath( MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
		                                    Integer.toString(imageID) );
		    myImage.setImageURI(uri);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.descricao, menu);
		return true;
	}

}
