/*
 * http://www.codeproject.com/KB/GDI-plus/queuelinearfloodfill.aspx
 * 
 * Original algorithm by J. Dunlap
 */

package pl.android.androidpaint;

import java.util.LinkedList;
import java.util.Queue;

import android.graphics.Bitmap;

/**
 * @author Owen Kaluza
 */
public class QueueLinearFloodFiller {

    // Image to fill, colour to fill, colour tolerance R,G,B
    protected Bitmap image = null;
    protected int[] tolerance = new int[] { 0, 0, 0 };
    // cached image properties
    protected int width = 0;
    protected int height = 0;
    // internal, initialized per fill
    protected boolean[] pixelsChecked;
    protected int fillColour = 0;
    protected int[] startColour = new int[] { 0, 0, 0 };

    // Queue of floodfill ranges
    protected Queue<FloodFillRange> ranges;

    public QueueLinearFloodFiller(Bitmap image) {
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.image = image;
    }

    /*
     * public QueueLinearFloodFiller(Bitmap img, int targetColor, int newColor) { // Use a pre-existing provided BufferedImage and write directly to it // cache
     * data in member variables to decrease overhead of property calls width = img.getWidth(); height = img.getHeight(); image = img;
     * 
     * pixels = new int[width * height];
     * 
     * image.getPixels(pixels, 0, width, 1, 1, width - 1, height - 1);
     * 
     * setFillColour(newColor);
     * 
     * startColour[0] = Color.red(targetColor); startColour[1] = Color.green(targetColor); startColour[2] = Color.blue(targetColor); }
     */

    public int getFillColor() {
        return fillColour;
    }

    public void setFillColour(int value) {
        fillColour = value;
    }

    public int[] getTolerance() {
        return tolerance;
    }

    public void setTolerance(int[] value) {
        tolerance = value;
    }

    public void setTolerance(int value) {
        tolerance = new int[] { value, value, value };
    }

    public Bitmap getImage() {
        return image;
    }

    /** Called before starting flood-fill */
    protected void prepare() {
        pixelsChecked = new boolean[width * height];
        ranges = new LinkedList<FloodFillRange>();
    }

    /**
     * Fills the specified point on the bitmap with the currently selected fill color.
     * 
     * @param x
     *            The starting coords for the fill
     * @param y
     *            The starting coords for the fill
     */
    public void floodFill(int x, int y) {
        // Setup
        prepare();

        // Get starting color.
        int startPixel = image.getPixel(x, y);
        startColour[0] = (startPixel >> 16) & 0xff;
        startColour[1] = (startPixel >> 8) & 0xff;
        startColour[2] = startPixel & 0xff;

        // Do first call to floodfill.
        linearFill(x, y);

        // Call floodfill routine while floodfill ranges still exist on the queue
        FloodFillRange range;
        while (ranges.size() > 0) {
            // Get Next Range Off the Queue
            range = ranges.remove();

            // Check Above and Below Each Pixel in the Floodfill Range
            int downPxIdx = (width * (range.y + 1)) + range.startX;
            int upPxIdx = (width * (range.y - 1)) + range.startX;
            int upY = range.y - 1;// so we can pass the y coord by ref
            int downY = range.y + 1;
            for (int i = range.startX; i <= range.endX; i++) {
                // Start Fill Upwards
                // if we're not above the top of the bitmap and the pixel above this one is within the color tolerance
                if (range.y > 0 && (!pixelsChecked[upPxIdx]) && checkPixel(image.getPixel(range.startX, range.y - 1)))
                    linearFill(i, upY);

                // Start Fill Downwards
                // if we're not below the bottom of the bitmap and the pixel below this one is within the color tolerance
                if (range.y < (height - 1) && (!pixelsChecked[downPxIdx]) && checkPixel(image.getPixel(range.startX, range.y + 1)))
                    linearFill(i, downY);
                downPxIdx++;
                upPxIdx++;
            }

        }
    }

    /**
     * Finds the furthermost left and right boundaries of the fill area on a given y coordinate, starting from a given x coordinate, filling as it goes. Adds
     * the resulting horizontal range to the queue of floodfill ranges, to be processed in the main loop.
     * 
     * @param x
     *            The starting coords
     * @param y
     *            The starting coords
     */
    protected void linearFill(int x, int y) {
        // Find Left Edge of Color Area
        int lFillLoc = x; // the location to check/fill on the left
        int pxIdx = (width * y) + x;
        while (true) {
            // fill with the color
            image.setPixel(lFillLoc, y, fillColour);
            // indicate that this pixel has already been checked and filled
            pixelsChecked[pxIdx] = true;
            // de-increment
            lFillLoc--; // de-increment counter
            pxIdx--; // de-increment pixel index
            // exit loop if we're at edge of bitmap or color area
            if (lFillLoc < 0 || (pixelsChecked[pxIdx]) || !checkPixel(image.getPixel(lFillLoc, y)))
                break;
        }
        lFillLoc++;

        // Find Right Edge of Color Area
        int rFillLoc = x; // the location to check/fill on the left
        pxIdx = (width * y) + x;
        while (true) {
            // fill with the color
            image.setPixel(rFillLoc, y, fillColour);
            // indicate that this pixel has already been checked and filled
            pixelsChecked[pxIdx] = true;
            // increment
            rFillLoc++; // increment counter
            pxIdx++; // increment pixel index
            // exit loop if we're at edge of bitmap or color area
            if (rFillLoc >= width || pixelsChecked[pxIdx] || !checkPixel(image.getPixel(rFillLoc, y)))
                break;
        }
        rFillLoc--;

        // add range to queue
        FloodFillRange r = new FloodFillRange(lFillLoc, rFillLoc, y);
        ranges.offer(r);
    }

    /** Sees if a pixel is within the color tolerance range. */
    protected boolean checkPixel(int px) {
        int red = (px >>> 16) & 0xff;
        int green = (px >>> 8) & 0xff;
        int blue = px & 0xff;

        return (red >= (startColour[0] - tolerance[0]) && red <= (startColour[0] + tolerance[0]) && green >= (startColour[1] - tolerance[1])
                && green <= (startColour[1] + tolerance[1]) && blue >= (startColour[2] - tolerance[2]) && blue <= (startColour[2] + tolerance[2]));
    }

    /** Represents a linear range to be filled and branched from. */
    protected class FloodFillRange {
        public int startX;
        public int endX;
        public int y;

        public FloodFillRange(int startX, int endX, int y) {
            this.startX = startX;
            this.endX = endX;
            this.y = y;
        }
    }
}
