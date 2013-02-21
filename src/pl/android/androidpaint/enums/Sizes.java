package pl.android.androidpaint.enums;

import pl.android.androidpaint.R;

public enum Sizes implements CharSequence {
    SMALL("Small", 2, R.drawable.pencil),
    MEDIUM("Medium", 10, R.drawable.brush),
    BIG("Big", 20, R.drawable.widebrush);

    private String description;
    private int size;
    private int icon;

    Sizes(String description, int size, int icon) {
        this.description = description;
        this.size = size;
        this.icon = icon;
    }

    @Override
    public char charAt(int index) {
        return description.charAt(index);
    }

    public int getIcon() {
        return icon;
    }

    public int getSize() {
        return size;
    }

    @Override
    public int length() {
        return description.length();
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return description.subSequence(start, end);
    }

    @Override
    public String toString() {
        return description;
    }
}
