package blackrobot.bestgarden;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.blackrobot.bestgarden.Aplicacion;
import com.blackrobot.bestgarden.AplicacionDao;
import com.blackrobot.bestgarden.Planta;
import com.blackrobot.bestgarden.PlantaDao;
import com.blackrobot.bestgarden.Producto;
import com.blackrobot.bestgarden.ProductoDao;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class FragmentTratamiento extends Fragment {

    ArbolesViewAdapter adapter;
    ListView listaEspecies;
    Context context;
    int[] imagenPlanta;
    String[] nombrePlanta;
    List<Planta> ListadoPlantasBD;
    Producto ObjEspecifico;
    TextView txtTituloPlanta;

    int choiseAux = 0;

    public static FragmentTratamiento newInstance()
    {
        return new FragmentTratamiento();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.mantenedor_listview_main,container,false);

        //list with names, images and description of elements
        ListadoPlantasBD = GlobalStore.getDaoSession(getActivity()).getPlantaDao()
                .queryBuilder().orderAsc(PlantaDao.Properties.Znombre).list();
        nombrePlanta = convertirAListaString(ListadoPlantasBD);

        imagenPlanta = new int[] { R.drawable.arbolesfrutales, R.drawable.arbustos,R.drawable.bulbos,R.drawable.cactus, R.drawable.cesped, R.drawable.floresacidas,
                R.drawable.plantamacetero,R.drawable.hortensias,R.drawable.orquideas, R.drawable.rosas};

        txtTituloPlanta = (TextView) rootView.findViewById(R.id.txtTituloPlanta);
        listaEspecies = (ListView) rootView.findViewById(R.id.listview);
        txtTituloPlanta.setText("Identifique la Planta");

        adapter = new ArbolesViewAdapter(getActivity(),nombrePlanta,imagenPlanta);

        listaEspecies.setAdapter(adapter);

        listaEspecies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                choiseAux = 0;
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.animator.enter_right, R.animator.exit_right, R.animator.pop_enter_right, R.animator.pop_exit_right);

                GlobalStore.productoEspecifico = new Producto();

                //create Bundle with parameter for the creation the view for answer
                final Bundle arguments = new Bundle();
                arguments.putInt("IDImagenPlanta", imagenPlanta[i]);


                PlantaDao plantaDao = GlobalStore.getDaoSession(context).getPlantaDao();
                final Planta planta = plantaDao.queryBuilder().where(PlantaDao.Properties.Znombre.eq(nombrePlanta[i])).unique();

                AplicacionDao aplicacionDao = GlobalStore.getDaoSession(context).getAplicacionDao();
                List<Aplicacion> listaAplicacion = aplicacionDao.queryBuilder().where(AplicacionDao
                        .Properties.Zid_planta.eq(planta.getZid_planta())).list();

                final List<Producto> productosParaPlanta = listarProductosPlanta(context,planta,listaAplicacion);

                GlobalStore.plantaConsultada = planta;
                GlobalStore.dosificacion = listaAplicacion;

                if(productosParaPlanta.size()==1)
                {
                    GlobalStore.productoEspecifico = productosParaPlanta.get(0);
                    FragmentRespuesta temporalFragment = FragmentRespuesta.newInstancePrepared(arguments);
                    //Set the fragment with de parameter for plants
                    ft.replace(R.id.container,temporalFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }else{
                    //show dialog for objective
                    final CharSequence[] objetivos = {"Aumentar  la floración e intensificar el color y el aroma de las flores y frutos",
                            "Intensificar el color verde de las hojas",
                            "Incentivar el desarrollo de raíces y aumentar la defensa natural de la planta"};

                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Desea un producto para:");
                    builder.setCancelable(false);
                    builder.setSingleChoiceItems(objetivos, choiseAux, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            choiseAux = i;
                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(choiseAux>=0){

                                String objetivoBuscado = objetivos[choiseAux].toString();
                                ObjEspecifico = seleccionPorObjetivo(objetivoBuscado, productosParaPlanta);
                                GlobalStore.productoEspecifico = ObjEspecifico;

                                FragmentRespuesta temporalFragment = FragmentRespuesta.newInstancePrepared(arguments);
                                ft.replace(R.id.container, temporalFragment);
                                ft.addToBackStack(null);
                                ft.commit();
                            }

                        }
                    });
                    builder.show();


                }

            }
        });

        return rootView;
    }

    private static String[] convertirAListaString(List<Planta> listado){
        String[] listaNombre = new String[10];
        int cont = 0;

        for(Planta item : listado){
            listaNombre[cont] = item.getZnombre();
            cont++;

        }

        return listaNombre;
    }

    private static ArrayList<Producto> listarProductosPlanta(Context context,Planta planta, List<Aplicacion> aplicaciones){
        ArrayList<Producto> products = new ArrayList<Producto>();
        ProductoDao productoDao = GlobalStore.getDaoSession(context).getProductoDao();

        for (Aplicacion aplicacion : aplicaciones)
        {
            if (aplicacion.getZid_planta()==planta.getZid_planta()){
                Producto prod = productoDao.queryBuilder().where(ProductoDao
                        .Properties.Zid_producto.eq(aplicacion.getZid_producto())).limit(1).unique();

                if(products.size()>0){
                    if(!products.contains(prod)){
                        products.add(prod);
                    }
                }else {
                    products.add(prod);
                }
            }
        }
        return  products;
    }

    private static Producto seleccionPorObjetivo(String objetivo,List<Producto> posibilidades){
        String Obj = objetivo.substring(0,5);
        Producto p = null;
        for (Producto prod : posibilidades)
        {
            if(prod.getZobjetivo().startsWith(Obj))
            {
                p=prod;
            }
        }

        return p;
    }
}
