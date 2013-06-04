package com.mCare.consulta.criaConsulta;

import android.app.ListActivity;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class SelecionaCampos extends ListActivity {
	
	final String[] fieldKind = {
			"Numérico",
			"Texto",
			"Foto",
			"Vídeo",
			"Áudio",
			"Data (dd/mm/aa)",
	};
	
	ListView kindList;
	SimpleCursorAdapter adapter;

}
