package com.nansoft.mipuribus.activity;

import java.net.MalformedURLException;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.microsoft.windowsazure.mobileservices.ApiJsonOperationCallback;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
import com.nansoft.mipuribus.HandlerDataBase;
import com.nansoft.mipuribus.NetworkUtil;
import com.nansoft.mipuribus.R;
import com.nansoft.mipuribus.Util;
import com.nansoft.mipuribus.adapter.RutaAdapterListView;
import com.nansoft.mipuribus.model.CarreraRuta;
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

		cargarRutas();
        adView = (AdView) findViewById(R.id.adViewAnuncio);

		mSwipeRefreshLayout.post(new Runnable() {
			@Override
			public void run() {
				mSwipeRefreshLayout.setRefreshing(true);
			}
		});


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
		includedLayout.setVisibility(View.GONE);
		mSwipeRefreshLayout.setEnabled(false);

		new AsyncTask<Void, Void, Boolean>() {

			MobileServiceClient mClient;
			MobileServiceTable<Ruta> rutaTable;

			@Override
			protected void onPreExecute()
			{
				try {

					mClient = new MobileServiceClient(
							Util.UrlMobileServices,
							Util.LlaveMobileServices,
							getApplicationContext()
					);

					rutaTable = mClient.getTable("Ruta", Ruta.class);

					mAdapter.clear();

				} catch (MalformedURLException e) {

				}
				catch (Exception e)
				{

				}

			}

			@Override
			protected Boolean doInBackground(Void... params) {
				try {

					final MobileServiceList<Ruta> result = rutaTable.orderBy("nombre", QueryOrder.Ascending).execute().get();
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							for (Ruta item : result)
							{
								mAdapter.add(item);
								mAdapter.notifyDataSetChanged();
							}

						}
					});

					return true;
				} catch (Exception exception) {

				}
				return false;
			}

			@Override
			protected void onPostExecute(Boolean success)
			{

				mSwipeRefreshLayout.setRefreshing(false);


				mSwipeRefreshLayout.setEnabled(true);

				if (!success)
				{
					includedLayout.setVisibility(View.VISIBLE);
				}
				else
				{
					includedLayout.setVisibility(View.GONE);
				}
			}

			@Override
			protected void onCancelled()
			{
				super.onCancelled();
			}
		}.execute();
	}
	
	   
}
