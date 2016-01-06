package com.nansoft.mipuribus.activity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.nansoft.mipuribus.database.HelperDatabase;
import com.nansoft.mipuribus.helper.Util;
import com.nansoft.mipuribus.adapter.HorarioAdapterListView;
import com.nansoft.mipuribus.R;
import com.nansoft.mipuribus.model.Horario;

import android.content.Intent;
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

public class HorariosActivity extends AppCompatActivity
{
	public static HorarioAdapterListView mAdapter;
	public static ListView listViewMaterias;
	static Bundle bundle;
    public static SwipeRefreshLayout mSwipeRefreshLayout;

	// layout de error
	View includedLayout;

	HelperDatabase objHandlerDatabase;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);

		// Set up action bar.
		ActionBar bar = getSupportActionBar();
		bar.show();
		bar.setDisplayHomeAsUpEnabled(true);

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
				intent.putExtra("idHorario", objHorario.id);
				intent.putExtra("dias", objHorario.dias);
				intent.putExtra("idRuta", bundle.getString("idRuta"));
				intent.putExtra("nombreRuta", bundle.getString("nombreRuta"));

				startActivity(intent);


			}

		});
		objHandlerDatabase = new HelperDatabase(this);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_horarios, menu);
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
