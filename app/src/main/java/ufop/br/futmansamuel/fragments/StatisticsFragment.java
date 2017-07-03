package ufop.br.futmansamuel.fragments;

import ufop.br.futmansamuel.R;
import ufop.br.futmansamuel.activities.MainActivity;
import ufop.br.futmansamuel.adapters.ReciclerPlayerAdapter;
import ufop.br.futmansamuel.adapters.StatisticsAdapter;
import ufop.br.futmansamuel.listeners.RecyclerTouchListener;
import ufop.br.futmansamuel.other.DividerItemDecoration;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class StatisticsFragment extends Fragment {



    private RecyclerView recyclerView;
    private StatisticsAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_statistics, container, false);
        initRecyclerView(fragmentView);
        return fragmentView;

    }
    private void initRecyclerView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.rvPlayerStatistics);
        mAdapter = new StatisticsAdapter(MainActivity.players);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                Toast.makeText(getActivity().getApplicationContext(), MainActivity.players.get(position) + " is selected!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//
//            }
//        }));


    }



}
