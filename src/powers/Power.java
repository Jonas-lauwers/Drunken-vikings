package powers;

import gui.Ship;
import gui.SimulationBody;
import org.dyn4j.collision.CategoryFilter;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

public abstract class Power extends SimulationBody {

    public static final String NOSHIELD = "/assets/Collectibles_Droppables/Powers/NoShield-1.png";
    public static final String NOFIRE = "/assets/Collectibles_Droppables/Powers/NoFire-1.png";
    public static final String NODRONE = "/assets/Collectibles_Droppables/Powers/NoPal-1.png";
    public static final String POISON = "/assets/Collectibles_Droppables/Powers/DeadlyPoison-1.png";
    public static final String BEER = "/assets/Collectibles_Droppables/Powers/Beer-1.png";
    public static final String CROSS = "/assets/Collectibles_Droppables/Powers/CrossShot-1.png";
    public static final String CRUEL = "/assets/Collectibles_Droppables/Powers/CruelFiring-1.png";
    public static final String POTION = "/assets/Collectibles_Droppables/Powers/MagicalSyrup-1.png";
    public static final String BOMB = "/assets/Collectibles_Droppables/Powers/Bomb-1.png";

    protected boolean instantActivate;
    protected int dropRate;

    public Power(int collideFilter) {
        Convex shape = Geometry.createCircle(25);
        BodyFixture fixture = new BodyFixture(shape);
        fixture.setFilter(new CategoryFilter(POWERCOLLIDE, collideFilter));
        this.addFixture(fixture);
        this.setMass(MassType.FIXED_LINEAR_VELOCITY);
    }

    public void setLocation(Vector2 location) {
        this.translateToOrigin();
        this.translate(location);
    }

    public int getDropRate() {
        return dropRate;
    }

    @Override
    public void isHit(SimulationBody body) {
        if (instantActivate) {
            this.doAction(body);
        } else if (body instanceof Ship) {
            body.addPower(this);
        }
    }

    public abstract void doAction(SimulationBody body);
}
