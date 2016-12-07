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
public class Enemy extends SimulationBody {

    private double angle;
    private Vector2 direction;

    public Enemy(int shield) {
        Convex shape = Geometry.createTriangle(new Vector2(20, 10), new Vector2(15, 20), new Vector2(10, 10));
        BodyFixture fixture = new BodyFixture(shape);
        fixture.setFilter(new CategoryFilter(ENEMYCOLLIDE,PLAYERCOLLIDE|BULLETCOLLIDE));
        this.addFixture(fixture);
        this.setMass(MassType.NORMAL);
        this.setLinearDamping(1);
        this.translateToOrigin();
        this.setGravityScale(10);
        this.translate(400,500);
        this.setAngularDamping(100.0);
        this.angle = 0;
        this.direction = new Vector2();
        this.shield = shield;
    }
    
    public void move(Vector2 point) {
        //calculate direction
        double impulsex = 0;
        double impulsey = 0;
        double directionx = point.x;
        double directiony = point.y;
        if(directionx > this.getWorldCenter().x) {
            impulsex = 50;
        }
        else if(directionx < this.getWorldCenter().x) {
            impulsex = -50;
        }
        if(directiony > this.getWorldCenter().y) {
            impulsey = 50;
        }
        else if(directiony < this.getWorldCenter().y) {
            impulsey = -50;
        }
        //apply direction
        turnToAngle(point);
        this.applyImpulse(new Vector2(impulsex,impulsey), point);
    }
    
    public void turnToAngle(Vector2 direction) {
        double degree = (Math.atan2(-(this.getWorldCenter().x - direction.y), this.getWorldCenter().y - direction.x) - Math.PI/2);
        this.rotate(this.angle - degree , this.getWorldCenter());
        this.angle = degree;
        this.direction = direction;
    }
    
    public void shoot() {
        //return new Bullet(this.getWorldCenter(), direction, ENEMYCOLLIDE);
    }
}