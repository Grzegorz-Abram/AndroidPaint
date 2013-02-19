package pl.android.androidpaint;

import android.graphics.Color;

public enum Colors implements CharSequence {
	RED("Red", Color.RED),
	GREEN("Green", Color.GREEN),
	BLUE("Blue", Color.BLUE),
	CYAN("Cyan", Color.CYAN),
	MAGENTA("Magenta", Color.MAGENTA),
	BLACK("Black", Color.BLACK);

	private String description;
	private int color;

	Colors(String description, int color) {
		this.description = description;
		this.color = color;
	}

	@Override
	public String toString() {
		return description;
	}

	public int getColor() {
		return color;
	}

	@Override
	public char charAt(int index) {
		return description.charAt(index);
	}

	@Override
	public int length() {
		return description.length();
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return description.subSequence(start, end);
	}
}
