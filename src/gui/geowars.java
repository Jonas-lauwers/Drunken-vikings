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

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import org.dyn4j.collision.manifold.Manifold;
import org.dyn4j.collision.narrowphase.Penetration;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.CollisionListener;
import org.dyn4j.dynamics.contact.ContactConstraint;

import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

/**
 * A simple scene of a circle that is controlled by the left and right arrow
 * keys that is moved by applying torques and forces.
 *
 * @author William Bittle
 * @since 3.2.1
 * @version 3.2.0
 */
public class geowars extends simulationPanel implements CollisionListener {

    /**
     * The serial version id
     */
    private static final long serialVersionUID = -313391186714427055L;

    /**
     * Default constructor for the window
     */
    public geowars() {
        super(1);

        KeyListener listener = new CustomKeyListener();
        this.addKeyListener(listener);
        MouseListener mouse = new CustomMouseListener();
        this.addMouseListener(mouse);
        this.addMouseMotionListener((MouseMotionListener) mouse);
        this.requestFocus();
    }

    private Ship ship;
    private Enemy enemy;

    private boolean moveLeft;
    private boolean moveRight;
    private boolean moveUp;
    private boolean moveDown;
    private boolean firing;

    // very very dirty :) refactor and delegate please :)
    @Override
    public boolean collision(Body body, BodyFixture bf, Body body1, BodyFixture bf1) {
        boolean cont = true;
        if(body instanceof SimulationBody && body1 instanceof SimulationBody) {
            SimulationBody b = (SimulationBody) body;
            SimulationBody b1 = (SimulationBody) body1;
            b.isHit();
            b1.isHit();
            if(b.isDead()) {
                this.world.removeBody(b);
                cont = false;
            }
            if(b1.isDead()) {
                this.world.removeBody(b1);
                cont = false;
            }
        }
        return false;

    }
    @Override
    public boolean collision(Body body, BodyFixture bf, Body body1, BodyFixture bf1, Penetration pntrtn) {
        System.out.println("penetration collision");
        return true;
    }

    @Override
    public boolean collision(Body body, BodyFixture bf, Body body1, BodyFixture bf1, Manifold mnfld) {
        System.out.println("manifold collision");
        return true;
    }

    @Override
    public boolean collision(ContactConstraint cc) {
        System.out.println("contactconstraint collision");
        return true;
    }

    private class CustomMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
           firing = true;
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
           firing = false;
        }
        
        @Override
        public void mouseMoved(MouseEvent e) {
            Point p = e.getPoint();
            ship.turnToAngle(new Vector2(p.getX(),p.getY()));
        }
    }
    
    /**
     * Custom key adapter to listen for key events.
     *
     * @author William Bittle
     * @version 3.2.1
     * @since 3.2.0
     */
    private class CustomKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
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
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
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

    /**
     * Creates game objects and adds them to the world.
     */
    protected void initializeWorld() {
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
        right.translate(1023, 768/2);
        this.world.addBody(right);
        SimulationBody left = new SimulationBody();
        left.addFixture(Geometry.createRectangle(1, 768));
        left.setMass(MassType.INFINITE);
        left.translate(0, 768/2);
        this.world.addBody(left);
        
        ship = new Ship();
        this.world.addBody(ship);
        enemy = new Enemy(5);
        this.world.addBody(enemy);
        this.world.addListener(this);
    }

    /* (non-Javadoc)
	 * @see org.dyn4j.samples.SimulationFrame#update(java.awt.Graphics2D, double)
     */
    @Override
    protected void update(Graphics2D g, double elapsedTime) {
        // apply a torque based on key input
        if (moveLeft) {
            ship.move(-50,0);
        }
        if (moveRight) {
            ship.move(50,0);
        }
        if (moveUp) {
            ship.move(0,-50);
        }
        if (moveDown) {
            ship.move(0,50);
        }
        if (firing) {
            this.world.addBody(ship.shoot());
            firing = false;
        }
        ship.turnToAngle();
        
       enemy.move(ship.getWorldCenter());
        
        if(ship.isInContact(enemy) && ship.isDead()) {
            this.world.removeBody(ship);
            this.world.removeBody(enemy);
        }
        //check all bullets if they hit something or are out of the field

        super.update(g, elapsedTime);
    }

    /**
     * Entry point for the example application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        JFrame temp = new JFrame("geowars");
        temp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        geowars simulation = new geowars();
        temp.add(simulation);
        temp.pack();
        temp.setVisible(true);
        simulation.setFocusable(true);
        simulation.run();
    }
}
