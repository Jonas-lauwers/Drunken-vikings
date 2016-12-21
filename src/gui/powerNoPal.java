package gui;

import org.dyn4j.geometry.Vector2;

public class powerNoPal extends Power{
	public powerNoPal(Vector2 location){
		super(location, PLAYERCOLLIDE);
		instantActivate = true;
	}
	public void doAction(SimulationBody body){
		Ship ship = (Ship)body;
		ship.deactivateDrone(3);
	}
}
