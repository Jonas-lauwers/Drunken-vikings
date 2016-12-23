package enemies;

// why doesn't this create the enemies? this should be a factory class that 

import java.util.Random;
import org.dyn4j.geometry.Vector2;

// creates and adds the enemies to the world, so it should have the world as 
// parameter to. then we can limit the enemies in the field by keeping a counter
// how many enemies are created etc
public class EnemySpawner {
    
    private int spawnSpeed;
    private Vector2 location;;
    private int difficulty;
    private int enemies;
    private Random rand = new Random();
    private int enemyCount;
    private int maxEnemies;
    
    private static final int WARRIOR = 1;
    private static final int DRAGON = 2;
    private static final int KING = 3;

    public EnemySpawner(int spawnSpeed, Vector2 location, int difficulty, int enemies, int maxEnemies) {
        this.spawnSpeed = spawnSpeed;
        this.location = location;
        this.enemies = enemies;
        this.enemyCount = 0;
        this.maxEnemies = maxEnemies;
        this.difficulty = difficulty;
    }
    
    public Enemy spawnEnemy() {
        if((enemyCount < maxEnemies) && (rand.nextInt(1000) <= spawnSpeed)) {
            enemyCount++;
            switch (rand.nextInt(enemies)+1) {
                case WARRIOR:
                    return new EnemyWarrior(location, difficulty);
                case DRAGON:
                    return new EnemyDragon(location, difficulty);
                case KING:
                    return new EnemyKing(location, difficulty, 1);
            }
        }
        return null;
    }
    
    public void enemyDied() {
        enemyCount--;
    }
    
}
