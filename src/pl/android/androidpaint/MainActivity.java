package pl.android.androidpaint;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private PaintView paintView;
	private Context ctx;
	private Button button_color;
	private Button button_pencil;
	private Button button_line;
	private Button button_circle;
	private Button button_rectangle;
	private Button button_eraser;
	private int lastColor;
	private int lastSize;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		paintView = (PaintView) findViewById(R.id.PaintView);
		button_color = (Button) findViewById(R.id.button_color);
		button_pencil = (Button) findViewById(R.id.button_pencil);
		button_line = (Button) findViewById(R.id.button_line);
		button_circle = (Button) findViewById(R.id.button_circle);
		button_rectangle = (Button) findViewById(R.id.button_rectangle);
		button_eraser = (Button) findViewById(R.id.button_eraser);
		ctx = getApplicationContext();

		lastColor = Colors.RED.getColor();
		lastSize = Sizes.MEDIUM.getSize();
		doPencil(paintView);
		button_color.setTextColor(lastColor);
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

	private void doFocus(Button button) {
		button_pencil.setTextAppearance(ctx, android.R.style.TextAppearance_Small_Inverse);
		button_line.setTextAppearance(ctx, android.R.style.TextAppearance_Small_Inverse);
		button_circle.setTextAppearance(ctx, android.R.style.TextAppearance_Small_Inverse);
		button_rectangle.setTextAppearance(ctx, android.R.style.TextAppearance_Small_Inverse);
		button_eraser.setTextAppearance(ctx, android.R.style.TextAppearance_Small_Inverse);

		button.setTextAppearance(ctx, android.R.style.TextAppearance_Large_Inverse);
	}

	public void doPencil(View view) {
		paintView.setFigure(Figures.POINT);
		paintView.setColor(lastColor);
		paintView.setSize(lastSize);
		button_color.setEnabled(true);

		doFocus(button_pencil);
	}

	public void doLine(View view) {
		paintView.setFigure(Figures.LINE);
		paintView.setColor(lastColor);
		paintView.setSize(lastSize);
		button_color.setEnabled(true);

		doFocus(button_line);
	}

	public void doCircle(View view) {
		paintView.setFigure(Figures.CIRCLE);
		paintView.setColor(lastColor);
		paintView.setSize(lastSize);
		button_color.setEnabled(true);

		doFocus(button_circle);
	}

	public void doRectangle(View view) {
		paintView.setFigure(Figures.RECTANGLE);
		paintView.setColor(lastColor);
		paintView.setSize(lastSize);
		button_color.setEnabled(true);

		doFocus(button_rectangle);
	}

	public void doEraser(View view) {
		paintView.setFigure(Figures.POINT);
		paintView.setColor(Color.WHITE);
		paintView.setSize(Sizes.VERYBIG.getSize());
		button_color.setEnabled(false);

		doFocus(button_eraser);
	}

	public void doUndo(View view) {
		paintView.undo(1);
	}

	public void doClear(View view) {
		paintView.undo(Integer.MAX_VALUE);
	}

	public void doSize(View view) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Size");

		alertDialogBuilder.setItems(Sizes.values(), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				lastSize = Sizes.values()[item].getSize();
				paintView.setSize(lastSize);
			}
		});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	public void doColor(View view) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Color");

		alertDialogBuilder.setItems(Colors.values(), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				lastColor = Colors.values()[item].getColor();
				paintView.setColor(lastColor);
				button_color.setTextColor(lastColor);
			}
		});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	private void about() {

	}
}
