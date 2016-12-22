package gui;

import org.dyn4j.collision.CategoryFilter;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

// this should also get a factory to spawn the bullets so we can also limit them
public class Bullet extends SimulationBody {
    
    private static final String ARROW = "/assets/Projectiles/Arrow-1.png";
    private static final String AXE = "/assets/Projectiles/Axe.png";
    private static final String HAMMER = "/assets/Projectiles/Hammer-1.png";
    
    /**
     * Create a bullet on a location moving towards a direction and can collide
     * with the given filter.
     *
     * @param location The location of spawning
     * @param direction The direction it's moving in
     * @param collideFilter The filter which tells what it can collide with
     */
    public Bullet(Vector2 location, Vector2 direction, long collideFilter, int damage) {
        Convex shape = Geometry.createRectangle(20, 8);
        BodyFixture fixture = new BodyFixture(shape);
        fixture.setFilter(new CategoryFilter(BULLETCOLLIDE, collideFilter));
        this.addFixture(fixture);
        this.setMass(MassType.FIXED_ANGULAR_VELOCITY);
        this.translateToOrigin();
        this.setAngularDamping(100.0);
        this.setBullet(true);
        
        //rotate body to allign with skin;
        this.rotate(Math.toRadians(-90), this.getWorldCenter());
        
        double degree = (Math.atan2(location.y - direction.y, location.x - direction.x) - Math.PI / 2);
        this.rotateAboutCenter(degree);
        double x = (15 * Math.cos(degree - Math.toRadians(90)));
        double y = (15 * Math.sin(degree - Math.toRadians(90)));

        this.translate(location.x + x, location.y + y);
        this.setLinearVelocity(x * 7, y * 7);
        this.damage = damage;
        this.skin = getImageSuppressExceptions(ARROW);
    }
    
    public Bullet(Vector2 location, double direction, long collideFilter, int damage) {
        Convex shape = Geometry.createRectangle(20, 8);
        BodyFixture fixture = new BodyFixture(shape);
        fixture.setFilter(new CategoryFilter(BULLETCOLLIDE, collideFilter));
        this.addFixture(fixture);
        this.setMass(MassType.FIXED_ANGULAR_VELOCITY);
        this.translateToOrigin();
        this.setAngularDamping(100.0);
        this.setBullet(true);
        
        //rotate body to allign with skin;
        this.rotate(Math.toRadians(90), this.getWorldCenter());
        
        //double degree = (Math.atan2(location.y - direction.y, location.x - direction.x) - Math.PI / 2);
        this.rotateAboutCenter(-direction);
        double x = (15 * Math.cos(direction - Math.toRadians(90)));
        double y = (-(15 * Math.sin(direction - Math.toRadians(90))));

        this.translate(location.x + x, location.y + y);
        this.setLinearVelocity(x * 7, y * 7);
        this.damage = damage;
        this.skin = getImageSuppressExceptions(ARROW);
    }
}
