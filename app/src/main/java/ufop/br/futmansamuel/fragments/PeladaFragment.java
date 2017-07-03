package ufop.br.futmansamuel.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import ufop.br.futmansamuel.R;
import ufop.br.futmansamuel.activities.MainActivity;
import ufop.br.futmansamuel.adapters.PlayersOfTeamInGameAdapter;
import ufop.br.futmansamuel.listeners.RecyclerTouchListener;
import ufop.br.futmansamuel.other.DividerItemDecoration;
import ufop.br.futmansamuel.other.PeladaManager;
import ufop.br.futmansamuel.other.PlayerInPelada;

public class PeladaFragment extends Fragment {
    String[] teste = {"Teste1", "Teste2", "Teste3", "Teste4", "Teste5", "Teste6", "Teste7"};
    private PlayersOfTeamInGameAdapter mAdapter1;
    private PlayersOfTeamInGameAdapter mAdapter2;
    private PlayersOfTeamInGameAdapter mAdapterSubs;
    RecyclerView lstTeamOne;
    RecyclerView lstTeamTwo;
    RecyclerView lstSubs;

    Chronometer crono;
    Button btnStart, btnPause, btnEndGame;
    android.support.v7.app.AlertDialog aDMenu;
    PeladaManager peladaManager;
    MediaPlayer mp;
    private long elapsedTime;
    private boolean isRunningChronometer = false;
    private boolean alarmElapsed = false;
    final String[] itemMenuDialogTeam = {"Add Goal", "Remove"};
    final String[] itemMenuDialogSubs = {"Add to Team 1", "Add to Team 2"};

    //    final String[] itemMenuDialog = {
//            getActivity().getString(R.string.add_goal),  getActivity().getString(R.string.remove_player_team)};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_pelada, container, false);
        refreshMenu();
        peladaManager = MainActivity.peladaManager;
        initElementsFromXml(fragmentView);
        initRecycleViews(fragmentView);
        initMediaPlayer();
        return fragmentView;
    }

    private void initRecycleViews(View v) {
        mAdapter1 = new PlayersOfTeamInGameAdapter(peladaManager.getPelada().getTeam1());
        mAdapter2 = new PlayersOfTeamInGameAdapter(peladaManager.getPelada().getTeam2());
        mAdapterSubs = new PlayersOfTeamInGameAdapter(peladaManager.getPelada().getSubstitutes());

        lstTeamOne = (RecyclerView) v.findViewById(R.id.lstViewTeamOne);
        lstTeamTwo = (RecyclerView) v.findViewById(R.id.lstViewTeamTwo);
        lstSubs = (RecyclerView) v.findViewById(R.id.lstViewSubstitutes);
        RecyclerView.LayoutManager mLayoutManager;

        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        lstTeamOne.setLayoutManager(mLayoutManager);
        lstTeamOne.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL));
        lstTeamOne.setItemAnimator(new DefaultItemAnimator());
        lstTeamOne.setAdapter(mAdapter1);
        lstTeamOne.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), lstTeamOne, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
