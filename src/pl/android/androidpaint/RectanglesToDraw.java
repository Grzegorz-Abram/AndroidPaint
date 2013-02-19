package pl.android.androidpaint;

public class RectanglesToDraw {
	private float left;
	private float top;
	private float right;
	private float bottom;
	private int color;
	private int size;

	public RectanglesToDraw(float left, float top, float right, float bottom, int color, int size) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.color = color;
		this.size = size;
	}

	public float getBottom() {
		return bottom;
	}

	public int getColor() {
		return color;
	}

	public float getLeft() {
		return left;
	}

	public float getRight() {
		return right;
	}
	
	public int getSize() {
		return size;
	}

	public float getTop() {
		return top;
	}

	public void setBottom(float bottom) {
		this.bottom = bottom;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setLeft(float left) {
		this.left = left;
	}

	public void setRight(float right) {
		this.right = right;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setTop(float top) {
		this.top = top;
	}
}