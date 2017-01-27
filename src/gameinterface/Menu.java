/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameinterface;

import javax.swing.JPanel;

/**
 *
 * @author Jonas Lauwers <jonas.lauwers AT gmail.org>
 */
public abstract class Menu extends JPanel {

    protected Gui frame;

    public Menu(Gui frame) {
        this.frame = frame;
        setOpaque(false);
        setFont(new java.awt.Font("PT Mono", 1, 18));
    }

}
