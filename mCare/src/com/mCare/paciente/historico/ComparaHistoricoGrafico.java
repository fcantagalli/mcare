package com.mCare.paciente.historico;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MenuItem;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;
import com.mCare.R;
import com.mCare.db.DbHelperConsultasRealizadas;
 
/**
 * The simplest possible example of using AndroidPlot to plot some data.
 */
public class ComparaHistoricoGrafico extends Activity
{
 
    private XYPlot mySimpleXYPlot;
 
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_grafico_compara);
        
        getActionBar().setTitle("Graphic");
		getActionBar().setDisplayHomeAsUpEnabled(true);
 
        criaGrafico();
    }
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home: onBackPressed();
		break;
		default: return super.onOptionsItemSelected(item);
		}
		return true;
	}
    
    public String reduzTamanho(double valor){
    	String string = "" + valor;
    	String novoTamanho = "";
    	if(string.length() > 4){
    		for(int i=0; i<4; i++){
    			novoTamanho += string.charAt(i);
    		}
    	}
    	return novoTamanho;
    }
    
    public void criaGrafico(){
        mySimpleXYPlot = (XYPlot) findViewById(R.id.mySimpleXYPlot);
        
        //pega o nome completo dos campos selecionados
        @SuppressWarnings("unchecked")
		ArrayList<String> campos = (ArrayList<String>) getIntent().getExtras().get("selecionados");
        
        //array para armazenar apenas os nomes formatados dos campos
        String nomesCampo[] = new String[campos.size()];
        for(int z=0; z<nomesCampo.length; z++){
        	nomesCampo[z] = campos.get(z).split("@")[0].replace("_", " ");
        }
        
        //instancia do BD para recuperar os dados
        DbHelperConsultasRealizadas db = new DbHelperConsultasRealizadas(this);
        
        //resgata o id do paciente
        long id_paciente = getIntent().getExtras().getLong("id_paciente");

        // definicao de cores para cada uma das linhas
        int colors[] = new int[7];
        colors[0] = Color.rgb(255, 255, 0);
        colors[1] = Color.rgb(0, 255, 255);
        colors[2] = Color.rgb(255, 0, 255);
        colors[3] = Color.rgb(255, 0, 0);
        colors[4] = Color.rgb(0, 255, 0);
        colors[5] = Color.rgb(0, 0, 255);
        colors[6] = Color.rgb(255,255,255);
        
        //para cada um dos campos que foram selecionados
        for(int k=0; k<campos.size(); k++){
        	//pega os valores do campo atual no banco de dados (esse array list possui (0)datas (1)valores
        	ArrayList<ArrayList<String>> info = db.retornaValoresCampo(id_paciente, campos.get(k));
        	
        	//array list para as datas
    		ArrayList<String> datas;
    		//array para os valores
    		//Number[] valores = null;
    		
    		if(info!=null){
    			//recupera as datas
    			datas = info.get(0);
    			
    			//recupera os valores
    			//valores = new Number[info.get(1).size()];
    			//for(int p=0; p<valores.length; p++){
    				//valores[p] = Double.parseDouble(info.get(1).get(p));
    			//}
    		}
    		Random r = new Random();
    		Number[] valores = {(r.nextDouble()%10),(r.nextDouble()%10),(r.nextDouble()%10),(r.nextDouble()%10),(r.nextDouble()%10),(r.nextDouble()%10),(r.nextDouble()%10),(r.nextDouble()%10),(r.nextDouble()%10),(r.nextDouble()%10)};
    		
    		//define a serie de valores
            XYSeries series1 = new SimpleXYSeries(
                    Arrays.asList(valores),          // SimpleXYSeries takes a List so turn our array into a List
                    SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                    nomesCampo[k]);                             // Set the display title of the series
 
            LineAndPointFormatter series1Format = new LineAndPointFormatter(colors[k], Color.rgb(0, 100, 0), null, null);
            
            //define a cor da linha
            Paint paint = series1Format.getLinePaint();
            paint.setStrokeWidth(13);
            series1Format.setLinePaint(paint);
     
            // add a new series to the xyplot:
            mySimpleXYPlot.addSeries(series1, series1Format);
        }

        // same as above:
        //mySimpleXYPlot.addSeries(series2, new LineAndPointFormatter(Color.rgb(0, 0, 200), Color.rgb(0, 0, 100), null, null));
 
        // reduce the number of range labels
       // mySimpleXYPlot.setTicksPerRangeLabel(valores.size());
       // mySimpleXYPlot.setTicksPerDomainLabel(valores.size()/2);
        mySimpleXYPlot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1.0);
        mySimpleXYPlot.setDomainLabel("Consultations");
        //mySimpleXYPlot.setRangeBottomMax(getIntent().getExtras().getDouble("minimo")-(int)getIntent().getExtras().getDouble("minimo")/8);
        //mySimpleXYPlot.setRangeTopMin(getIntent().getExtras().getDouble("maximo")+(int)getIntent().getExtras().getDouble("maximo")/8);
        mySimpleXYPlot.setRangeStep(XYStepMode.SUBDIVIDE, 16);

    }
}