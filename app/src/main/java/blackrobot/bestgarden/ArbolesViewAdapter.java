package blackrobot.bestgarden;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Nicolas on 27/08/2014.
 */
public class ArbolesViewAdapter extends BaseAdapter{

    Context context;
    String[] nombrePlanta;
    int[] imagenPlanta;
    LayoutInflater inflater;

    public ArbolesViewAdapter(Context context, String[] nombrePlanta, int[] imagenPlanta) {
        this.context = context;
        this.nombrePlanta = nombrePlanta;
        this.imagenPlanta = imagenPlanta;
    }


    @Override
    public int getCount() {
        return nombrePlanta.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView (final int position, View convertView, ViewGroup parent) {

        // Declare Variables
        TextView txtnombrePlanta;
        ImageView imgimagenPlanta;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.mantenedor_listview_item, parent, false);

        // Locate the TextViews in listview_item.xml
        txtnombrePlanta = (TextView) itemView.findViewById(R.id.txtnombrePlaga);
        // Locate the ImageView in listview_item.xml
        imgimagenPlanta = (ImageView) itemView.findViewById(R.id.imgimagenPlaga);

        // Capture position and set to the TextViews
        txtnombrePlanta.setText(nombrePlanta[position]);
        // Capture position and set to the ImageView
        imgimagenPlanta.setImageResource(imagenPlanta[position]);

        return itemView;

    }
}
