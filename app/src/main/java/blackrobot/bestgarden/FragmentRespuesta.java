package blackrobot.bestgarden;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.blackrobot.bestgarden.Aplicacion;
import com.blackrobot.bestgarden.Planta;
import com.blackrobot.bestgarden.Producto;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * Created by Daniel on 05-08-2014.
 */
public class FragmentRespuesta extends Fragment {

    Context context;

    TextView txtNombrePlanta;
    ImageView imgImagenPlanta;
    TextView txtDescrip;
    ImageView imgImagenProductoPlanta;
    TextView txtProductoPlanta;
    TextView txtObjetivo;

    //table Columns frecuencia
    TextView txtfrecuenciaprimavera;
    TextView txtfrecuenciaverano;
    TextView txtfrecuenciaotoño;
    TextView txtfrecuenciainvierno;

    //table Columns Dosificacion
    TextView txtdosificacionprimavera;
    TextView txtdosificacionverano;
    TextView txtdosificacionotoño;
    TextView txtdosificacioninvierno;
    ImageView imgFertilizante;

    //columns for change background color
    TableRow cabecera;
    TextView cColumnaPrimavera;
    TextView cColumnaVerano;
    TextView cColumnaInvierno;
    TextView cColumnaOtoño;

    int[] imagenesProducto = new int[]{R.drawable.superureamas,R.drawable.supertriplemas,
            R.drawable.superpotasicomas,R.drawable.rosas1,R.drawable.floresacidas1,
            R.drawable.cesped1,R.drawable.floresdejardin,R.drawable.producto_orquideas,R.drawable.producto_hortensias,
            R.drawable.producto_cactus,R.drawable.producto_bulbos};

    String[] nombreProducto = new String[]{"Fertilizante Super Urea +","Fertilizante Super Triple +",
            "Fertilizante Super Potasico +","Fertilizante para Rosas 12-14-4","Fertilizante para Flores Acidas 15-4-7",
            "Fertilizante para Cesped 23-4-5","Fertilizante para Flores de Jardín\ny Plantas de Maceteros 18-5-9","Fertilizante para Orquídeas 20-10-10",
            "Fertilizante para Hortensias","Fertilizante para Cactus 5-8-5","Fertilizante para Bulbos 18-15-12"};

    Producto productoRecomendado;

    static Bundle arguments;
    TableLayout tablaAplicacion;
    RadioButton recienPlantadas;
    RadioButton masDeAño;
    RadioButton establecidas;
    RadioGroup grupoRadios;



    public static FragmentRespuesta newInstance(){
        return new FragmentRespuesta();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();
    }


    //Return an intance with parameter "arguments"
    public static FragmentRespuesta newInstancePrepared(Bundle arguments)
    {
        FragmentRespuesta fr = new FragmentRespuesta();
        if (arguments != null)
        {
            FragmentRespuesta.arguments = arguments;
            fr.setArguments(arguments);
        }
        return fr;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_respuesta_consulta, container, false);


        //create the aplication`s table
        tablaAplicacion = (TableLayout) vista.findViewById(R.id.lyAplicacion);
        txtfrecuenciaprimavera = (TextView) vista.findViewById(R.id.txtfrecuenciainviernoprimavera);
        txtfrecuenciaverano = (TextView) vista.findViewById(R.id.txtfrecuenciaverano);
        txtfrecuenciaotoño = (TextView) vista.findViewById(R.id.txtfrecuenciaotoño);
        txtfrecuenciainvierno = (TextView) vista.findViewById(R.id.txtfrecuenciainvierno);
        txtdosificacionprimavera = (TextView) vista.findViewById(R.id.txtdosificacionprimavera);
        txtdosificacionverano = (TextView) vista.findViewById(R.id.txtdosificacionverano);
        txtdosificacionotoño = (TextView) vista.findViewById(R.id.txtdosificacionotoño);
        txtdosificacioninvierno = (TextView) vista.findViewById(R.id.txtdosificacioninvierno);
        imgFertilizante = (ImageView)vista.findViewById(R.id.imgFertilizante);


