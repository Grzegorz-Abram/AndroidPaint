package pl.android.androidpaint;

import android.graphics.RectF;

public class PointsToDraw {

	private RectF figure;
	private int color;

	public PointsToDraw(RectF figure, int color) {
		this.figure = figure;
		this.color = color;
	}

	public int getColor() {
		return color;
	}

	public RectF getFigure() {
		return figure;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setFigure(RectF figure) {
		this.figure = figure;
	}
}