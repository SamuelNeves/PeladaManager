package ufop.br.futmansamuel.sort;

import java.util.Comparator;

import ufop.br.futmansamuel.other.Players;

/**
 * @author Samuel Neves
 *         Created on 07/07/17.
 */

public class OrderByTotalGames implements Comparator<Players> {
    @Override
    public int compare(Players o1, Players o2) {
        return o1.getTotalOfGames()-o2.getTotalOfGames();
    }
}
