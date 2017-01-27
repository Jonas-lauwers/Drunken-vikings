/*
 * Copyright (c) 2010-2016 William Bittle  http://www.dyn4j.org/
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted 
 * provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice, this list of conditions 
 *     and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice, this list of conditions 
 *     and the following disclaimer in the documentation and/or other materials provided with the 
 *     distribution.
 *   * Neither the name of dyn4j nor the names of its contributors may be used to endorse or 
 *     promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR 
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL 
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER 
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gui;

import enemies.Enemy;
import enemies.EnemySpawner;
import gameinterface.GamePanel;
import powers.Power;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.dyn4j.collision.manifold.Manifold;
import org.dyn4j.collision.narrowphase.Penetration;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.CollisionAdapter;
import org.dyn4j.dynamics.contact.ContactConstraint;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

public class geowars extends simulationPanel {

    private GamePanel parent;

    public geowars(GamePanel parent) {
        // creates a simulation panel with a scale of 1
        super(1);

        // add key and collisionListener
        KeyListener listener = new CustomKeyListener();
        this.addKeyListener(listener);
        MouseListener mouse = new CustomMouseListener();
        this.addMouseListener(mouse);
        this.addMouseMotionListener((MouseMotionListener) mouse);
        this.world.addListener(new CustomCollisionListener());

        // temp for testing enemy shooting
        this.rand = new Random();
        this.parent = parent;
    }

    Controller controller;

    // temp vars for ship and enemy for testing
    protected Ship ship;
    private ArrayList<Enemy> enemyList;
    private ArrayList<EnemySpawner> spawnerList;
    private int experience = 0;
    private int score = 0;
    private int multiplier = 1;

    private int deadCount = 0;

    protected boolean moveLeft;
    protected boolean moveRight;
    protected boolean moveUp;
    protected boolean moveDown;
    protected boolean firing;
    protected boolean usingController;

    private boolean droneAdded;
    private boolean droneRemoved;

    private Random rand;

    private class CustomCollisionListener extends CollisionAdapter {

        private void addDroppedPowers(List<Power> powers) {
            for (Power p : powers) {
                if (!world.containsBody(p)) {
                    world.addBody(p);
                }
            }
        }

        private boolean checkDeadness(SimulationBody b) {
            if (b.isDead() && !b.getMass().isInfinite()) {
                world.removeBody(b);
                // if is enemy make it drop a gem
                if (b instanceof Enemy) {
                    Enemy e = (Enemy) b;
                    world.addBody(e.dropGem());
                    addDroppedPowers(e.getPowers(e.getWorldCenter()));
                    enemyList.remove(e);
                    deadCount++;
                }
                experience += b.getExpPoints() * multiplier;
                score += b.getScorePoints() * multiplier;
                return false;
            }
            return true;
        }

        @Override
        public boolean collision(Body body, BodyFixture bf, Body body1, BodyFixture bf1) {
            boolean cont = true;
            if (body instanceof SimulationBody && body1 instanceof SimulationBody) {
                SimulationBody b = (SimulationBody) body;
                SimulationBody b1 = (SimulationBody) body1;
                b.isHit(b1);
                b1.isHit(b);
                cont = checkDeadness(b) && cont;
                cont = checkDeadness(b1) && cont;
            }
            return cont;

        }

        @Override
        public boolean collision(Body body, BodyFixture bf, Body body1, BodyFixture bf1, Penetration pntrtn) {
            // System.out.println("penetration collision");
            return true;
        }

        @Override
        public boolean collision(Body body, BodyFixture bf, Body body1, BodyFixture bf1, Manifold mnfld) {
            // System.out.println("manifold collision");
            return true;
        }

        @Override
        public boolean collision(ContactConstraint cc) {
            // System.out.println("contactconstraint collision");
            return true;
        }
    }

    private class CustomMouseListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            if (!usingController) {
                firing = true;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (!usingController) {
                firing = false;
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if (!usingController) {
                Point p = e.getPoint();
                ship.turnToAngle(new Vector2(p.getX(), p.getY()));
            }
        }
    }

    private class CustomKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (!usingController) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        moveLeft = true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        moveRight = true;
                        break;
                    case KeyEvent.VK_UP:
                        moveUp = true;
                        break;
                    case KeyEvent.VK_DOWN:
                        moveDown = true;
                        break;
                    case KeyEvent.VK_ESCAPE:
                        pause();
                        parent.pause();
                }
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (!usingController) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        moveLeft = false;
                        break;
                    case KeyEvent.VK_RIGHT:
                        moveRight = false;
                        break;
                    case KeyEvent.VK_UP:
                        moveUp = false;
                        break;
                    case KeyEvent.VK_DOWN:
                        moveDown = false;
                        break;
                }
            }
        }
    }

    /**
     * Creates game objects and adds them to the world.
     */
    protected void initializeWorld() {
        controller = Controller.getInstance(this, 1);
        // set gravity to none :) welcome to space.
        this.world.setGravity(new Vector2(0, 0));
        // the floor
        SimulationBody floor = new SimulationBody();
        floor.addFixture(Geometry.createRectangle(1024, 1));
        floor.setMass(MassType.INFINITE);
        floor.translate(512, 767);
        this.world.addBody(floor);

        SimulationBody ceiling = new SimulationBody();
        ceiling.addFixture(Geometry.createRectangle(1024, 1));
        ceiling.setMass(MassType.INFINITE);
        ceiling.translate(512, 0);
        this.world.addBody(ceiling);

        // some bounding shapes
        SimulationBody right = new SimulationBody();
        right.addFixture(Geometry.createRectangle(1, 768));
        right.setMass(MassType.INFINITE);
        right.translate(1023, 768 / 2);
        this.world.addBody(right);
        SimulationBody left = new SimulationBody();
        left.addFixture(Geometry.createRectangle(1, 768));
        left.setMass(MassType.INFINITE);
        left.translate(0, 768 / 2);
        this.world.addBody(left);

        ship = new Ship(usingController);
        this.world.addBody(ship);
        this.world.addBody(ship.getDrone());
        droneAdded = true;
        droneRemoved = false;

        enemyList = new ArrayList<>();
        spawnerList = new ArrayList<>();
        spawnerList.add(new EnemySpawner(3, new Vector2(50, 50), 1, 1, 50));
        spawnerList.add(new EnemySpawner(3, new Vector2(1000, 50), 1, 1, 50));
        spawnerList.add(new EnemySpawner(3, new Vector2(50, 750), 1, 1, 50));

    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see org.dyn4j.samples.SimulationFrame#update(java.awt.Graphics2D,
	 * double)
     */
    @Override
    protected void update(Graphics2D g, double elapsedTime) {
        // do player action based on key/mouse input
        if (ship.getDroneIsActive() && !droneAdded) {
            Drone temp = ship.getDrone();
            this.world.addBody(temp);
            temp.rotateToAngle();
            droneAdded = !droneAdded;
            droneRemoved = !droneRemoved;
        }
        if (!ship.getDroneIsActive() && !droneRemoved) {
            this.world.removeBody(ship.getDrone());
            droneAdded = !droneAdded;
            droneRemoved = !droneRemoved;
        }

        ship.decreaseTimers(elapsedTime);

        if (moveLeft) {
            ship.move(-50, 0);
        }
        if (moveRight) {
            ship.move(50, 0);
        }
        if (moveUp) {
            ship.move(0, -50);
        }
        if (moveDown) {
            ship.move(0, 50);
        }
        if (firing && !ship.isDead() && ship.getFireIsActive()) {
            this.world.addBody(ship.shoot());
            firing = false;
        }
        if (!usingController) {
            ship.turnToAngle();
        }

        for (EnemySpawner s : spawnerList) {
            Enemy enemy = s.spawnEnemy();
            if (enemy != null) {
                this.world.addBody(enemy);
                enemyList.add(enemy);
            }
        }

        for (Enemy e : enemyList) {
            e.move(ship.getWorldCenter());
            if (!e.noFire && e.canShoot()) {
                this.world.addBody(e.shoot());
            }
        }

        // stop simulation if player is dead.
        if (!ship.isDead()) {
            parent.setScore(score);
            parent.setMultiplier(multiplier);
            parent.setExperience(experience);
            super.update(g, elapsedTime);
        } else {

            this.stop();
            parent.stop();
        }

        //if (deadCount == 10) {
        //	multiplier = 2;
        //	ship.getDrone().setRotateSpeed(0.2);
        //}
    }
}
