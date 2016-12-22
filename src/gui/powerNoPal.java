package gui;


public class powerNoPal extends Power{
    	
    public powerNoPal(int droprate){
        super(PLAYERCOLLIDE);
        instantActivate = true;
        this.dropRate = droprate;
        this.skin = getImageSuppressExceptions(NODRONE);
    }
    public void doAction(SimulationBody body){
        body.deactivateDrone(3);
    }
}
