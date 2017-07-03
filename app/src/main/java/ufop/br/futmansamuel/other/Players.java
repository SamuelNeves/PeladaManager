package ufop.br.futmansamuel.other;

import java.io.Serializable;

/**
 * @author Samuel Neves
 * Created on 23/06/17.
 */

public class Players implements Serializable{


    private  int numberOfGoals;
    private String id;
    private String firstName;
    private String lastName;
    private String nickName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;
    public Players(){
        this.numberOfWins=0;
        this.numberOfDefeats=0;
        this.numberOfGoals=0;
    }

    public Players(Players player) {
        this.id = player.getId();
        this.firstName = player.getFirstName();
        this.lastName = player.getLastName();
        this.nickName = player.getNickName();
        this.email = player.getEmail();
        this.phone = player.getPhone();
        this.numberOfWins = player.getNumberOfWins();
        this.numberOfDefeats = player.getNumberOfDefeats();
        this.numberOfWins = player.getNumberOfWins();
        this.numberOfDefeats = player.getNumberOfDefeats();
        this.numberOfGoals = player.getNumberOfGoals();

    }

    public Players(String id, String firstName, String lastName, String nickName, String phone, int numberOfWins, int numberOfDefeats) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.phone = phone;
        this.numberOfWins = numberOfWins;
        this.numberOfDefeats = numberOfDefeats;
        this.numberOfWins=0;
        this.numberOfDefeats=0;
        this.numberOfGoals=0;
    }

    private String phone;
    private int numberOfWins;
    private int numberOfDefeats;

    public String getWinRate() {
        try {
            return String.valueOf(numberOfWins * 100 / getTotalOfGames()) + "%";
        } catch (ArithmeticException e) {
            return "0%";
        }
    }
    public String getFullName(){
        return firstName+" \""+nickName+"\" "+lastName;
    }
    public String getName() {
        return firstName+" "+lastName ;
    }

    public String getId() {
        return id;
    }
    public int getNumberOfWins() {
        return numberOfWins;
    }

    public Players setNumberOfWins(int numberOfWins) {
        this.numberOfWins = numberOfWins;
        return this;
    }

    public int getNumberOfDefeats() {
        return numberOfDefeats;
    }

    public Players setNumberOfDefeats(int numberOfDefeats) {
        this.numberOfDefeats = numberOfDefeats;
        return this;
    }

    public int getTotalOfGames() {
        return numberOfDefeats +numberOfWins;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return ""+firstName+" "+lastName+" "+nickName+" "+phone+" "+email;
    }

    public int getNumberOfGoals() {
        return numberOfGoals;
    }
}
