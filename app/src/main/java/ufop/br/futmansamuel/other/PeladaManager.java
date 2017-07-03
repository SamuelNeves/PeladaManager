package ufop.br.futmansamuel.other;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

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
        Team substitutes= new Team();
        Collections.shuffle(allPlayers);
        for (int i = 0; i < maxPlayersByTeam * 2; i++) {
            if (allPlayers.size() > 1) {
                t1.getPlayers().add(allPlayers.remove(0));
                t2.getPlayers().add(allPlayers.remove(0));
            }
        }
        substitutes.setPlayers(allPlayers);
        pelada= new Pelada(t1,t2,substitutes,new Date());

    }

    private void endPelada(long durationOfPelada, Team t1, Team t2) {

    }

    private void configSharedPreferences() {
        sharedPreferences = context.getSharedPreferences(PELADASETTINGSPREFSNAME,
                Context.MODE_PRIVATE);
    }

    private void loadPeladaConfigurations() {
        maxDurationOfPelada = sharedPreferences.getLong("duration", 7 * 60000);
        maxPlayersByTeam = sharedPreferences.getInt("number_players_team", 5);

    }

    public void criaPelada() {


    }

}
