package pl.android.androidpaint;

import android.graphics.RectF;

public class FiguresToDraw {
	private float left;
	private float top;
	private float right;
	private float bottom;
	private float circleX;
	private float circleY;
	private float circleRadius;
	private RectF bounds;
	private float[] pts;
	private int color;
	private int size;
	private Figures figure;

	public FiguresToDraw(RectF bounds, int color) {
		this.bounds = bounds;
		this.color = color;
		this.figure = Figures.POINT;
	}

	public FiguresToDraw(float[] pts, int color, int size) {
		this.pts = pts;
		this.color = color;
		this.size = size;
		this.figure = Figures.LINE;
	}

	public FiguresToDraw(float circleX, float circleY, float circleRadius, int color, int size) {
		this.circleX = circleX;
		this.circleY = circleY;
		this.circleRadius = circleRadius;
		this.color = color;
		this.size = size;
		this.figure = Figures.CIRCLE;
	}

	public FiguresToDraw(float left, float top, float right, float bottom, int color, int size) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.color = color;
		this.size = size;
		this.figure = Figures.RECTANGLE;
	}

	public float getLeft() {
		return left;
	}

	public float getTop() {
		return top;
	}

	public float getRight() {
		return right;
	}

	public float getBottom() {
		return bottom;
	}

	public float getCircleX() {
		return circleX;
	}

	public float getCircleY() {
		return circleY;
	}

	public float getCircleRadius() {
		return circleRadius;
	}

	public float[] getPts() {
		return pts;
	}

	public int getSize() {
		return size;
	}

	public RectF getBounds() {
		return bounds;
	}

	public int getColor() {
		return color;
	}
	
	public Figures getFigure() {
		return figure;
	}
}
