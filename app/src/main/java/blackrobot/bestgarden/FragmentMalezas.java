package blackrobot.bestgarden;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Nicolas on 13/11/2014.
 */
public class FragmentMalezas extends Fragment {

    public static FragmentMalezas newInstance(){return new FragmentMalezas();}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_respuesta_hogar,null);



        ImageView imgMaleza = (ImageView)vista.findViewById(R.id.imgInsectoCasa);
        imgMaleza.setImageResource(R.drawable.malezas);
        imgMaleza.setScaleType(ImageView.ScaleType.CENTER_CROP);

        TextView txtTitulo = (TextView)vista.findViewById(R.id.lblEspecieHogar);
        txtTitulo.setText("Malezas");

        TextView txtSimilares = (TextView)vista.findViewById(R.id.txtEspecieHogar);
        txtSimilares.setText("Aplica en toda maleza de hoja ancha y gramíneas");

        TextView txtProducto = (TextView)vista.findViewById(R.id.txtProductoSug);
        txtProducto.setText("Herbicida Roundup");

        ImageView imgHerbisida = (ImageView)vista.findViewById(R.id.imgRecomendadoHogar);
        imgHerbisida.setImageResource(R.drawable.roundup);

        TextView txtObjetivo = (TextView)vista.findViewById(R.id.txtObjetivoHogar);
        txtObjetivo.setText("■ Herbicida Sistémico No Selectivo.\n" +
                "■ Líquido Concentrado Soluble 33%\n" +
                "■ No tiene efecto Residual.\n■ Especialmente recomendado para controlar todo tipo de malezas, bordes de camino, acequias y cultivos mayores.");
        TextView txtDosis = (TextView)vista.findViewById(R.id.txtDosis);
        txtDosis.setText("100cc diluidos en 3lt de agua");

        TextView txtRendimiento = (TextView)vista.findViewById(R.id.txtRendimiento);
        txtRendimiento.setText("Basta con una aplicación");

        TextView txtRecom = (TextView)vista.findViewById(R.id.txtRecom);
        txtRecom.setText("Idealmente se recomienda regar antes las malezas y luego aplicar el herbicida siguiendo las instrucciones de la etiqueta del envase.");

        imgHerbisida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog builder = new Dialog(getActivity());
                builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.zoom_image_view,null);
                ImageView imageView = (ImageView) view1.findViewById(R.id.imageZoom);
                imageView.setImageResource(R.drawable.roundup);
                builder.setContentView(view1);
                builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                builder.setCancelable(true);
                builder.show();

            }
        });

        imgMaleza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog builder = new Dialog(getActivity());
                builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.zoom_image_view,null);
                ImageView imageView = (ImageView) view1.findViewById(R.id.imageZoom);
                imageView.setImageResource(R.drawable.malezas);
                builder.setContentView(view1);
                builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                builder.setCancelable(true);
                builder.show();
            }
        });


        return vista;
    }
}
