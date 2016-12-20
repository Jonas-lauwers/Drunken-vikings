package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Convex;

/**
 * Custom Body class to add drawing functionality.
 *
 * @author William Bittle
 * @version 3.2.1
 * @since 3.0.0 Has some extra functionality (isHit, isDead ....) will be
 * replaced by a full blown object that encapsulates all moving/living/shooting
 * objects.
 */
public class SimulationBody extends Body {

    /**
     * The color of the object
     */
    protected Color color;

    /**
     * the filter categories to define what can collide with who *
     */
    public static final int PLAYERCOLLIDE = 1;
    public static final int ENEMYCOLLIDE = 2;
    public static final int BULLETCOLLIDE = 4;
    public static final int GEMCOLLIDE = 8;
    public static final int DRONECOLLIDE = 16;

    protected int shield = 0;
    protected int damage = 0;
    protected int expPoints = 0;
    protected int scorePoints = 0;

    /**
     * Default constructor.
     */
    public SimulationBody() {
        // randomly generate the color
        this.color = new Color(
                (float) Math.random() * 0.5f + 0.5f,
                (float) Math.random() * 0.5f + 0.5f,
                (float) Math.random() * 0.5f + 0.5f);
    }

    /**
     * Constructor.
     *
     * @param color a set color
     */
    public SimulationBody(Color color) {
        this.color = color;
    }

    //added body param to get damage of the colliding body
    //if it's a gem or something else they still can get hit but with 0 damage
    public void isHit(SimulationBody b) {
        shield -= b.getDamage();
    }

    public boolean isDead() {
        return shield <= 0;
    }

    public int getDamage() {
        return damage;
    }

    public int getExpPoints() {
        return expPoints;
    }

    public int getScorePoints() {
        return scorePoints;
    }

    /**
     * Draws the body.
     * <p>
     * Only coded for polygons and circles.
     *
     * @param g the graphics object to render to
     * @param scale the scaling factor
     */
    public void render(Graphics2D g, double scale) {
        this.render(g, scale, this.color);
    }

    /**
     * Draws the body.
     * <p>
     * Only coded for polygons and circles.
     *
     * @param g the graphics object to render to
     * @param scale the scaling factor
     * @param color the color to render the body
     */
    public void render(Graphics2D g, double scale, Color color) {
        // save the original transform
        AffineTransform ot = g.getTransform();

        // transform the coordinate system from world coordinates to local coordinates
        AffineTransform lt = new AffineTransform();
        lt.translate(this.transform.getTranslationX() * scale, this.transform.getTranslationY() * scale);
        lt.rotate(this.transform.getRotation());

        // apply the transform
        g.transform(lt);

        // loop over all the body fixtures for this body
        for (BodyFixture fixture : this.fixtures) {
            this.renderFixture(g, scale, fixture, color);
        }
        // set the original transform
        g.setTransform(ot);
    }

    /**
     * Renders the given fixture.
     *
     * @param g the graphics object to render to
     * @param scale the scaling factor
     * @param fixture the fixture to render
     * @param color the color to render the fixture
     */
    protected void renderFixture(Graphics2D g, double scale, BodyFixture fixture, Color color) {
        // get the shape on the fixture
        Convex convex = fixture.getShape();

        // brighten the color if asleep
        if (this.isAsleep()) {
            color = color.brighter();
        }

        // render the fixture
        Graphics2DRenderer.render(g, convex, scale, color);
    }
}
