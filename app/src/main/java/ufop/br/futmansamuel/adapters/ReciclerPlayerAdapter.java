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

public class ReciclerPlayerAdapter extends RecyclerView.Adapter<ReciclerPlayerAdapter.MyViewHolder> {

    private ArrayList<Players> players;
    private Context context;

    public ReciclerPlayerAdapter(ArrayList<Players> players) {
        this.players = players;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtId, txtNick, txtPhone, txtNumberOfWins, txtNumberOfDefeats, txtWinrate;

        public MyViewHolder(View v) {
            super(v);
            txtId = (TextView) v.findViewById(R.id.txtListId);
            txtNick = (TextView) v.findViewById(R.id.txtListNick);
            txtPhone = (TextView) v.findViewById(R.id.txtListPhone);
//        txtNumberOfWins = (TextView) v.findViewById(R.id.txtListNumberOfWins);
//        txtNumberOfDefeats = (TextView) v.findViewById(R.id.txtListNumberOfDefeats);
            txtWinrate = (TextView) v.findViewById(R.id.txtListWinRate);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_row_player, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d("RECI",""+ players.get(position).toString());
        holder.txtId.setText(players.get(position).getId().toString());
        holder.txtNick.setText(players.get(position).getFullName());
        holder.txtPhone.setText(players.get(position).getPhone().toString());
//        txtNumberOfWins.setText(String.valueOf(player.getNumberOfWins()));
//        txtNumberOfDefeats.setText(String.valueOf(player.getNumberOfDefeats()));
        holder.txtWinrate.setText(players.get(position).getWinRate().toString());
    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
