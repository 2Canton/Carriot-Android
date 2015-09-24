package com.nansoft.mipuribus.activity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
import com.nansoft.mipuribus.R;
import com.nansoft.mipuribus.database.HandlerDataBase;
import com.nansoft.mipuribus.helper.Util;
import com.nansoft.mipuribus.adapter.RutaAdapterListView;
import com.nansoft.mipuribus.model.CarreraRuta;
import com.nansoft.mipuribus.model.Horario;
import com.nansoft.mipuribus.model.Parada;
import com.nansoft.mipuribus.model.Ruta;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.table.query.Query;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncTable;

public class RutasActivity extends Activity
{	
	public static RutaAdapterListView mAdapter;
	
	ListView listViewMaterias;
    public static SwipeRefreshLayout mSwipeRefreshLayout;

    AdView adView;
    AdRequest adRequestBanner;

	// layout de error
	View includedLayout;

	HandlerDataBase objHandlerDataBase;

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

		includedLayout = findViewById(R.id.sindatos);

	
		listViewMaterias.setAdapter(mAdapter);
		listViewMaterias.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Ruta objRuta = (Ruta) parent.getItemAtPosition(position);

				Intent intent = new Intent(getApplicationContext(), HorariosActivity.class);
				intent.putExtra("idRuta", objRuta.getIdRuta());
				intent.putExtra("nombreRuta", objRuta.getNombreRuta());

				startActivity(intent);

			}
		});
		mAdapter.clear();
        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {

				sincronizarDatos();
			}

		});

		objHandlerDataBase = new HandlerDataBase(this);

        adView = (AdView) findViewById(R.id.adViewAnuncio);

		mSwipeRefreshLayout.post(new Runnable() {
			@Override
			public void run() {
				mSwipeRefreshLayout.setRefreshing(true);
			}
		});

		Util objUtil = new Util(getApplicationContext());
		if(!objUtil.inicializarBaseDatos())
			Toast.makeText(getApplicationContext(),"Ha ocurrido un error al inicializar la copia local",Toast.LENGTH_SHORT).show();



		sincronizarDatos();
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

	public void onClick(View vista)
	{
		Recargar();
	}


	public void Recargar()
	{
		Intent intent = getIntent();
		finish();
		startActivity(intent);
	}





	private void sincronizarDatos()
	{
		if (Util.isNetworkAvailable(getApplicationContext())) {

			includedLayout.setVisibility(View.GONE);
			mSwipeRefreshLayout.setEnabled(false);

			new AsyncTask<Void, Void, Boolean>() {

				MobileServiceSyncTable<Ruta> rutaTable;
				MobileServiceSyncTable<Horario> horarioTable;
				MobileServiceSyncTable<CarreraRuta> carreraRutaTable;
				MobileServiceSyncTable<Parada> paradaTable;

				Query mPullQueryRuta;
				Query mPullQueryHorario;
				Query mPullQueryCarreraRuta;
				Query mPullQueryParada;




				@Override
				protected void onPreExecute()
				{
					// referencia a las tablas que se va usar
					rutaTable = Util.mClient.getSyncTable("Ruta", Ruta.class);
					horarioTable = Util.mClient.getSyncTable("Horario", Horario.class);
					carreraRutaTable = Util.mClient.getSyncTable("CarreraRuta", CarreraRuta.class);
					paradaTable = Util.mClient.getSyncTable("Parada", Parada.class);

					mPullQueryRuta = Util.mClient.getTable(Ruta.class).orderBy("nombre", QueryOrder.Ascending);
					mPullQueryHorario = Util.mClient.getTable("Horario",Horario.class).top(10);
					mPullQueryCarreraRuta = Util.mClient.getTable("CarreraRuta",CarreraRuta.class).top(1000);
					mPullQueryParada = Util.mClient.getTable("Parada",Parada.class).top(100);

					// se limpia el adapter mientras carga
					mAdapter.clear();



					// se elimina base de datos
					HandlerDataBase.db.delete("Ruta", null, null);
					HandlerDataBase.db.delete("Horario", null, null);
					HandlerDataBase.db.delete("CarreraRuta", null, null);
					HandlerDataBase.db.delete("Parada", null, null);


				}

				@Override
				protected Boolean doInBackground(Void... params) {
					try {
						//se cargan los últimos cambios
						rutaTable.pull(mPullQueryRuta).get();
						horarioTable.pull(mPullQueryHorario).get();
						carreraRutaTable.pull(mPullQueryCarreraRuta).get();
						paradaTable.pull(mPullQueryParada).get();

						final MobileServiceList<Ruta> resultRuta = rutaTable.read(mPullQueryRuta).get();
						final MobileServiceList <Horario> resultHorario = horarioTable.read(mPullQueryHorario).get();
						final MobileServiceList <CarreraRuta> resultCarreraRuta = carreraRutaTable.read(mPullQueryCarreraRuta).get();
						final MobileServiceList <Parada> resultParada = paradaTable.read(mPullQueryParada).get();



							runOnUiThread(new Runnable() {

								@Override
								public void run() {

									// se agregan los registros en la base de datos
									for (Ruta ruta: resultRuta)
									{
										objHandlerDataBase.InsertarRuta(ruta);
										mAdapter.add(ruta);
										mAdapter.notifyDataSetChanged();
									}
								}

							});


						for (Horario horario: resultHorario)
						{
							objHandlerDataBase.InsertarHorario(horario);
						}

						for (CarreraRuta carreraRuta: resultCarreraRuta)
						{
							objHandlerDataBase.InsertarCarrera(carreraRuta);
						}

						for (Parada parada: resultParada)
						{
							objHandlerDataBase.InsertarSitioSalida(parada);
						}

						// se cargan los registros de la base de datos local en el adapter
						//objHandlerDataBase.CargarAdapter();


						return true;
					} catch (final Exception exception) {

						return false;
					}

				}

				@Override
				protected void onPostExecute(Boolean success) {

					mSwipeRefreshLayout.setRefreshing(false);


					mSwipeRefreshLayout.setEnabled(true);

					if (!success) {
						includedLayout.setVisibility(View.VISIBLE);
					} else {
						includedLayout.setVisibility(View.GONE);
					}
				}

				@Override
				protected void onCancelled() {
					super.onCancelled();
				}
			}.execute();
		}
		else
		{

			if(objHandlerDataBase.VerificarDatosRuta())
			{
				objHandlerDataBase.CargarAdapter();
			}
			else
			{

				setContentView(R.layout.error);



			}
			mSwipeRefreshLayout.setRefreshing(false);


			mSwipeRefreshLayout.setEnabled(true);
		}
	}



}