        recienPlantadas = (RadioButton) vista.findViewById(R.id.rbtTransplantada);
        masDeAño = (RadioButton) vista.findViewById(R.id.rbtUnAño);
        establecidas = (RadioButton) vista.findViewById(R.id.rbtEstablecida);
        grupoRadios = (RadioGroup) vista.findViewById(R.id.rbtgAplicacion);

        //get the control that represent each variable
        txtNombrePlanta = (TextView) vista.findViewById(R.id.lblEspecieRsp);
        imgImagenPlanta = (ImageView) vista.findViewById(R.id.imagenPlaga);
        imgFertilizante = (ImageView) vista.findViewById(R.id.imgFertilizante);
        txtDescrip = (TextView) vista.findViewById(R.id.txtEspecie);
        txtProductoPlanta = (TextView) vista.findViewById(R.id.txtProductoSug);
        txtObjetivo = (TextView) vista.findViewById(R.id.txtObjetivo);
        imgImagenProductoPlanta = (ImageView) vista.findViewById(R.id.imgimagenProductoPlaga);

        //elements to change background
        cabecera = (TableRow) vista.findViewById(R.id.Cabecera);
        cColumnaPrimavera = (TextView) vista.findViewById(R.id.ColumnaPrimavera);
        cColumnaVerano = (TextView) vista.findViewById(R.id.ColumnaVerano);
        cColumnaInvierno = (TextView) vista.findViewById(R.id.Columnainvierno);
        cColumnaOtoño = (TextView) vista.findViewById(R.id.ColumnaOtoño);

        //set the value for each control in the fragmentRespuesta´s view
        Planta plantaConsultada = GlobalStore.plantaConsultada;
        productoRecomendado = GlobalStore.productoEspecifico;

        //SegmentedGroup segmented2 = (SegmentedGroup) vista.findViewById(R.id.rbEtapas);
       // segmented2.setTintColor(R.color.bluelight);

