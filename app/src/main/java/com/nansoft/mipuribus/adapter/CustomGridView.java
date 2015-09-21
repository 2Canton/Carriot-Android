package com.nansoft.mipuribus.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nansoft.mipuribus.R;
import com.nansoft.mipuribus.model.SitioInteres;

/**
 * Created by Carlos on 03/04/2015.
 */
public class CustomGridView extends ArrayAdapter<SitioInteres>
{
    ViewHolder holder;
    Context mContext;
    Resources res;

    public CustomGridView(Context context)
    {
        super(context, R.layout.itemgrid);
        mContext = context;
        res = this.mContext.getResources();
    }

    // regresa la vista de cada elemento de la lista
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        View row = convertView;
        final ViewHolder holder;
        final SitioInteres currentItem = getItem(position);

        // verificamos si la fila que se va dibujar no existe
        if (row == null)
        {
            // si es así la creamos
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.itemgrid, parent, false);
            holder = new ViewHolder();

        }
        else
        {
            // en caso contrario la recuperamos
            holder = (ViewHolder) convertView.getTag();
        }

        holder.imgLogoDepartamento = (ImageView) row.findViewById(R.id.imgvDepartamento);
        holder.imgLogoDepartamento.setImageResource(res.getIdentifier(currentItem.getUrlImagen(),
                "drawable", mContext.getPackageName()));

        holder.txtvTituloDepartamento = (TextView) row.findViewById(R.id.txtvNombreDepartamento);
        holder.txtvTituloDepartamento.setText(currentItem.getNombreSitioInteres());

        row.setTag(holder);
        return row;

    }

    // guarda el estado de cada vista la primera vez que se dibuja
    static class ViewHolder
    {
        protected ImageView imgLogoDepartamento;
        protected TextView txtvTituloDepartamento;
    }


}