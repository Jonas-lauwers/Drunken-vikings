package powers;

import gui.SimulationBody;

public class PowerPoison extends Power{

	public PowerPoison(int droprate) {
		super(PLAYERCOLLIDE|ENEMYCOLLIDE);
        instantActivate = true;
        this.dropRate = droprate;
        this.skin = getImageSuppressExceptions(POISON);
	}

	@Override
	public void doAction(SimulationBody body) {
		body.removeShield(1);
	}

}
