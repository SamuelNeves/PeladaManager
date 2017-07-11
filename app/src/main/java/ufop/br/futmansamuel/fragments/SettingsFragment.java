package ufop.br.futmansamuel.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import ufop.br.futmansamuel.R;
import ufop.br.futmansamuel.activities.MainActivity;
import ufop.br.futmansamuel.other.PeladaManager;

public class SettingsFragment extends Fragment {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPrefEditor;

    private Spinner spnDuration, spnNumberOfPlayers;
    private Button btnSubmit, btnCancel;
    private long durationOfPelada;
    private int numberOfPlayers, minDuration = 5, minNumberOfPlayers=5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_settings, container, false);
        refreshMenu();
        configSharedPreferences();
        initElementsFromXml(fragmentView);
        return fragmentView;
    }

    private void refreshMenu() {
        MainActivity.actualFragment=MainActivity.STATE_SETTINGS_FRAGMENT;
        getActivity().invalidateOptionsMenu();
    }

    private void initElementsFromXml(View v) {
        spnDuration = (Spinner) v.findViewById(R.id.spnDuration);
        spnDuration.setSelection((int) ((durationOfPelada/60000)-minDuration));
        spnNumberOfPlayers = (Spinner) v.findViewById(R.id.spnNumberOfPlayers);
        spnNumberOfPlayers.setSelection((int) (numberOfPlayers)-minNumberOfPlayers);
        btnCancel = (Button) v.findViewById(R.id.btnSettingsCancel);
        btnSubmit = (Button) v.findViewById(R.id.btnSettingsSubmit);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TODO implement onclick Cancel
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOnSharedPreferences();

            }
        });

        spnDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                durationOfPelada = (position + minDuration) * 60000;
//                Toast.makeText(getActivity().getApplicationContext(), "" + durationOfPelada, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnNumberOfPlayers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numberOfPlayers = position + minNumberOfPlayers;
//                Toast.makeText(getActivity().getApplicationContext(), "" + numberOfPlayers, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
private void saveOnSharedPreferences(){
    sharedPrefEditor.putLong("duration",durationOfPelada);
    sharedPrefEditor.putInt("number_players_team",numberOfPlayers);
    sharedPrefEditor.commit();
    Toast.makeText(getActivity().getApplicationContext(), "Settings Saved!", Toast.LENGTH_SHORT).show();
}
    private void configSharedPreferences() {
        sharedPreferences = getActivity().getSharedPreferences(PeladaManager.PELADASETTINGSPREFSNAME,
                Context.MODE_PRIVATE);
        sharedPrefEditor = sharedPreferences.edit();
        durationOfPelada=sharedPreferences.getLong("duration",7*60000);
        numberOfPlayers =sharedPreferences.getInt("number_players_team",7);
    }

}