//                Toast.makeText(getActivity().getApplicationContext(), MainActivity.players.get(position) + " is selected!", Toast.LENGTH_SHORT).show();
                showMenuDialogTeam(view, position,1);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));


        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        lstTeamTwo.setLayoutManager(mLayoutManager);
        lstTeamTwo.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL));
        lstTeamTwo.setItemAnimator(new DefaultItemAnimator());
        lstTeamTwo.setAdapter(mAdapter2);
        lstTeamTwo.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), lstTeamTwo, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                showMenuDialogTeam(view, position,2);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));


        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        lstSubs.setLayoutManager(mLayoutManager);
        lstSubs.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL));
        lstSubs.setItemAnimator(new DefaultItemAnimator());
        lstSubs.setAdapter(mAdapterSubs);
        lstSubs.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), lstSubs, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                    showMenuDialogSubs(view, position);
            }

            @Override
            public void onLongClick(View view, int position) {


            }
        }));

    }

    private void refreshMenu() {
        MainActivity.actualFragment = MainActivity.STATE_PELADA_FRAGMENT;
        getActivity().invalidateOptionsMenu();
    }

    private void initMediaPlayer() {
        try {
            mp = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.whistle);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void initElementsFromXml(View v) {
        crono = (Chronometer) v.findViewById(R.id.chrono);
        btnStart = (Button) v.findViewById(R.id.btnPeladaStart);
        btnPause = (Button) v.findViewById(R.id.btnPeladaPause);
        btnEndGame = (Button) v.findViewById(R.id.btnPeladaEnd);
        initListernerOfButtons();
        initListernerOfChronometer(peladaManager.getMaxDurationOfPelada());
    }

    private void initListernerOfChronometer(final long timeToAlert) {
        crono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() > chronometer.getBase() + 3000) && !alarmElapsed) {
                    alarmElapsed = true;
                    pauseChronometer();
                    mp.start();
                }
            }
        });
    }

    private void initListernerOfButtons() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isRunningChronometer) {
                    crono.setBase(SystemClock.elapsedRealtime() + elapsedTime);
                    crono.start();
                    isRunningChronometer = true;
                } else {
                    crono.setText("Start");
                }
                btnPause.setEnabled(true);
                btnStart.setEnabled(false);

            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseChronometer();

            }
        });

        btnEndGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndDialog();
            }
        });
    }


    private void showMenuDialogTeam(View v, final int positionOfSelectedItem, final int team) {
        aDMenu = new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setItems(itemMenuDialogTeam, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface dialogInterface, int position) {

                        switch (position) {
                            case 0:
//                                TODO
                                break;
                            case 1:
                               removePlayerFromTeam(team,positionOfSelectedItem);
                                break;

                        }

                        String item = itemMenuDialogTeam[position];
                        Toast.makeText(getActivity().getApplicationContext(), "" + item, Toast.LENGTH_SHORT).show();
                    }
                })
                .create();
        WindowManager.LayoutParams wmlp = aDMenu.getWindow().getAttributes();
//        wmlp.gravity = Gravity.TOP;
        wmlp.y = (int) v.getY();
        wmlp.x = (int) v.getX();
        aDMenu.show();
    }


    private void showMenuDialogSubs(View v, final int positionOfSelectedItem) {
        aDMenu = new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setItems(itemMenuDialogSubs, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface dialogInterface, int position) {

                        switch (position) {
                            case 0:
//                                TODO
                                break;
                            case 1:
                                break;

                        }

                        String item = itemMenuDialogTeam[position];
                        Toast.makeText(getActivity().getApplicationContext(), "" + item, Toast.LENGTH_SHORT).show();
                    }
                })
                .create();
        WindowManager.LayoutParams wmlp = aDMenu.getWindow().getAttributes();
//        wmlp.gravity = Gravity.TOP;
//        wmlp.horizontalWeight=30;
        wmlp.y = (int) v.getY();
        wmlp.x = (int) v.getX();
        aDMenu.show();
    }

    private void pauseChronometer() {
        if (isRunningChronometer) {
            btnStart.setEnabled(true);
            btnStart.setText("Continue");
            crono.stop();
            btnStart.setEnabled(true);
            btnPause.setEnabled(false);
            isRunningChronometer = false;
            elapsedTime = crono.getBase() - SystemClock.elapsedRealtime();
        }
    }

    private void removePlayerFromTeam(int team, int position) {
        PlayerInPelada playerToEnter=peladaManager.getPelada().getSubstitutes().getPlayers().remove(0);
        if (team == 1) {
            PlayerInPelada playerToExit =peladaManager.getPelada().getTeam1().getPlayers().remove(position);
            peladaManager.getPelada().getTeam1().getPlayers().add(playerToEnter);
            peladaManager.getPelada().getSubstitutes().getPlayers().add(playerToExit);
            mAdapter1.notifyDataSetChanged();
        } else {
            PlayerInPelada playerToExit =peladaManager.getPelada().getTeam2().getPlayers().remove(position);
            peladaManager.getPelada().getTeam2().getPlayers().add(playerToEnter);
            peladaManager.getPelada().getSubstitutes().getPlayers().add(playerToExit);
            mAdapter2.notifyDataSetChanged();
        }
            mAdapterSubs.notifyDataSetChanged();
    }

    private void showEndDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle("End Pelada")
                .setMessage("Do you really want to end this pelada?")
                .setPositiveButton("End",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                StatisticsAtEndOfPelada f = new StatisticsAtEndOfPelada();
                                MainActivity.transitionToNewFragment(f, getActivity());
                            }

                        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity().getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
                    }
                })
                .create();
        alertDialog.show();
    }
}
