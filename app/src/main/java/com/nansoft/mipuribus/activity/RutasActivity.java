package com.nansoft.mipuribus.activity;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
import com.nansoft.mipuribus.R;
import com.nansoft.mipuribus.database.HelperDatabase;
import com.nansoft.mipuribus.helper.Util;
import com.nansoft.mipuribus.adapter.RutaAdapterListView;
import com.nansoft.mipuribus.model.CarreraRuta;
import com.nansoft.mipuribus.model.Horario;
import com.nansoft.mipuribus.model.Version;
import com.nansoft.mipuribus.model.Parada;
import com.nansoft.mipuribus.model.Ruta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.table.query.Query;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncTable;

public class RutasActivity extends AppCompatActivity
{	
	public static RutaAdapterListView mAdapter;
	
	ListView listViewMaterias;
    public static SwipeRefreshLayout mSwipeRefreshLayout;



	// layout de error
	View includedLayout;

	HelperDatabase objHandlerDataBase;

	private static final int VERSION_BASEDATOS = 1;
	private SharedPreferences prefs;
	private String nombrePref = "Preferencias";

	Version OBJ_VERSION;

	MobileServiceSyncTable<Ruta> rutaTable;
	Query mPullQuery;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);


		// Set up action bar.
		ActionBar bar = getSupportActionBar();
		bar.show();
		bar.setDisplayHomeAsUpEnabled(true);

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
				intent.putExtra("idRuta", objRuta.id);
				intent.putExtra("nombreRuta", objRuta.nombre);

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

		objHandlerDataBase = new HelperDatabase(this);



		mSwipeRefreshLayout.post(new Runnable() {
			@Override
			public void run() {
				mSwipeRefreshLayout.setRefreshing(true);
			}
		});

		Util objUtil = new Util(getApplicationContext());
		if(!objUtil.inicializarBaseDatos())
			Toast.makeText(getApplicationContext(),"Ha ocurrido un error al inicializar la copia local",Toast.LENGTH_SHORT).show();

		prefs = getSharedPreferences(nombrePref, MODE_PRIVATE);

		rutaTable = Util.mClient.getSyncTable("Ruta", Ruta.class);
		mPullQuery = Util.mClient.getTable(Ruta.class).where().orderBy("nombre",QueryOrder.Ascending);

		syncAsync();

		//sincronizarDatos();
	}

	public boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager
				= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}


	public void syncAsync(){
		if (isNetworkAvailable()) {
			new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					try {
						Util.mClient.getSyncContext().push().get();
						rutaTable.pull(mPullQuery).get();

						final MobileServiceList<Ruta> resultRuta = rutaTable.read(mPullQuery).get();

						runOnUiThread(new Runnable() {

							@Override
							public void run() {

								// se agregan los registros en la base de datos
								for (Ruta ruta : resultRuta) {
									mAdapter.add(ruta);
									mAdapter.notifyDataSetChanged();
								}
							}

						});

					} catch (Exception exception) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {

								Toast.makeText(RutasActivity.this, "Error al sincronizar!", Toast.LENGTH_LONG).show();
							}
						});
					}
					return null;
				}
			}.execute();
		} else {
			Toast.makeText(this, "You are not online, re-sync later!" +
					"", Toast.LENGTH_LONG).show();
		}
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
		int VERSION_BASEDATOS_ACTUAL = prefs.getInt("VERSION_BASEDATOS", 1);
		OBJ_VERSION = new Version(VERSION_BASEDATOS_ACTUAL);

		// verificamos la versión de base de datos
		final ListenableFuture <Version> resultVersion = Util.mClient.invokeApi("version","GET",null,Version.class);

		// agregamos un callback
		Futures.addCallback(resultVersion, new FutureCallback<Version>() {
			@Override
			public void onFailure(Throwable exc) {
			}

			@Override
			public void onSuccess(Version result) {


				// establecemos la versión remota en el objeto
				OBJ_VERSION.setVersionRemota(result.getVersionRemota());

				// verificamos si se debe actualizar
				//if (OBJ_VERSION.estadoActualizarBaseDatos()) {
				if (true) {

					//includedLayout.setVisibility(View.GONE);
					mSwipeRefreshLayout.setEnabled(false);

					new AsyncTask<Void, Void, Boolean>() {

						MobileServiceSyncTable<Ruta> rutaTable;
						MobileServiceTable<Horario> horarioTable;
						MobileServiceTable<CarreraRuta> carreraRutaTable;
						MobileServiceTable<Parada> paradaTable;

						@Override
						protected void onPreExecute() {

							// referencia a las tablas que se va usar
							rutaTable = Util.mClient.getSyncTable("Ruta", Ruta.class);
							horarioTable = Util.mClient.getTable("Horario", Horario.class);
							carreraRutaTable = Util.mClient.getTable("CarreraRuta", CarreraRuta.class);
							paradaTable = Util.mClient.getTable("Parada", Parada.class);

							// se limpia el adapter mientras carga
							mAdapter.clear();

							// se elimina base de datos
							HelperDatabase.db.delete("Ruta", null, null);
							HelperDatabase.db.delete("Horario", null, null);
							HelperDatabase.db.delete("CarreraRuta", null, null);
							HelperDatabase.db.delete("SitioSalida", null, null);

						}

						@Override
						protected Boolean doInBackground(Void... params) {
							try {

								//se cargan los últimos cambios

								//final MobileServiceList<Ruta> resultRuta = rutaTable.orderBy("nombre", QueryOrder.Ascending).execute().get();
								/*
								final MobileServiceList<Horario> resultHorario = horarioTable.execute().get();
								final MobileServiceList<CarreraRuta> resultCarreraRuta = carreraRutaTable.execute().get();
								final MobileServiceList<Parada> resultParada = paradaTable.execute().get();


								runOnUiThread(new Runnable() {

									@Override
									public void run() {

										// se agregan los registros en la base de datos
										for (Ruta ruta : resultRuta) {
											objHandlerDataBase.InsertarRuta(ruta);
											mAdapter.add(ruta);
											mAdapter.notifyDataSetChanged();
										}
									}

								});

								/*
								for (Horario horario : resultHorario) {
									objHandlerDataBase.InsertarHorario(horario);
								}

								for (CarreraRuta carreraRuta : resultCarreraRuta) {
									objHandlerDataBase.InsertarCarrera(carreraRuta);
								}

								for (Parada Parada : resultParada) {
									objHandlerDataBase.InsertarSitioSalida(Parada);
								}
								*/

								// se cargan los registros de la base de datos local en el adapter
								//objHandlerDataBase.CargarAdapter();


								return true;
							} catch (final Exception exception) {

								return false;
							}

						}

						@Override
						protected void onPostExecute(Boolean success) {

							verificarEstado(success);


							if (success) {

								// guardamos en las preferencias de usuario la versión de base de datos que tenemos
								savePreferences(OBJ_VERSION.getVersionRemota());

							}
						}

						@Override
						protected void onCancelled() {
							super.onCancelled();
						}
					}.execute();

				} else {

					boolean status = objHandlerDataBase.VerificarDatosRuta();

					if (status) {
						objHandlerDataBase.CargarAdapter();
					}

					verificarEstado(status);
				}

			}
		});


	}

	private void verificarEstado(boolean status)
	{
		if (status)
		{
			includedLayout.setVisibility(View.GONE);
		}
		else
		{
			includedLayout.setVisibility(View.VISIBLE);
		}

		mSwipeRefreshLayout.setRefreshing(false);

		mSwipeRefreshLayout.setEnabled(true);

	}

	private void savePreferences(int pVersion)
	{
		prefs = getSharedPreferences(nombrePref, MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("VERSION_BASEDATOS", pVersion);
		editor.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_rutas, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.


		switch(item.getItemId())
		{
			case android.R.id.home:
				super.onBackPressed();
				break;
		}

		return super.onOptionsItemSelected(item);
	}

}
