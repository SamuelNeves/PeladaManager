package ufop.br.futmansamuel.sort;

import java.util.Comparator;

import ufop.br.futmansamuel.other.Players;

/**
 * @author Samuel Neves
 *         Created on 07/07/17.
 */

public class OrderByGoalsInv implements Comparator<Players> {
    @Override
    public int compare(Players o1, Players o2) {
        return o2.getNumberOfGoals()-o1.getNumberOfGoals();
    }
}
