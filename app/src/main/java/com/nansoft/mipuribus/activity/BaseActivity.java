package com.nansoft.mipuribus.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by itadmin on 3/13/16.
 */
public class BaseActivity extends AppCompatActivity
{
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


}
