package pl.android.paint;

import android.graphics.RectF;

public class FigureToDraw {
	private float left;
	private float top;
	private float right;
	private float bottom;
	private float circleX;
	private float circleY;
	private float circleRadius;
	private RectF figure;
	private float[] pts;
	private int color;
	private int size;

	public FigureToDraw(RectF figure, int color) {
		this.figure = figure;
		this.color = color;
	}

	public FigureToDraw(float[] pts, int color, int size) {
		this.pts = pts;
		this.color = color;
		this.size = size;
	}

	public FigureToDraw(float circleX, float circleY, float circleRadius, int color, int size) {
		this.circleX = circleX;
		this.circleY = circleY;
		this.circleRadius = circleRadius;
		this.color = color;
		this.size = size;
	}

	public FigureToDraw(float left, float top, float right, float bottom, int color, int size) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.color = color;
		this.size = size;
	}

	public float getLeft() {
		return left;
	}

	public void setLeft(float left) {
		this.left = left;
	}

	public float getTop() {
		return top;
	}

	public void setTop(float top) {
		this.top = top;
	}

	public float getRight() {
		return right;
	}

	public void setRight(float right) {
		this.right = right;
	}

	public float getBottom() {
		return bottom;
	}

	public void setBottom(float bottom) {
		this.bottom = bottom;
	}

	public float getCircleX() {
		return circleX;
	}

	public void setCircleX(float circleX) {
		this.circleX = circleX;
	}

	public float getCircleY() {
		return circleY;
	}

	public void setCircleY(float circleY) {
		this.circleY = circleY;
	}

	public float getCircleRadius() {
		return circleRadius;
	}

	public void setCircleRadius(float circleRadius) {
		this.circleRadius = circleRadius;
	}

	public float[] getPts() {
		return pts;
	}

	public void setPts(float[] pts) {
		this.pts = pts;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public RectF getFigure() {
		return figure;
	}

	public void setFigure(RectF figure) {
		this.figure = figure;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
}
