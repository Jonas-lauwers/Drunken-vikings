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

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.dyn4j.dynamics.World;

/**
 * A simple scene of a bowling ball bouncing on the floor.
 * <p>
 * Primarily used to illustrate the computation of the mass and size of the
 * ball. See the {@link WheelJointTesting#initializeWorld()} method.
 *
 * @author William Bittle
 * @since 3.2.0
 * @version 3.2.0 Test frame for the game, will be replaced with a more defined
 * version
 */
public abstract class simulationPanel extends Canvas {

    /**
     * The serial version id
     */
    private static final long serialVersionUID = 7659608187025022915L;

    /**
     * The conversion factor from nano to base
     */
    public static final double NANO_TO_BASE = 1.0e9;

    /**
     * The dynamics engine
     */
    protected final World world;

    /**
     * The pixels per meter scale factor
     */
    protected final double scale;

    /**
     * True if the simulation is exited
     */
    private boolean stopped;

    /**
     * True if the simulation is paused
     */
    private boolean paused;

    /**
     * The time stamp for the last iteration
     */
    private long last;

    
    protected BufferedImage background;
    /**
     * Constructor.
     * <p>
     * By default creates a 800x600 canvas.
     *
     * @param name the frame name
     * @param scale the pixels per meter scale factor
     */
    public simulationPanel(double scale) {

        // set the scale
        this.scale = scale;

        // create the world
        this.world = new World();

        // create the size of the window
        Dimension size = new Dimension(1024, 700);

        // create a canvas to paint to 
        this.setPreferredSize(size);
        this.setMinimumSize(size);
        this.setMaximumSize(size);

        // setup the world
        this.initializeWorld();
    }
    
    protected BufferedImage getImageSuppressExceptions(String pathOnClasspath) {
        try {
            return ImageIO.read(SimulationBody.class.getResource(pathOnClasspath));
        } catch (IOException | IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Creates game objects and adds them to the world.
     */
    protected abstract void initializeWorld();

    /**
     * Start active rendering the simulation.
     * <p>
     * This should be called after the JFrame has been shown.
     */
    private void start() {
        // initialize the last update time
        this.last = System.nanoTime();
        // don't allow AWT to paint the canvas since we are
        this.setIgnoreRepaint(true);
        // enable double buffering (the JFrame has to be
        // visible before this can be done)
        this.createBufferStrategy(2);
        // run a separate thread to do active rendering
        // because we don't want to do it on the EDT
        Thread thread = new Thread() {
            public void run() {
                // perform an infinite loop stopped
                // render as fast as possible
                while (!isStopped()) {
                    gameLoop();
                    // you could add a Thread.yield(); or
                    // Thread.sleep(long) here to give the
                    // CPU some breathing room
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                    }
                }
            }
        };
        // set the game loop thread to a daemon thread so that
        // it cannot stop the JVM from exiting
        thread.setDaemon(true);
        // start the game loop
        thread.start();
    }

    /**
     * The method calling the necessary methods to update the game, graphics,
     * and poll for input.
     */
    private void gameLoop() {
        // get the graphics object to render to
        Graphics2D g = (Graphics2D) this.getBufferStrategy().getDrawGraphics();

        // by default, set (0, 0) to be the center of the screen with the positive x axis
        // pointing right and the positive y axis pointing up
        //this.transform(g);
        // reset the view
        this.clear(g);

        // get the current time
        long time = System.nanoTime();
        // get the elapsed time from the last iteration
        long diff = time - this.last;
        // set the last time
        this.last = time;
        // convert from nanoseconds to seconds
        double elapsedTime = (double) diff / NANO_TO_BASE;

        // render anything about the simulation (will render the World objects)
        this.render(g, elapsedTime);

        if (!paused) {
            // update the World
            this.update(g, elapsedTime);
        }

        // dispose of the graphics object
        g.dispose();

        // blit/flip the buffer
        BufferStrategy strategy = this.getBufferStrategy();
        if (!strategy.contentsLost()) {
            strategy.show();
        }

        // Sync the display on some systems.
        // (on Linux, this fixes event queue problems)
        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * Performs any transformations to the graphics.
     * <p>
     * By default, this method puts the origin (0,0) in the center of the window
     * and points the positive y-axis pointing up.
     *
     * @param g the graphics object to render to
     */
    protected void transform(Graphics2D g) {
        final int w = this.getWidth();
        final int h = this.getHeight();

        // before we render everything im going to flip the y axis and move the
        // origin to the center (instead of it being in the top left corner)
        AffineTransform yFlip = AffineTransform.getScaleInstance(1, -1);
        AffineTransform move = AffineTransform.getTranslateInstance(w / 2, -h / 2);
        g.transform(yFlip);
        g.transform(move);
    }

    /**
     * Clears the previous frame.
     *
     * @param g the graphics object to render to
     */
    protected void clear(Graphics2D g) {
        final int w = this.getWidth();
        final int h = this.getHeight();

        // lets draw over everything with a white background
        g.setColor(new Color(73,144,255));
        g.fillRect(0, 0, w, h);
        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
    }

    /**
     * Renders the example.
     *
     * @param g the graphics object to render to
     * @param elapsedTime the elapsed time from the last update
     */
    protected void render(Graphics2D g, double elapsedTime) {
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // draw all the objects in the world
        for (int i = 0; i < this.world.getBodyCount(); i++) {
            // get the object
            SimulationBody body = (SimulationBody) this.world.getBody(i);
            this.render(g, elapsedTime, body);
        }
    }

    /**
     * Renders the body.
     *
     * @param g the graphics object to render to
     * @param elapsedTime the elapsed time from the last update
     * @param body the body to render
     */
    protected void render(Graphics2D g, double elapsedTime, SimulationBody body) {
        // draw the object
        body.render(g, this.scale);
    }

    /**
     * Updates the world.
     *
     * @param g the graphics object to render to
     * @param elapsedTime the elapsed time from the last update
     */
    protected void update(Graphics2D g, double elapsedTime) {
        // update the world with the elapsed time
        this.world.update(elapsedTime);
    }

    /**
     * Stops the simulation.
     */
    public synchronized void stop() {
        this.stopped = true;
    }

    /**
     * Returns true if the simulation is stopped.
     *
     * @return boolean true if stopped
     */
    public boolean isStopped() {
        return this.stopped;
    }

    /**
     * Pauses the simulation.
     */
    public synchronized void pause() {
        this.paused = true;
    }

    /**
     * Pauses the simulation.
     */
    public synchronized void resume() {
        this.paused = false;
    }

    /**
     * Returns true if the simulation is paused.
     *
     * @return boolean true if paused
     */
    public boolean isPaused() {
        return this.paused;
    }

    /**
     * Starts the simulation.
     */
    public void run() {
        
        this.requestFocus();
        // show it
        this.setVisible(true);

        // start it
        this.start();
    }
}
