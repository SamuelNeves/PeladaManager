package ufop.br.futmansamuel.other;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import ufop.br.futmansamuel.activities.MainActivity;

/**
 * Created by samuel on 23/06/17.
 */

public class PeladaManager {
    private SharedPreferences sharedPreferences;
    public static final String PELADASETTINGSPREFSNAME = "peladaConfigs";
    ArrayList<PlayerInPelada> allPlayers;

    private Pelada pelada;

    public long getMaxDurationOfPelada() {
        return maxDurationOfPelada;
    }


    private long maxDurationOfPelada;
    private int maxPlayersByTeam;
    Context context;

    public PeladaManager(Context context, ArrayList<PlayerInPelada> allPlayers) {
        this.allPlayers = allPlayers;
        this.context = context;
        configSharedPreferences();
        loadPeladaConfigurations();
        sortTeams();

    }
    public boolean isTeam1Full(){
        return (pelada.getTeam1().getPlayers().size()==maxPlayersByTeam);
    }
    public boolean isTeam2Full(){
        return (pelada.getTeam2().getPlayers().size()==maxPlayersByTeam);
    }

    public PeladaManager(Context context, Team t1, Team t2, Team subs) {
        this.context = context;
        configSharedPreferences();
        loadPeladaConfigurations();
        arrangeTeams(t1, t2, subs);
    }

    public void arrangeTeams(Team t1, Team t2, Team subs) {
        Team newTeamOne = new Team();
        Team newTeamTwo = new Team();
        Team newSubstitutes = new Team();
        ArrayList<PlayerInPelada> otherPlayers = new ArrayList<PlayerInPelada>();
        otherPlayers.addAll(t1.getPlayers());
        otherPlayers.addAll(t2.getPlayers());
        Collections.shuffle(newSubstitutes.getPlayers());
        Collections.shuffle(otherPlayers);
        for (int i = 0; i < maxPlayersByTeam; i++) {
            if (subs.getPlayers().size() > 1) {
                newTeamOne.getPlayers().add(subs.getPlayers().remove(0));
                newTeamTwo.getPlayers().add(subs.getPlayers().remove(0));
            }
        }
        while (subs.getPlayers().size() > 0) {
            otherPlayers.add(0, subs.getPlayers().remove(0));
        }

        for (int i = newTeamOne.getPlayers().size(); i <= maxPlayersByTeam; i++) {
            if (otherPlayers.size() > 1) {
                newTeamOne.getPlayers().add(otherPlayers.remove(0));
                newTeamTwo.getPlayers().add(otherPlayers.remove(0));
            }
        }
        newSubstitutes.setPlayers(otherPlayers);
        pelada= new Pelada(newTeamOne,newTeamTwo,newSubstitutes,new Date());

    }

    public Pelada getPelada() {
        return pelada;
    }

    public void sortTeams() {
        Team t1 = new Team();
        Team t2 = new Team();
        Team substitutes = new Team();
        Collections.shuffle(allPlayers);
        for (int i = 0; i < maxPlayersByTeam; i++) {
            if (allPlayers.size() > 1) {
                t1.getPlayers().add(allPlayers.remove(0));
                t2.getPlayers().add(allPlayers.remove(0));
            }
        }
        substitutes.setPlayers(allPlayers);
        pelada = new Pelada(t1, t2, substitutes, new Date());

    }


    public void endPelada(int winnerTeam) {
        updateStatisticsOfPlayers(winnerTeam);
    }

    void updateStatisticsOfPlayers(int winnerTeam) {
        ArrayList<Players> players = MainActivity.players;

        for (PlayerInPelada p : getPelada().getTeam1().getPlayers()) {
            for (Players player : players) {
                Log.d("AQUI", p.getId() + " " + player.getId());
                if (player.getId().equals(p.getId())) {
                    Log.d("teste", player.getNickName());
                    if (winnerTeam == 1) {
                        player.setNumberOfWins(player.getNumberOfWins() + 1);
                    } else {
                        player.setNumberOfDefeats(player.getNumberOfDefeats() + 1);
                    }
                    player.setNumberOfGoals(player.getNumberOfGoals() + p.getNumberOfGoalsInGame());
                }
            }

        }

        for (PlayerInPelada p : getPelada().getTeam2().getPlayers()) {
            for (Players player : players) {
                if (player.getId().equals(p.getId())) {
                    if (winnerTeam == 2) {
                        player.setNumberOfWins(player.getNumberOfWins() + 1);
                    } else {
                        player.setNumberOfDefeats(player.getNumberOfDefeats() + 1);
                    }
                    player.setNumberOfGoals(player.getNumberOfGoals() + p.getNumberOfGoalsInGame());
                }
            }

        }


        for (PlayerInPelada p : getPelada().getSubstitutes().getPlayers()) {
            for (Players player : players) {
                if (player.getId().equals(p.getId())) {
                    player.setNumberOfGoals(player.getNumberOfGoals() + p.getNumberOfGoalsInGame());
                }
            }

        }


    }

    private void configSharedPreferences() {
        sharedPreferences = context.getSharedPreferences(PELADASETTINGSPREFSNAME,
                Context.MODE_PRIVATE);
    }

    private void loadPeladaConfigurations() {
        maxDurationOfPelada = sharedPreferences.getLong("duration", 7 * 60000);
        maxPlayersByTeam = sharedPreferences.getInt("number_players_team", 5);

    }


}
