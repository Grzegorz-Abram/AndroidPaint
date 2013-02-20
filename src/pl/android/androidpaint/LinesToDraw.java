package pl.android.androidpaint;

public class LinesToDraw {

    private float[] pts;
    private int color;
    private int size;

    public LinesToDraw(float[] pts, int color, int size) {
        this.pts = pts;
        this.color = color;
        this.size = size;
    }

    public int getColor() {
        return color;
    }

    public float[] getPts() {
        return pts;
    }

    public int getSize() {
        return size;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setPts(float[] pts) {
        this.pts = pts;
    }

    public void setSize(int size) {
        this.size = size;
    }
}