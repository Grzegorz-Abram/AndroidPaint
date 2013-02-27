
package pl.android.androidpaint.enums;

import android.content.res.Resources;

import pl.android.androidpaint.R;

public enum Sizes {
    SMALL(R.string.small, 2, R.drawable.pencil),
    MEDIUM(R.string.medium, 10, R.drawable.brush),
    BIG(R.string.big, 20, R.drawable.widebrush);

    private Integer resId;
    private int size;
    private int icon;

    Sizes(int resId, int size, int icon) {
        this.resId = resId;
        this.size = size;
        this.icon = icon;
    }

    public StringBuffer getDescription(Resources r) {
        return new StringBuffer(r.getText(resId));
    }

    public int getIcon() {
        return icon;
    }

    public int getSize() {
        return size;
    }
}
