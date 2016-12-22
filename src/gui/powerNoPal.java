package gui;


public class powerNoPal extends Power{
    	
        public powerNoPal(int droprate){
		super(PLAYERCOLLIDE);
		instantActivate = true;
                this.dropRate = droprate;
	}
	public void doAction(SimulationBody body){
		Ship ship = (Ship)body;
		ship.deactivateDrone(3);
	}
}
