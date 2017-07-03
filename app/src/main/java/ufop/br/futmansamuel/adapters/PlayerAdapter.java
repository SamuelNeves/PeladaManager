package ufop.br.futmansamuel.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ufop.br.futmansamuel.R;
import ufop.br.futmansamuel.other.Players;

/**
 * @author Samuel Neves
 *         Created on 24/06/17.
 */

public class PlayerAdapter extends BaseAdapter {

    private ArrayList<Players> players;
    private Context context;

    public PlayerAdapter(Context context, ArrayList<Players> players) {
        this.players = players;
        this.context = context;

    }

    @Override
    public int getCount() {
        return players.size();
    }

    @Override
    public Object getItem(int position) {
        return players.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Players player = players.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.player, null);

        TextView txtId, txtNick, txtPhone, txtNumberOfWins, txtNumberOfDefeats, txtWinrate;

        txtId = (TextView) v.findViewById(R.id.txtListId);
        txtNick = (TextView) v.findViewById(R.id.txtListNick);
        txtPhone = (TextView) v.findViewById(R.id.txtListPhone);
//        txtNumberOfWins = (TextView) v.findViewById(R.id.txtListNumberOfWins);
//        txtNumberOfDefeats = (TextView) v.findViewById(R.id.txtListNumberOfDefeats);
        txtWinrate = (TextView) v.findViewById(R.id.txtListWinRate);

        txtId.setText(player.getId());
        txtNick.setText(player.getFullName());
        txtPhone.setText(player.getPhone());
//        txtNumberOfWins.setText(String.valueOf(player.getNumberOfWins()));
//        txtNumberOfDefeats.setText(String.valueOf(player.getNumberOfDefeats()));
        txtWinrate.setText(player.getWinRate());

        return v;

    }
}
