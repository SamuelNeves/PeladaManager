package ufop.br.futmansamuel.other;

/**
 * @author Samuel Neves
 *         Created on 01/07/17.
 */

public class PlayerInPelada extends Players {


    private boolean presentInPelada;
    private int numberOfGoalsInGame;

    public PlayerInPelada(Players player,boolean presentInPelada, int numberOfGoalsInGame) {
        super(player);
        this.presentInPelada = presentInPelada;
    }

    public PlayerInPelada(String id, String firstName, String lastName, String nickName, String phone, int numberOfWins, int numberOfDefeats, boolean presentInPelada, int numberOfGoalsInGame) {
        super(id, firstName, lastName, nickName, phone, numberOfWins, numberOfDefeats);
        this.presentInPelada = presentInPelada;
        this.numberOfGoalsInGame = numberOfGoalsInGame;
    }
    public boolean isPresentInPelada() {
        return presentInPelada;
    }

    public void setPresentInPelada(boolean presentInPelada) {
        this.presentInPelada = presentInPelada;
    }

    public void addGoal(){
        this.numberOfGoalsInGame++;

    }
    public int getNumberOfGoalsInGame() {
        return numberOfGoalsInGame;
    }

    public void setNumberOfGoalsInGame(int numberOfGoalsInGame) {
        this.numberOfGoalsInGame = numberOfGoalsInGame;
    }
}
