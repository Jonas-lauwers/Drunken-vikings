/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enemies;

import org.dyn4j.geometry.Vector2;
import powers.PowerFactory;

/**
 *
 * @author Jonas Lauwers <jonas.lauwers AT gmail.org>
 */
public class EnemyWarrior extends Enemy {
    
    private static final String WARRIOR = "/assets/Enemies/Enemy-WarriorShip-1.png";
    private final int[] shielding = {2, 4, 6};
    
    public EnemyWarrior(Vector2 location, int difficulty) {
        super();
        this.translate(location);
        this.shield = shielding[difficulty];
        this.damage = 1 * difficulty;
        this.gemDrop = "wood";
        this.gemValue = 10;
        this.scorePoints = 0;
        this.skin = getImageSuppressExceptions(WARRIOR);
        this.pf = new PowerFactory(100, 34);
        this.canShoot = true;
    }
    
}
