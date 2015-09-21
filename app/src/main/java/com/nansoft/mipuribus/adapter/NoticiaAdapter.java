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
import com.nansoft.mipuribus.model.Noticia;

/**
 * Created by Carlos on 19/08/2015.
 */
public class NoticiaAdapter extends ArrayAdapter<Noticia> {

    int mLayoutResourceId;
    ViewHolder holder;
    Context mContext;

    public NoticiaAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        mContext = context;
        mLayoutResourceId = layoutResourceId;

    }

    // regresa la vista de cada elemento de la lista
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        final Noticia currentItem = getItem(position);


        // verificamos si la fila que se va dibujar no existe
        if (row == null) {
            // si es así la creamos
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
            ViewHolder holder = new ViewHolder();

            holder.imgLogo = (ImageView) row.findViewById(R.id.imgvLogoNoticia);
            holder.txtvTitulo = (TextView) row.findViewById(R.id.txtvTituloNoticia);
            holder.txtvFecha = (TextView) row.findViewById(R.id.txtvFechaNoticia);
            holder.txtvDescripcion = (TextView) row.findViewById(R.id.txtvDescripcionNoticia);
            holder.txtvAutor = (TextView) row.findViewById(R.id.txtvAutorNoticia);

            row.setTag(holder);

        }
        // en caso contrario la recuperamos
        ViewHolder holder = (ViewHolder) row.getTag();

        Glide.with(mContext)
                .load(currentItem.getUrlImagen().trim())
                .asBitmap()
                .fitCenter()
                .placeholder(R.drawable.picture)
                .error(R.drawable.picture_removed)
                .into(holder.imgLogo);

        holder.txtvTitulo.setText(currentItem.getNombre());
        holder.txtvFecha.setText(currentItem.getFecha());
        holder.txtvDescripcion.setText(currentItem.getDescripcion());
        holder.txtvAutor.setText("Autor: " + currentItem.getAutor());


        return row;

    }

    // guarda el estado de cada vista la primera vez que se dibuja
    static class ViewHolder {
        protected ImageView imgLogo;
        protected TextView txtvTitulo;
        protected TextView txtvFecha;
        protected TextView txtvDescripcion;
        protected TextView txtvAutor;
    }


}
