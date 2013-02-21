package pl.android.androidpaint;

import java.util.LinkedList;
import java.util.Queue;

import android.graphics.Bitmap;
import android.graphics.Point;

public class FloodFiller {
    private Bitmap bitmap;
    private int newColor;
    protected int[] tolerance;
    protected int[] oldColor;

    public FloodFiller(Bitmap bitmap, int newColor) {
        this.bitmap = bitmap;
        this.newColor = newColor;
        this.oldColor = new int[] { 0, 0, 0 };
        this.tolerance = new int[] { 0, 0, 0 };
    }

    public FloodFiller(Bitmap bitmap, int newColor, int tolerance) {
        this.bitmap = bitmap;
        this.newColor = newColor;
        this.oldColor = new int[] { 0, 0, 0 };
        this.tolerance = new int[] { tolerance, tolerance, tolerance };
    }

    private boolean checkPixel(int px) {
        int red = (px >>> 16) & 0xff;
        int green = (px >>> 8) & 0xff;
        int blue = px & 0xff;

        return (red >= (oldColor[0] - tolerance[0]) && red <= (oldColor[0] + tolerance[0]) && green >= (oldColor[1] - tolerance[1])
                && green <= (oldColor[1] + tolerance[1]) && blue >= (oldColor[2] - tolerance[2]) && blue <= (oldColor[2] + tolerance[2]));
    }

    public void floodFill(Point point) {
        int old = bitmap.getPixel(point.x, point.y);
        oldColor[0] = (old >> 16) & 0xff;
        oldColor[1] = (old >> 8) & 0xff;
        oldColor[2] = old & 0xff;

        if (old != newColor) {
            Queue<Point> queue = new LinkedList<Point>();
            do {
                int x = point.x;
                int y = point.y;
                while (x > 0 && checkPixel(bitmap.getPixel(x - 1, y))) {
                    x--;
                }
                boolean spanUp = false;
                boolean spanDown = false;
                while (x < bitmap.getWidth() && checkPixel(bitmap.getPixel(x, y))) {
                    bitmap.setPixel(x, y, newColor);
                    if (!spanUp && y > 0 && checkPixel(bitmap.getPixel(x, y - 1))) {
                        queue.add(new Point(x, y - 1));
                        spanUp = true;
                    } else if (spanUp && y > 0 && !checkPixel(bitmap.getPixel(x, y - 1))) {
                        spanUp = false;
                    }
                    if (!spanDown && y < bitmap.getHeight() - 1 && checkPixel(bitmap.getPixel(x, y + 1))) {
                        queue.add(new Point(x, y + 1));
                        spanDown = true;
                    } else if (spanDown && y < bitmap.getHeight() - 1 && !checkPixel(bitmap.getPixel(x, y + 1))) {
                        spanDown = false;
                    }
                    x++;
                }
            } while ((point = queue.poll()) != null);
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
