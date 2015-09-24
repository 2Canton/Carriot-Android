package com.nansoft.mipuribus.activity;

import java.util.List;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.microsoft.windowsazure.mobileservices.table.query.Query;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncTable;
import com.nansoft.mipuribus.database.HandlerDataBase;
import com.nansoft.mipuribus.helper.Util;
import com.nansoft.mipuribus.adapter.HorarioAdapterListView;
import com.nansoft.mipuribus.R;
import com.nansoft.mipuribus.model.CarreraRuta;
import com.nansoft.mipuribus.model.Horario;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class HorariosActivity extends Activity
{
	public static HorarioAdapterListView mAdapter;
	public static ListView listViewMaterias;
	static Bundle bundle;
    public static SwipeRefreshLayout mSwipeRefreshLayout;

	// layout de error
	View includedLayout;

	HandlerDataBase objHandlerDatabase;
	
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
				intent.putExtra("idRuta", bundle.getString("idRuta"));
				intent.putExtra("nombreRuta", bundle.getString("nombreRuta"));

				startActivity(intent);


			}

		});
		objHandlerDatabase = new HandlerDataBase(this);
		mAdapter.clear();
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

		Util objUtil = new Util(getApplicationContext());
		if(!objUtil.inicializarBaseDatos())
			Toast.makeText(getApplicationContext(),"Ha ocurrido un error al inicializar la copia local",Toast.LENGTH_SHORT).show();


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
		objHandlerDatabase.CargarHorariosRuta(bundle.getString("idRuta"));

		mSwipeRefreshLayout.post(new Runnable() {
			@Override
			public void run() {
				mSwipeRefreshLayout.setRefreshing(false);
			}
		});
	}

}
