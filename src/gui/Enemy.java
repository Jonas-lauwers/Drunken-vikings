/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
/**
 *
 * @author Jonas Lauwers
 */
public class Enemy extends SimulationBody {

    private double xPos;
    private double yPos;
    private double angle;
    private Vector2 direction;

    public Enemy() {
        this.addFixture(Geometry.createTriangle(new Vector2(20, 10), new Vector2(15, 20), new Vector2(10, 10)), 1.0, 1.0, 1.0);
        this.setMass(MassType.NORMAL);
        this.setLinearDamping(1);
        this.translateToOrigin();
        this.setGravityScale(10);
        this.translate(400,500);
        this.setAngularDamping(100.0);
        Vector2 center = this.getWorldCenter();
        this.xPos = center.x;
        this.yPos = center.y;
        this.angle = 0;
        this.direction = new Vector2();
    }
    
    private void updatePos() {
        Vector2 center = this.getWorldCenter();
        this.xPos = center.x;
        this.yPos = center.y;
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
        double degree = (Math.atan2(-(this.yPos - direction.y), this.xPos - direction.x) - Math.PI/2);
        this.rotate(this.angle - degree , this.getWorldCenter());
        this.angle = degree;
        this.direction = direction;
        updatePos();
    }

    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
    }
}