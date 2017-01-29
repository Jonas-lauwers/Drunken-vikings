package gui;


import ch.aplu.xboxcontroller.*;

public class Controller {
        private static Controller instance;
	private XboxController xc;
        private geowars geo;

	private Controller(int player) {
		xc = new XboxController(player);
		
	}
        
        public void initController(geowars geo) {
            this.geo = geo;
            if (!xc.isConnected()) {
			xc.release();
			geo.usingController = false;
			return;
		}
		geo.usingController = true;
		xc.setLeftThumbDeadZone(0.5);
		xc.setRightThumbDeadZone(0.5);
		xc.addXboxControllerListener(new XboxControllerAdapter() {
			public void rightTrigger(double value) {
				if(value == 1){
				geo.firing = true;
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
        
        public static Controller getInstance(int player) {
            if( instance == null) {
                instance =  new Controller(player);
            }
            return instance;
        }
}