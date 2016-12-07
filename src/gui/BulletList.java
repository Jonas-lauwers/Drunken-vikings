package gui;

import java.awt.Graphics;
import java.util.ArrayList;

public class BulletList {
    
	private ArrayList<Bullet> bulletList = new ArrayList<>(); 
        
	public void add(Bullet bullet){
		bulletList.add(bullet);
	}
        
        //test for bullets
        public int getNumber() {
            return bulletList.size();
        }
}
