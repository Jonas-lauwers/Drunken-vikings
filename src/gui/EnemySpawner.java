 package gui;

public class EnemySpawner {
	private int speed;
	private int xPos;
	private int yPos;
	private String type;
	public EnemySpawner(int speed, int xPos, int yPos, String type) {
		this.speed = speed;
		this.xPos = xPos;
		this.yPos = yPos;
		this.type = type;
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
