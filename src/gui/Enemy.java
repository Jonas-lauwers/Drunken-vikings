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
 * @author Jonas Lauwers For comments see ship for now is pretty much the same
 * except for the move function :) Looks like it can definitly be refactored :)
 */
public class Enemy extends SimulationBody {

    private double angle;
    private Vector2 direction;

    public Enemy(int shield, int damage, int xPos, int yPos, String imageLoc) {

        Convex shape = Geometry.createRectangle(30, 30);
        //Geometry.createTriangle(new Vector2(20, 10), new Vector2(15, 20), new Vector2(10, 10));
        BodyFixture fixture = new BodyFixture(shape);
        fixture.setFilter(new CategoryFilter(ENEMYCOLLIDE, PLAYERCOLLIDE | BULLETCOLLIDE | DRONECOLLIDE));

        this.addFixture(fixture);

        this.setMass(MassType.FIXED_ANGULAR_VELOCITY);

        this.setLinearDamping(1);

        this.translateToOrigin();

        this.setGravityScale(10);

        this.setAutoSleepingEnabled(false);

        this.translate(xPos, yPos);

        this.angle = 0;

        this.direction = new Vector2();

        this.shield = shield;
        this.damage = damage;
        this.scorePoints = 10;
        
        //rotate body to allign with skin;
        this.rotate(Math.toRadians(90), this.getWorldCenter());
        
        this.skin = getImageSuppressExceptions(imageLoc);
    }

    public void move(Vector2 point) {
        //calculate direction
        turnToAngle(point);
        double impulsex = 0;
        double impulsey = 0;
        if (point.x > this.getWorldCenter().x) {
            impulsex = 50;
        } else if (point.x < this.getWorldCenter().x) {
            impulsex = -50;
        }
        if (point.y > this.getWorldCenter().y) {
            impulsey = 50;
        } else if (point.y < this.getWorldCenter().y) {
            impulsey = -50;
        }
        //apply direction
        this.applyImpulse(new Vector2(impulsex, impulsey), point);
    }

    public void turnToAngle(Vector2 p) {
        double degree = (Math.atan2(-(this.getWorldCenter().y - p.y), this.getWorldCenter().x - p.x) - Math.PI / 2);
        this.rotate(this.angle - degree, this.getWorldCenter());
        this.angle = degree;
        this.direction = p;
    }

    public Bullet shoot() {
        return new Bullet(this.getWorldCenter(), direction, PLAYERCOLLIDE|DRONECOLLIDE, damage);
    }

    public SimulationBody dropGem() {
        return new Gem(this.getWorldCenter(), 5, "wood");
    }
}
