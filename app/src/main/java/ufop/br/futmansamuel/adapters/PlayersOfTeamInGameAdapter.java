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
import ufop.br.futmansamuel.other.Team;

/**
 * @author Samuel Neves
 *         Created on 26/06/17.
 */

public class PlayersOfTeamInGameAdapter extends RecyclerView.Adapter<PlayersOfTeamInGameAdapter.PlayerInTeamViewHolder> {

    private ArrayList<PlayerInPelada> team;
    private Context context;

    public PlayersOfTeamInGameAdapter(Team team) {
        this.team = team.getPlayers();
    }
    public class PlayerInTeamViewHolder extends RecyclerView.ViewHolder {
        public TextView txtId, txtNick, txtPhone, txtNumberOfWins, txtNumberOfDefeats, txtWinrate;
        public ImageView imgView;

        public PlayerInTeamViewHolder(View v) {
            super(v);
            txtNick = (TextView) v.findViewById(R.id.txtListNickPlayerOfTeam);
//            txtPhone = (TextView) v.findViewById(R.id.txtListPhone);
////        txtNumberOfWins = (TextView) v.findViewById(R.id.txtListNumberOfWins);
////        txtNumberOfDefeats = (TextView) v.findViewById(R.id.txtListNumberOfDefeats);
//            txtWinrate = (TextView) v.findViewById(R.id.txtListWinRate);
//            imgView=(ImageView)v.findViewById(R.id.imgViewListIconPresenceCheck);
        }
    }

    @Override
    public PlayerInTeamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_row_player_team, parent, false);

        return new PlayerInTeamViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlayerInTeamViewHolder holder, int position) {
        Log.d("RECI",""+ team.get(position).toString());
//        holder.txtId.setText(players.get(position).getId().toString());
        holder.txtNick.setText(team.get(position).getNickName());
//        holder.txtPhone.setText(players.get(position).getPhone().toString());
//        txtNumberOfWins.setText(String.valueOf(player.getNumberOfWins()));
//        txtNumberOfDefeats.setText(String.valueOf(player.getNumberOfDefeats()));
//        holder.txtWinrate.setText(players.get(position).getWinRate().toString());
//        holder.imgView.setImageResource(getImage(team.get(position).isPresentInPelada()));

    }

    @Override
    public int getItemCount() {
        return team .size();
    }

}
