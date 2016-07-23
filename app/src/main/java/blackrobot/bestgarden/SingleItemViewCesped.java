package blackrobot.bestgarden;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;

/**
 * Created by Mario on 15-08-2014.
 */
public class SingleItemViewCesped extends Activity {
    // Declare Variables
    TextView txtNombreCesped;
    ImageView imgImagenCesped;
    ImageView imgImagenProductoCesped;
    TextView txtProductoCesped;
    TextView txtObjetivo;
    TextView txtContenido;

    String[] productoCesped;
    String[] nombreCesped;
    String[] objetivoProductoCesped;
    String[] contenidoproducto;

    int[] imagenCesped;
    int[] imagenProductoCesped;
    int position;

    Context context;
    LayoutInflater inflater;

    TableLayout layoutAplicacion;
    RadioGroup rbg;
    RadioButton siembra;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleitemviewcesped);
        getActionBar().hide();
        context = this;
        // Retrieve data from MainActivity on listview item click
        Intent i = getIntent();
        // Get a single position
        position = i.getExtras().getInt("position");
        // Get the list of rank
        nombreCesped = i.getStringArrayExtra("NombreCesped");
        // Get the list of flag
        imagenCesped = i.getIntArrayExtra("");
        productoCesped = i.getStringArrayExtra("Producto Sugerido Cesped");
        imagenProductoCesped = i.getIntArrayExtra("IPPC");
        objetivoProductoCesped = i.getStringArrayExtra("ObjetivoC");
        contenidoproducto = i.getStringArrayExtra("Coontenido del producto");

        // Locate the TextViews in singleitemview_cesped.xml
        txtNombreCesped = (TextView) findViewById(R.id.txtnombreCesped);
        txtProductoCesped = (TextView) findViewById(R.id.txtproductoCesped);
        txtObjetivo = (TextView) findViewById(R.id.txtobjetivocesped);
        txtContenido = (TextView) findViewById(R.id.txtContenidoProducto);
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Locate the ImageView in singleitemviewcesped.xmled.xml
        imgImagenCesped = (ImageView) findViewById(R.id.ivimagencesped);
        imgImagenCesped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view1 = inflater.inflate(R.layout.zoom_image_view, null);
                ImageView imageView = (ImageView) view1.findViewById(R.id.imageZoom);
                imageView.setImageResource(imagenCesped[position]);
                builder.setView(view1);
                builder.setCancelable(true);
                builder.show();

            }
        });
        imgImagenProductoCesped = (ImageView) findViewById(R.id.imgimagenProductoCesped);

        imgImagenProductoCesped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Dialog builder = new Dialog(context);
                 builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                 View view1 = inflater.inflate(R.layout.zoom_image_view, null);
                 ImageView imageView = (ImageView) view1.findViewById(R.id.imageZoom);
                 imageView.setImageResource(imagenProductoCesped[position]);
                 builder.setContentView(view1);
                 builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                 builder.setCancelable(true);
                 builder.show();


            }
        });
        // Load the text into the TextViews followed by the position
        txtNombreCesped.setText(nombreCesped[position]);
        txtProductoCesped.setText(productoCesped[position]);
        txtObjetivo.setText(objetivoProductoCesped[position]);
        txtContenido.setText(contenidoproducto[position]);

        // Load the image into the ImageView followed by the position
        imgImagenCesped.setImageResource(imagenCesped[position]);
        imgImagenProductoCesped.setImageResource(imagenProductoCesped[position]);

        layoutAplicacion = (TableLayout) findViewById(R.id.lyAplicacionCesped);
        siembra = (RadioButton) findViewById(R.id.rbtSiembra);
        rbg = (RadioGroup) findViewById(R.id.rbtgAplicacionCesped);



        //SegmentedGroup segmented2 = (SegmentedGroup) findViewById(R.id.rbtgAplicacionCesped);
        //segmented2.setTintColor(Color.CYAN);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.animator.pop_enter_right, R.animator.pop_exit_right);
    }

    public void cambiodeLayout(View v){

        if(siembra.isChecked()){
            layoutAplicacion.setVisibility(View.VISIBLE);
        }
        else
        {
            layoutAplicacion.setVisibility(View.VISIBLE);
        }

    }
}
