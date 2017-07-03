package ufop.br.futmansamuel.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ufop.br.futmansamuel.R;
import ufop.br.futmansamuel.other.PlayerInPelada;

/**
 * @author Samuel Neves
 *         Created on 26/06/17.
 */

public class PlayerPresenceCheckerAdapter extends RecyclerView.Adapter<PlayerPresenceCheckerAdapter.PresenceCheckerViewHolder> {

    private ArrayList<PlayerInPelada> players;
    private Context context;

    public PlayerPresenceCheckerAdapter(ArrayList<PlayerInPelada> players) {
        this.players = players;
    }
    public class PresenceCheckerViewHolder extends RecyclerView.ViewHolder {
        public TextView txtId, txtNick, txtPhone, txtNumberOfWins, txtNumberOfDefeats, txtWinrate;
        public ImageView imgView;

        public PresenceCheckerViewHolder(View v) {
            super(v);
            txtNick = (TextView) v.findViewById(R.id.txtListNickPresenceCheck);
//            txtPhone = (TextView) v.findViewById(R.id.txtListPhone);
////        txtNumberOfWins = (TextView) v.findViewById(R.id.txtListNumberOfWins);
////        txtNumberOfDefeats = (TextView) v.findViewById(R.id.txtListNumberOfDefeats);
//            txtWinrate = (TextView) v.findViewById(R.id.txtListWinRate);
            imgView=(ImageView)v.findViewById(R.id.imgViewListIconPresenceCheck);
        }
    }

    @Override
    public PresenceCheckerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_row_player_presence_list, parent, false);

        return new PresenceCheckerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PresenceCheckerViewHolder holder, int position) {
        Log.d("RECI",""+ players.get(position).toString());
//        holder.txtId.setText(players.get(position).getId().toString());
        holder.txtNick.setText(players.get(position).getNickName());
//        holder.txtPhone.setText(players.get(position).getPhone().toString());
//        txtNumberOfWins.setText(String.valueOf(player.getNumberOfWins()));
//        txtNumberOfDefeats.setText(String.valueOf(player.getNumberOfDefeats()));
//        holder.txtWinrate.setText(players.get(position).getWinRate().toString());
        holder.imgView.setImageResource(getImage(players.get(position).isPresentInPelada()));

    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    private int getImage(boolean present){
        if(present)
            return R.mipmap.ic_player_present;
        else
            return R.mipmap.ic_player_not_present;
    }
}
