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
public class EnemyKing extends Enemy {
    
    private static final String KING = "/assets/Enemies/Enemy-KingsShip-1.png";
    
    public EnemyKing(Vector2 location, int difficulty, int level) {
        super();
        this.translate(location);
        this.shield = 5 * difficulty * level;
        this.damage = 3 * difficulty * level;
        this.gemDrop = "gold";
        this.gemValue = 50;
        this.scorePoints = 0;
        this.skin = getImageSuppressExceptions(KING);
        
        this.pf = new PowerFactory(100, 34);
    }
}
