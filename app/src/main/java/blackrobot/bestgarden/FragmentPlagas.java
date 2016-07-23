package blackrobot.bestgarden;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentPlagas extends Fragment {

    //Declaracion de Variables
    PlagasListViewAdapter adapter;
    Context context;
    ListView list;

    char aux = '■';
    int[] imagenPlaga;
    int[] imagenProductoPlaga;
    String[] nombrePlaga;
    String[] productoPlaga;
    String[] objetivoProductoPlaga;
    String freePlag = aux + " Controlar a los insectos que atacan a las plantas (Inhibidor de la alimentación)." + "\n" +
            aux + " Estimular el desarrollo integral de las plantas (Fertilización).";
    String Pyc = aux + " Controlar a los insectos que atacan a las plantas (Inhibidor de la alimentación)." + "\n" +
            aux+ " Estimular el desarrollo integral de las plantas (Fertilización).";
    String Hyf = aux + " Controlar el ataque de Hongos Foliares." + "\n" +
            aux + " Estimular el desarrollo integral de las plantas (Fertilización).";


    public static FragmentPlagas newInstance(){
        return new FragmentPlagas();
    }

    /**@Override
    public void onAttach(Activity activity) {
    super.onAttach(activity);
    }**/


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.mantenedor_listview_main, container, false);
        //Alfabetico
        nombrePlaga = new String[] { "Arañitas Rojas", "Botritis", "Chanchitos Blancos", "Cloca", "Conchuelas", "Escamas", "Fumagina",
                "Langostinos", "Mildiu", "Mosquita Blanca", "Oidio", "Peste Negra", "Pulgones", "Tizones",
                "Trips entre otros insectos chupadores y masticadores" };

//<<<<<<< Updated upstream
        imagenPlaga = new int[] { R.drawable.aranitaroja, R.drawable.brotritis, R.drawable.chanchitoblanco,
                R.drawable.cloca, R.drawable.conchuelas, R.drawable.escama, R.drawable.fumagina, R.drawable.langostino, R.drawable.mildiu,
                R.drawable.moscablanca,R.drawable.oidium, R.drawable.pestenegra, R.drawable.pulgones, R.drawable.tizon, R.drawable.thrips };
//=======
        //Alfabetico
        nombrePlaga = new String[] { "Arañitas Rojas", "Botritis", "Chanchitos Blancos", "Cloca", "Conchuelas", "Escamas", "Fumagina",
                "Langostinos", "Mildiu", "Mosquita Blanca", "Oidio", "Peste Negra", "Pulgones", "Tizones",
                "Trips entre otros insectos chupadores y masticadores" };

        imagenPlaga = new int[] { R.drawable.aranitaroja, R.drawable.brotritis,R.drawable.caracoll, R.drawable.chanchitoblanco, R.drawable.cloca, R.drawable.conchuelas,
                R.drawable.escama, R.drawable.fumagina, R.drawable.langostino, R.drawable.mildiu, R.drawable.moscablanca,
                R.drawable.oidium, R.drawable.pestenegra, R.drawable.pulgones, R.drawable.tizon, R.drawable.thrips };

        productoPlaga = new String[] {"Free-Plag", "Hongos y Fumagina","Free-Plag", "Hongos y Fumagina", "Pulgón y Concuelas", "Free-Plag", "Hongos y Fumagina",
                "Free-Plag", "Hongos y Fumagina", "Free-Plag", "Hongos y Fumagina", "Hongos y Fumagina",
                "Pulgón y Concuelas", "Hongos y Fumagina", "Free-Plag" };

        imagenProductoPlaga = new int[] { R.drawable.freeplag, R.drawable.hyf, R.drawable.freeplag, R.drawable.hyf, R.drawable.pyc, R.drawable.freeplag, R.drawable.hyf,
                R.drawable.freeplag, R.drawable.hyf, R.drawable.freeplag, R.drawable.hyf, R.drawable.hyf, R.drawable.pyc, R.drawable.hyf, R.drawable.freeplag };

        objetivoProductoPlaga = new String[] {freePlag, Hyf,freePlag, Hyf, Pyc, freePlag, Hyf, freePlag, Hyf, freePlag, Hyf, Hyf, Pyc, Hyf, freePlag };

        // Busca el ListView en listview_main.xml
        list = (ListView) rootView.findViewById(R.id.listview);

        // Entrego el resultado a la clase ListViewAdapter
        adapter = new PlagasListViewAdapter(getActivity(), nombrePlaga, imagenPlaga, productoPlaga, imagenProductoPlaga, objetivoProductoPlaga);

        list.setAdapter(adapter);

        // Capture ListView item click
        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(getActivity(), PlagasSingleItemView.class);
                // Paso todos los datos de nombre
                i.putExtra("Nombre", nombrePlaga);
                i.putExtra("", imagenPlaga);
                i.putExtra("Producto Sugerido", productoPlaga);
                i.putExtra("IPP", imagenProductoPlaga);
                i.putExtra("Objetivo", objetivoProductoPlaga);
                // paso solo una posicion
                i.putExtra("position", position);
                // Open SingleItemView.java Activity
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(i);
                getActivity().overridePendingTransition(R.animator.enter_right,R.animator.exit_right);
            }
        });
        
        return rootView;
    }
}
