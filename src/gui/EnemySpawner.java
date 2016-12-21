package gui;


public class EnemySpawner {

    private static final String DRAGON = "/assets/Enemies/Enemy-DragonShip-1.png";
    private static final String KING = "/assets/Enemies/Enemy-KingsShip-1.png";
    private static final String WARRIOR = "/assets/Enemies/Enemy-WarriorShip-1.png";
    
    private int speed;
    private int xPos;
    private int yPos;
    private String type;

    public EnemySpawner(int speed, int xPos, int yPos, String type) {
        this.speed = speed;
        this.xPos = xPos;
        this.yPos = yPos;
        try {
            this.type = (String) EnemySpawner.class.getDeclaredField(type.toUpperCase()).get(null);
        }
        catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException ex) {
            this.type = "";
        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public String getType() {
        return type;
    }
}
