package blackrobot.bestgarden;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Mario on 15-08-2014.
 */
public class ListViewAdapterCesped extends BaseAdapter {

    //Declaracion de variables
    Context context;
    String[] nombreCesped;
    String[] productoCesped;
    int[] imagenCesped;
    int[] imagenProductoCesped;
    String[] objetivoProductoCesped;
    String[] contenidoproducto;
    LayoutInflater inflater;

    public ListViewAdapterCesped(Context context, String[] nombreCesped, int[] imagenCesped, String[] productoCesped,
                                 int[] imagenProductoCesped, String[] objetivoProductoCesped, String[] contenidoproducto) {
        this.context = context;
        this.nombreCesped = nombreCesped;
        this.imagenCesped = imagenCesped;
        this.productoCesped = productoCesped;
        this.imagenProductoCesped = imagenProductoCesped;
        this.objetivoProductoCesped = objetivoProductoCesped;
        this.contenidoproducto = contenidoproducto;
    }

    @Override
    public int getCount() {
        return nombreCesped.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables
        TextView txtNombreCesped;
        ImageView ivimagencesped;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.listview_item_cesped, parent, false);

        // Locate the TextViews in listview_item_cesped.xmlped.xml
        txtNombreCesped = (TextView) itemView.findViewById(R.id.txtnombreCesped);
        // Locate the ImageView in listview_item_cesped.xmlped.xml
        ivimagencesped = (ImageView) itemView.findViewById(R.id.imgimagenCesped);

        // Capture position and set to the TextViews
        txtNombreCesped.setText(nombreCesped[position]);
        // Capture position and set to the ImageView
        ivimagencesped.setImageResource(imagenCesped[position]);

        return itemView;

    }
}
