package gui;

import org.dyn4j.geometry.Vector2;

import ch.aplu.xboxcontroller.*;

public class Controller {
	private XboxController xc;

	public Controller(geowars geo, int player) {
		xc = new XboxController(player);
		if (!xc.isConnected()) {
			System.out.println("NO CONTROLLER");
			xc.release();
			return;
		}
		xc.setLeftThumbDeadZone(0.5);
		xc.setRightThumbDeadZone(0.5);
		xc.addXboxControllerListener(new XboxControllerAdapter() {
			public void rightTrigger(double value) {
				if(value == 1){
				geo.firing = true;
				System.out.println("FIRE");
				}
			}
			public void leftThumbDirection(double value){
				System.out.println(value);
				geo.moveUp = false;
				geo.moveDown = false;
				geo.moveLeft = false;
				geo.moveRight = false;
				if(value>=25 && value<=155){
					geo.moveRight = true;
				}
				if(value>=200 && value<=335){
					geo.moveLeft = true;
				}
				if(value>=115 && value<=245){
					geo.moveDown = true;
				}
				if(value>=290 || value<=65){
					geo.moveUp = true;
				}
			}
			
			public void leftThumbMagnitude(double value){
				if(value==0){
					geo.moveUp = false;
					geo.moveDown = false;
					geo.moveLeft = false;
					geo.moveRight = false;
				}
			}
			
			public void rightThumbDirection(double value){
				geo.ship.turnToAngle(-Math.toRadians(value+180));
			}
		});
	}
}