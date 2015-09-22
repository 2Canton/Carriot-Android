package com.nansoft.mipuribus.helper;

import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.nansoft.mipuribus.model.Horario;
import com.nansoft.mipuribus.model.Ruta;

/**
 * Created by Carlos on 21/09/2015.
 */
public class Util
{
    public static String UrlMobileServices = "https://puriscal.azure-mobile.net/";
    public static String UrlHorarioRuta = "https://puriscal.azure-mobile.net/api/horarioruta";
    public static String LlaveMobileServices = "CtavDeXtaLeUclXFhrPrjLJiUeeEek84";

    public static MobileServiceList<Ruta> resultRutas;
    public static MobileServiceList<Horario> resultHorarios;

}
