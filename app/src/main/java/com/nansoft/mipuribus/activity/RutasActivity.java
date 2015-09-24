package com.nansoft.mipuribus.activity;

import java.net.MalformedURLException;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.SimpleSyncHandler;
import com.nansoft.mipuribus.R;
import com.nansoft.mipuribus.helper.Util;
import com.nansoft.mipuribus.adapter.RutaAdapterListView;
import com.nansoft.mipuribus.model.Ruta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Map;

import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.table.query.Query;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncTable;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
public class RutasActivity extends Activity
{	
	public static RutaAdapterListView mAdapter;
	
	ListView listViewMaterias;
    public static SwipeRefreshLayout mSwipeRefreshLayout;

    AdView adView;
    AdRequest adRequestBanner;

	// layout de error
	View includedLayout;


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

				cargarRutas();
			}

		});


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



		cargarRutas();
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



	private void cargarRutas()
	{
		if (Util.isNetworkAvailable(getApplicationContext())) {

			includedLayout.setVisibility(View.GONE);
			mSwipeRefreshLayout.setEnabled(false);

			new AsyncTask<Void, Void, Boolean>() {


				MobileServiceSyncTable<Ruta> rutaTable;
				Query mPullQuery;

				@Override
				protected void onPreExecute() {




						// referencia a la tabla que se va usar
						rutaTable = Util.mClient.getSyncTable("Ruta", Ruta.class);
						mPullQuery = Util.mClient.getTable(Ruta.class).orderBy("nombre", QueryOrder.Ascending);
						mAdapter.clear();




				}

				@Override
				protected Boolean doInBackground(Void... params) {
					try {
						//se cargan los últimos cambios
						rutaTable.pull(mPullQuery).get();

						final MobileServiceList<Ruta> result = rutaTable.read(mPullQuery).get();

						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								for (Ruta item : result) {
									mAdapter.add(item);
									mAdapter.notifyDataSetChanged();
								}

							}
						});


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
			Toast.makeText(getApplicationContext(),"No hay conexión a internet, se trabajará con una copia local",Toast.LENGTH_SHORT).show();
		}
	}



}
