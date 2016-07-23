package blackrobot.bestgarden;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blackrobot.bestgarden.Aplicacion;

import org.w3c.dom.Text;

import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * Created by Mario on 15-08-2014.
 */
public class PlagasSingleItemView extends Activity {
    // Declare Variables
    TextView txtNombrePlaga;
    ImageView imgImagenPlaga;
    ImageView imgImagenProductoPlaga;
    TextView txtProductoPlaga;
    TextView txtObjetivo;
    String[] productoPlaga;
    String[] nombrePlaga;
    String[] objetivoProductoPlaga;
    int[] imagenPlaga;
    int[] imagenProductoPlaga;
    int position;
    //
    TableLayout layoutAplicacion;
    TableLayout layoutAplicacion2;
    RadioGroup rbg;
    RadioButton preventivo;
    RadioButton curativo;
    LayoutInflater inflater;
    Context context;

    //tabla
    TextView txt1;
    TextView txt2;
    TextView txt3;
    TextView txt4;
    TextView txt5;
    TextView txt6;
    TextView txt7;
    TextView txt8;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        context = this;
        setContentView(R.layout.plagas_singleitemview);

        layoutAplicacion = (TableLayout) findViewById(R.id.lyAplicacion);
        layoutAplicacion2 = (TableLayout) findViewById(R.id.lyAplicacion2);
        preventivo = (RadioButton) findViewById(R.id.rbtPreventivo);
        curativo = (RadioButton) findViewById(R.id.rbtCurativo);
        rbg = (RadioGroup) findViewById(R.id.rbtgAplicacion);


        // Retrieve data from MainActivity on listview item click
        Intent i = getIntent();
        // Get a single position
        position = i.getExtras().getInt("position");
        // Get the list of rank
        nombrePlaga = i.getStringArrayExtra("Nombre");
        // Get the list of flag
        imagenPlaga = i.getIntArrayExtra("");
        productoPlaga = i.getStringArrayExtra("Producto Sugerido");
        imagenProductoPlaga = i.getIntArrayExtra("IPP");
        objetivoProductoPlaga = i.getStringArrayExtra("Objetivo");

        // Locate the TextViews in plagas_singleitemview.xmliew.xml
        txtNombrePlaga = (TextView) findViewById(R.id.txtnombrePlaga);
        txtProductoPlaga = (TextView) findViewById(R.id.txtproductoPlaga);
        txtObjetivo = (TextView) findViewById(R.id.txtobjetivo);

        // Locate the ImageView in plagas_singleitemviewtemview.xml
        imgImagenPlaga = (ImageView) findViewById(R.id.imgimagenPlaga);
        imgImagenProductoPlaga = (ImageView) findViewById(R.id.imgimagenProductoPlaga);
        // Load the text into the TextViews followed by the position
        txtNombrePlaga.setText(nombrePlaga[position]);
        txtProductoPlaga.setText(productoPlaga[position]);
        txtObjetivo.setText(objetivoProductoPlaga[position]);
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Load the image into the ImageView followed by the position
        imgImagenPlaga.setImageResource(imagenPlaga[position]);
        imgImagenPlaga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view1 = inflater.inflate(R.layout.zoom_image_view, null);
                ImageView imageView = (ImageView) view1.findViewById(R.id.imageZoom);
                imageView.setImageResource(imagenPlaga[position]);
                builder.setView(view1);
                builder.setCancelable(true);
                builder.show();

            }
        });
        //cambiar el color del radio button
        //SegmentedGroup segmented2 = (SegmentedGroup) findViewById(R.id.rbtgAplicacion);
        //segmented2.setTintColor(R.color.bluelight);
        imgImagenProductoPlaga.setImageResource(imagenProductoPlaga[position]);

        imgImagenProductoPlaga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog builder = new Dialog(context);
                builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                View view1 = inflater.inflate(R.layout.zoom_image_view, null);
                ImageView imageView = (ImageView) view1.findViewById(R.id.imageZoom);
                imageView.setImageResource(imagenProductoPlaga[position]);
                builder.setContentView(view1);
                builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                builder.setCancelable(true);
                builder.show();
            }
        });
        //rbg.setOnCheckedChangeListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.animator.pop_enter_right, R.animator.pop_exit_right);
    }

    public void cambiodeLayout(View v){
            if(preventivo.isChecked()){
                layoutAplicacion.setVisibility(View.VISIBLE);
                layoutAplicacion2.setVisibility(View.GONE);
            }
            if (curativo.isChecked()){
                layoutAplicacion2.setVisibility(View.VISIBLE);
                layoutAplicacion.setVisibility(View.GONE);
                if(txtNombrePlaga.getText().equals("Caracoles")) {
                    txt1.setText("100gr rinden 100m²");
                    txt3.setText("100gr rinden 100m²");
                    txt5.setText("100gr rinden 100m²");
                    txt7.setText("100gr rinden 100m²");

                    txt2.setText("Decisión propia");
                    txt4.setText("Decisión propia");
                    txt6.setText("Decisión propia");
                    txt8.setText("Decisión propia");
                }

        }

    }

}

