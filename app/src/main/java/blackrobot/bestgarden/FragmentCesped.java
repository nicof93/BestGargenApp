package blackrobot.bestgarden;

//import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


/**
 * Created by Mario on 30-10-2014.
 */
public class FragmentCesped extends Fragment {
    Context context;
    char aux = '■';
    char aux2 = '✓';

    ListView list;
    ListViewAdapterCesped adapter;

    int[] imagenCesped;
    int[] imagenProductoCesped;

    String[] nombreCesped;
    String[] productoCesped;
    String[] objetivoProductoCesped;
    String[] contenidoproducto;

    String estadio = aux + " Esta especialmente recomendada para zonas de juegos de niños, canchas de football, parcelas de agrado y zonas de alto tráfico.\n" +
            "Mezcla de gran resistencia a sequías y heladas. De textura firme, forma prados densos y durables.";
    String law = aux + " Ideal para antejardines, zonas de estacionamiento, orillas de veredas y parques. \n" +
            "Mezcla de gran resistencia al pisoteo y condiciones climáticas adversas.\n" +
            "De color verde todo el año, rápido establecimiento y bajo costo de mantención.";
    String alf = aux + " Especialmente recomendada para jardines ornamentales, zonas de esparcimiento y recreación.\n" +
            "Mezcla resistente al uso y de color verde todo el año.\n" +
            "De textura firme, forma prados densos y durables.";
    String zp = aux + " Recomendada para zonas de piscinas, parcelas de agrado y zonas de alto tráfico. \n" +
            "Muy resistente al pisoteo. De color verde todo el año y rápido establecimiento.";

    String Cestadio = "Cada bolsa de semilla de pasto Estadio Súper contiene : "+"\n" +
            aux2 + " Lolium Linn 84%"+"\n"+
            aux2 + " Poa Pratensis Balin 10%"+"\n"+
            aux2 + " Festuca Arundinacea Titanium 3%"+"\n"+
            aux2 + " Bermuda Grass 3%";
    String Claw = "Cada bolsa de semilla de pasto Lawngrass con Trébol contiene : "+"\n"+
            aux2 + " Lolium Linn 93%"+"\n"+
            aux2 + " Poa Pratensis Balin 2%"+"\n"+
            aux2 + " Festuca Arundinacea Titanium 2%"+"\n"+
            aux2 + " Trébol Blanco Huia 3%";
    String Calf = "Cada bolsa de semilla de pasto Alfombra Verde contiene : "+ "\n"+
            aux2 + " Lolium Linn 70%"+"\n"+
            aux2 + " Festuca Rubra Navigator 10%"+"\n"+
            aux2 + " Festuca Chewings Tatiana 8%"+"\n"+
            aux2 + " Poa Pratensis Balin 8%"+"\n"+
            aux2 + " Trébol Frutilla O´Connors 4%";
    String Czp = "Cada bolsa de semilla de pasto Zona Piscinas contiene : "+"\n"+
            aux2 + " Lolium Linn 80%"+"\n"+
            aux2 + " Poa Pratensis Balin 5%"+"\n"+
            aux2 + " Festuca Arundinacea Titanium 10%"+"\n"+
            aux2 + " Trébol Blanco Huia 5%";



    public static FragmentCesped newInstance(){
        return new FragmentCesped();
    }

   // @Override
   // public void onAttach(Activity activity) {
   //     super.onAttach(activity);
   // }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        //cambiar mantenedor
        View rootView = inflater.inflate(R.layout.listview_main_cesped, container, false);

        //generara datos

        nombreCesped = new String[] {"Estadio Súper" , "Lawngrass con Trébol", "Zona Piscinas", "Alfombra Verde"};

        imagenCesped = new int[] { R.drawable.estadio, R.drawable.lawngrassimag, R.drawable.zonapiscinaimag, R.drawable.alfombraverdeimg };

        productoCesped = new String[] { "Estadio Súper Premium", "Lawngrass con Trébol Premium", "Zona Piscinas Premium", "Alfombra Verde Premium" };

        imagenProductoCesped = new int[] {R.drawable.estadiosuperpremium, R.drawable.lawgrass, R.drawable.zonapiscinas, R.drawable.alfombraverde};

        objetivoProductoCesped = new String[] {estadio, law, zp, alf};

        contenidoproducto = new String[] { Cestadio, Claw, Czp, Calf};

        // Busca el ListView en listview_main_cesped_cesped.xml
        list = (ListView) rootView.findViewById(R.id.listviewcesped);

        // Entrego el resultado a la clase ListViewAdapter
        adapter = new ListViewAdapterCesped(getActivity(), nombreCesped, imagenCesped, productoCesped, imagenProductoCesped, objetivoProductoCesped, contenidoproducto);

        list.setAdapter(adapter);

        // Capture ListView item click
        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent in = new Intent(getActivity(), SingleItemViewCesped.class);
                // Paso todos los datos de nombre
                in.putExtra("NombreCesped", nombreCesped);
                in.putExtra("", imagenCesped);
                in.putExtra("Producto Sugerido Cesped", productoCesped);
                in.putExtra("IPPC", imagenProductoCesped);
                in.putExtra("ObjetivoC", objetivoProductoCesped);
                in.putExtra("Coontenido del producto", contenidoproducto);
                // paso solo una posicion
                in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                in.putExtra("position", position);
                // Open SingleItemView.java Activity
                context.startActivity(in);
                getActivity().overridePendingTransition(R.animator.enter_right,R.animator.exit_right);
            }
        });
        return rootView;
    }
}
