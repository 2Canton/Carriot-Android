package com.nansoft.mipuribus.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.nansoft.mipuribus.R;
import com.nansoft.mipuribus.database.HelperDatabase;
import com.nansoft.mipuribus.model.CarreraRuta;

import static com.wagnerandade.coollection.Coollection.*;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DescHoraActivity extends AppCompatActivity
{

	TableLayout tabla;
	TableLayout cabecera;
	TableRow.LayoutParams layoutFila;
	TableRow.LayoutParams layoutTextView;

	Resources rs;

	List<CarreraRuta> listCarreras;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layinforuta);

		// Set up action bar.
		ActionBar bar = getSupportActionBar();
		bar.show();
		bar.setDisplayHomeAsUpEnabled(true);

		Bundle bundle = getIntent().getExtras();
		setTitle(bundle.getString("nombreRuta") + " - " + bundle.getString("dias"));

		rs = this.getResources();
		tabla = (TableLayout)findViewById(R.id.BodyTable);
		layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT);

		layoutTextView = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT,1f);

		listCarreras = new ArrayList<CarreraRuta>();

		HelperDatabase objHandlerDatabase = new HelperDatabase(this);

		ArrayList<CarreraRuta> ordered = objHandlerDatabase.CargarHorario(bundle.getString("idRuta"), bundle.getString("idHorario"));

		if (ordered.isEmpty())
		{
			setContentView(R.layout.error);
		}
		else
		{

			listCarreras = from(ordered).orderBy("getDescHora").all();
			agregarFilasTabla();
		}

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
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_deschora, menu);
        return true;
    }
	*/

	public void agregarFilasTabla()
	{

		TableRow fila;
		TextView txtvSitioSalida;
		TextView txtvHora;
		TextView txtvNota;

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
		Date date = null;
		String sufijo = "";
		int intHoraTemporal = 0;
		for(int i = 0,TamArray = listCarreras.size();i<TamArray;i++)
		{

			fila = new TableRow(this);

			fila.setLayoutParams(layoutFila);

			txtvSitioSalida = new TextView(this);
			txtvHora = new TextView(this);
			txtvNota = new TextView(this);

			txtvSitioSalida.setText(listCarreras.get(i).nombreSitioSalida);
			txtvSitioSalida.setTextAppearance(this,R.style.BodyText);
			txtvSitioSalida.setGravity(Gravity.CENTER);
			txtvSitioSalida.setLayoutParams(layoutTextView);
			txtvSitioSalida.setTextColor(getResources().getColor(R.color.black));
			txtvSitioSalida.setBackgroundResource(R.drawable.border);


			try
			{
				date = sdf.parse(listCarreras.get(i).descHora);
			}
			catch (ParseException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			sufijo = " am";
			intHoraTemporal = Integer.parseInt(listCarreras.get(i).descHora
					.substring(0, 2));


			if (intHoraTemporal == 12)
			{
				sufijo = " md";
			}
			else if (intHoraTemporal > 12)
			{
				sufijo = " pm";
			}

			if (intHoraTemporal == 0)
			{
				txtvHora.setText(" no hay bus");
			}
			else {
				txtvHora.setText(sdf.format(date) + sufijo);
			}
			txtvHora.setTextAppearance(this,R.style.BodyText);
			txtvHora.setGravity(Gravity.CENTER);
			txtvHora.setLayoutParams(layoutTextView);
			txtvHora.setTextColor(getResources().getColor(R.color.black));
			txtvHora.setBackgroundResource(R.drawable.border);
			// layoutTextView.span = 2;

			txtvNota.setText(listCarreras.get(i).nota);
			txtvNota.setTextAppearance(this, R.style.BodyText);
			txtvNota.setGravity(Gravity.CENTER);
			txtvNota.setLayoutParams(layoutTextView);
			txtvNota.setTextColor(getResources().getColor(R.color.black));
			txtvNota.setBackgroundResource(R.drawable.border);

			fila.addView(txtvSitioSalida);
			fila.addView(txtvHora);
			fila.addView(txtvNota);

			if( i%2 == 0)
			{
				fila.setBackgroundResource(R.color.blanco);
			}
			else
			{
				fila.setBackgroundResource(R.color.azure);
			}

			tabla.addView(fila);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_deschora, menu);
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
