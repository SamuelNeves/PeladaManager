package ufop.br.futmansamuel.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ufop.br.futmansamuel.R;
import ufop.br.futmansamuel.other.Players;

/**
 * @author Samuel Neves
 *         Created on 26/06/17.
 */

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.MyViewHolder> {

    private ArrayList<Players> players;
    private Context context;

    public StatisticsAdapter(ArrayList<Players> players) {
        this.players = players;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNick, txtGoals,txtWinrate,txtLoses,txtPP, txtWins;

        public MyViewHolder(View v) {
            super(v);
            txtNick = (TextView) v.findViewById(R.id.txtPlayerStatsNick);
            txtGoals = (TextView) v.findViewById(R.id.txtPlayerStatsGoals);
            txtWinrate = (TextView) v.findViewById(R.id.txtPlayerStatsWinrate);
            txtWins = (TextView) v.findViewById(R.id.txtPlayerStatsWins);
            txtLoses = (TextView) v.findViewById(R.id.txtPlayerStatsLoses);
            txtPP = (TextView) v.findViewById(R.id.txtPlayerStatsPP);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_row_player_statistics, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.txtNick.setText(players.get(position).getNickName());
        holder.txtWinrate.setText(players.get(position).getWinRate());
        holder.txtGoals.setText(String.valueOf(players.get(position).getNumberOfGoals()));
        holder.txtWins.setText(String.valueOf(players.get(position).getNumberOfWins()));
        holder.txtLoses.setText(String.valueOf(players.get(position).getNumberOfDefeats()));
        holder.txtPP.setText(String.valueOf(players.get(position).getTotalOfGames()));
    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
