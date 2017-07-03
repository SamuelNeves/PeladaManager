package ufop.br.futmansamuel.sort;

import java.util.Comparator;

import ufop.br.futmansamuel.other.PlayerInPelada;
import ufop.br.futmansamuel.other.Players;

/**
 * @author Samuel Neves
 *         Created on 01/07/17.
 */

public class OrderByPresence implements Comparator<PlayerInPelada> {
    private int getInt(PlayerInPelada o){
        if(o.isPresentInPelada())
            return 1;
        else  return 0;
    }
    @Override
    public int compare(PlayerInPelada o1, PlayerInPelada o2) {
        return getInt(o1)-getInt(o2);
    }
}
