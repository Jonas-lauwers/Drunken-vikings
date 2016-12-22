package gui;

import org.dyn4j.collision.CategoryFilter;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;


public abstract class Power extends SimulationBody{
	protected boolean instantActivate;
        protected int dropRate;
        
	public Power(int collideFilter){
    	Convex shape = Geometry.createCircle(5);
        BodyFixture fixture = new BodyFixture(shape);
        fixture.setFilter(new CategoryFilter(POWERCOLLIDE,collideFilter));
        this.addFixture(fixture);
        this.setMass(MassType.FIXED_LINEAR_VELOCITY);
	}
        
        public void setLocation (Vector2 location) {
            this.translateToOrigin();
            this.translate(location);
        }
        
        public int getDropRate() {
            return dropRate;
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
