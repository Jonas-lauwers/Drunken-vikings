/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameinterface;

import gui.geowars;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Jonas Lauwers <jonas.lauwers AT gmail.org>
 */
public class Gui extends JFrame {

    protected Menu highScore = new MainMenu(this);
    protected Menu store = new MainMenu(this);
    protected Menu settings = new MainMenu(this);
    protected Menu info = new MainMenu(this);
    
    private JPanel container;
    private geowars game;
    private Player player;
    
    public static void main(String[] args) {
        JFrame drunkenVikings = new Gui();
    }

    public Gui() {
        this.player = new Player(1, "test");
        this.game = new geowars();
        Init();
    }

    private void Init() {
        setAlwaysOnTop(true);
        setLocation(new java.awt.Point(0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFont(new java.awt.Font("PT Mono", 1, 18)); // NOI18N
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        container = new JPanel(new CardLayout());
        container.setMaximumSize(new java.awt.Dimension(1024, 768));
        container.setMinimumSize(new java.awt.Dimension(1024, 768));
        container.setPreferredSize(new java.awt.Dimension(1024, 768));
        container.setSize(new java.awt.Dimension(1024, 768));
        container.setBackground(new Color(24,33,42));
        container.setFont(new java.awt.Font("PT Mono", 1, 18));
        
        container.add(new MainMenu(this), "main");
        container.add(settings, "settings");
        container.add(info, "info");
        container.add(store, "store");
        container.add(highScore, "highScore");
        container.add(new PauseMenu(this), "pause");
        container.add(new GameOverMenu(this), "gameover");
        container.add(new DifficultyMenu(this), "difficulty");
        container.add(new GamePanel(this, game), "game");
        container.setVisible(true);
        
        add(container);
        pack();
        setVisible(true);
    }

    public void switchMenu(String menu) {
        CardLayout layout = (CardLayout) container.getLayout();
        layout.show(container, menu);
    }

    public void start() {
        game.setVisible(true);
        game.setFocusable(true);
        game.requestFocus();
        if (game.isPaused()) {
            game.resume();
        } else {
            game.run();
        }
    }
    
    public void pause() {
        game.setVisible(false);
        switchMenu("pause");
    }
    
    public void setFinishedScore(int score, int currency) {
        player.setScore(score, game.getDifficulty());
        player.setCurrency(currency);
    }
    
    public void stop() {
        game = new geowars();
        game.setCurrency(player.getCurrency());
    }

    public void setDifficulty(String type) {
        game.setDifficulty(type);
        player.setLastDifficulty(type);
    }

    public boolean isPaused() {
        return game.isPaused();
    }
    
    public geowars getGame() {
        return this.game;
    }
    
    public int getLastScore() {
        return player.getLastScore();
    }
    
    public int getCurrency() {
        return player.getCurrency();
    }
    
    public String getDifficulty() {
        return player.getLastDifficulty();
    }
    
    public int getHighScore() {
        return player.getHighScore();
    }

}