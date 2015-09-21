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
import com.nansoft.mipuribus.NetworkUtil;
import com.nansoft.mipuribus.R;
import com.nansoft.mipuribus.adapter.RutaAdapterListView;
import com.nansoft.mipuribus.model.Carrera;
import com.nansoft.mipuribus.model.Horario;
import com.nansoft.mipuribus.model.Ruta;
import com.nansoft.mipuribus.model.SitioSalida;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;


public class RutasActivity extends Activity
{	
	public static RutaAdapterListView mAdapter;
	
	 ListView listViewMaterias;
    public static SwipeRefreshLayout mSwipeRefreshLayout;

    AdView adView;
    AdRequest adRequestBanner;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);
		setTitle(getString(R.string.app_name) + " - Rutas");
		// creamos un adaptador para el listview
		mAdapter = new RutaAdapterListView(this, R.layout.item_ruta);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swpActualizar);

        listViewMaterias = (ListView) findViewById(R.id.lstvLista);
	
		listViewMaterias.setAdapter(mAdapter);
		listViewMaterias.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{

				Ruta objRuta = (Ruta)parent.getItemAtPosition(position);
						
				Intent intent = new Intent(getApplicationContext(), HorariosActivity.class);
				intent.putExtra("idRuta", objRuta.getIdRuta());
				intent.putExtra("nombreRuta", objRuta.getNombreRuta());
						
				startActivity(intent);

			}
		});
		mAdapter.clear();
        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                VerificarConexion(getApplicationContext());
            }

		});
		VerificarConexion(this);

        adView = (AdView) findViewById(R.id.adViewAnuncio);




	}
	
	@Override
	public void onResume()
	{
		super.onResume();
        

		try
		{
			adRequestBanner = new AdRequest.Builder().build();
	        adView.loadAd(adRequestBanner);
		}
		catch(Exception e)
		{
			
		}
	
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	
	
	public void VerificarEstadoAdapter()
	{
		HandlerDataBase objHandlerDataBase = new HandlerDataBase(this);
		if(objHandlerDataBase.VerificarDatosRuta())
		{
			objHandlerDataBase.CargarAdapter();
		}
		else
		{
			
			setContentView(R.layout.layerror);
			Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
			
			
		}
	}
	
	private void VerificarConexion(Context pContexto)
	{
		// verificamos si el dispositivo est� conectado a internet
		if(NetworkUtil.isOnline(pContexto))
		{
			// si es as� ponemos el estado de la list view cargando
            //mSwipeRefreshLayout.setRefreshing(true);
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
            });
			// instanciamos la clase que nos trae los datos del backend
			MiTareaAsincrona tarea1 = new MiTareaAsincrona();
			tarea1.execute();
			
			new CountDownTimer(20000,1000) {
				
				@Override
				public void onTick(long arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onFinish() {
					
					VerificarEstadoAdapter();
					
				}
			}.start();
	
		}
		else
		{
			// en caso que no este conectado cambiamos el estado del listview a completo
            mSwipeRefreshLayout.setRefreshing(false);
			VerificarEstadoAdapter();
			
		}
	}
		
	public void Recargar()
	{
		Intent intent = getIntent();
		finish();
		startActivity(intent);
	}
	
	public void onClick(View vista)
	{
		Recargar();
	}
	

	// esta clase se encarga de conectarse con el backend
	private class MiTareaAsincrona extends AsyncTask<Void, Integer, Boolean> 
	{
		
		HandlerDataBase objHandlerDataBase;
		MobileServiceClient mClient;
        Gson objJson = new Gson();
        ListView lstView;
		
		@Override
		protected Boolean doInBackground(Void... params) {
			
			HandlerDataBase.db.delete("Ruta", null, null);
			mClient.invokeApi("Ruta", null, "GET", null, new ApiJsonOperationCallback() 
			{
				@Override
				public void onCompleted(JsonElement data, Exception error,ServiceFilterResponse arg2) 
				{
					try{
					// verificamos que no se haya producido un error al consultar la api
					if (error == null) {
						
						// verificamos que lo se devolvi� se un array json
						if (data.isJsonArray())
				        {
				        	// obtenemos el array json y lo guardamos en un array json
					        JsonArray array = data.getAsJsonArray();
					        Ruta objRuta;
					        
					       // MainActivity.mAdapter.clear();
					        // for each por cada elemento json en el array
					        for(JsonElement element : array)
					        {
					        	objRuta = new Ruta();

                                objRuta = objJson.fromJson(element,Ruta.class);

					        	objHandlerDataBase.InsertarRuta(objRuta);
					        	
					        }
					        objHandlerDataBase.CargarAdapter();
							
				        }
						
					
		            } 
					else 
					{
						
		            	
					}	
					
					}catch(Exception e)
					{
						//Toast.makeText(context, "verifique la conexi�n a internet catch! " ,Toast.LENGTH_SHORT).show();    
						//VerificarEstadoListView();
						
					}
				}
			});		
			
			HandlerDataBase.db.delete("SitioSalida", null, null);
			mClient.invokeApi("SitioSalida", null, "GET", null, new ApiJsonOperationCallback() 
			{
				@Override
				public void onCompleted(JsonElement data, Exception error,ServiceFilterResponse arg2) 
				{
					try{
					// verificamos que no se haya producido un error al consultar la api
					if (error == null) {
						
						// verificamos que lo se devolvi� se un array json
						if (data.isJsonArray())
				        {
				        	// obtenemos el array json y lo guardamos en un array json
					        JsonArray array = data.getAsJsonArray();
					        SitioSalida objSitioSalida;
					       
					        // for each por cada elemento json en el array
					        for(JsonElement element : array)
					        {
					        	objSitioSalida = new SitioSalida();
					        	
					        	objSitioSalida = objJson.fromJson(element,SitioSalida.class);
					        	objHandlerDataBase.InsertarSitioSalida(objSitioSalida); 	
					        }
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
			
			HandlerDataBase.db.delete("Horario", null, null);
			mClient.invokeApi("Horario", null, "GET", null, new ApiJsonOperationCallback() 
			{
				@Override
				public void onCompleted(JsonElement data, Exception error,ServiceFilterResponse arg2) 
				{
					try{
					// verificamos que no se haya producido un error al consultar la api
					if (error == null) {
						
						// verificamos que lo se devolvi� se un array json
						if (data.isJsonArray())
				        {
				        	// obtenemos el array json y lo guardamos en un array json
					        JsonArray array = data.getAsJsonArray();
					        Horario objHorario;
					       
					        // for each por cada elemento json en el array
					        for(JsonElement element : array)
					        {
					        	objHorario = new Horario();
					        	objHorario = objJson.fromJson(element,Horario.class);
					        	
					        	objHandlerDataBase.InsertarHorario(objHorario); 	
					        }
					        
					      
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
							
							// verificamos que lo se devolvi� se un array json
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
			//VerificarEstadoAdapter();
			
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
			
			objHandlerDataBase = new HandlerDataBase(RutasActivity.this);
			try 
			{

				mClient = new MobileServiceClient( "https://mipuribus.azure-mobile.net/","kQQrnVDAhvNrQDwXlkDewMxrUufXkk69", RutasActivity.this );

            } catch (MalformedURLException e)
			{
				// TODO Auto-generated catch block
				Toast.makeText(RutasActivity.this, R.string.error,
						Toast.LENGTH_SHORT).show();
				VerificarEstadoAdapter();
                mSwipeRefreshLayout.setRefreshing(false);
			
				
			}

            lstView = (ListView) findViewById(R.id.lstvLista);
            lstView.setClickable(false);


			
		}

		@Override
		protected void onPostExecute(Boolean result)
		{
			/*
			if (result)ASD
				Toast.makeText(MainActivity.this, "Tarea finalizada!",Toast.LENGTH_SHORT).show();ASDASD
			*/
            lstView.setClickable(true);
		}

		@Override
		protected void onCancelled()
		{
			/*
			Toast.makeText(MainActivity.this, "Tarea cancelada!",
					Toast.LENGTH_SHORT).show();
			VerificarEstadoAdapter();
			*/
		}
	}

	
	   
}
