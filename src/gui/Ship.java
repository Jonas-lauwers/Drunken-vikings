/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Point;
import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

/**
 *
 * @author Jonas Lauwers
 */
public class Ship extends SimulationBody {

    private double xPos;
    private double yPos;
    private double angle;
    private Point direction;
    private int shield;

    public Ship() {
        this.addFixture(Geometry.createTriangle(new Vector2(20, 10), new Vector2(15, 20), new Vector2(10, 10)), 1.0, 1.0, 1.0);
        this.setMass(MassType.FIXED_ANGULAR_VELOCITY);
        this.setLinearDamping(1);
        this.translateToOrigin();
        this.setGravityScale(10);
        this.setAngularDamping(100.0);
        this.setLinearVelocity(new Vector2(0,0));
        Vector2 center = this.getWorldCenter();
        this.setAutoSleepingEnabled(false);
        this.translate(512, 512);
        this.xPos = center.x;
        this.yPos = center.y;
        this.angle = 0;
        this.shield = 10;
        this.direction = new Point();
    }
    
    private void updatePos() {
        Vector2 center = this.getWorldCenter();
        this.xPos = center.x;
        this.yPos = center.y;
    }
    
    public void move(double x, double y) {
        this.getLinearVelocity().add(x, y);
    }
    
    public void turnToAngle(Point p) {
        double degree = (Math.atan2(-(this.yPos - p.getY()), this.xPos - p.getX()) - Math.PI/2);
        this.rotate(this.angle - degree , this.getWorldCenter());
        this.angle = degree;
        this.direction = p;
        updatePos();
    }
    
    public void turnToAngle() {
        turnToAngle(direction);
    }

    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
    }
    
    public Bullet shoot() {
        return new Bullet(this.getWorldCenter(), direction);
    }
    
    @Override
    public boolean isInContact(Body body) {
        if(super.isInContact(body)) {
            isHit();
            return true;
        }
        return false;
    }
    
    public void isHit() {
        shield--;
    }
    
    public boolean isDead() {
        return shield <= 0;
    }
}
