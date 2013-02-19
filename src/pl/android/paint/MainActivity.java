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
			mainView.setPencilColor(Color.WHITE);
			mainView.setPencilSize(50);
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
		String[] items = { "1", "5", "10" };

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Pencil size");

		alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				switch (item) {
				case 0:
					mainView.setPencilSize(1);
					break;
				case 1:
					mainView.setPencilSize(5);
					break;
				case 2:
					mainView.setPencilSize(10);
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
		alertDialogBuilder.setTitle("Pencil color");

		alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				switch (item) {
				case 0:
					mainView.setPencilColor(Color.RED);
					break;
				case 1:
					mainView.setPencilColor(Color.GREEN);
					break;
				case 2:
					mainView.setPencilColor(Color.BLUE);
					break;
				}
			}
		});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
}
