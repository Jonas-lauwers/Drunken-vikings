/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author Jonas Lauwers <jonas.lauwers AT gmail.org>
 */
public class powerNoFire extends Power {
    
    public powerNoFire(int droprate){
        super(PLAYERCOLLIDE|ENEMYCOLLIDE);
        instantActivate = true;
        this.dropRate = droprate;
        this.skin = getImageSuppressExceptions(NOFIRE);
    }

    @Override
    public void doAction(SimulationBody body) {
        body.disableFire(8);
    }
    
}
