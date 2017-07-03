package ufop.br.futmansamuel.sort;

import java.util.Comparator;

import ufop.br.futmansamuel.other.PlayerInPelada;

/**
 * @author Samuel Neves
 *         Created on 01/07/17.
 */

public class OrderByNick implements Comparator<PlayerInPelada> {

    @Override
    public int compare(PlayerInPelada o1, PlayerInPelada o2) {
        return o1.getNickName().compareTo(o2.getNickName());
    }
}
