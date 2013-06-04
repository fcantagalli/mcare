package com.mCare.main;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class WeatherThread extends Thread implements Runnable {

	private Handler mainHandler;
	
	private Handler myThreadHandler = new Handler(){
        public void handleMessage(Message msg) {
            // ...
        }
    };
	
	public WeatherThread(Handler h) {
		// TODO Auto-generated constructor stub
		this.mainHandler = h;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run(); 
    	 try{
    		 Log.i("temp", "ENTROU NO RUN");
        	 String dadosTemp = null;// = Dados de temperatura
    		 Message mensagem = new Message();
    		 mensagem.setTarget(mainHandler);
    		 /**
    		  * faz a porra toda aquiiiiii
    		  */
    		 this.sleep(5000);
    		 
        	 if(dadosTemp == null){
        		 Log.i("temp", "DADOS TEMP E NULL");
        		 mensagem.what = 0;
        		 mensagem.sendToTarget();
        	 }
        	 else{
        		 /** Pegar os dados de retorno, arruma o textTemp com as imformacoes e tamb�m a imagem **/
        	 }
    	 }catch(Exception e){
    		 Log.e("TestingAreaLOG","Main loop exception - " + e);
    	 }
		
	}
	
	public Handler getHandler() {
        return myThreadHandler;
    }

}
