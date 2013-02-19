package pl.android.androidpaint;

import pl.android.androidpaint.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	MainView mainView;
	Context ctx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mainView = (MainView) findViewById(R.id.mainView);
		ctx = getApplicationContext();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_eraser_tool:
			mainView.setShape(Figures.POINT);
			mainView.setColor(Color.WHITE);
			mainView.setSize(50);
			break;
		case R.id.menu_pencil:
			mainView.setShape(Figures.POINT);
			menu_pencil_color();
			menu_pencil_size();
			break;
		case R.id.menu_line:
			mainView.setShape(Figures.LINE);
			menu_pencil_color();
			menu_pencil_size();
			break;
		case R.id.menu_circle:
			mainView.setShape(Figures.CIRCLE);
			menu_pencil_color();
			menu_pencil_size();
			break;
		case R.id.menu_rectangle:
			mainView.setShape(Figures.RECTANGLE);
			menu_pencil_color();
			menu_pencil_size();
			break;
		}

		return true;
	}

	private void menu_pencil_size() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Size");

		alertDialogBuilder.setItems(Sizes.values(), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				mainView.setSize(Sizes.values()[item].getSize());
			}
		});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	private void menu_pencil_color() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Color");

		alertDialogBuilder.setItems(Colors.values(), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				mainView.setColor(Colors.values()[item].getColor());
			}
		});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
}
