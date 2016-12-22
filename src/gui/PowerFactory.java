package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.dyn4j.geometry.Vector2;

public class PowerFactory {
    
        private Random rand = new Random();
    
        private int dropRate;
        private List<Power> powerList = new ArrayList<Power>();
        
        public PowerFactory(int generalDropRate, int enemyDroprate) {
            this.dropRate = generalDropRate;
            powerList.add(new powerNoPal(enemyDroprate));
            powerList.add(new powerNoShield(enemyDroprate));
            powerList.add(new powerNoFire(enemyDroprate));
        }
        
        //add offset if more than one power returned
	public List<Power> getPowers(Vector2 location){
            List<Power> droppedPowers = new ArrayList<>();
            for(Power p: powerList) {
                if(willDropPower(p)){
                    p.setLocation(location);
                    droppedPowers.add(p);
                }
            }
		return droppedPowers;
	}
        
        public boolean willDropPower(Power power) {
            int random = rand.nextInt(100);
            if((random <= dropRate) && (random <= power.getDropRate())) {
                return true;
            }
            return false;
        }
}