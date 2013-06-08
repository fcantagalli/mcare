package com.mCare.consulta.realizarConsulta;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import com.mCare.db.DbHelperMedia;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class Midia_Fragment extends Fragment {

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ScrollView scroll = new ScrollView(getActivity());
		scroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));

		LinearLayout layout = new LinearLayout(getActivity());
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		layout.setPadding(16, 16, 16, 16);

		Button fotos = new Button(getActivity());
		fotos.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		fotos.setPadding(6, 6, 6, 6);
		fotos.setText("Fotos");
		fotos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tirarFoto();
			}
		});
		layout.addView(fotos);

		Button videos = new Button(getActivity());
		videos.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		videos.setPadding(6, 6, 6, 6);
		videos.setText("Videos");
		videos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent fotoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
				startActivityForResult(fotoIntent,
						CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
			}
		});
		layout.addView(videos);

		Button audio = new Button(getActivity());
		audio.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		audio.setPadding(6, 6, 6, 6);
		audio.setText("Áudio");
		layout.addView(audio);

		scroll.addView(layout);

		return scroll;
	}
	
	private static File getOutputMediaFile(int type){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.

	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "mCare");
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	        	Log.i("midia", "nao foi possivel criar o diretorio");
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE){
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "IMG_"+ timeStamp + ".jpg");
	    } else if(type == MEDIA_TYPE_VIDEO) {
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "VID_"+ timeStamp + ".mp4");
	    } else {
	    	Log.i("midia", "erro na codificacao");
	        return null;
	    }
	    Log.i("midia", mediaFile.getPath());
	    return mediaFile;
	}
	
	public void tirarFoto(){
		// create Intent to take a picture and return control to the calling application
	    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getOutputMediaFile(MEDIA_TYPE_IMAGE))); // set the image file name
	    // start the image capture Intent
	    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	        if (resultCode == Activity.RESULT_OK) {
	            // Image captured and saved to fileUri specified in the Intent
	            Toast.makeText(getActivity(), "Image saved to:\n" + data.getData(), Toast.LENGTH_LONG).show();
	        } else if (resultCode == Activity.RESULT_CANCELED) {
	        	Toast.makeText(getActivity(), "A imagem não foi salva", Toast.LENGTH_LONG).show();
	        } else {
	        	Toast.makeText(getActivity(), "Não foi possível salvar a imagem", Toast.LENGTH_LONG).show();
	        }
	    }
		
		String caminho = data.getData().toString();
		DbHelperMedia dbMidia = new DbHelperMedia(getActivity());
		GregorianCalendar now = new GregorianCalendar();
		
		dbMidia.insereMedia(dbMidia.FOTO, caminho, "descicao", now);		
	}

}
