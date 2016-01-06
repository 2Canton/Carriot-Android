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
import com.nansoft.mipuribus.model.Ruta;

public class RutaAdapterListView extends ArrayAdapter<Ruta>
{

	int mLayoutResourceId;
	ViewHolder holder;
	Context mContext;
    String rutaImagen;
    Resources res;
	
	public RutaAdapterListView(Context context, int layoutResourceId) 
	{
		super(context, layoutResourceId);
		mContext = context;
		mLayoutResourceId = layoutResourceId;
        res = this.mContext.getResources();
	}
	
	// regresa la vista de cada elemento de la lista
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{	
 
		View row = convertView;
		final ViewHolder holder;
		final Ruta currentItem = getItem(position);
		
		// verificamos si la fila que se va dibujar no existe
		if (row == null) 
		{
			// si es así la creamos
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			row = inflater.inflate(mLayoutResourceId, parent, false);
			holder = new ViewHolder();
			
		}
		else
		{
			// en caso contrario la recuperamos
			holder = (ViewHolder) convertView.getTag();
		}

        rutaImagen = "bus";
        switch(currentItem.idEmpresa)
        {
            case "0":
                rutaImagen += "2";
                break;

            case "1":
                rutaImagen += "1";
                break;

            case "2":
                rutaImagen += "2";
                break;

            case "3":
                rutaImagen += "3";
                break;

            case "4":
                rutaImagen += "4";
                break;

            case "5":
                rutaImagen += "5";
                break;

            case "6":
                rutaImagen += "6";
                break;

            default:
                break;
        }

        holder.imgLogoEmpresaa = (ImageView) row.findViewById(R.id.imgvLogoEmpresaRuta);
        holder.imgLogoEmpresaa.setImageResource(res.getIdentifier(rutaImagen,
                "drawable", mContext.getPackageName()));
		
		holder.txtvTituloRuta = (TextView) row.findViewById(R.id.txtvTituloRuta);
		holder.txtvTituloRuta.setText(currentItem.nombre);
		
		holder.txtvCostoRuta = (TextView) row.findViewById(R.id.txtvCostoRuta);
		holder.txtvCostoRuta.setText("Precio " + currentItem.costo);
		
		
		row.setTag(holder);
		return row;
		 
	}
		
	// guarda el estado de cada vista la primera vez que se dibuja
	static class ViewHolder
	{   
		protected ImageView imgLogoEmpresaa; 
		protected TextView txtvTituloRuta;
		protected TextView txtvCostoRuta;
	} 
	
	
}