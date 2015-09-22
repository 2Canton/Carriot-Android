package com.nansoft.mipuribus.activity;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.windowsazure.mobileservices.ApiJsonOperationCallback;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
import com.nansoft.mipuribus.CollectionSerializer;
import com.nansoft.mipuribus.HandlerDataBase;
import com.nansoft.mipuribus.HttpRequest;
import com.nansoft.mipuribus.Util;
import com.nansoft.mipuribus.adapter.HorarioAdapterListView;
import com.nansoft.mipuribus.NetworkUtil;
import com.nansoft.mipuribus.R;
import com.nansoft.mipuribus.model.CarreraRuta;
import com.nansoft.mipuribus.model.Horario;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.nansoft.mipuribus.model.Parameter;
import com.nansoft.mipuribus.model.Ruta;

import org.json.JSONArray;
import org.json.JSONObject;

public class HorariosActivity extends Activity //implements ConnectivityObserver
{
	public static HorarioAdapterListView mAdapter;
	public static ListView listViewMaterias;
	public static HandlerDataBase objHandlerDataBase ;
	static Bundle bundle;
    public static SwipeRefreshLayout mSwipeRefreshLayout;

	// layout de error
	View includedLayout;
	
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

		includedLayout = findViewById(R.id.sindatos);

        mAdapter = new HorarioAdapterListView(this, R.layout.item_ruta);
		listViewMaterias = (ListView) findViewById(R.id.lstvLista);
		
		listViewMaterias.setAdapter(mAdapter);
		listViewMaterias.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Horario objHorario = (Horario) parent.getItemAtPosition(position);

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

				cargarHorarios();
			}

		});
		mSwipeRefreshLayout.post(new Runnable() {
			@Override
			public void run() {
				mSwipeRefreshLayout.setRefreshing(true);
			}
		});



		cargarHorarios();


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


	private void cargarHorarios()
	{
		includedLayout.setVisibility(View.GONE);
		mSwipeRefreshLayout.setEnabled(false);

		new AsyncTask<Void, Void, Boolean>() {

			MobileServiceClient mClient;
			List<Pair<String, String>> parameters;

			@Override
			protected void onPreExecute()
			{
				try {

					mClient = new MobileServiceClient(
							Util.UrlMobileServices,
							Util.LlaveMobileServices,
							getApplicationContext()
					);

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

					// se obtiene la respuesta del contenido

					final String response = HttpRequest.get(Util.UrlHorarioRuta, true, "id", bundle.getString("idRuta")).body();

					final Gson objJson = new Gson();

					JsonParser jsonParser = new JsonParser();
					final JsonArray jsonObject = (JsonArray) jsonParser.parse(response);

					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Horario objHorario;
							if (jsonObject.isJsonArray())
							{
								JsonArray jsonArray = jsonObject.getAsJsonArray();

								for(JsonElement element : jsonArray)
								{
									objHorario = new Horario();

									objHorario = objJson.fromJson(element,Horario.class);


									mAdapter.add(objHorario);
									mAdapter.notifyDataSetChanged();
								}
							}


						}
					});

					return true;
				} catch (final Exception exception) {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(getApplicationContext(), "Ha ocurrido un error al cargar, intenta de nuevo", Toast.LENGTH_SHORT).show();

						}
					});

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
