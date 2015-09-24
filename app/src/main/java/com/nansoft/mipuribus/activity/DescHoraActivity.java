package com.nansoft.mipuribus.activity;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
import com.nansoft.mipuribus.R;
import com.nansoft.mipuribus.helper.Util;
import com.nansoft.mipuribus.model.CarreraRuta;
import com.nansoft.mipuribus.model.Ruta;

import static com.wagnerandade.coollection.Coollection.*;

import android.app.Activity;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
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
    
    List<CarreraRuta> listCarreraRutas;
	static ArrayList<CarreraRuta> ordered;
        
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
  
		listCarreraRutas = new ArrayList<CarreraRuta>();
		

		//ArrayList<CarreraRuta> ordered = objHandlerDatabase.CargarHorario(bundle.getInt("idRuta"), bundle.getInt("idHorario"));
		 ordered = new ArrayList<CarreraRuta>();
		cargarDescHora(bundle.getString("idRuta"), bundle.getString("idHorario"));


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
	     for(int i = 0,TamArray = listCarreraRutas.size();i<TamArray;i++)
	     {  
	    	 	    	 
	         fila = new TableRow(this);  
	         
	         fila.setLayoutParams(layoutFila);  
	         
	         txtvSitioSalida = new TextView(this);  
	         txtvHora = new TextView(this);  

	         txtvSitioSalida.setText(listCarreraRutas.get(i).getNombreSitioSalida());
	         txtvSitioSalida.setTextAppearance(this,R.style.BodyText);  
	         txtvSitioSalida.setGravity(Gravity.CENTER);
	         txtvSitioSalida.setLayoutParams(layoutTextView); 
	         txtvSitioSalida.setTextColor(getResources().getColor(R.color.black));
	         txtvSitioSalida.setBackgroundResource(R.drawable.border);
	         
	        
			try
            {
				date = sdf.parse(listCarreraRutas.get(i).getDescHora().toString());
			} 
			catch (ParseException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			sufijo = " am";
            intHoraTemporal = Integer.parseInt(listCarreraRutas.get(i).getDescHora().toString()
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

	private void cargarDescHora(final String pID_RUTA,final String pID_HORARIO)
	{
		//includedLayout.setVisibility(View.GONE);
		//mSwipeRefreshLayout.setEnabled(false);

		new AsyncTask<Void, Void, Boolean>() {

			MobileServiceClient mClient;
			MobileServiceTable<CarreraRuta> carreraRutaTable;

			@Override
			protected void onPreExecute()
			{
				try {

					mClient = new MobileServiceClient(
							Util.UrlMobileServices,
							Util.LlaveMobileServices,
							getApplicationContext()
					);

					carreraRutaTable = mClient.getTable("CarreraRuta", CarreraRuta.class);

					//mAdapter.clear();

				} catch (MalformedURLException e) {

				}
				catch (Exception e)
				{

				}

			}

			@Override
			protected Boolean doInBackground(Void... params) {
				try {

					final MobileServiceList<CarreraRuta> result = carreraRutaTable.where().field("idruta").eq(pID_RUTA).and().field("idhorario").eq(pID_HORARIO).execute().get();

					ordered = result;
					return true;
				} catch (Exception exception) {

				}
				return false;
			}

			@Override
			protected void onPostExecute(Boolean success)
			{

				//mSwipeRefreshLayout.setRefreshing(false);


				//mSwipeRefreshLayout.setEnabled(true);

				if (!success)
				{
					//includedLayout.setVisibility(View.VISIBLE);
				}
				else
				{
					//includedLayout.setVisibility(View.GONE);
				}


				listCarreraRutas = from(ordered).orderBy("getDescHora").all();
				agregarFilasTabla();
			}

			@Override
			protected void onCancelled()
			{
				super.onCancelled();
			}
		}.execute();
	}

}
