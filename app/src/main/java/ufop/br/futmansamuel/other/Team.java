package ufop.br.futmansamuel.other;

import java.util.ArrayList;

/**
 * Created by samuel on 23/06/17.
 */

public class Team {

    private ArrayList<PlayerInPelada> players;

    public Team() {
        this.players = new ArrayList<>();
    }

    public ArrayList<PlayerInPelada> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<PlayerInPelada> players) {
        this.players = players;
    }

}
