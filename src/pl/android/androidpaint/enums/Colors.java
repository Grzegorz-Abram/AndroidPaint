package pl.android.androidpaint.enums;

import pl.android.androidpaint.R;
import android.content.res.Resources;
import android.graphics.Color;

public enum Colors {
    RED(R.string.red, Color.RED),
    GREEN(R.string.green, Color.GREEN),
    BLUE(R.string.blue, Color.BLUE),
    CYAN(R.string.cyan, Color.CYAN),
    MAGENTA(R.string.magenta, Color.MAGENTA),
    BLACK(R.string.black, Color.BLACK);

    private Integer resId;
    private int color;

    Colors(int resId, int color) {
        this.resId = resId;
        this.color = color;
    }

    public StringBuffer getDescription(Resources r) {
        return new StringBuffer(r.getText(resId));
    }

    public int getColor() {
        return color;
    }
}
