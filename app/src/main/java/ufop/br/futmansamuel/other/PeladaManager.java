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
                Log.d("AQUI",p.getId()+" "+ player.getId());
                if (player.getId().equals(p.getId())) {
                    Log.d("teste",player.getNickName());
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
