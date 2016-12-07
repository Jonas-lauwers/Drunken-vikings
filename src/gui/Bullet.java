package gui;

import java.awt.Point;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

public class Bullet extends SimulationBody {

	public Bullet(Vector2 location, Point direction) {
            this.addFixture(Geometry.createRectangle(4,10));
            this.setMass(MassType.FIXED_ANGULAR_VELOCITY);
            this.translateToOrigin();
            this.setAngularDamping(100.0);
            this.setBullet(true);
            
            
            double degree = (Math.atan2(location.y - direction.getY(), location.x - direction.getX()) - Math.PI/2);
            this.rotateAboutCenter(degree);
            double x =(15*Math.cos(degree-Math.toRadians(90)));
            double y = (15*Math.sin(degree-Math.toRadians(90)));
            
            this.translate(location.x+x,location.y+y);
            this.setLinearVelocity(x*2,y*2);
	}
}
