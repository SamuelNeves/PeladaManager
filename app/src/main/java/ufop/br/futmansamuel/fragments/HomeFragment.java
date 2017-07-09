package ufop.br.futmansamuel.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ufop.br.futmansamuel.R;
import ufop.br.futmansamuel.activities.MainActivity;


public class HomeFragment extends Fragment {
    private Button btnStartPelada;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_home, container, false);
        refreshMenu();
        initElementsFromXml(fragmentView);
        return fragmentView;

    }

    private void refreshMenu() {
        MainActivity.actualFragment=MainActivity.STATE_HOME_FRAGMENT;
        getActivity().invalidateOptionsMenu();
    }


    private void  initElementsFromXml(View v){
        btnStartPelada= (Button) v.findViewById(R.id.btnStartPelada);
        btnStartPelada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                PresencePlayersFragment fragment = new PresencePlayersFragment();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commitAllowingStateLoss();
            }
        });


    }
}
