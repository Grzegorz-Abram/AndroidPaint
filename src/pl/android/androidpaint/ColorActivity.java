
package pl.android.androidpaint;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class ColorActivity extends Activity {

    public final static String COLOR_RETURN = "pl.android.androidpaint.COLOR_RETURN";
    private Spinner spinner;

    public void doCancel(View view) {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    public void doOk(View view) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("kolor", spinner.getSelectedItem().toString());
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        // Get the message from the intent
        Intent intent = getIntent();
        String message = intent.getStringExtra(PaintActivity.MESSAGE);

        TextView textView = (TextView) findViewById(R.id.textView1);
        textView.setText(message);

        spinner = (Spinner) findViewById(R.id.spinner1);
        // Create an ArrayAdapter using the string array and a default spinner
        // layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.colors_array,
                android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.color, menu);
        return true;
    }
}
