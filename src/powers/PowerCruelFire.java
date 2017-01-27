/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package powers;

import gui.SimulationBody;
import static gui.SimulationBody.ENEMYCOLLIDE;
import static gui.SimulationBody.PLAYERCOLLIDE;
import static powers.Power.CRUEL;

/**
 *
 * @author Jonas Lauwers <jonas.lauwers AT gmail.org>
 */
public class PowerCruelFire extends Power {
 
    public PowerCruelFire(int droprate){
        super(PLAYERCOLLIDE|ENEMYCOLLIDE);
        instantActivate = true;
        this.dropRate = droprate;
        this.skin = getImageSuppressExceptions(CRUEL);
    }

    @Override
    public void doAction(SimulationBody body) {
        body.setBulletSpeed(25, 8);
    }
}
