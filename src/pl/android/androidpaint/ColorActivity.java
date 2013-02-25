package pl.android.androidpaint;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class ColorActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_color);
		
		// Get the message from the intent
	    Intent intent = getIntent();
	    String message = intent.getStringExtra(PaintActivity.EXTRA_MESSAGE);

	    TextView textView = (TextView) findViewById(R.id.textView1);
	    textView.setText(message);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.color, menu);
		return true;
	}

}
