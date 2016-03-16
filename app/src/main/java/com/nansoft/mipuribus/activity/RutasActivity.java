package com.nansoft.mipuribus.activity;

import com.microsoft.applicationinsights.library.ApplicationInsights;
import com.microsoft.applicationinsights.library.TelemetryClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
import com.nansoft.mipuribus.R;
import com.nansoft.mipuribus.database.HelperDatabase;
import com.nansoft.mipuribus.helper.Util;
import com.nansoft.mipuribus.adapter.RutaAdapterListView;
import com.nansoft.mipuribus.model.CarreraRuta;
import com.nansoft.mipuribus.model.Horario;
import com.nansoft.mipuribus.model.Parada;
import com.nansoft.mipuribus.model.Ruta;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.table.query.Query;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncTable;

public class RutasActivity extends BaseActivity
{	
	public static RutaAdapterListView mAdapter;
	
	ListView listViewMaterias;

	HelperDatabase objHandlerDataBase;

	MobileServiceSyncTable<Ruta> rutaTable;
	MobileServiceSyncTable<Horario> horarioTable;
	MobileServiceSyncTable<CarreraRuta> carreraRutaTable;
	MobileServiceSyncTable<Parada> paradaTable;

	Query mPullQueryRuta;
	Query mPullQueryHorario;
	Query mPullQueryCarreraRuta;
	Query mPullQueryParada;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);

		enableHomeActionBar();

		// aplication insights
		ApplicationInsights.setup(this.getApplicationContext(), this.getApplication());
		ApplicationInsights.start();

		setTitle("Rutas");

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

				TelemetryClient client = TelemetryClient.getInstance();
				client.trackPageView("Ruta: " + objRuta.nombre);

				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

			}
		});
		mAdapter.clear();
        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {

				syncAsync();
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


		// se obtiene la referencia a las tablas
		rutaTable = Util.mClient.getSyncTable("Ruta", Ruta.class);
		horarioTable = Util.mClient.getSyncTable("Horario", Horario.class);
		carreraRutaTable = Util.mClient.getSyncTable("CarreraRuta", CarreraRuta.class);
		paradaTable = Util.mClient.getSyncTable("Parada", Parada.class);

		// query de las tablas
		mPullQueryRuta = Util.mClient.getTable(Ruta.class).orderBy("nombre", QueryOrder.Ascending);
		mPullQueryHorario = Util.mClient.getTable(Horario.class).orderBy("id", QueryOrder.Ascending);
		mPullQueryCarreraRuta = Util.mClient.getTable(CarreraRuta.class).orderBy("idruta", QueryOrder.Ascending);
		mPullQueryParada = Util.mClient.getTable(Parada.class).orderBy("nombre", QueryOrder.Ascending);



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
			new AsyncTask<Void, Void, Boolean>() {

				@Override
				protected Boolean doInBackground(Void... params) {
					try {
						Util.mClient.getSyncContext().push().get();

						rutaTable.pull(mPullQueryRuta).get();
						horarioTable.pull(mPullQueryHorario).get();
						carreraRutaTable.pull(mPullQueryCarreraRuta).get();
						paradaTable.pull(mPullQueryParada).get();

						final MobileServiceList<Ruta> resultRuta = rutaTable.read(mPullQueryRuta).get();
						final MobileServiceList<Horario> resultHorario = horarioTable.read(mPullQueryHorario).get();
						final MobileServiceList<CarreraRuta> resultCarreraRuta = carreraRutaTable.read(mPullQueryCarreraRuta).get();
						final MobileServiceList<Parada> resultParada = paradaTable.read(mPullQueryParada).get();

						// una vez que se obtienen los datos se limpia la base de datos
						HelperDatabase.db.delete("Ruta", null, null);
						HelperDatabase.db.delete("Horario", null, null);
						HelperDatabase.db.delete("CarreraRuta", null, null);
						HelperDatabase.db.delete("SitioSalida", null, null);

						for (Horario horario : resultHorario) {
							objHandlerDataBase.InsertarHorario(horario);
						}

						for (CarreraRuta carreraRuta : resultCarreraRuta) {
							objHandlerDataBase.InsertarCarrera(carreraRuta);
						}

						for (Parada Parada : resultParada) {
							objHandlerDataBase.InsertarSitioSalida(Parada);
						}

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

						return true;
					} catch (final Exception exception) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {

								Toast.makeText(RutasActivity.this, "Error al sincronizar! " + exception.toString(), Toast.LENGTH_LONG).show();
							}
						});
						return false;
					}
				}

				@Override
				protected void onPostExecute(Boolean success) {

					verificarEstado(success);

				}

			}.execute();
		} else {
			// conexión offline

			// se verifica si hay datos
			if (objHandlerDataBase.VerificarDatosRuta())
			{
				objHandlerDataBase.CargarAdapter();
				verificarEstado(true);
			}
			else
			{
				verificarEstado(false);
			}



		}
	}

	private void verificarEstado(boolean status)
	{
		mSwipeRefreshLayout.setEnabled(true);

		mSwipeRefreshLayout.post(new Runnable() {
			@Override
			public void run() {
				mSwipeRefreshLayout.setRefreshing(false);
			}
		});


		if (status)
		{
			includedLayout.setVisibility(View.GONE);
		}
		else
		{
			includedLayout.setVisibility(View.VISIBLE);
		}



	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_rutas, menu);
		return true;
	}



}
