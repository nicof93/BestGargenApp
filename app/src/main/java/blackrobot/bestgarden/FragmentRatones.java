package blackrobot.bestgarden;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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

import com.blackrobot.bestgarden.Plaga;
import com.blackrobot.bestgarden.PlagaDao;
import com.blackrobot.bestgarden.Producto;
import com.blackrobot.bestgarden.ProductoDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicolas on 13/11/2014.
 */
public class FragmentRatones extends Fragment {

    private ListView LVRatones;
    private Context context;
    private TextView txtTitulo;
    int choiseItem ;

    private int[] imagenes = new int[]{R.drawable.raton,R.drawable.rata};

    public static FragmentRatones newInstance(){return new FragmentRatones();}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.mantenedor_listview_main,null);
        LVRatones = (ListView) vista.findViewById(R.id.listview);
        context = getActivity();

        final List<Plaga> lista =  GlobalStore.getDaoSession(context).getPlagaDao().queryBuilder().where(PlagaDao
        .Properties.Zdescripcion.eq("roedor")).list();

        final String[] ratas = convertirAListaString(lista);
        ArbolesViewAdapter adapter = new ArbolesViewAdapter(context,ratas,imagenes);
        LVRatones.setAdapter(adapter);

        LVRatones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                choiseItem = 0;
                GlobalStore.plagaHogar = lista.get(position);

                final List<Producto> listaProd = GlobalStore.getDaoSession(context).getProductoDao()
                        .queryBuilder().where(ProductoDao.Properties.Ztipo.eq("raticida")).list();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Elija un producto");
                builder.setCancelable(false);

                builder.setSingleChoiceItems(items(listaProd),choiseItem,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        choiseItem = i;
                    }
                });

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.animator.enter_right, R.animator.exit_right, R.animator.pop_enter_right, R.animator.pop_exit_right);

                final Bundle imagen = new Bundle();
                imagen.putInt("ImagenPlagaHogar",imagenes[position]);

                builder.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(choiseItem >= 0)
                        {
                            GlobalStore.productoEspecifico = listaProd.get(choiseItem);
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
        });


        txtTitulo = (TextView)vista.findViewById(R.id.txtTituloPlanta);
        txtTitulo.setText("Ratones");
        return vista;
    }

    private static String[] convertirAListaString(List<Plaga> listado){
        String[] listaNombre = new String[listado.size()];

        for (int i = 0; i<listado.size();i++)
        {

            listaNombre[i] = listado.get(i).getZnombre();
        }
        return listaNombre;
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
