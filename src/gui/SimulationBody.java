package gui;

import enemies.Enemy;
import powers.Power;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import javax.imageio.ImageIO;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.*;

/**
 * Custom Body class to add drawing functionality.
 *
 * @author William Bittle
 * @version 3.2.1
 * @since 3.0.0 Has some extra functionality (isHit, isDead ....) will be
 *        replaced by a full blown object that encapsulates all
 *        moving/living/shooting objects.
 */
public class SimulationBody extends Body {

	/**
	 * The color of the object
	 */
	protected Color color;

	protected boolean hasSprite;
	protected int numberOfSprites;
	protected int spriteHeight;
	protected int spriteWidth;
	private int spritecounter;
	private int currentSprite;
	/**
	 * the filter categories to define what can collide with who *
	 */
	public static final int PLAYERCOLLIDE = 1;
	public static final int ENEMYCOLLIDE = 2;
	public static final int BULLETCOLLIDE = 4;
	public static final int GEMCOLLIDE = 8;
	public static final int DRONECOLLIDE = 16;
	public static final int POWERCOLLIDE = 32;

	protected int shield = 0;
	protected int damage = 0;
	protected int expPoints = 0;
	protected int scorePoints = 0;
	protected BufferedImage skin = null;
	protected Power power;
	protected HashMap<String, Double> timerMap;
	protected boolean droneIsActive = true;
	protected boolean instantDeath;
	protected boolean noFire;
        protected boolean cruelFire;
        protected int bulletSpeed = 7;

	/**
	 * Default constructor.
	 */
	public SimulationBody() {
		// randomly generate the color
		this.color = new Color((float) Math.random() * 0.5f + 0.5f, (float) Math.random() * 0.5f + 0.5f,
				(float) Math.random() * 0.5f + 0.5f);
		this.timerMap = new HashMap<>();
	}

	/**
	 * Constructor.
	 *
	 * @param color
	 *            a set color
	 */
	public SimulationBody(Color color) {
		this.color = color;
	}

	// added body param to get damage of the colliding body
	// if it's a gem or something else they still can get hit but with 0 damage
	public void isHit(SimulationBody b) {
		if ((b instanceof Ship || b instanceof Enemy) && instantDeath) {
			shield = 1;
		}
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

	public void addPower(Power power) {
		this.power = power;
	}

	public void decreaseTimers(double timePassed) {
		for (String key : timerMap.keySet()) {
			timerMap.put(key, timerMap.get(key) - timePassed);
		}
		checkTimers();
	}

	public void checkTimers() {
		Iterator it = timerMap.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			Double temp = timerMap.get(key);
			if (temp <= 0) {
				try {
					boolean reverse = SimulationBody.class.getDeclaredField(key).getBoolean(this);
					SimulationBody.class.getDeclaredField(key).setBoolean(this, !reverse);
					it.remove();
				} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
						| SecurityException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean getDroneIsActive() {
		return droneIsActive;
	}

	public boolean getFireIsActive() {
		return !noFire;
	}

	public void deactivateDrone(double time) {
		droneIsActive = false;
		timerMap.put("droneIsActive", time);
	}

	public void shieldDown(double time) {
		instantDeath = true;
		timerMap.put("instantDeath", time);
	}

	public void disableFire(double time) {
		noFire = true;
		timerMap.put("noFire", time);
	}
        
        public void setBulletSpeed(int speed, double time) {
            cruelFire = true;
            this.bulletSpeed = speed;
            timerMap.put("cruelFire", time);
        }

	protected BufferedImage getImageSuppressExceptions(String pathOnClasspath) {
		try {
			return ImageIO.read(SimulationBody.class.getResource(pathOnClasspath));
		} catch (IOException | IllegalArgumentException e) {
			return null;
		}
	}

	/**
	 * Draws the body.
	 * <p>
	 * Only coded for polygons and circles.
	 *
	 * @param g
	 *            the graphics object to render to
	 * @param scale
	 *            the scaling factor
	 */
	public void render(Graphics2D g, double scale) {
		this.render(g, scale, this.color);
	}

	/**
	 * Draws the body.
	 * <p>
	 * Only coded for polygons and circles.
	 *
	 * @param g
	 *            the graphics object to render to
	 * @param scale
	 *            the scaling factor
	 * @param color
	 *            the color to render the body
	 */
	public void render(Graphics2D g, double scale, Color color) {
		// save the original transform
		AffineTransform ot = g.getTransform();

		// transform the coordinate system from world coordinates to local
		// coordinates
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
	 * @param g
	 *            the graphics object to render to
	 * @param scale
	 *            the scaling factor
	 * @param fixture
	 *            the fixture to render
	 * @param color
	 *            the color to render the fixture
	 */
	protected void renderFixture(Graphics2D g, double scale, BodyFixture fixture, Color color) {
		// get the shape on the fixture
		Convex convex = fixture.getShape();

		// brighten the color if asleep
		if (this.isAsleep()) {
			color = color.brighter();
		}
		if (skin != null) {
			if (convex instanceof Polygon) {
				// since Triangle, Rectangle, and Polygon are all of
				// type Polygon in addition to their main type
				if (convex instanceof Rectangle) {
					Rectangle r = (Rectangle) convex;
					Vector2 c = r.getCenter();
					double w = r.getWidth();
					double h = r.getHeight();
					if (hasSprite) {
						drawSprite(g, c, r, scale);
					} else {
						g.drawImage(skin, (int) Math.ceil((c.x - w / 2.0) * scale),
								(int) Math.ceil((c.y - h / 2.0) * scale), (int) Math.ceil(w * scale),
								(int) Math.ceil(h * scale), null);
					}
					//g.drawRect((int) Math.ceil((c.x - w / 2.0) * scale), (int) Math.ceil((c.y - h / 2.0) * scale),
						//	(int) Math.ceil(w * scale), (int) Math.ceil(h * scale));
				} else {
					Graphics2DRenderer.render(g, convex, scale, this.color);
				}
			} else if (convex instanceof Circle) {
				// cast the shape to get the radius
				Circle c = (Circle) convex;
				double r = c.getRadius();
				Vector2 cc = c.getCenter();
				int x = (int) Math.ceil((cc.x - r) * scale);
				int y = (int) Math.ceil((cc.y - r) * scale);
				int w = (int) Math.ceil(r * 2 * scale);
				if (this.getFixtureCount() == 1) {
					// lets us an image instead
					g.drawImage(skin, x, y, w, w, null);
				} else {
					Graphics2DRenderer.render(g, convex, scale, this.color);
				}
			} else {
				Graphics2DRenderer.render(g, convex, scale, this.color);
			}
		} else {
			Graphics2DRenderer.render(g, convex, scale, this.color);
		}
	}

	protected void drawSprite(Graphics2D g, Vector2 c, Rectangle r, double scale) {
		g.drawImage(skin, (int) Math.ceil((c.x - r.getWidth() / 2.0) * scale),
				(int) Math.ceil((c.y - r.getHeight() / 2.0) * scale), (int) Math.ceil(r.getWidth() / 2.0 * scale),
				(int) Math.ceil(r.getHeight() / 2.0 * scale), (currentSprite - 1) * spriteWidth, 0,
				currentSprite * spriteWidth, spriteHeight, null);
		if (spritecounter % 11 == 0) {
			currentSprite++;
		}
		spritecounter++;
		if (currentSprite >= numberOfSprites) {
			currentSprite = 1;
		}
	}
}
