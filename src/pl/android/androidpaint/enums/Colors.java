
package pl.android.androidpaint.enums;

import android.content.res.Resources;
import android.graphics.Color;

import pl.android.androidpaint.R;

public enum Colors {
    RED(R.string.red, Color.RED),
    GREEN(R.string.green, Color.GREEN),
    BLUE(R.string.blue, Color.BLUE),
    CYAN(R.string.cyan, Color.CYAN),
    MAGENTA(R.string.magenta, Color.MAGENTA),
    YELLOW(R.string.yellow, Color.YELLOW),
    BLACK(R.string.black, Color.BLACK);

    private Integer resId;
    private int color;

    Colors(int resId, int color) {
        this.resId = resId;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public StringBuffer getDescription(Resources r) {
        return new StringBuffer(r.getText(resId));
    }
}
