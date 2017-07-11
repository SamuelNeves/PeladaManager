package ufop.br.futmansamuel.sort;

import java.util.Comparator;

import ufop.br.futmansamuel.other.Players;

/**
 * @author Samuel Neves
 *         Created on 07/07/17.
 */

public class OrderByWinRate implements Comparator<Players> {
    @Override
    public int compare(Players o1, Players o2) {
        int winrateo1, winrateo2;
        if (o1.getTotalOfGames() != 0) {

            winrateo1 = o1.getNumberOfWins() / o1.getTotalOfGames();
        } else {
            winrateo1 = 0;
        }
        if (o2.getTotalOfGames() != 0) {
            winrateo2 = o2.getNumberOfWins() / o2.getTotalOfGames();

        } else {
            winrateo2 = 0;
        }
        return winrateo1 - winrateo2;
    }
}
