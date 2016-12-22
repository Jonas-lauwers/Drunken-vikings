/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Graphics2D;
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
public class Drone extends SimulationBody {
    
    private static final String DRAGON = "/assets/Drones/Dragon-1.png";
    private static final String DRAGONSPRITE = "/assets/Drones/DragonSprite-1.png";

    private Vector2 difference; //distance between ship and drone;
    private final Ship ship;    //holds the ship to use for getting realtime center
    private Vector2 direction;  //holds the position it should go to or be at
    private double angle;          //holds the angle of rotation 
    //(only for attacker i guess so it can rotate around itself to fire)
    
    //place some booleans to define wheter we rotate around ourself or not
    //to define if we can fire, to define more like this :P
    private boolean canFire;    //makes drone able to fire and rotate around its center
    private boolean rotatesShip; //makes the drone rotate around the ship
    private double rotateSpeed;

    //long constructor, can be placed in setters instead and use defaults when not setting them.
    public Drone(Ship ship, Vector2 distance, int shield, int damage, long collides, boolean canFire) {
        // Create shape, fixture, body and add a collision filter to it
        Convex shape = Geometry.createRectangle(50, 50);
        BodyFixture fixture = new BodyFixture(shape);
        //THIS WILL DEPEND ON TYPE OF DRONE ... 
        //DEFENDER WILL COLLIDE WITH ENEMY AND BULLET, 
        //ATTACKER COLLIDES WITH NOTHING
        //PICKUP WILL COLLIDE WITH GEM ....
        fixture.setFilter(new CategoryFilter(DRONECOLLIDE, collides));
        //aad fixture to body
        this.addFixture(fixture);
        //set body to NOT rotate when hit
        this.setMass(MassType.FIXED_ANGULAR_VELOCITY);
        //set body to slow down?(only for applyforce and impulse?)
        this.setLinearDamping(1);
        //no clue at all
        this.translateToOrigin();
        //set impact of gravity ... there should not be gravity ... we're in space
        this.setGravityScale(10);
        //set moving velocity to 0;
        this.setLinearVelocity(new Vector2(0, 0));
        //never let this body sleep and stop doing collisiondetection
        this.setAutoSleepingEnabled(false);
        //set the shippos wich we will use to rotate around
        this.ship = ship;
        //set the distance between ship and drone
        this.difference = distance;
        //put drone on the screen next to ship
        this.translate(ship.getWorldCenter().add(distance));
        //set start angle to 0
        this.angle = 0;
        //set shield value(equal to life)
        this.shield = shield;
        //set the direction were facing to 0
        this.direction = new Vector2();
        //set default damage of drone
        this.damage = damage;

        this.canFire = canFire;
        this.rotatesShip = true;
        this.rotateSpeed = -0.02;
        
        this.skin = getImageSuppressExceptions(DRAGONSPRITE);
        this.hasSprite = true;
        this.numberOfSprites = 16;
        this.spriteHeight = 128;
        this.spriteWidth = 128;
    }
    
    /**
     * Set the rotate speed in radians. The ship will rotate this radial 
     * every frame, so the actual speed is dependent on the frame rate.
     * 
     * @param speed Radians to rotate per frame
     */
    public void setRotateSpeed(double speed) {
        this.rotateSpeed = speed;
    }
    
    /**
     * Return true if the drone can fire
     * 
     * @return boolean true if ship can fire
     */
    public boolean canFire() {
        return canFire;
    }

    /**
     * Rotate drone around it's axis towards point p, could be mouse pointer
     * like the ship does or could be the point of the closest bullet near the
     * drone or player.
     *
     * @param p the point it should turn to.
     */
    public void turnToAngle(Vector2 p) {
        if (canFire) {
            double degree = (Math.atan2(-(this.getWorldCenter().y - p.y), this.getWorldCenter().x - p.x) - Math.PI / 2);
            this.rotate(this.angle - degree, this.getWorldCenter());
            this.angle = degree;
            this.direction = p;
        }
    }

    /**
     * Rotate the drone around the ship should be private and only called by
     * render if this is applicable (if applicable do setRotatesShip(true))
     */
    public void rotateToAngle() {
        if (rotatesShip) {
            this.rotate(rotateSpeed , ship.getWorldCenter());
            difference = difference.rotate(rotateSpeed);
        }
    }

    /**
     * Moves the drone in the direction of point x,y
     *
     * @param x direction coordinate
     * @param y direction coordinate
     */
    public void move(double x, double y) {
        this.getLinearVelocity().add(x, y);
    }
    
    /**
     * Make the ship shoot a bullet in the direction it's aiming
     *
     * @return The bullet
     */
    public Bullet shoot() {
        if(canFire) {
            return new Bullet(this.getWorldCenter(), direction, ENEMYCOLLIDE, damage);
        }
        return null;
    }
    
    /**
     * Overrides isHit from simbody just to make it stay in the right distance
     * of the player
     * 
     * @param the simulation body that hit with it
     */
    @Override
    public void isHit(SimulationBody b) {
        super.isHit(b);
        Vector2 diff = this.getWorldCenter().difference(ship.getWorldCenter());
        if(!diff.equals(difference)) {
            translate(new Vector2(difference).subtract(diff));
        }
        
    }
    
    /**
     * Overrides to make it rotate around the ship all the time.
     * @param g graphics
     * @param scale scale
     */
    @Override
    public void render(Graphics2D g, double scale) {
        super.render(g, scale);
        rotateToAngle();
    }
}
