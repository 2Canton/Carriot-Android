package com.nansoft.mipuribus.activity;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
import com.nansoft.mipuribus.R;
import com.nansoft.mipuribus.database.HelperDatabase;
import com.nansoft.mipuribus.helper.Util;
import com.nansoft.mipuribus.adapter.RutaAdapterListView;
import com.nansoft.mipuribus.model.CarreraRuta;
import com.nansoft.mipuribus.model.Horario;
import com.nansoft.mipuribus.model.Version;
import com.nansoft.mipuribus.model.parada;
import com.nansoft.mipuribus.model.Ruta;

import android.content.Intent;
import android.content.SharedPreferences;
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



		sincronizarDatos();
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
		int VERSION_BASEDATOS_ACTUAL = prefs.getInt("VERSION_BASEDATOS",1);
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
				if (OBJ_VERSION.estadoActualizarBaseDatos()) {

					// si es así verificamos si hay internet
					if (Util.isNetworkAvailable(getApplicationContext())) {

						includedLayout.setVisibility(View.GONE);
						mSwipeRefreshLayout.setEnabled(false);

						new AsyncTask<Void, Void, Boolean>() {

							MobileServiceSyncTable<Ruta> rutaTable;
							MobileServiceSyncTable<Horario> horarioTable;
							MobileServiceSyncTable<CarreraRuta> carreraRutaTable;
							MobileServiceSyncTable<parada> paradaTable;

							Query mPullQueryRuta;
							Query mPullQueryHorario;
							Query mPullQueryCarreraRuta;
							Query mPullQueryParada;


							@Override
							protected void onPreExecute() {
								// referencia a las tablas que se va usar
								rutaTable = Util.mClient.getSyncTable("Ruta", Ruta.class);
								horarioTable = Util.mClient.getSyncTable("Horario", Horario.class);
								carreraRutaTable = Util.mClient.getSyncTable("CarreraRuta", CarreraRuta.class);
								paradaTable = Util.mClient.getSyncTable("Parada", parada.class);

								mPullQueryRuta = Util.mClient.getTable(Ruta.class).orderBy("nombre", QueryOrder.Ascending);
								mPullQueryHorario = Util.mClient.getTable("Horario", Horario.class).top(10);
								mPullQueryCarreraRuta = Util.mClient.getTable("CarreraRuta", CarreraRuta.class).top(1000);
								mPullQueryParada = Util.mClient.getTable("Parada", parada.class).top(100);

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
									rutaTable.pull(mPullQueryRuta).get();
									horarioTable.pull(mPullQueryHorario).get();
									carreraRutaTable.pull(mPullQueryCarreraRuta).get();
									paradaTable.pull(mPullQueryParada).get();

									final MobileServiceList<Ruta> resultRuta = rutaTable.read(mPullQueryRuta).get();
									final MobileServiceList<Horario> resultHorario = horarioTable.read(mPullQueryHorario).get();
									final MobileServiceList<CarreraRuta> resultCarreraRuta = carreraRutaTable.read(mPullQueryCarreraRuta).get();
									final MobileServiceList<parada> resultParada = paradaTable.read(mPullQueryParada).get();


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


									for (Horario horario : resultHorario) {
										objHandlerDataBase.InsertarHorario(horario);
									}

									for (CarreraRuta carreraRuta : resultCarreraRuta) {
										objHandlerDataBase.InsertarCarrera(carreraRuta);
									}

									for (parada parada : resultParada) {
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

						if (objHandlerDataBase.VerificarDatosRuta()) {
							objHandlerDataBase.CargarAdapter();
						} else {

							setContentView(R.layout.error);


						}
						mSwipeRefreshLayout.setRefreshing(false);


						mSwipeRefreshLayout.setEnabled(true);
					}
				} else {
					if (objHandlerDataBase.VerificarDatosRuta()) {
						objHandlerDataBase.CargarAdapter();
					} else {

						setContentView(R.layout.error);


					}
					mSwipeRefreshLayout.setRefreshing(false);


					mSwipeRefreshLayout.setEnabled(true);
				}

			}
		});


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
