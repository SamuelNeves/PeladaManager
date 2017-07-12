package ufop.br.futmansamuel.fragments;

import ufop.br.futmansamuel.R;
import ufop.br.futmansamuel.activities.MainActivity;
import ufop.br.futmansamuel.adapters.ReciclerPlayerAdapter;
import ufop.br.futmansamuel.listeners.RecyclerTouchListener;
import ufop.br.futmansamuel.other.DividerItemDecoration;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;


public class ListPlayersFragment extends Fragment {

    AlertDialog aDMenu;
    private ReciclerPlayerAdapter mAdapter;
    final String[] itemMenuDialog = {
            "Contact", "Edit", "Remove"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_list_players, container, false);
//        initListView(getActivity().getApplicationContext(), fragmentView);
        refreshMenu();
        initRecyclerView(fragmentView);
        return fragmentView;
    }

    private void refreshMenu() {
        MainActivity.actualFragment=MainActivity.STATE_LIST_PLAYER_FRAGMENT;
        getActivity().invalidateOptionsMenu();
    }

    private void initRecyclerView(View v) {
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.lstPlayers);
        mAdapter = new ReciclerPlayerAdapter(MainActivity.players);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
//                Toast.makeText(getActivity().getApplicationContext(), MainActivity.players.get(position) + " is selected!", Toast.LENGTH_SHORT).show();
//                showMenuDialog(view, position);
            }

            @Override
            public void onLongClick(View view, int position) {
                showMenuDialog(view, position);


            }
        }));


    }

    private void showMenuDialog(View v, final int positionOfSelectedItem) {
        aDMenu = new AlertDialog.Builder(getActivity())
//                .setTitle("Dialog Title")
                .setItems(itemMenuDialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface dialogInterface, int position) {

                        switch (position) {
                            case 0:
                                String phoneNumber= MainActivity.players.get(positionOfSelectedItem).getPhone();
                                openWhatsAppActivity(phoneNumber);
                                break;
                            case 1:
                                    openEditFragment(positionOfSelectedItem);
                                break;
                            case 2:
                                showAlertDialog(positionOfSelectedItem);
                                break;

                        }
                        String item = itemMenuDialog[position];
//                        Toast.makeText(getActivity().getApplicationContext(), "" + item, Toast.LENGTH_SHORT).show();
                    }
                })
                .create();

        WindowManager.LayoutParams wmlp = aDMenu.getWindow().getAttributes();
        wmlp.gravity = Gravity.TOP;
        wmlp.y = (int) v.getY();
        aDMenu.show();
    }

    private void openEditFragment(int positionToEdit) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        EditPlayerFragment fragment = EditPlayerFragment.newInstance(positionToEdit);
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commitAllowingStateLoss();

    }

    private void openWhatsAppActivity(String phoneNumber) {

        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(i, ""));
    }

    private void showAlertDialog(final int positionToRemove) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle("Remove Player")
                .setMessage("Do you really want to delete this player?")
                .setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(
                                    DialogInterface dialogInterface,
                                    int which) {
                                MainActivity.players.remove(positionToRemove);
                                mAdapter.notifyItemRemoved(positionToRemove);
                            }


                        })

                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }

                        ).create();
        alertDialog.show();
    }


}

