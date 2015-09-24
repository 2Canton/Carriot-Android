package com.nansoft.mipuribus.activity;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.nansoft.mipuribus.R;


public class HistoriaActivity extends AppCompatActivity {

    AdView adView;
    AdRequest adRequestBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia);

        // Set up action bar.
        ActionBar bar = getSupportActionBar();
        bar.show();
        bar.setDisplayHomeAsUpEnabled(true);

        setTitle(getString(R.string.app_name) + " - Historia");

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView txtvResenia = (TextView) findViewById(R.id.txtvResenia);

        String resenia = "<h1>Puriscal</h1><br>";
        resenia += "<h2>Reseña</h2>";
        resenia += "<p>Puriscal es el cantón 4 de la provincia de San José de Costa Rica. Su capital es la ciudad de Santiago de Puriscal.</p>";

        resenia += "<p>En el cantón de Puriscal se encuentran varios patrimonios históricos nacionales, tales como el Templo de Barbacoas, el Templo de Pedernal y el emblemático Antiguo Templo Católico de Puriscal, decretado como Patrimonio histórico arquitectónico de Costa Rica. Otro monumento emblemático es El Sapo, que está situado en el Parque del Agricultor de Puriscal.</p>";

        resenia += "<p>Antiguamente, la región que hoy corresponde al cantón de Puriscal fue conocida como Cola de Pavo, así denominada por dos comerciantes, Jorge y Jesús Retana, que comerciaban en este lugar cuando en él solo había pocas familias residentes. El nombre del cantón proviene de la evolución de la palabra purisco, la que se refiere al momento en el que el frijol está en flor. Puriscal en sus orígenes fue un lugar conocido por sus sembradíos de frijoles.</p>";
        resenia += "<h2>Historia</h2><br><h3>Periodo precolombino</h3>";

        resenia += "<p>En la época precolombina, el territorio que actualmente corresponde al cantón de Puriscal estuvo habitado por indígenas huetares, que se encontraban distribuidos en diversos reinos. La mayor parte del cantón se encontraba dentro del Reino de Pacaca, que al momento de la conquista era gobernado por el rey Coquiva. Existían otros dos cacicazgos de importancia en territorio de Puriscal: el cacicazgo de Puririse o Puririsí, ubicado donde se encuentra actualmente el centro de la ciudad, y el cacicazgo de Chucasque, que actualmente corresponde a la zona de Chucas de Puriscal. Estos dos cacicazgos eran, a su vez, parte del Señorío de Garabito o Reino Huetar de Occidente. Testimonio de ese hecho son los múltiples hallazgos arqueológicos, principalmente en las localidades actuales de ciudad de Santiago y Mercedes Sur de Barbacoas, las cuales fueron grandes cementerios nativos. Los aborígenes que residían en el lugar eran de espíritu belicoso, y mantenían constantes luchas con sus vecinos de la tribu del cacique Aczarri. Tanto Garabito como un hermano de Coquiva, el príncipe Quizarco, fueron importantes líderes de la resistencia indígena durante la conquista española.</p>";
        resenia += "<p>Desde antes de la llegada de los españoles, Puriscal fue una encrucijada importante. Prueba de ello es que fue lugar de paso de los indígenas que venían del noroeste e iban al suroeste del presente territorio nacional.</p>";

        resenia += "<br><h3>Colonia</h3>";
        resenia += "<p>El conquistador español Juan de Cavallón y Arboleda pasó por Puriscal antes de internarse en el Valle Central y fundar en 1561 la aldea de Garcimuñoz ―que se supone que es la actual Santa Ana―. Durante la Colonia, el camino de mulas que a partir de 1601 se utilizó para comerciar con Panamá, también pasaba por la zona de Puriscal.</p>";
        resenia += "<p>Se estima que la región comenzó a colonizarse a partir de 1815, con familias provenientes en su mayoría de los cantones actuales de Desamparados, Alajuelita, Tibás. Sus apellidos eran Aguilar, Barboza, Chacón, Charpentier (de origen francés), Gómez, Montero, Retana, Salazar, Segura y Valverde.</p>";
        resenia += "<p>La primera ermita se construyó en 1858, en un terreno que donó Pedro Jiménez Meléndez, durante el episcopado de Joaquín Anselmo Llorente y Lafuente, primer obispo de Costa Rica. En el año 1871 se erigió la parroquia, dedicada a Santiago Apóstol; la cual actualmente es sufragánea de la arquidiócesis de San José, de la provincia eclesiástica de Costa Rica.</p>";
        resenia += "<p>El 18 de octubre de 1915, durante el gobierno del presidente Alfredo González Flores, se promulgó la ley n.º 20 sobre división territorial para efectos administrativos, que le otorgó a Puriscal el título de «villa». Posteriormente el 20 de julio de 1926, en la segunda administración del presidente Ricardo Jiménez Oreamuno se decretó la ley n.º 40, que le confirió a la villa la categoría de ciudad.</p>";
        resenia += "<p>En 1886 funcionó en Santiago una escuela para niñas, en 1891 se inauguró una escuela de música financiada por vecinos del cantón. La actual escuela Darío Flores Hernández se construyó en 1900, en el segundo gobierno del presidente Rafael Iglesias Castro. En el año 1945 se instaló la Escuela Complementaria, de enseñanza secundaria, que ocupó parte de las instalaciones de la escuela Darío Flores Hernández. El 8 de febrero de 1953 se inauguró el edificio de la Escuela Complementaria, en la administración de Otilio Ulate Blanco; tres años después se comenzó a llamar Liceo de Puriscal.</p>";
        resenia += "<p>El agua de la primera cañería traída por gravedad desde Mercedes Sur, fue instalada por un italiano, allá por los años de 1920 a 1923. En 1934, se construyó una nueva cañería, en el tercer gobierno del presidente Ricardo Jiménez Oreamuno.</p>";
        resenia += "<p>El alumbrado público, eléctrico, se inauguró el 25 de julio de 1926, en la segunda administración de Ricardo Jiménez Oreamuno.</p>";
        resenia += "<br><h2>División política</h2><br>";
        resenia += "<ol><li>Santiago</li><li>Mercedes Sur</li><li>Barbacoas</li><li>Grifo Alto</li><li>San Rafael</li><li>Candelarita</li><li>Desamparaditos</li><li>San Antonio</li><li>Chires</li></ol><br>";

        resenia += "<h2>Flora y fauna</h2><p>Puriscal presenta un clima apropiado para la producción de diversas actividades agrícolas, tiene dos estaciones claramente definidas, invierno; la cual va desde junio a noviembre y el verano que va desde diciembre y finaliza en mayo.</p>";
        resenia += "<p>El tipo de bosque es montano bajo. Las especies de flora son 1.000, de las cuales 44 son endémicas, siendo las más características el cocobolo, ron ron, nazareno, ajo, pochote, roble de sabana, botarrrama, cachimbo, caobilla, cedro amargo, cristóbal, cenizaro, espavel, fruta dorada, guachipelín, madero negro y mastate. En el cantón de Puriscal se pueden encontrar varias especies endémicas, o sea, que no se encuentran en otras partes del mundo, ya que en Puriscal se da el clima ideal para su proliferación. Un ejemplo es el Plinia puriscalensis, el cual es un árbol que se da en los alrededores de los ríos y las montañas del Parque Nacional La Cangreja, ubicado en el distrito de Chires. Plinia puriscalensis da sus frutos en el tronco (similar al fruto del cacao). Este fruto tiene forma redonda y un sabor similar a la guayaba.</p>";
        resenia += "<p>En cuanto la fauna, debido al clima se presentan diversos tipos de aves y mamíferos, tales como lapas rojas (Ara macao), yigüirros (Turdus grayi) y oropéndolas (Psaracolius montezuma) en época de invierno, y mamíferos tales como pizotes (Nasua narica), tepezcuintles (Agouti paca).</p>";
        resenia += "<br><h3>Parque Nacional La Cangreja</h3>";
        resenia += "<p>Ubicado en el sureste del cantón de Puriscal, en los distritos Mercedes Sur y Chires. El Parque se localiza cerca del pueblo de Mastatal, a 45 kilómetros sur de Santiago de Puriscal. Se sigue la antigua carretera de lastre a Parrita, y luego desviándose al este (hay señalización). Para su ingreso es recomendado el uso de vehículo de doble tracción en la época lluviosa, pues la vía es en su mayor parte de lastre y barro.</p>";
        resenia += "<p>Fue creado por medio de la Ley No. 6975 Art. 93 03-12-84 Creación Decreto No. 17455-MAG 31-03-87 (Zona Protectora), y nombrado parque nacional en 2002. Tiene una extensión de 2.240 hectáreas.</p>";
        resenia += "<p>A pesar de su tamaño relativamente pequeño, La Cangreja es un refugio de aves migratorias y posee una flora más rica que la del Parque Nacional Corcovado. Es el único sitio en el mundo donde existe el árbol Plinia puriscalensis, que da el fruto pegado al tronco. Además protege el último reducto de bosque de Puriscal.</p>";
        resenia += "<br><h4>Antecedentes</h4>";
        resenia += "<p>El nombre La Cangreja, se refiere a la forma característica del Cerro La Cangreja de 1.305 metros de altura, que domina el Parque. El cerro La Cangreja, al verlo desde arriba da, la apariencia de estar mirando un cangrejo, y las lomas que salen hacia los lados constituirían las patas del mismo, por otra parte la historia indígena habla de un gran Cangrejo que en tiempos remotos se estableció en el cerro e impedía el paso de los lugareños hacia las otras aldeas, hasta que en un momento un valeroso guerrero luchó contra él y logró cortarle una pata lo que desató su rabia y al verse vencido decidió convertirse en piedra, por eso ahora la parte alta del cerro es una formación rocosa.</p>";
        resenia += "<br><h4>Características</h4>";
        resenia += "<p>Es un reducto de bosque de transición entre zonas secas y lluviosas en medio de una zona erosionada. Tiene dos zonas de vida: bosque muy húmedo transición a premontano y bosque pluvial premontano. La precipitación promedio anual es de 2.700 mm y la altitud mínima de 350 msnm y la máxima de 1.305 msnm.</p>";

        resenia += "<br><h2>Arquitectura</h2><br><h3>Antiguo Templo Católico</h3>";
         resenia += "<p>El antiguo templo católico de Puriscal es uno de los edificios simbólicos del cantón de Puriscal. En el aspecto arquitectónico, el edificio tiene la forma de una gran cruz, donde la nave central mide 50 metros de largo y la transversal 28 de ancho. Sin embargo, la distancia de columna a columna en la nave central, es solo de 18 metros, lo que la hace reducida. La altura de las paredes hasta el techo es de 25 metros y las torres alcanzaran una altura de 35 metros. Las cerchas son de hierro y por tanto muy pesadas. El cielorraso es de cedro caoba.</p>";

        resenia += "<p>Su construcción inició entre 1936 y 1937, con el objetivo de substituir otro templo en mal estado, levantado en el mismo sitio. El cura párroco era el Presbítero Recaredo Rodríguez de Guadalupe, había llegado en 1932 y estuvo hasta 1939, pero la construcción no finalizó hasta 1965, durante el servicio de los curas párrocos Rafael Vargas Vargas (entre 1939-1959) y Jorge Calvo Robles (desde 1959 y hasta la inauguración). Con el fin de reunir los fondos necesarios para la construcción, se nombró una Junta Edificadora y se pidió la ayuda de la feligresía, tomando como principal fuente de ingresos los festejos patronales y turnos.</p>";

        resenia += "<p>En 1936, una vez reunido el dinero necesario, se encargó el diseño del plano al arquitecto y pintor Teodorico Quirós, y la construcción al ingeniero Jacinto Rodríguez. El maestro de obras fue Raúl Cascante. Para esos tiempos no había ningún edificio de cemento en Puriscal, pero sí había mucha madera de excelente calidad. Sin embargo, como afirmó un poblador de Puriscal, se decidió hacerlo en concreto «para que vengan a este templo las futuras generaciones». Cuando se decidió construir el templo en concreto, se pensó en que las donaciones que realizaran la mayoría de los pobladores serían en especie, las cuales requerían un proceso de industrialización, por lo que se pensó que había que procesar madera, arroz y quebrar piedra. Precisamente, la construcción del templo de Puriscal se caracterizó por las donaciones y voluntariados de los habitantes del cantón: se enviaban diariamente entre 15 y 20 personas para ayudar en la edificación, que incluyó la muerte accidental de uno de los trabajadores. Entre las donaciones más destacadas, se pueden citar las contribuciones de Juan Mora Cordero, quien donó un aserradero, un quebrador de piedra, una cepilladura de madera y una descascaradora de arroz, maquinaría que estuvo durante muchos años al servicio de los puriscaleños. Con la intención de no interrumpir las labores de la iglesia, el templo de cemento se fue construyendo alrededor de la iglesia de madera, que no fue demolida sino hasta que la construcción estuvo terminada.</p>";

        resenia += "<p>Otras donaciones importantes fueron: con el dinero obtenido de un turno, se mandó a traer el altar de mármol de Italia. Lo armó Edwin Villalta. Las bancas las donaron los tabacaleros. También se logró adquirir con fondos propios la pila bautismal y las barandas, también de mármol. La empresa UTRASA, actualmente conocida como Comtrasuli, donó el púlpito de mármol. El cielorraso fue colocado en el año 1956, todo en cedro caoba, para lo cual se construyeron los andamios con un costo de 4000 colones. Parte de los ventanales fueron donados por Juan Mora Cordero. Por el año 1960 con un préstamo de Prudencio Jiménez Jiménez, fue colocado el terrazo.</p>";

        resenia += "<p>El templo fue finalmente inaugurado en 1965, y fue utilizado para las actividades de la iglesia hasta su cierre en 1991. Ya desde 1955, la estructura comenzó a presentar grietas, pero el mayor daño lo ocasionaron los terremotos que azotaron la región en 1990, que ocasionaron que el edificio se declara inhabitable y se recomendara su demolición. Fue así como en 2009, y tras 19 años de haber sido declarada inhabitable, el Ministerio de Salud de Costa Rica decretó la demolición de la Iglesia Vieja de Puriscal, lo que activó la iniciativa de diversos grupos de la comunidad para organizarse y restaurar la edificación, hasta que en agosto del 2012, el templo fue declarado como Patrimonio histórico arquitectónico de Costa Rica, lo cual impide la demolición del inmueble.</p>";

        txtvResenia.setText(Html.fromHtml(resenia));

        adView = (AdView) findViewById(R.id.adViewAnuncioHistoria);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_historia, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
