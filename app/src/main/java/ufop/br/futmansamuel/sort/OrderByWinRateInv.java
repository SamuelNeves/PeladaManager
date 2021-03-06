package ufop.br.futmansamuel.sort;

import java.util.Comparator;

import ufop.br.futmansamuel.other.Players;

/**
 * @author Samuel Neves
 *         Created on 07/07/17.
 */

public class OrderByWinRateInv implements Comparator<Players> {
    @Override
    public int compare(Players o2, Players o1) {

        return (int)(o1.getWinRateFloat()-o2.getWinRateFloat());    }
}
