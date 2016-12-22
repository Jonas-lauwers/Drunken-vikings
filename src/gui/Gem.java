/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import static gui.SimulationBody.BULLETCOLLIDE;
import static gui.SimulationBody.DRONECOLLIDE;
import static gui.SimulationBody.GEMCOLLIDE;
import static gui.SimulationBody.PLAYERCOLLIDE;
import org.dyn4j.collision.CategoryFilter;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

/**
 *
 * @author Jonas Lauwers <jonas.lauwers AT gmail.org>
 */
public class Gem extends SimulationBody {
    
    public static final String DIAMOND = "/assets/Collectibles_Droppables/Points/Diamond-1.png";
    public static final String GOLD = "/assets/Collectibles_Droppables/Points/Gold-1.png";
    public static final String PILE = "/assets/Collectibles_Droppables/Points/PileOfDiamond-1.png";
    public static final String WOOD = "/assets/Collectibles_Droppables/Points/Wood-1.png";
    private String type;
    
    public Gem(Vector2 location, int exp, String type) {
        Convex shape = Geometry.createCircle(10);
        BodyFixture fixture = new BodyFixture(shape);
        fixture.setFilter(new CategoryFilter(GEMCOLLIDE, PLAYERCOLLIDE | BULLETCOLLIDE | DRONECOLLIDE));
        this.addFixture(fixture);
        this.setMass(MassType.FIXED_LINEAR_VELOCITY);
        this.translateToOrigin();
        this.translate(location);
        this.expPoints = exp;
        try {
            this.type = (String) Gem.class.getDeclaredField(type.toUpperCase()).get(null);
        }
        catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException ex) {
            this.type = "";
        }
        this.skin = getImageSuppressExceptions(this.type);
    }
    
    @Override
    public void isHit(SimulationBody b) {
        super.isHit(b);
        if(b instanceof Bullet) {
            this.expPoints = 0;
        }
    }
}
