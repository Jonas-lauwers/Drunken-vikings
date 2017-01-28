/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameinterface;

import gui.Ship;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jonas Lauwers <jonas.lauwers AT gmail.org>
 */
public class Player {
    
    private int id;
    private String name;
    private int currency;
    private int rank;
    private Map<String, Integer> score;
    private Ship ship;
    
    //player creation should be based on local data or login information
    public Player(int id, String name) {
        this.id = id;
        //score, rank, currency and name should be loaded from db or localstorage
        this.name = name;
        this.currency = 0;
        this.rank = 0;
        this.score = new HashMap<>();
        score.put("easy", 0);
        score.put("medium", 0);
        score.put("hard", 0);
        //make setter in ship to set controller
        //ship should be loaded from db or localstorage and should be all ships
        this.ship = new Ship(false);
    }
    
    public void setScore(int newScore, String difficulty) {
        int currentScore = score.get(difficulty.toLowerCase());
        if(currentScore < newScore) {
            score.put(difficulty.toLowerCase(), newScore);
        }
    }
    
    public void setCurrency(int currency) {
        this.currency = currency;
    }
    
    public int getCurrency() {
        return this.currency;
    }
    
}
