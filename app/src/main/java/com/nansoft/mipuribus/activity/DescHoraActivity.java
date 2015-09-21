package com.nansoft.mipuribus.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.nansoft.mipuribus.HandlerDataBase;
import com.nansoft.mipuribus.R;
import com.nansoft.mipuribus.model.Carrera;

import static com.wagnerandade.coollection.Coollection.*;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DescHoraActivity extends Activity
{
	
	TableLayout tabla;  
    TableLayout cabecera;  
    TableRow.LayoutParams layoutFila;  
    TableRow.LayoutParams layoutTextView;  
    
    Resources rs;  
    
    List<Carrera> listCarreras;
        
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layinforuta);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		Bundle bundle = getIntent().getExtras();
        setTitle(bundle.getString("nombreRuta") + " - " + bundle.getString("dias"));

		rs = this.getResources();  
        tabla = (TableLayout)findViewById(R.id.BodyTable);    
		layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT);  
		
		layoutTextView = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT,1f);  
  
		listCarreras = new ArrayList<Carrera>();
		
		HandlerDataBase objHandlerDatabase = new HandlerDataBase(this);
		
		ArrayList<Carrera> ordered = objHandlerDatabase.CargarHorario(bundle.getInt("idRuta"), bundle.getInt("idHorario"));
		
		if (ordered.isEmpty())
		{
			setContentView(R.layout.layerror);
		}
		else
		{
		
			listCarreras = from(ordered).orderBy("getDescHora").all();
			agregarFilasTabla();
		}
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		try
		{
			AdView adView = (AdView) findViewById(R.id.adViewAnuncio);
	        AdRequest adRequestBanner = new AdRequest.Builder().build();
	        adView.loadAd(adRequestBanner);
		}
		catch(Exception e)
		{
			
		}
	}
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_deschora, menu);
        return true;
    }
	*/

	 public void agregarFilasTabla()
	 {  
		  
	     TableRow fila;  
	     TextView txtvSitioSalida;  
	     TextView txtvHora;  
	     
	     SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
	     Date date = null;
	     String sufijo = "";
	     int intHoraTemporal = 0;
	     for(int i = 0,TamArray = listCarreras.size();i<TamArray;i++)
	     {  
	    	 	    	 
	         fila = new TableRow(this);  
	         
	         fila.setLayoutParams(layoutFila);  
	         
	         txtvSitioSalida = new TextView(this);  
	         txtvHora = new TextView(this);  

	         txtvSitioSalida.setText(listCarreras.get(i).getNombreSitioSalida());
	         txtvSitioSalida.setTextAppearance(this,R.style.BodyText);  
	         txtvSitioSalida.setGravity(Gravity.CENTER);
	         txtvSitioSalida.setLayoutParams(layoutTextView); 
	         txtvSitioSalida.setTextColor(getResources().getColor(R.color.black));
	         txtvSitioSalida.setBackgroundResource(R.drawable.border);
	         
	        
			try
            {
				date = sdf.parse(listCarreras.get(i).getDescHora().toString());
			} 
			catch (ParseException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			sufijo = " am";
            intHoraTemporal = Integer.parseInt(listCarreras.get(i).getDescHora().toString()
                     .substring(0, 2));


			if (intHoraTemporal == 12)
            {
				sufijo = " md";
			}
            else if (intHoraTemporal > 12)
            {
				sufijo = " pm";
			}

             if (intHoraTemporal == 0)
             {
                 txtvHora.setText(" no hay bus");
             }
             else {
                 txtvHora.setText(sdf.format(date) + sufijo);
             }
	         txtvHora.setTextAppearance(this,R.style.BodyText);  
	         txtvHora.setGravity(Gravity.CENTER);
	         txtvHora.setLayoutParams(layoutTextView);  
	         txtvHora.setTextColor(getResources().getColor(R.color.black));
	         txtvHora.setBackgroundResource(R.drawable.border);
	        // layoutTextView.span = 2;
	         fila.addView(txtvSitioSalida);  
	         fila.addView(txtvHora);  

             if( i%2 == 0)
             {
                 fila.setBackgroundResource(R.color.blanco);
             }
             else
             {
                 fila.setBackgroundResource(R.color.azure);
             }

	         tabla.addView(fila);  
	     }  
	     
	 }
}
