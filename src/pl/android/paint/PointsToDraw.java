package pl.android.paint;

import android.graphics.RectF;

public class PointsToDraw {

	private RectF figure;
	private int color;

	public PointsToDraw(RectF figure, int color) {
		this.figure = figure;
		this.color = color;
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