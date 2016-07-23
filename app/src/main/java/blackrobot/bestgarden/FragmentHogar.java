package blackrobot.bestgarden;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.blackrobot.bestgarden.Aplicacion;
import com.blackrobot.bestgarden.AplicacionDao;
import com.blackrobot.bestgarden.Plaga;
import com.blackrobot.bestgarden.PlagaDao;
import com.blackrobot.bestgarden.Producto;
import com.blackrobot.bestgarden.ProductoDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicolas on 30/10/2014.
 */
public class FragmentHogar extends Fragment {

    private ListView listaHogar;
    private TextView txtTitulo;
    private Context context;
    private List<Plaga> listaPlagas;
    int[] imagenesPlagas = new int[]{R.drawable.alacran, R.drawable.aranarincon, R.drawable.avispa,R.drawable.caracoll,
    R.drawable.chinche, R.drawable.cucarachas, R.drawable.garrapata, R.drawable.gorgojos, R.drawable.hormigas,
    R.drawable.moscas, R.drawable.polillas, R.drawable.pulga, R.drawable.tabano, R.drawable.tijereta, R.drawable.zancudo};
    int choiseItem = 0;

    public FragmentHogar(){}

    public static FragmentHogar newInstance(){return new FragmentHogar(); }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.mantenedor_listview_main,container,false);

        txtTitulo = (TextView)rootView.findViewById(R.id.txtTituloPlanta);
        listaHogar = (ListView) rootView.findViewById(R.id.listview);
        context = getActivity();

        listaPlagas = GlobalStore.getDaoSession(context).getPlagaDao().queryBuilder()
                .where(PlagaDao.Properties.Zdescripcion.notEq("roedor")).orderAsc(PlagaDao.Properties.Znombre).list();
        String[] listaNombres = convertirAListaString(listaPlagas);

        ArbolesViewAdapter adapter = new ArbolesViewAdapter(context,listaNombres,imagenesPlagas);


        txtTitulo.setText("Plagas del Hogar");
        listaHogar.setAdapter(adapter);
        listaHogar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                choiseItem = 0;
                GlobalStore.plagaHogar = listaPlagas.get(position);

                final List<Producto> productosParaControlHogar = listadoProductos(GlobalStore.plagaHogar);

                final Bundle imagen = new Bundle();
                imagen.putInt("ImagenPlagaHogar",imagenesPlagas[position]);

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.animator.enter_right, R.animator.exit_right, R.animator.pop_enter_right, R.animator.pop_exit_right);


                if(productosParaControlHogar.size()==1)
                {
                    GlobalStore.productoEspecifico = productosParaControlHogar.get(0);
                    GlobalStore.productoEspecifico = productosParaControlHogar.get(choiseItem);
                    ft.replace(R.id.container,FragmentRespuestHogar.newInstance(imagen));
                    ft.addToBackStack(null);
                    ft.commit();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Elija un producto");
                    builder.setCancelable(false);

                    builder.setSingleChoiceItems(items(productosParaControlHogar),choiseItem,new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            choiseItem = i;
                        }
                    });
                    builder.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(choiseItem>=0)
                            {
                                GlobalStore.productoEspecifico = productosParaControlHogar.get(choiseItem);
                                ft.replace(R.id.container,FragmentRespuestHogar.newInstance(imagen));
                                ft.addToBackStack(null);
                                ft.commit();
                            }
                        }
                    });

                    builder.setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    builder.show();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    private static String[] convertirAListaString(List<Plaga> listado){
        String[] listaNombre = new String[listado.size()];

        for (int i = 0; i<listado.size();i++)
        {

            listaNombre[i] = listado.get(i).getZnombre();
        }
        return listaNombre;
    }

    /**
     * A partir de un listado de aplicaciones, devuelve la lista de los productos a los que corresponden
     * @return listado de productos
     */
    private List<Producto> listadoProductos(Plaga plaga)
    {
        List<Aplicacion> posibilidades = GlobalStore.getDaoSession(getActivity()).getAplicacionDao().queryBuilder()
                .where(AplicacionDao.Properties.Zid_plaga.isNotNull(), AplicacionDao.Properties.Zid_plaga.eq(plaga.getZid_planta())).list();

        //List<Producto> productos = GlobalStore.getDaoSession(getActivity()).getProductoDao().queryBuilder()
          //      .where(ProductoDao.Properties.Zid_producto.isNotNull(),ProductoDao.Properties.Zid_producto.eq(plaga.getZid_planta())).list();

        List<Producto> lista = new ArrayList<Producto>();

        for (Aplicacion app : posibilidades)
        {
            if(app.getZid_plaga() == plaga.getZid_planta()){
                Producto producto = GlobalStore.getDaoSession(getActivity()).getProductoDao().queryBuilder()
                        .where(ProductoDao.Properties.Zid_producto.eq(app.getZid_producto())).limit(1).unique();
                if(!lista.contains(producto))
                {
                    lista.add(producto);
                }
            }
        }

        return lista;
    }


    private CharSequence[] items(List<Producto> prods)
    {
        ArrayList<String> sequences = new ArrayList<String>();

        for (Producto prod : prods){
            sequences.add(prod.getZnombre());
        }

        CharSequence[] seq = sequences.toArray(new CharSequence[sequences.size()]);

        return seq;
    }
}
