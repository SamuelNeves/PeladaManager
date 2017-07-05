package ufop.br.futmansamuel.other;

import java.util.ArrayList;

/**
 * Created by samuel on 23/06/17.
 */

public class Team {

    public int getNumberOfGoals() {
        return numberOfGoals;
    }
    public void addGoal(){
        this.numberOfGoals++;
    }
    public void setNumberOfGoals(int numberOfGoals) {
        this.numberOfGoals = numberOfGoals;
    }

    private int numberOfGoals=0;
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
