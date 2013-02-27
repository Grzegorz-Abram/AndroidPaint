
package pl.android.androidpaint;

import android.graphics.Path;
import android.graphics.RectF;

import pl.android.androidpaint.enums.Figures;

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
    private Path path;

    public FiguresToDraw(float left, float top, float right, float bottom, int color, int size) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.color = color;
        this.size = size;
        this.figure = Figures.RECTANGLE;
    }

    public FiguresToDraw(float circleX, float circleY, float circleRadius, int color, int size) {
        this.circleX = circleX;
        this.circleY = circleY;
        this.circleRadius = circleRadius;
        this.color = color;
        this.size = size;
        this.figure = Figures.CIRCLE;
    }

    public FiguresToDraw(float[] pts, int color, int size) {
        this.pts = pts;
        this.color = color;
        this.size = size;
        this.figure = Figures.LINE;
    }

    public FiguresToDraw(Path path, int color, int size) {
        this.path = path;
        this.color = color;
        this.size = size;
        this.figure = Figures.POINT;
    }

    public FiguresToDraw(RectF bounds, int color) {
        this.bounds = bounds;
        this.color = color;
        this.figure = Figures.POINT;
    }

    public float getBottom() {
        return bottom;
    }

    public RectF getBounds() {
        return bounds;
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

    public Figures getFigure() {
        return figure;
    }

    public float getLeft() {
        return left;
    }

    public Path getPath() {
        return path;
    }

    public float[] getPts() {
        return pts;
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
}
