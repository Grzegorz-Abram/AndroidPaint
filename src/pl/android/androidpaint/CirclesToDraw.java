package pl.android.androidpaint;

public class CirclesToDraw {

	private float circleX;
	private float circleY;
	private float circleRadius;
	private int color;
	private int size;

	public CirclesToDraw(float circleX, float circleY, float circleRadius, int color, int size) {
		this.circleX = circleX;
		this.circleY = circleY;
		this.circleRadius = circleRadius;
		this.color = color;
		this.size = size;
	}

	public float getCircleRadius() {
		return circleRadius;
	}

	public float getCircleX() {
		return circleX;
	}

	public float getCircleY() {
		return circleY;
	}

	public int getColor() {
		return color;
	}

	public int getSize() {
		return size;
	}

	public void setCircleRadius(float circleRadius) {
		this.circleRadius = circleRadius;
	}

	public void setCircleX(float circleX) {
		this.circleX = circleX;
	}

	public void setCircleY(float circleY) {
		this.circleY = circleY;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setSize(int size) {
		this.size = size;
	}
}