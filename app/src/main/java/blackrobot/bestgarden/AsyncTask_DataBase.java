package blackrobot.bestgarden;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XD on 06/10/2014.
 */
public class AsyncTask_DataBase extends AsyncTask<Void, Long, Boolean> {


        private Context mContext;
        private final ProgressDialog mDialog;

        private ArrayList<String> valores;

        public AsyncTask_DataBase(Context context) {
            mContext = context;
            mDialog = new ProgressDialog(context);
            mDialog.setMessage("Cargado Datos. Esto puede tomar algunos segundos.");
            mDialog.setCancelable(false);
            mDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean result;

            try {
                GlobalStore.getDaoSession(mContext).getPlantaDao().deleteAll();
                GlobalStore.getDaoSession(mContext).getProductoDao().deleteAll();
                GlobalStore.getDaoSession(mContext).getAplicacionDao().deleteAll();
                GlobalStore.getDaoSession(mContext).getPlagaDao().deleteAll();
                //---- Precarga de datos
                GlobalStore.PrecargaPlanta(mContext);
                GlobalStore.PrecargaProducto(mContext);
                GlobalStore.PrecargaAplicacion(mContext);
                GlobalStore.PrecargaPlaga(mContext);
                Log.d("Actualizacion", "Funciono" );

                mDialog.dismiss();
                result = true;

            } catch (Exception e) {
                result=false;
            }

            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            mDialog.dismiss();
            if (result) {
                showToast("Carga exitosa");
            }
        }

        private void showToast(String msg) {
            Toast error = Toast.makeText(mContext, msg, Toast.LENGTH_LONG);
            error.show();
        }


    }

