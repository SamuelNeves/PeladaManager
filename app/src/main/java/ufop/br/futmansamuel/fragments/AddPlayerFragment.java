package ufop.br.futmansamuel.fragments;

import ufop.br.futmansamuel.R;
import ufop.br.futmansamuel.activities.MainActivity;
import ufop.br.futmansamuel.other.Players;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddPlayerFragment extends Fragment {
    Button btnSubmit,btnCancel;
    EditText txtId,txtFirstName,txtLastName,txtNick,txtEmail,txtPhone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView =inflater.inflate(R.layout.fragment_add_player, container, false);
        refreshMenu();
        initComponentsFromXml(fragmentView);
        setOnClickListenersOfWidgets();
        return fragmentView;
    }

    private void refreshMenu() {
        MainActivity.actualFragment=MainActivity.STATE_ADD_FRAGMENT;
        getActivity().invalidateOptionsMenu();
    }


    void initComponentsFromXml(View v){

        btnCancel=(Button)v.findViewById(R.id.btnAddCancel);
        btnSubmit=(Button)v.findViewById(R.id.btnAddSubmit);

        txtId= (EditText) v.findViewById(R.id.txtAddId);
        txtId.setText(String.valueOf(MainActivity.players.size()+1));
        txtFirstName= (EditText) v.findViewById(R.id.txtAddFirstName);
        txtLastName= (EditText) v.findViewById(R.id.txtAddLastName);
        txtNick= (EditText) v.findViewById(R.id.txtAddNick);
        txtEmail= (EditText) v.findViewById(R.id.txtAddEmail);
        txtPhone= (EditText) v.findViewById(R.id.txtAddTelephone);

    }
    private Players getPlayerFromScreenElements(){
        Players player= new Players();
        player.setId(txtId.getText().toString());
        player.setFirstName(txtFirstName.getText().toString());
        player.setLastName(txtLastName.getText().toString());
        player.setPhone(txtPhone.getText().toString());
        player.setNickName(txtNick.getText().toString());
        player.setEmail(txtEmail.getText().toString());
        return player;

    }

    private void goToHomeFragment(){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        HomeFragment  fragment = new HomeFragment();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }
    void setOnClickListenersOfWidgets(){
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Here","here");
                Toast.makeText(getActivity().getApplicationContext(), "Player  Added!", Toast.LENGTH_SHORT).show();
                MainActivity.players.add(getPlayerFromScreenElements());
                goToHomeFragment();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Operation Canceled", Toast.LENGTH_SHORT).show();
                goToHomeFragment();
            }
        });


    }
}
