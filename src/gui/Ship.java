/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import org.dyn4j.collision.CategoryFilter;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

/**
 *
 * @author Jonas Lauwers
 */
public class Ship extends SimulationBody {
    
    private static final String PLAYER = "/assets/Player/Player-1.png";
    private static final String PLAYERSPRITE = "/assets/Player/Player-Sprite-277px.png";

    private double angle;       // holds the angle in which we are turned
    private Vector2 direction;  // holds the coordinate of the mouse
    private Drone drone;        //holds the drone your playing with

    public Ship() {
        // Create shape, fixture, body and add a collision filter to it
        Convex shape = Geometry.createRectangle(65, 45);
        //Geometry.createTriangle(new Vector2(20, 10), new Vector2(15, 20), new Vector2(10, 10));
        BodyFixture fixture = new BodyFixture(shape);
        fixture.setFilter(new CategoryFilter(PLAYERCOLLIDE, ENEMYCOLLIDE | BULLETCOLLIDE | GEMCOLLIDE | POWERCOLLIDE));

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
        //put player in a certain position on screen
        this.translate(512, 512);
        //set start angle to 0
        this.angle = 0;
        //set shield value(equal to life)
        this.shield = 20;
        //set the direction were facing to 0
        this.direction = new Vector2();
        //set default damage of ship
        this.damage = 1;
        
        //rotate body to allign with skin;
        this.rotate(Math.toRadians(90), this.getWorldCenter());
        
        // creation of drones should go somewhere else, menu probably and given
        //to the ship with a setter or in the constructor
        
        //this drone can defend and pickup gems
        //this.drone = new Drone(this, new Vector2(20,20), 500, 10, ENEMYCOLLIDE | BULLETCOLLIDE | GEMCOLLIDE, false);
        // this drone only defends
        this.drone = new Drone(this, new Vector2(50,50), 500, 10, ENEMYCOLLIDE | BULLETCOLLIDE, false);
        // this drone only picks up gems
        //this.drone = new Drone(this, new Vector2(20,20), 500, 10, GEMCOLLIDE, false);
        // this drone only attacks
        //this.drone = new Drone(this, new Vector2(20,20), 500, 10, 0, true);
        
        this.skin = getImageSuppressExceptions(PLAYERSPRITE);
        this.hasSprite = true;
        this.numberOfSprites = 6;
        this.spriteHeight = 100;
        this.spriteWidth = 277;
    }

    /**
     * Move the body in a linear line according the values of direction x and
     * direction y
     *
     * @param x The speed value to move along the x axis
     * @param y The speed value to move along the y axis
     */
    public void move(double x, double y) {
        this.getLinearVelocity().add(x, y);
        drone.move(x, y);
    }

    /**
     * Rotate the body around it's center to a certain point on the screen
     *
     * @param p The point where the body should turn to
     */
    public void turnToAngle(Vector2 p) {
        double degree = (Math.atan2(-(this.getWorldCenter().y - p.y), this.getWorldCenter().x - p.x) - Math.PI / 2);
        this.rotate(this.angle - degree, this.getWorldCenter());
        this.angle = degree;
        this.direction = p;
        drone.turnToAngle(p);
    }

    /**
     * Rotate the body around it's center to the last given point.
     */
    public void turnToAngle() {
        turnToAngle(direction);
    }

    /**
     * Make the ship shoot a bullet in the direction it's aiming
     *
     * @return The bullet
     */
    public Bullet shoot() {
        return new Bullet(this.getWorldCenter(), direction, ENEMYCOLLIDE, damage);
    }
    
    public Drone getDrone() {
        return drone;
    }
}
