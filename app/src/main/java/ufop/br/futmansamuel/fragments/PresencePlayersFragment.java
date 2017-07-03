package ufop.br.futmansamuel.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import ufop.br.futmansamuel.R;
import ufop.br.futmansamuel.activities.MainActivity;
import ufop.br.futmansamuel.adapters.PlayerPresenceCheckerAdapter;
import ufop.br.futmansamuel.adapters.ReciclerPlayerAdapter;
import ufop.br.futmansamuel.listeners.RecyclerTouchListener;
import ufop.br.futmansamuel.other.DividerItemDecoration;
import ufop.br.futmansamuel.other.PeladaManager;
import ufop.br.futmansamuel.other.PlayerInPelada;
import ufop.br.futmansamuel.other.Players;


public class PresencePlayersFragment extends Fragment {
    public static ArrayList<PlayerInPelada> playersInPeladaList;
    private RecyclerView recyclerView;
    public static PlayerPresenceCheckerAdapter mAdapter;
    private Button btnSortTeams;

    public static boolean orderByPresenceInverse=true;
    public static boolean orderByNickInverse=true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_verify_present_players_in_pelada, container, false);
        refreshMenu();
        initArrayPresentPlayers();
        initRecyclerView(fragmentView);
        initButtonListenet(fragmentView);
        return fragmentView;

    }

    private void initButtonListenet(View fragmentView) {
        btnSortTeams= (Button) fragmentView.findViewById(R.id.btnSortTeams);
        btnSortTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<PlayerInPelada> presentPlayers= new ArrayList<PlayerInPelada>();
                for(PlayerInPelada p:playersInPeladaList){
                    if(p.isPresentInPelada()){
                        presentPlayers.add(p);
                    }
                }
                MainActivity.peladaManager= new PeladaManager(getActivity().getApplicationContext(),presentPlayers);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                PeladaFragment fragment = new PeladaFragment();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commitAllowingStateLoss();

            }
        });
    }

    private void refreshMenu() {
        MainActivity.actualFragment=MainActivity.STATE_CHECK_PRESENCE_FRAGMENT;
        getActivity().invalidateOptionsMenu();
    }

    private void initRecyclerView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.lstPlayersToVerifyPresence);
        mAdapter = new PlayerPresenceCheckerAdapter(playersInPeladaList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        playersInPeladaList.get(position).setPresentInPelada(!playersInPeladaList.get(position).isPresentInPelada());
                        mAdapter.notifyItemChanged(position, playersInPeladaList.get(position));
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        Toast.makeText(getActivity().getApplicationContext(), "LongCLick", Toast.LENGTH_SHORT).show();
                    }


                })
        );


    }

    private void initArrayPresentPlayers() {
        playersInPeladaList = new ArrayList<>();
        for (Players p : MainActivity.players) {
            playersInPeladaList.add(new PlayerInPelada(p, true, 0));
//            Log.d("PLAYERS",p.toString());
        }
    }


}