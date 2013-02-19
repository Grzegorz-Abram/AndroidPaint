package pl.android.androidpaint;

public enum Sizes implements CharSequence {
	VERYSMALL("Very small", 2),
	SMALL("Small", 4),
	MEDIUM("Medium", 10),
	BIG("Big", 16),
	VERYBIG("Very big", 22);

	private String description;
	private int size;

	Sizes(String description, int size) {
		this.description = description;
		this.size = size;
	}

	@Override
	public String toString() {
		return description;
	}

	public int getSize() {
		return size;
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
