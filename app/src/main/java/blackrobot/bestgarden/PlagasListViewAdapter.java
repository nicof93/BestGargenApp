package blackrobot.bestgarden;

import android.app.AlertDialog;
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
public class PlagasListViewAdapter extends android.widget.BaseAdapter {

    //Declaracion de variables
    Context context;
    String[] nombrePlaga;
    String[] productoPlaga;
    int[] imagenPlaga;
    int[] imagenProductoPlaga;
    String[] objetivoProductoPlaga;
    LayoutInflater inflater;

    public PlagasListViewAdapter(Context context, String[] nombrePlaga, int[] imagenPlaga, String[] productoPlaga,
                                 int[] imagenProductoPlaga, String[] objetivoProductoPlaga) {
        this.context = context;
        this.nombrePlaga = nombrePlaga;
        this.imagenPlaga = imagenPlaga;
        this.productoPlaga = productoPlaga;
        this.imagenProductoPlaga = imagenProductoPlaga;
        this.objetivoProductoPlaga = objetivoProductoPlaga;
    }

    @Override
    public int getCount() {
        return nombrePlaga.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        // Declare Variables
        TextView txtnombrePlaga;
        ImageView imgimagenPlaga;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.mantenedor_listview_item, parent, false);

        // Locate the TextViews in listview_item.xml
        txtnombrePlaga = (TextView) itemView.findViewById(R.id.txtnombrePlaga);
        // Locate the ImageView in listview_item.xml
        imgimagenPlaga = (ImageView) itemView.findViewById(R.id.imgimagenPlaga);

        // Capture position and set to the TextViews
        txtnombrePlaga.setText(nombrePlaga[position]);
        // Capture position and set to the ImageView
        imgimagenPlaga.setImageResource(imagenPlaga[position]);

       /** imgimagenPlaga.setOnClickListener(new View.OnClickListener() {
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
        });**/

        return itemView;

    }
}
