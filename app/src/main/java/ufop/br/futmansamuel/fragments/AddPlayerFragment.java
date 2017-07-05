package ufop.br.futmansamuel.fragments;

import ufop.br.futmansamuel.R;
import ufop.br.futmansamuel.activities.MainActivity;
import ufop.br.futmansamuel.other.Players;
import ufop.br.futmansamuel.other.Validator;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddPlayerFragment extends Fragment {
    Button btnSubmit, btnCancel;
    EditText txtId, txtFirstName, txtLastName, txtNick, txtEmail, txtPhone;
    TextInputLayout tilId, tilFirstN, tilLastN, tilNick, tilEmail, tilPhone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_add_player, container, false);
        refreshMenu();
        initComponentsFromXml(fragmentView);
        setOnClickListenersOfWidgets();
        return fragmentView;
    }

    private void refreshMenu() {
        MainActivity.actualFragment = MainActivity.STATE_ADD_FRAGMENT;
        getActivity().invalidateOptionsMenu();
    }

    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    void initComponentsFromXml(View v) {

        btnCancel = (Button) v.findViewById(R.id.btnAddCancel);
        btnSubmit = (Button) v.findViewById(R.id.btnAddSubmit);

        txtId = (EditText) v.findViewById(R.id.txtAddId);
        txtId.setText(String.valueOf(MainActivity.players.size() + 1));
        txtFirstName = (EditText) v.findViewById(R.id.txtAddFirstName);
        txtLastName = (EditText) v.findViewById(R.id.txtAddLastName);
        txtNick = (EditText) v.findViewById(R.id.txtAddNick);
        txtEmail = (EditText) v.findViewById(R.id.txtAddEmail);
        txtPhone = (EditText) v.findViewById(R.id.txtAddTelephone);

        tilId = (TextInputLayout) v.findViewById(R.id.tilId);
        tilFirstN = (TextInputLayout) v.findViewById(R.id.tilFirstName);
        tilLastN = (TextInputLayout) v.findViewById(R.id.tilLastName);
        tilNick = (TextInputLayout) v.findViewById(R.id.tilNick);
        tilEmail = (TextInputLayout) v.findViewById(R.id.tilEmail);
        tilPhone = (TextInputLayout) v.findViewById(R.id.tilPhone);


    }

    private Players getPlayerFromScreenElements() {
        Players player = new Players();
        player.setId(txtId.getText().toString());
        player.setFirstName(txtFirstName.getText().toString());
        player.setLastName(txtLastName.getText().toString());
        player.setPhone(txtPhone.getText().toString());
        player.setNickName(txtNick.getText().toString());
        player.setEmail(txtEmail.getText().toString());
        return player;

    }

    private void goToHomeFragment() {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        HomeFragment fragment = new HomeFragment();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    void setOnClickListenersOfWidgets() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             Log.d("Here", "here");
                                             submitForm();
                                         }
                                     }

        );

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Operation Canceled", Toast.LENGTH_SHORT).show();
                goToHomeFragment();
            }
        });


    }

    private void submitForm() {
        Log.d("Here", "here");
        hideKeyboard();

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

        Toast.makeText(getActivity().getApplicationContext(), "Player  Added!", Toast.LENGTH_SHORT).show();
        MainActivity.players.add(getPlayerFromScreenElements());
        goToHomeFragment();
    }

}



