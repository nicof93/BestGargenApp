package blackrobot.bestgarden;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.blackrobot.bestgarden.Aplicacion;
import com.blackrobot.bestgarden.AplicacionDao;
import com.blackrobot.bestgarden.Plaga;
import com.blackrobot.bestgarden.PlantaDao;
import com.blackrobot.bestgarden.Producto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.dao.query.WhereCondition;


/**
 * Created by Nicolas on 30/10/2014.
 */
public class FragmentRespuestHogar extends Fragment {

    private ImageView imgInsectoCasa;
    private TextView lblEspecieHogar;
    private TextView txtEspecieHogar;
    private TextView txtProductoSug;
    private ImageView imgRecomendadoHogar;
    private TextView txtObjetivoHogar;
    private TextView txtDosis;
    private TextView txtRendimiento;
    private Bundle data;
    private TextView txtRecom;

    private HashMap<String,Integer> imagenes = new HashMap<String, Integer>();
    List<String> voladores = Arrays.asList(new String[]{"Mosca", "Zancudo", "Avispa", "Polilla", "Tábano", ""});


    public FragmentRespuestHogar(){
    }

    public static FragmentRespuestHogar newInstance(Bundle data){
        FragmentRespuestHogar preparedFagment =  new FragmentRespuestHogar();
        preparedFagment.data = data;
        return preparedFagment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagenes.put("Insecticida CiperPoint 20 ec Ingrediente activo: Cipermetrina", R.drawable.ciperpoint);
        imagenes.put("Insecticida Premium Flo 6 sc Ingrediente activo: Alphacipermetrina", R.drawable.premiumflo);
        imagenes.put("Insecticida GlacoXan C-10 Ingrediente activo: Cipermetrima microemulsión", R.drawable.glacoxan);
        imagenes.put("Insecticida Aerosol FumiXan FOG Válvula de Descarga Total", R.drawable.fumixanfag);
        imagenes.put("FumiXan PRO en Tabletas Fumigenas", R.drawable.fumixan);
        imagenes.put("CucaXan Gel", R.drawable.cucaxan);
        imagenes.put("Raticida Ratador Pellet para Uso en Interiores",R.drawable.pellets);
        imagenes.put("Raticida Ratador MiniBloque para Uso en Exteriores",R.drawable.ratador);
        imagenes.put("Molusquicida Clartex+R",R.drawable.clartexr);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_respuesta_hogar,container,false);

        RadioButton rbtAplicacionHogar = (RadioButton)rootView.findViewById(R.id.rbtAplicacionHogar);
        imgInsectoCasa = (ImageView) rootView.findViewById(R.id.imgInsectoCasa);
        lblEspecieHogar = (TextView) rootView.findViewById(R.id.lblEspecieHogar);
        txtEspecieHogar = (TextView) rootView.findViewById(R.id.txtEspecieHogar);
        txtProductoSug = (TextView) rootView.findViewById(R.id.txtProductoSug);
        imgRecomendadoHogar = (ImageView) rootView.findViewById(R.id.imgRecomendadoHogar);
        txtObjetivoHogar = (TextView) rootView.findViewById(R.id.txtObjetivoHogar);
        txtDosis = (TextView) rootView.findViewById(R.id.txtDosis);
        txtRendimiento = (TextView) rootView.findViewById(R.id.txtRendimiento);
        txtRecom = (TextView) rootView.findViewById(R.id.txtRecom);
        rbtAplicacionHogar.setEnabled(false);

        imgInsectoCasa.setImageResource(data.getInt("ImagenPlagaHogar"));
        imgInsectoCasa.setScaleType(ImageView.ScaleType.CENTER_CROP);

        imgInsectoCasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View amplificador = inflater.inflate(R.layout.zoom_image_view,null);
                ImageView imagenGrande = (ImageView) amplificador.findViewById(R.id.imageZoom);
                imagenGrande.setImageResource(data.getInt("ImagenPlagaHogar"));
                builder.setView(amplificador);
                builder.show();
            }
        });
        Plaga plaga = GlobalStore.plagaHogar;
        lblEspecieHogar.setText(plaga.getZnombre());

        //esta imagen se muestra en el ImageView de Respuesta pero al mostrar en grande usando el mismo resource no lo toma
        final int resourseImage = imagenProd(GlobalStore.productoEspecifico);

        imgRecomendadoHogar.setImageResource(resourseImage);
        imgRecomendadoHogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog builder = new Dialog(getActivity());
                builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                View view1 = inflater.inflate(R.layout.zoom_image_view, null);
                ImageView imageView = (ImageView) view1.findViewById(R.id.imageZoom);
                imageView.setImageResource(resourseImage);
                builder.setContentView(view1);
                builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                builder.setCancelable(true);
                builder.show();

            }
        });

        txtProductoSug.setText(GlobalStore.productoEspecifico.getZnombre());
        txtObjetivoHogar.setText(formateado(GlobalStore.productoEspecifico.getZobjetivo()));

        Aplicacion datosAplicacionesProductoHogar = DosisRendimientoRecomendacion();

        txtRendimiento.setText(datosAplicacionesProductoHogar.getZfrecuencia());
        txtRecom.setText(datosAplicacionesProductoHogar.getZepoca_anual());
        txtDosis.setText(datosAplicacionesProductoHogar.getZdosificacion());

        txtEspecieHogar.setText(aplicaTambienEn(GlobalStore.productoEspecifico));

        if(plaga.getZnombre().equals("Caracoles"))
            txtEspecieHogar.setText("Aplica también en babosas y moluscos.");

        LinearLayout lnRecomendacion = (LinearLayout)rootView.findViewById(R.id.lnRecomendacion);
        if(plaga.getZdescripcion().equals("roedor")||plaga.getZdescripcion().equals("molusco"))
        {
            lnRecomendacion.setVisibility(View.GONE);
        }else
        {
            lnRecomendacion.setVisibility(View.VISIBLE);
        }

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    private int imagenProd(Producto pro)
    {
        int aux = 0;

        for(Map.Entry elemento : imagenes.entrySet()) {
            if (elemento.getKey().toString().equals(pro.getZnombre()))
            {
                aux = (Integer)(elemento.getValue());
            }

        }
        return aux;
    }

    private String formateado(String format)
    {
        String objetivoFormateado = " ";

        String[] partido = format.split("\n");
        for(String cadena : partido)
        {
            objetivoFormateado+=" ■ ";
            objetivoFormateado+=(cadena+"\n");
        }
        return objetivoFormateado;
    }

    private Aplicacion DosisRendimientoRecomendacion()
    {
        Producto producto = GlobalStore.productoEspecifico;
        Plaga plaga = GlobalStore.plagaHogar;

        //condiciones where
        WhereCondition aplicacionesProducto = AplicacionDao.Properties.Zid_producto.eq(producto.getZid_producto());
        WhereCondition ProductoParaPlaga = AplicacionDao.Properties.Zid_plaga.eq(plaga.getZid_planta());

        Aplicacion definitiva = GlobalStore.getDaoSession(getActivity())
                .getAplicacionDao().queryBuilder().where(aplicacionesProducto,ProductoParaPlaga).limit(1).unique();

        return definitiva;
    }

    private String aplicaTambienEn(Producto producto)
    {
        WhereCondition plagasHogarDeEsteProd = AplicacionDao.Properties.Zid_producto.eq(producto.getZid_producto());

        boolean isVolador = isVolador(GlobalStore.plagaHogar);

        List<Aplicacion> listaPrevia = GlobalStore.getDaoSession(getActivity()).getAplicacionDao().queryBuilder()
                .where(plagasHogarDeEsteProd).list();

        String similares = "Aplica tambien en: ";

        for (Aplicacion app : listaPrevia)
        {
            int idplaga = app.getZid_plaga();

            List<Plaga>listaDPlagas = GlobalStore.getDaoSession(getActivity()).getPlagaDao()
                    .queryBuilder().where(PlantaDao.Properties.Zid_planta.eq(idplaga)).list();

            for(Plaga plaga : listaDPlagas){
                if(!similares.contains(plaga.getZnombre()) && plaga.getZnombre()!= GlobalStore.plagaHogar.getZnombre() )
                {
                    if(isVolador == isVolador(plaga))
                        similares += plaga.getZnombre() + ", ";
                }
            }
        }

        String similarListo = similares.substring(0,similares.length() - 2);
        similarListo += ".";
        return similarListo;

    }

    private boolean isVolador(Plaga plaga)
    {
        boolean flag = false;

        for (String nombre : voladores) {
            if(nombre.equals(plaga.getZnombre()))
                flag = true;
        }
        return flag;
    }



}
