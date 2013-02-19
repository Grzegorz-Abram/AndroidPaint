package pl.android.paint;

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
			mainView.setShape(1);
			mainView.setColor(Color.WHITE);
			mainView.setSize(50);
			break;
		case R.id.menu_pencil:
			mainView.setShape(1);
			menu_pencil_color();
			menu_pencil_size();
			break;
		case R.id.menu_line:
			mainView.setShape(2);
			menu_pencil_color();
			menu_pencil_size();
			break;
		case R.id.menu_circle:
			mainView.setShape(3);
			menu_pencil_color();
			menu_pencil_size();
			break;
		case R.id.menu_rectangle:
			mainView.setShape(4);
			menu_pencil_color();
			menu_pencil_size();
			break;
		}

		return true;
	}

	private void menu_pencil_size() {
		String[] items = { "Small", "Medium", "Big" };

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Size");

		alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				switch (item) {
				case 0:
					mainView.setSize(4);
					break;
				case 1:
					mainView.setSize(10);
					break;
				case 2:
					mainView.setSize(16);
					break;
				}
			}
		});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	private void menu_pencil_color() {
		String[] items = { "Red", "Green", "Blue" };

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Color");

		alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				switch (item) {
				case 0:
					mainView.setColor(Color.RED);
					break;
				case 1:
					mainView.setColor(Color.GREEN);
					break;
				case 2:
					mainView.setColor(Color.BLUE);
					break;
				}
			}
		});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
}
