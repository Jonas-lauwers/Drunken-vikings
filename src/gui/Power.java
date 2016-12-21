package gui;

import org.dyn4j.collision.CategoryFilter;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

import com.sun.xml.internal.ws.client.sei.ResponseBuilder.Body;

public abstract class Power extends SimulationBody{
	protected boolean instantActivate;
	public Power(Vector2 location, int collideFilter){
    	Convex shape = Geometry.createCircle(5);
        BodyFixture fixture = new BodyFixture(shape);
        fixture.setFilter(new CategoryFilter(POWERCOLLIDE,collideFilter));
        this.addFixture(fixture);
        this.setMass(MassType.FIXED_LINEAR_VELOCITY);
        this.translateToOrigin();      
        this.translate(location);
	}
	@Override
	public void isHit(SimulationBody body){
		if(instantActivate){
			this.doAction(body);
		}
		else if(body instanceof Ship){
			body.addPower(this);
		}
	}
	public abstract void doAction(SimulationBody body);
}
