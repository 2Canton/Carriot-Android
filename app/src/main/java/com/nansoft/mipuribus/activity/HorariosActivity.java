package com.nansoft.mipuribus.activity;

import java.net.MalformedURLException;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.microsoft.windowsazure.mobileservices.ApiJsonOperationCallback;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.nansoft.mipuribus.HandlerDataBase;
import com.nansoft.mipuribus.adapter.HorarioAdapterListView;
import com.nansoft.mipuribus.NetworkUtil;
import com.nansoft.mipuribus.R;
import com.nansoft.mipuribus.model.Carrera;
import com.nansoft.mipuribus.model.Horario;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class HorariosActivity extends Activity //implements ConnectivityObserver
{
	public static HorarioAdapterListView mAdapter;
	public static ListView listViewMaterias;
	public static HandlerDataBase objHandlerDataBase ;
	static Bundle bundle;
    public static SwipeRefreshLayout mSwipeRefreshLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);

        try {
            bundle = getIntent().getExtras();
            setTitle(bundle.getString("nombreRuta"));
        } catch(Exception e)
        {

        }
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swpActualizar);

        mAdapter = new HorarioAdapterListView(this, R.layout.item_ruta);
		listViewMaterias = (ListView) findViewById(R.id.lstvLista);
		
		listViewMaterias.setAdapter(mAdapter);
		listViewMaterias.setOnItemClickListener(new OnItemClickListener() 
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{

				Horario objHorario = (Horario)parent.getItemAtPosition(position);
						
				Intent intent = new Intent(getApplicationContext(), DescHoraActivity.class);
				intent.putExtra("idHorario", objHorario.getIdHorario());
				intent.putExtra("dias", objHorario.getDias());
				intent.putExtra("idRuta", bundle.getInt("idRuta"));
				intent.putExtra("nombreRuta", bundle.getString("nombreRuta"));
						
				startActivity(intent);


				
			}
			
		});
		
		mAdapter.clear();
		objHandlerDataBase = new HandlerDataBase(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //VerificarConexion(getApplicationContext());
                CargarHorarios();
            }

        });
				
		CargarHorarios();
		
	    
		
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
	
	
	
	private void VerificarConexion(Context pContexto)
	{
		if(NetworkUtil.isOnline(pContexto))
		{
            mSwipeRefreshLayout.setRefreshing(true);
			MiTareaAsincrona tarea1 = new MiTareaAsincrona();
			tarea1.execute();
			
			 new CountDownTimer(20000,1000) {
					
					@Override
					public void onTick(long arg0) {
						// TODO Auto-generated method stub
						}
					
					@Override
					public void onFinish() {
						
						CargarHorarios();
						
					}
				}.start();
	
		}
		else
		{
            mSwipeRefreshLayout.setRefreshing(false);

			CargarHorarios();
			
		}
	}
	
	public void CargarHorarios()
	{

		HandlerDataBase objHandlerDataBase = new HandlerDataBase(this);
		
		// este metodo carga los horarios, devuelve true si lo hizo false en caso contrario
		if(!objHandlerDataBase.CargarHorariosRuta(bundle.getInt("idRuta")))
		{
			setContentView(R.layout.layerror);
			Toast.makeText(getApplicationContext(),  R.string.error, Toast.LENGTH_SHORT).show();
		
		}

	}
	
	public void Recargar()
	{
		Intent intent = new Intent(getApplicationContext(), RutasActivity.class);
		finish();
		startActivity(intent);
	}
	
	public void onClick(View vista)
	{
		Recargar();
	}
	
	




	
	private class MiTareaAsincrona extends AsyncTask<Void, Integer, Boolean> {

		HandlerDataBase objHandlerDataBase;
		MobileServiceClient mClient;
        Gson objJson = new Gson();
        ListView lstView;

		@Override
		protected Boolean doInBackground(Void... params) {

			HandlerDataBase.db.delete("Carrera", null, null);
			mClient.invokeApi("Carrera", null, "GET", null, new ApiJsonOperationCallback()
			{
				@Override
				public void onCompleted(JsonElement data, Exception error,ServiceFilterResponse arg2)
				{
					try
					{
						// verificamos que no se haya producido un error al consultar la api
						if (error == null) {

							// verificamos que lo se devolvió se un array json
							if (data.isJsonArray())
					        {
					        	// obtenemos el array json y lo guardamos en un array json
						        JsonArray array = data.getAsJsonArray();
						        Carrera objCarrera;

						        // for each por cada elemento json en el array
						        for(JsonElement element : array)
						        {
						        	objCarrera = new Carrera();
						        	/*
						        	objCarrera.setIdCarrera((((JsonObject) element).get("idCarreraRuta").getAsInt()));
						        	objCarrera.setIdHorario((((JsonObject) element).get("idHorario").getAsInt()));
						        	objCarrera.setIdRuta((((JsonObject) element).get("idRuta").getAsInt()));
						        	objCarrera.setIdSitioSalida((((JsonObject) element).get("idSitioSalida").getAsInt()));
						        	objCarrera.setDescHora((((JsonObject) element).get("descHora").getAsString()));
						        	//objCarrera.setNombreSitioSalida((((JsonObject) element).get("nombreSitioSalida").getAsString()));
						        	objCarrera.setNota((((JsonObject) element).get("nota").getAsString()));
                                    */
						        	objCarrera = objJson.fromJson(element,Carrera.class);
						        	objHandlerDataBase.InsertarCarrera(objCarrera);
						        }

						        CargarHorarios();
					        }
			            }
						else
						{

						}

					}catch(Exception e)
					{

					}
				}
			});

			//if (isCancelled())


			return true;
		}

		@Override
		protected void onProgressUpdate(Integer... values)
		{
			//int progreso = values[0].intValue();

		}

		@Override
		protected void onPreExecute()
		{

			objHandlerDataBase = new HandlerDataBase(HorariosActivity.this);
            lstView = (ListView) findViewById(R.id.lstvLista);
            lstView.setClickable(false);
			try
			{
				mClient = new MobileServiceClient( "https://mbsapibuspurisco.azure-mobile.net/", "qxutpCaKYuXSWyzfsTZKgUeZabvSgh48", HorariosActivity.this );
			} catch (MalformedURLException e)
			{
				// TODO Auto-generated catch block
				Toast.makeText(HorariosActivity.this,  R.string.error,
						Toast.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);


			}

		}

		@Override
		protected void onPostExecute(Boolean result)
		{
			/*
			if (result)
				Toast.makeText(MainActivity.this, "Tarea finalizada!",Toast.LENGTH_SHORT).show();
			*/
            lstView.setClickable(true);
		}

		@Override
		protected void onCancelled()
		{
			/*
			Toast.makeText(MainActivity.this, "Tarea cancelada!",
					Toast.LENGTH_SHORT).show();*/
		}
	}
}
