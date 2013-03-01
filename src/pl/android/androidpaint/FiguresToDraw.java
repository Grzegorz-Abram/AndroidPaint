
package pl.android.androidpaint;

import android.graphics.Path;

public class FiguresToDraw {
    private int color;
    private int size;
    private Path path;

    public FiguresToDraw(Path path, int color, int size) {
        this.path = path;
        this.color = color;
        this.size = size;
    }

    public int getColor() {
        return color;
    }

    public Path getPath() {
        return path;
    }

    public int getSize() {
        return size;
    }
}