        //Planta
        txtNombrePlanta.setText(plantaConsultada.getZnombre());
        txtDescrip.setText(plantaConsultada.getZdescripcion());
        imgImagenPlanta.setImageResource(arguments.getInt("IDImagenPlanta"));
        imgImagenPlanta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view1 = inflater.inflate(R.layout.zoom_image_view, null);
                ImageView imageView = (ImageView) view1.findViewById(R.id.imageZoom);
                imageView.setImageResource(arguments.getInt("IDImagenPlanta"));
                builder.setView(view1);
                builder.setCancelable(true);
                builder.show();

            }
        });

        //Producto
        txtProductoPlanta.setText(productoRecomendado.getZnombre());
        String objetivoFormateado = " ";

        String[] partido = productoRecomendado.getZobjetivo().split("\n");
        for(String cadena : partido)
        {
            objetivoFormateado+=" ■ ";
            objetivoFormateado+=(cadena+"\n");
        }
        txtObjetivo.setText(objetivoFormateado);

        final int auxIndice = mostrarImagenDelProducto(productoRecomendado);
        imgFertilizante.setImageResource(imagenesProducto[auxIndice]);

        imgFertilizante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog builder = new Dialog(context);
                builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                View view1 = inflater.inflate(R.layout.zoom_image_view, null);
                ImageView imageView = (ImageView) view1.findViewById(R.id.imageZoom);
                imageView.setImageResource(imagenesProducto[auxIndice]);
                builder.setContentView(view1);
                builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                builder.setCancelable(true);
                builder.show();
            }
        });

        if(productoRecomendado.getZtipo().equals("especifico")){
            masDeAño.setVisibility(View.GONE);

            int caso = productoRecomendado.getZid_producto();
            switch (caso){
                case 5:
                    //cesped
                    recienPlantadas.setText("Recien Sembrado");
                    establecidas.setText("Pasto Establecido");
                    mostrarTB("Plantas nuevas");
                    break;
                case 7:
                    //Orquideas
                    recienPlantadas.setText("Flores nuevas");
                    establecidas.setText("Flores establecidas");
                    mostrarTB("Flores nuevas");
                    break;
                case 9:
                    //Cactus
                    recienPlantadas.setText("Más de 1 año");
                    establecidas.setText("Menos de 1 año");
                    mostrarTB("Más de 1 año");
                    break;
                case 10:
                    //Bulbos
                    recienPlantadas.setText("Más de 1 año");
                    establecidas.setText("Menos de 1 año");
                    mostrarTB("Más de 1 año");
                    break;
                default:
                    recienPlantadas.setText("Plantas nuevas");
                    establecidas.setText("Más de 1 año");
                    mostrarTB("Plantas nuevas");
                    break;
            }
        }else
        {
            mostrarTB("Recien transplantados");
        }


        return vista;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recienPlantadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarFondo(R.drawable.cell_shape);
                if(productoRecomendado.getZtipo().equals("especifico"))
                    mostrarTB(recienPlantadas.getText().toString());
                else
                    mostrarTB("Recien transplantados");
            }
        });

        masDeAño.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarFondo(R.drawable.cell_shape2);
                mostrarTB("Pleno crecimiento");
            }
        });


        establecidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarFondo(R.drawable.cell_shape_greendark);
                if (productoRecomendado.getZtipo().equals("especifico")) {
                    int caso2 = productoRecomendado.getZid_producto();
                    switch (caso2) {
                        case 5:
                            //cesped
                            mostrarTB("Plantas más de 1 año");
                            break;
                        case 7:
                            //Orquideas
                            mostrarTB(establecidas.getText().toString());
                            break;
                        case 9:
                            //Cactus
                            mostrarTB(establecidas.getText().toString());
                            break;
                        case 10:
                            //Bulbos
                            mostrarTB(establecidas.getText().toString());
                            break;
                        default:
                            mostrarTB("Plantas más de 1 año");
                            break;
                    }
                }
                else {
                    mostrarTB("Adulto, ya establecido");
                }
            }
        });
    }

    public void cambiarFondo(int shape_name)
    {
        cabecera.setBackgroundResource(shape_name);
        cColumnaInvierno.setBackgroundResource(shape_name);
        cColumnaOtoño.setBackgroundResource(shape_name);
        cColumnaPrimavera.setBackgroundResource(shape_name);
        cColumnaVerano.setBackgroundResource(shape_name);
    }

    public void mostrarTB(String etapa)
    {
        List<Aplicacion> dosificaciones = GlobalStore.dosificacion;
        final String etapaCrecimiento = etapa;

        for (Aplicacion aplicacion : dosificaciones)
        {
            if(aplicacion.getZepoca_anual().equals("Primavera") && aplicacion.getZetapa().equals(etapaCrecimiento))
            {
                txtdosificacionprimavera.setText(aplicacion.getZdosificacion());
                txtfrecuenciaprimavera.setText(aplicacion.getZfrecuencia());
            }

            if(aplicacion.getZepoca_anual().equals("Verano") && aplicacion.getZetapa().equals(etapaCrecimiento))
            {
                txtdosificacionverano.setText(aplicacion.getZdosificacion());
                txtfrecuenciaverano.setText(aplicacion.getZfrecuencia());
            }

            if(aplicacion.getZepoca_anual().equals("Otoño") && aplicacion.getZetapa().equals(etapaCrecimiento))
            {
                txtdosificacionotoño.setText(aplicacion.getZdosificacion());
                txtfrecuenciaotoño.setText(aplicacion.getZfrecuencia());
            }

            if(aplicacion.getZepoca_anual().equals("Invierno") && aplicacion.getZetapa().equals(etapaCrecimiento))
            {
                txtdosificacioninvierno.setText(aplicacion.getZdosificacion());
                txtfrecuenciainvierno.setText(aplicacion.getZfrecuencia());
            }
        }

    }

    private int mostrarImagenDelProducto(Producto prod)
    {
        int aux = 0;
        for (int i = 0; i < nombreProducto.length ; i++) {
            if(nombreProducto[i].equals(prod.getZnombre()))
            {
                aux = i;
            }
        }
        return aux;
    }
}
