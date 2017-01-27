/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameinterface;

import javax.swing.JFrame;

/**
 *
 * @author Jonas Lauwers <jonas.lauwers AT gmail.org>
 */
public class Gui extends JFrame {
    
    private Menu mainMenu;
    private Menu difficultyMenu;
    private Menu game;
    
    public Gui() {
        this.setSize(1024, 768);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void switchMenu(Menu removeMenu, Menu addMenu) {
        removeMenu.setVisiblity(false);
        addMenu.setVisibility(true);
    }
    
}
