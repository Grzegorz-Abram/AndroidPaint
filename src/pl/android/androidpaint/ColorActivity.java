
package pl.android.androidpaint;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

        Intent intent = getIntent();
        int lastColor = intent.getIntExtra(PaintActivity.LAST_COLOR, -1);

        spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.colors_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        switch (lastColor) {
            case Color.RED:
                spinner.setSelection(0);
                break;
            case Color.GREEN:
                spinner.setSelection(1);
                break;
            case Color.BLUE:
                spinner.setSelection(2);
                break;
            case Color.CYAN:
                spinner.setSelection(3);
                break;
            case Color.MAGENTA:
                spinner.setSelection(4);
                break;
            case Color.YELLOW:
                spinner.setSelection(5);
                break;
            case Color.BLACK:
                spinner.setSelection(6);
                break;
            default:
                spinner.setSelection(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
