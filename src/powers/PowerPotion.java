package powers;

import gui.SimulationBody;

public class PowerPotion extends Power{

	public PowerPotion(int droprate) {
		super(PLAYERCOLLIDE|ENEMYCOLLIDE);
        instantActivate = true;
        this.dropRate = droprate;
        this.skin = getImageSuppressExceptions(POTION);
	}

	@Override
	public void doAction(SimulationBody body) {
		body.addShield(1);
	}

}
