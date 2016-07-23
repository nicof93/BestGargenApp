package blackrobot.bestgarden;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.blackrobot.bestgarden.Aplicacion;
import com.blackrobot.bestgarden.AplicacionDao;
import com.blackrobot.bestgarden.DaoMaster;
import com.blackrobot.bestgarden.DaoSession;
import com.blackrobot.bestgarden.Plaga;
import com.blackrobot.bestgarden.PlagaDao;
import com.blackrobot.bestgarden.Planta;
import com.blackrobot.bestgarden.PlantaDao;
import com.blackrobot.bestgarden.Producto;
import com.blackrobot.bestgarden.ProductoDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Daniel on 03-08-2014.
 */
public class GlobalStore extends Application {
    //---- preferencias
    private static GlobalStore mInstance;
    public static SQLiteDatabase database;
    private static final String DB_NAME_X = "best_garden-db";
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    public static boolean actualizacion1 = false;
    public static int versionCode;
    public static Producto productoEspecifico;
    public static Planta plantaConsultada;
    public static List<Aplicacion> dosificacion;
    public static Plaga plagaHogar;
    public static List<Aplicacion> dosificacionHogar;

    public static final String FIRST_TIME_RUN = "first_time_run";
    public static final String ACTUALIZACION3 = "actualizacion3";
    public static final String PREFERENCES_NAME = "PREFERENCIAS";
    private static BestGardenActivity bestGardenActivity;

    public static Fragment getVisibleFragment() {
        FragmentManager fragmentManager = bestGardenActivity.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible()) {
                return fragment;
            }
        }
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (mInstance == null)
            mInstance = this;

        // Initialize the singletons so their instances
        // are bound to the application process.
    }

    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {

            //ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(context, DB_NAME);
            //database = dbOpenHelper.openDataBase();

            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME_X, null);
            database = helper.getWritableDatabase();

            daoMaster = new DaoMaster(database);

        }
        return daoMaster;
    }


    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    public static String loadJSONFromAsset(String jsonFileName, Context context) {
        String json = null;
        try {

            InputStream is = context.getAssets().open(jsonFileName);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static void PrecargaPlanta(Context context) {

        try {

            String stringJson = loadJSONFromAsset("Plantas.json", context);

            JSONArray arrayJson = new JSONArray(stringJson);

            for (int i = 0; i < arrayJson.length(); i++) {
                JSONObject planta = arrayJson.getJSONObject(i);

                Planta objPlanta = new Planta(null,i, planta.getString("nombre"), planta.getString("descripcion"));

                PlantaDao plantaDao = GlobalStore.getDaoSession(context).getPlantaDao();
                plantaDao.insert(objPlanta);

                Log.d("OK Planta-->", planta.getString("nombre"));
            }
        } catch (JSONException x) {
            x.printStackTrace();
            Log.i("CARGA PLANTAS", "Error al cargar los plantas: " + x.getMessage());
        }

    }

    public static void PrecargaProducto(Context context) {

        try {

            String stringJson = loadJSONFromAsset("Productos_plantas.json", context);

            JSONArray arrayJson = new JSONArray(stringJson);

            for (int i = 0; i < arrayJson.length(); i++) {
                JSONObject producto = arrayJson.getJSONObject(i);

                Producto objProducto = new Producto(null,i, producto.getString("nombre"), producto.getString("objetivo"), producto.getString("tipo"), producto.getString("link"));

                ProductoDao productoDao = GlobalStore.getDaoSession(context).getProductoDao();
                productoDao.insert(objProducto);

                Log.d("OK PRODUCTO_PLANTA-->", producto.getString("nombre"));
            }
        } catch (JSONException x) {
            x.printStackTrace();
            Log.i("CARGA PRODUCTO_PLANTAS", "Error al cargar los plantas: " + x.getMessage());
        }

    }

    public static void PrecargaAplicacion(Context context) {

        try {

            String stringJson = loadJSONFromAsset("Aplicacion.json", context);

            JSONArray arrayJson = new JSONArray(stringJson);

            for (int i = 0; i < arrayJson.length(); i++) {
                JSONObject aplicacion = arrayJson.getJSONObject(i);

                Aplicacion objAplicacion;
                if(aplicacion.getString("id_planta").isEmpty())
                {
                    objAplicacion = new Aplicacion(null,i, aplicacion.getString("epoca_anual"), aplicacion.getString("dosificacion"),aplicacion.getString("frecuencia"),
                            aplicacion.getString("etapa"), null , aplicacion.getInt("id_plaga") ,aplicacion.getInt("id_producto"));
                }else
                {
                    objAplicacion = new Aplicacion(null,i, aplicacion.getString("epoca_anual"), aplicacion.getString("dosificacion"),aplicacion.getString("frecuencia"),
                            aplicacion.getString("etapa"), aplicacion.getInt("id_planta") , null ,aplicacion.getInt("id_producto"));
                }

                AplicacionDao aplicacionDao = GlobalStore.getDaoSession(context).getAplicacionDao();
                aplicacionDao.insert(objAplicacion);

                Log.d("OK Aplicacion-->", "App" + i + "  " + aplicacion.getString("etapa"));
            }
        } catch (JSONException x) {
            x.printStackTrace();
            Log.i("CARGA APLICACION", "Error al cargar la aplicacion: " + x.getMessage());
        }

    }

    public static void PrecargaPlaga(Context context) {

        try {

            String stringJson = loadJSONFromAsset("Plagas.json", context);

            JSONArray arrayJson = new JSONArray(stringJson);

            for (int i = 0; i < arrayJson.length(); i++) {
                JSONObject plaga = arrayJson.getJSONObject(i);

                Plaga objPlaga = new Plaga(null,i, plaga.getString("nombre_plaga"), plaga.getString("descripción plaga"));

                PlagaDao plagaDao = GlobalStore.getDaoSession(context).getPlagaDao();
                plagaDao.insert(objPlaga);

                Log.d("OK Plaga-->", plaga.getString("nombre_plaga"));
            }
        } catch (JSONException x) {
            x.printStackTrace();
            Log.i("CARGA PLAGAS HOGAR", "Error al cargar los insectos: " + x.getMessage());
        }

    }

}