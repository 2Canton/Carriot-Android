package com.nansoft.mipuribus.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nansoft.mipuribus.R;
import com.nansoft.mipuribus.model.TipoEmpresa;

/**
 * Created by Carlos on 19/08/2015.
 */
public class TipoEmpresaAdapter extends ArrayAdapter<TipoEmpresa>
{

    int mLayoutResourceId;
    ViewHolder holder;
    Context mContext;

    public TipoEmpresaAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        mContext = context;
        mLayoutResourceId = layoutResourceId;

    }

    // regresa la vista de cada elemento de la lista
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        final TipoEmpresa currentItem = getItem(position);


        // verificamos si la fila que se va dibujar no existe
        if (row == null) {
            // si es así la creamos
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
            ViewHolder holder = new ViewHolder();

            holder.imgLogoEmpresaa = (ImageView) row.findViewById(R.id.imgvLogoEmpresaRuta);
            holder.txtvTituloRuta = (TextView) row.findViewById(R.id.txtvTituloRuta);

            row.setTag(holder);

        }
        // en caso contrario la recuperamos
        ViewHolder holder = (ViewHolder) row.getTag();

        Glide.with(mContext)
                .load(currentItem.getUrlimagen().trim())
                .asBitmap()
                .fitCenter()
                .placeholder(R.drawable.picture)
                .error(R.drawable.picture_removed)
                .into(holder.imgLogoEmpresaa);


        holder.txtvTituloRuta.setText(currentItem.getNombre());

        //holder.txtvCostoRuta = (TextView) row.findViewById(R.id.txtvCostoRuta);
        //holder.txtvCostoRuta.setText("Precio " + currentItem.getCosto());

        return row;

    }

    // guarda el estado de cada vista la primera vez que se dibuja
    static class ViewHolder {
        protected ImageView imgLogoEmpresaa;
        protected TextView txtvTituloRuta;
        //protected TextView txtvCostoRuta;
    }


}