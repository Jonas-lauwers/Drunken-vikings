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
public class EnemyDragon extends Enemy{
    
    private static final String DRAGON = "/assets/Enemies/Enemy-DragonShip-1.png";
    private final int[] shielding = {1, 2, 3};
    
    public EnemyDragon(Vector2 location, int difficulty) {
        super();
        this.translate(location);
        this.shield = shielding[difficulty];
        this.damage = 1 * difficulty;
        this.scorePoints = 0;
        this.gemDrop = "wood";
        this.gemValue = 10;
        this.skin = getImageSuppressExceptions(DRAGON);
        this.pf = new PowerFactory(100, 34);
    }
    
}
