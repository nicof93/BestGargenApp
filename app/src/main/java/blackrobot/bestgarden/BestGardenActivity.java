package blackrobot.bestgarden;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.Window;
import android.widget.Toast;
import android.os.Handler;

import com.blackrobot.bestgarden.DaoMaster;


public class BestGardenActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_best_garden);

        SharedPreferences settings = getSharedPreferences(GlobalStore.PREFERENCES_NAME, 0);

        try {
            PackageInfo pckginfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int currentVersionCode = pckginfo.versionCode;
            GlobalStore.versionCode = currentVersionCode;
            Log.d("Actualizacion", "Version Code: " + currentVersionCode );

        }catch(PackageManager.NameNotFoundException e){
            Log.e("AutoUpdate", "Ha habido un error con el paquete ", e);

        }


        if(GlobalStore.versionCode == 5 && settings.getBoolean(GlobalStore.ACTUALIZACION3, true)){

            AsyncTask_DataBase asyncTask_dataBase = new AsyncTask_DataBase(this);
            asyncTask_dataBase.execute();

            Log.d("Actualizacion", "Funciono" );

            settings.edit().putBoolean(GlobalStore.ACTUALIZACION3, false).commit();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, FragmentConsultas.newInstance())
                .attach(FragmentConsultas.newInstance())
                .commit();

    }

    @Override
    public void onBackPressed() {
        int HistoryBack = getSupportFragmentManager().getBackStackEntryCount();

        if (HistoryBack == 0){
            new AlertDialog.Builder(this)
                    .setMessage("¿Deseas salir de Best Garden ahora?")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                            //onDestroy();//destrulle la actividada no la cierra
                        }
                    }).setNegativeButton("Cancelar", null).show();
        } else {
            super.onBackPressed();

        }
    }

    public void onClickHomeCenter(View v){
        //Intent open = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sodimac.cl/"));
        Intent open = new Intent(Intent.ACTION_VIEW, Uri.parse("http://goo.gl/VSWcjE"));
        startActivity(open);
    }

    //public void onClickMts(View v){
       // Intent open = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mts.cl/"));
       // startActivity(open);
    //}
}