/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Point;
import org.dyn4j.collision.CategoryFilter;
import org.dyn4j.dynamics.Body;
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

    private double angle;
    private Vector2 direction;

    public Ship() {
        // Create shape, fixture, body and add a collision filter to it
        Convex shape = Geometry.createTriangle(new Vector2(20, 10), new Vector2(15, 20), new Vector2(10, 10));
        BodyFixture fixture = new BodyFixture(shape);
        fixture.setFilter(new CategoryFilter(PLAYERCOLLIDE,ENEMYCOLLIDE));
        
        
        this.addFixture(fixture);
        this.setMass(MassType.FIXED_ANGULAR_VELOCITY);
        this.setLinearDamping(1);
        this.translateToOrigin();
        this.setGravityScale(10);
        this.setAngularDamping(100.0);
        this.setLinearVelocity(new Vector2(0,0));
        this.setAutoSleepingEnabled(false);
        this.translate(512, 512);
        this.angle = 0;
        this.shield = 10;
        this.direction = new Vector2();
    }
    
    public void move(double x, double y) {
        this.getLinearVelocity().add(x, y);
    }
    
    public void turnToAngle(Vector2 p) {
        double degree = (Math.atan2(-(this.getWorldCenter().y - p.y), this.getWorldCenter().x - p.x) - Math.PI/2);
        this.rotate(this.angle - degree , this.getWorldCenter());
        this.angle = degree;
        this.direction = p;
    }
    
    public void turnToAngle() {
        turnToAngle(direction);
    }
    
    public Bullet shoot() {
        return new Bullet(this.getWorldCenter(), direction, ENEMYCOLLIDE);
    }
}
