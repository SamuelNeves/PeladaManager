package ufop.br.futmansamuel.sort;

import java.util.Comparator;

import ufop.br.futmansamuel.other.PlayerInPelada;
import ufop.br.futmansamuel.other.Players;

/**
 * @author Samuel Neves
 *         Created on 01/07/17.
 */

public class OrderByPresence implements Comparator<PlayerInPelada> {
    private int getInt(PlayerInPelada o) {
        if (o.isPresentInPelada())
            return 0;
        else return 1;
    }

    @Override
    public int compare(PlayerInPelada o2, PlayerInPelada o1) {
        return getInt(o1) - getInt(o2);
    }
}