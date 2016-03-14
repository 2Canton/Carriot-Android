package com.nansoft.mipuribus.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nansoft.mipuribus.R;
import com.nansoft.mipuribus.helper.Util;
import com.nansoft.mipuribus.model.Reporte;

public class ContactoActivity extends BaseActivity implements View.OnClickListener
{

    Spinner spnrTipoReporte;
    EditText edtDescripcion;
    Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        enableHomeActionBar();

        setTitle("Contacto");

        util = new Util(getApplicationContext());

        // obtenemos la referencia del spinner
        spnrTipoReporte = (Spinner) findViewById(R.id.spnrTipoReporte);

        // obtenemos la referencia del edit text
        edtDescripcion = (EditText) findViewById(R.id.edtDescripcion);

        // se establecen los onclick listener de los botones
        findViewById(R.id.btnEnviar).setOnClickListener(this);
        findViewById(R.id.btnRestablecer).setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        // según el item seleccionado se debe realizar una acción
        switch (view.getId())
        {
            case R.id.btnEnviar:
                String descripcionReporte = edtDescripcion.getText().toString();

                // se verifica si hay texto
                if (!descripcionReporte.isEmpty())
                {
                    // se envía el objeto de tipo reporte, devuleve true o false
                    boolean reportStatus = util.addReport(new Reporte(descripcionReporte,String.valueOf(spnrTipoReporte.getSelectedItemPosition())));

                    // según el resultado se establece un valor a mostrar
                    String mensajeMostrar = reportStatus ? "Reporte enviado, gracias por sus comentarios":"Ha ocurrido un error al enviar el reporte, intente de nuevo";

                    Toast.makeText(getApplicationContext(),mensajeMostrar,Toast.LENGTH_SHORT).show();

                    // se verifica si se agregó exitosamente
                    if (reportStatus)
                    {
                        Restablecer();
                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Debe ingresar la descripción",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnRestablecer:
                Restablecer();
                break;

            default:
        }
    }


    private void Restablecer()
    {
        edtDescripcion.setText("");
        spnrTipoReporte.setSelection(0);
    }

}
