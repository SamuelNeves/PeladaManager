package ufop.br.futmansamuel.other;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;

import ufop.br.futmansamuel.activities.MainActivity;

/**
 * Created by samuel on 23/06/17.
 */

public class Pelada {
    private Team team1;
    private Team team2;

    public Team getSubstitutes() {
        return substitutes;
    }

    private Team substitutes;
    private long duration;


    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    Date date;


    SimpleDateFormat ft;

    public Pelada() {
    }

    public Pelada(Team team1, Team team2,Team substitutes, Date date) {
        this.team1 = team1;
        this.team2 = team2;
        this.substitutes=substitutes;
        this.date = date;
        ft = new SimpleDateFormat("HH:mm dd/MM/yyyy");


    }

    public String getDate() {
        return ft.format(date);
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

}
