package blackrobot.bestgarden;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Daniel on 03-08-2014.
 */
public class FragmentConsultas extends Fragment{

    private Context context;
    private LinearLayout mTratamiento;
    private LinearLayout mPlagas;
    private LinearLayout mPastos;
    private LinearLayout mPlagasHogar;
    private LinearLayout lnRatones;
    private LinearLayout lnMaleza;

    public static FragmentConsultas newInstance(){
        return new FragmentConsultas();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_consultas, container, false);
        mTratamiento =(LinearLayout) rootView.findViewById(R.id.lnTratamiento);
        mPlagas =(LinearLayout) rootView.findViewById(R.id.lnPlagas);
        mPastos = (LinearLayout) rootView.findViewById(R.id.lnPastos);
        mPlagasHogar = (LinearLayout) rootView.findViewById(R.id.lnPlagasHogar);
        lnRatones = (LinearLayout) rootView.findViewById(R.id.lnRatones);
        lnMaleza = (LinearLayout) rootView.findViewById(R.id.lnMaleza);


        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.animator.enter_right, R.animator.exit_right, R.animator.pop_enter_right, R.animator.pop_exit_right);

        mTratamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ft.replace(R.id.container, FragmentTratamiento.newInstance());
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        mPlagas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ft.replace(R.id.container, FragmentPlagas.newInstance());
                ft.addToBackStack(null);
                ft.commit();
            }
        });
//
        mPastos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ft.replace(R.id.container, FragmentCesped.newInstance());
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        mPlagasHogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ft.replace(R.id.container,FragmentHogar.newInstance());
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        lnRatones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ft.replace(R.id.container, FragmentRatones.newInstance());
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        lnMaleza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ft.replace(R.id.container, FragmentMalezas.newInstance());
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return rootView;
    }



}



