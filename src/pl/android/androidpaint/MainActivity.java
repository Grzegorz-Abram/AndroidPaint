package pl.android.androidpaint;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private PaintView paintView;
	private Button button_color;
	private Button button_size;
	private Button button_pencil;
	private Button button_line;
	private Button button_circle;
	private Button button_rectangle;
	private Button button_eraser;
	private Button button_fill;
	private int lastColor;
	private int lastSize;

	private void about() {

	}

	public void doCircle(View view) {
		paintView.setFigure(Figures.CIRCLE);
		paintView.setColor(lastColor);
		paintView.setSize(lastSize);
		button_color.setEnabled(true);

		doFocus(button_circle);
	}

	public void doClear(View view) {
		paintView.undo(Integer.MAX_VALUE);
	}

	public void doColor(View view) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Color");

		alertDialogBuilder.setItems(Colors.values(), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				lastColor = Colors.values()[item].getColor();
				paintView.setColor(lastColor);
				button_color.setTextColor(lastColor);
			}
		});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	public void doEraser(View view) {
		paintView.setFigure(Figures.POINT);
		paintView.setColor(Color.WHITE);
		paintView.setSize(lastSize);
		button_color.setEnabled(false);

		doFocus(button_eraser);
	}

	public void doFill(View view) {
		paintView.setFigure(Figures.FILL);
		paintView.setColor(lastColor);
		paintView.setSize(lastSize);
		button_color.setEnabled(true);

		doFocus(button_fill);
	}

	private void doFocus(Button button) {
		button_pencil.setTypeface(null, Typeface.NORMAL);
		button_line.setTypeface(null, Typeface.NORMAL);
		button_circle.setTypeface(null, Typeface.NORMAL);
		button_rectangle.setTypeface(null, Typeface.NORMAL);
		button_eraser.setTypeface(null, Typeface.NORMAL);
		button_fill.setTypeface(null, Typeface.NORMAL);

		button.setTypeface(null, Typeface.BOLD);
	}

	public void doLine(View view) {
		paintView.setFigure(Figures.LINE);
		paintView.setColor(lastColor);
		paintView.setSize(lastSize);
		button_color.setEnabled(true);

		doFocus(button_line);
	}

	public void doOpen(View view) {
		try {
			String path = Environment.getExternalStorageDirectory() + "/AndroidPaint.jpg";
			Bitmap bitmap = BitmapFactory.decodeFile(path);

			if (bitmap == null) {
				throw new Exception("Error opening image " + path);
			}

			bitmap = Bitmap.createScaledBitmap(bitmap, paintView.getWidth(), paintView.getHeight(), true);

			paintView.undo(Integer.MAX_VALUE);
			paintView.open(bitmap);

			showMessage("Successfully opened image " + path);
		} catch (Exception e) {
			showMessage(e.getMessage());
		}
	}

	public void doPencil(View view) {
		paintView.setFigure(Figures.POINT);
		paintView.setColor(lastColor);
		paintView.setSize(lastSize);
		button_color.setEnabled(true);

		doFocus(button_pencil);
	}

	public void doRectangle(View view) {
		paintView.setFigure(Figures.RECTANGLE);
		paintView.setColor(lastColor);
		paintView.setSize(lastSize);
		button_color.setEnabled(true);

		doFocus(button_rectangle);
	}

	public void doSave(View view) {
		Bitmap bitmap = Bitmap.createBitmap(paintView.getWidth(), paintView.getHeight(), Bitmap.Config.ARGB_8888);
		paintView.draw(new Canvas(bitmap));

		String path = Environment.getExternalStorageDirectory() + "/AndroidPaint.jpg";
		File file = new File(path);

		try {
			file.createNewFile();
			OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			showMessage("Image saved to " + path);
		} catch (IOException e) {
			showMessage(e.getMessage());
		}
	}

	public void doSize(View view) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Size");

		alertDialogBuilder.setItems(Sizes.values(), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				lastSize = Sizes.values()[item].getSize();
				paintView.setSize(lastSize);
				button_size.setCompoundDrawablesWithIntrinsicBounds(0, Sizes.values()[item].getIcon(), 0, 0);
			}
		});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	public void doUndo(View view) {
		paintView.undo(1);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		paintView = (PaintView) findViewById(R.id.PaintView);
		button_color = (Button) findViewById(R.id.button_color);
		button_size = (Button) findViewById(R.id.button_size);
		button_pencil = (Button) findViewById(R.id.button_pencil);
		button_line = (Button) findViewById(R.id.button_line);
		button_circle = (Button) findViewById(R.id.button_circle);
		button_rectangle = (Button) findViewById(R.id.button_rectangle);
		button_eraser = (Button) findViewById(R.id.button_eraser);
		button_fill = (Button) findViewById(R.id.button_fill);

		lastColor = Colors.RED.getColor();
		lastSize = Sizes.MEDIUM.getSize();

		button_color.setTextColor(lastColor);
		button_size.setCompoundDrawablesWithIntrinsicBounds(0, Sizes.MEDIUM.getIcon(), 0, 0);

		doPencil(paintView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_about:
			about();
			break;
		}

		return true;
	}

	private void showMessage(String message) {
		AlertDialog ad = new AlertDialog.Builder(this).create();
		ad.setCancelable(false);
		ad.setMessage(message);
		ad.setButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		ad.show();
	}
}
