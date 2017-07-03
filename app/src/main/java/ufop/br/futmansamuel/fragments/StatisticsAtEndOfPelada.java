package ufop.br.futmansamuel.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ufop.br.futmansamuel.R;
import ufop.br.futmansamuel.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsAtEndOfPelada extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_statistics_at_end_of_pelada, container, false);
        refreshMenu();
        return fragmentView;
    }

    private void refreshMenu() {
        MainActivity.actualFragment = MainActivity.STATE_STATISTICS_FRAGMENT;
        getActivity().invalidateOptionsMenu();
    }

}
