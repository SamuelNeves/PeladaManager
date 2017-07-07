package ufop.br.futmansamuel.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ufop.br.futmansamuel.R;
import ufop.br.futmansamuel.activities.MainActivity;
import ufop.br.futmansamuel.other.Players;
import ufop.br.futmansamuel.other.Validator;

public class EditPlayerFragment extends Fragment {
    Button btnSubmit, btnCancel;
    EditText txtId, txtFirstName, txtLastName, txtNick, txtEmail, txtPhone;
    TextInputLayout tilId, tilFirstN, tilLastN, tilNick, tilEmail, tilPhone;
    private int positionToEdit;

    //Stack Over Flow salvando
    public static EditPlayerFragment newInstance(int position) {

        EditPlayerFragment fragment = new EditPlayerFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_edit_player, container, false);
        initComponentsFromXml(fragmentView);
        positionToEdit = getArguments().getInt("position");
        fillTextLists();
        initListeners();
        return fragmentView;

    }


    private void goToHomeFragment() {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        HomeFragment fragment = new HomeFragment();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void initListeners() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHomeFragment();
            }
        });
    }

    private void fillTextLists() {

        txtId.setText(MainActivity.players.get(positionToEdit).getId());
        txtFirstName.setText(MainActivity.players.get(positionToEdit).getFirstName());
        txtLastName.setText(MainActivity.players.get(positionToEdit).getLastName());
        txtNick.setText(MainActivity.players.get(positionToEdit).getNickName());
        txtEmail.setText(MainActivity.players.get(positionToEdit).getEmail());
        txtPhone.setText(MainActivity.players.get(positionToEdit).getPhone());
    }


    private void updatePlayerFromScreenElements() {
        Players player = MainActivity.players.get(positionToEdit);
        player.setId(txtId.getText().toString());
        player.setFirstName(txtFirstName.getText().toString());
        player.setLastName(txtLastName.getText().toString());
        player.setPhone(txtPhone.getText().toString());
        player.setNickName(txtNick.getText().toString());
        player.setEmail(txtEmail.getText().toString());
        Toast.makeText(getActivity().getApplicationContext(), "Player Updated", Toast.LENGTH_SHORT).show();
        goToListPlayersFragment();

    }

    private void goToListPlayersFragment() {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        ListPlayersFragment fragment = new ListPlayersFragment();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }


    private void initComponentsFromXml(View v) {


        btnCancel = (Button) v.findViewById(R.id.btnEditCancel);
        btnSubmit = (Button) v.findViewById(R.id.btnEditSubmit);

        txtId = (EditText) v.findViewById(R.id.txtEditId);
        txtFirstName = (EditText) v.findViewById(R.id.txtEditFirstName);
        txtLastName = (EditText) v.findViewById(R.id.txtEditLastName);
        txtNick = (EditText) v.findViewById(R.id.txtEditNick);
        txtEmail = (EditText) v.findViewById(R.id.txtEditEmail);
        txtPhone = (EditText) v.findViewById(R.id.txtEditTelephone);

        tilId = (TextInputLayout) v.findViewById(R.id.tilIdEdit);
        tilFirstN = (TextInputLayout) v.findViewById(R.id.tilFirstNameEdit);
        tilLastN = (TextInputLayout) v.findViewById(R.id.tilLastNameEdit);
        tilNick = (TextInputLayout) v.findViewById(R.id.tilNickEdit);
        tilEmail = (TextInputLayout) v.findViewById(R.id.tilEmailEdit);
        tilPhone = (TextInputLayout) v.findViewById(R.id.tilPhoneEdit);
    }

    private void submitForm() {
        Log.d("Here", "here");

        if (!Validator.validadeID(txtId.getText().toString())) {
            tilId.setError("Invalid Id");
            return;
        } else {
            tilId.setErrorEnabled(false);
        }
        if (!Validator.validateFirstName(txtFirstName.getText().toString())) {
            tilFirstN.setError("Please insert your first name");
            return;
        } else {
            tilFirstN.setErrorEnabled(false);
        }
        if (!Validator.validateLastName(txtLastName.getText().toString())) {
            tilLastN.setError("Please insert your  last name");
            return;

        } else {
            tilLastN.setErrorEnabled(false);
        }
        if (!Validator.validateNickName(txtNick.getText().toString())) {
            tilNick.setError("Please insert your nick");
            return;
        } else {
            tilNick.setErrorEnabled(false);
        }

        if (!Validator.validateEmail(txtEmail.getText().toString())) {
            tilEmail.setError("Invalid Email");
            return;
        } else {
            tilEmail.setErrorEnabled(false);
        }

        if (!Validator.validateTelephone(txtPhone.getText().toString())) {
            tilPhone.setError("Invalid Phone");
            return;
        } else {
            tilPhone.setErrorEnabled(false);
        }

        Toast.makeText(getActivity().getApplicationContext(), "Player  Edited!", Toast.LENGTH_SHORT).show();
        updatePlayerFromScreenElements();
        goToListPlayersFragment();
    }


}