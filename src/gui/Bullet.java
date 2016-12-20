package gui;

import org.dyn4j.collision.CategoryFilter;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

public class Bullet extends SimulationBody {

    /**
     * Create a bullet on a location moving towards a direction and can collide
     * with the given filter.
     * @param location The location of spawning
     * @param direction The direction it's moving in
     * @param collideFilter The filter which tells what it can collide with
     */
    public Bullet(Vector2 location, Vector2 direction, long collideFilter, int damage) {
        Convex shape = Geometry.createRectangle(4, 10);
        BodyFixture fixture = new BodyFixture(shape);
        fixture.setFilter(new CategoryFilter(BULLETCOLLIDE, collideFilter));
        this.addFixture(fixture);
        this.setMass(MassType.FIXED_ANGULAR_VELOCITY);
        this.translateToOrigin();
        this.setAngularDamping(100.0);
        this.setBullet(true);

        double degree = (Math.atan2(location.y - direction.y, location.x - direction.x) - Math.PI / 2);
        this.rotateAboutCenter(degree);
        double x = (15 * Math.cos(degree - Math.toRadians(90)));
        double y = (15 * Math.sin(degree - Math.toRadians(90)));

        this.translate(location.x + x, location.y + y);
        this.setLinearVelocity(x * 7, y * 7);
        this.damage = damage;
        
    }
}
