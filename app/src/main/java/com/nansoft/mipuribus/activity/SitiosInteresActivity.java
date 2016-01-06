package com.nansoft.mipuribus.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.nansoft.mipuribus.adapter.CustomGridView;
import com.nansoft.mipuribus.R;
import com.nansoft.mipuribus.model.SitioInteres;


public class SitiosInteresActivity extends Activity
{
    AdView adView;
    AdRequest adRequestBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitios_interes);
        final CustomGridView adapter = new CustomGridView(SitiosInteresActivity.this);
        GridView grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);

        adapter.add(new SitioInteres(0,"Taxis","24165252","taxi"));
        adapter.add(new SitioInteres(1,"Cruz Roja","24165555","redcross"));
        adapter.add(new SitioInteres(2,"Hospital","24166032","hospital"));
        adapter.add(new SitioInteres(3,"Bomberos","24168018","firedept"));
        adapter.add(new SitioInteres(4,"Policía","24166190","police"));
        adapter.add(new SitioInteres(5,"OIJ","24168900","oij"));
        adapter.add(new SitioInteres(6,"Servicio Civil","24166130","civildept"));
        adapter.add(new SitioInteres(7,"CTP Puriscal","24166130","ctp"));

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {

                if (position == 7)
                {
                    try
                    {
                        String url = "waze://?ll=9.844285,-84.318397&navigate=yes";
                        Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( url ) );
                        startActivity( intent );
                    }
                    catch ( ActivityNotFoundException ex  )
                    {
                        Intent intent =
                                new Intent( Intent.ACTION_VIEW, Uri.parse( "market://details?id=com.waze" ) );
                        startActivity(intent);
                    }
                }
                else
                {
                    Llamar(adapter.getItem(position).numeroTelefono);
                }
            }
        });

        adView = (AdView) findViewById(R.id.adViewAnuncioSitiosInteres);
    }

    @Override
    protected void onResume()
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

    public void Llamar(String pNumeroTelefono)
    {
        try
        {

            Intent Llamada = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + pNumeroTelefono));
            startActivity(Llamada);

        } catch (ActivityNotFoundException activityException) {

            Toast.makeText(getBaseContext(), "Error al realizar la llamada, intente más tarde", Toast.LENGTH_SHORT).show();

        }
    }




}
