
package pl.android.androidpaint;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
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

import pl.android.androidpaint.enums.Colors;
import pl.android.androidpaint.enums.Figures;
import pl.android.androidpaint.enums.Sizes;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

public class PaintActivity extends Activity {

    public final static String MESSAGE = "pl.android.androidpaint.MESSAGE";

    private PaintView paintView;
    private Button button_color;
    private Button button_size;
    private Button button_pencil;
    private Button button_line;
    private Button button_circle;
    private Button button_rectangle;
    private Button button_eraser;
    private Button button_fill;
    private Button button_undo;
    private int lastColor;
    private int lastSize;

    private void about() {
        showMessage(":-)");
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
        updateUndoButton();
    }

    public void doColor(View view) {
        boolean old = false;

        if (old) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(getResources().getText(R.string.color));

            StringBuffer[] items = new StringBuffer[Colors.values().length];
            for (int i = 0; i < items.length; i++) {
                items[i] = Colors.values()[i].getDescription(getResources());
            }

            alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    lastColor = Colors.values()[item].getColor();
                    paintView.setColor(lastColor);
                    button_color.setTextColor(lastColor);
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else {
            Intent intent = new Intent(this, ColorActivity.class);
            String message = "Wybierz kolor";
            intent.putExtra(MESSAGE, message);
            startActivity(intent);
        }
    }

    public void doEraser(View view) {
        paintView.setFigure(Figures.POINT);
        paintView.setColor(Color.WHITE);
        paintView.setSize(lastSize);
        button_color.setEnabled(false);

        doFocus(button_eraser);
    }

    public void doFill(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getResources().getText(R.string.fill));

        String[] items = new String[] {
                getResources().getText(R.string.high_tolerance).toString(),
                getResources().getText(R.string.low_tolerance).toString()
        };

        alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    paintView.setTolerance(128);
                    button_fill.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.fill_high, 0,
                            0);
                } else {
                    paintView.setTolerance(0);
                    button_fill.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.fill_low, 0,
                            0);
                }

                paintView.setFigure(Figures.FILL);
                paintView.setColor(lastColor);
                paintView.setSize(lastSize);
                button_color.setEnabled(true);

                doFocus(button_fill);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"
                    + getResources().getText(R.string.screenshot_name)
                    + getResources().getText(R.string.screenshot_extension);
            Bitmap bitmap = BitmapFactory.decodeFile(path);

            if (bitmap == null) {
                throw new Exception(getResources().getText(R.string.error_opening_image) + "\n"
                        + path);
            }

            bitmap = Bitmap.createScaledBitmap(bitmap, paintView.getWidth(), paintView.getHeight(),
                    true);

            paintView.undo(Integer.MAX_VALUE);
            updateUndoButton();

            paintView.open(bitmap);

            showMessage(getResources().getText(R.string.message_opening_image) + "\n" + path);
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
        Bitmap bitmap = Bitmap.createBitmap(paintView.getWidth(), paintView.getHeight(),
                Bitmap.Config.ARGB_8888);
        paintView.draw(new Canvas(bitmap));

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"
                + getResources().getText(R.string.screenshot_name)
                + getResources().getText(R.string.screenshot_extension);
        File file = new File(path);

        try {
            file.createNewFile();
            OutputStream out = new BufferedOutputStream(new FileOutputStream(file));

            if (getResources().getText(R.string.screenshot_extension).toString()
                    .toUpperCase(Locale.US).endsWith("JPG")) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            } else {
                throw new IOException((getResources().getText(R.string.error_saving_image)) + "\n"
                        + path);
            }

            showMessage(getResources().getText(R.string.message_saving_image) + "\n" + path);
        } catch (IOException e) {
            showMessage(e.getMessage() + "\n" + path);
        }
    }

    public void doSize(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getResources().getText(R.string.size));

        StringBuffer[] items = new StringBuffer[Sizes.values().length];
        for (int i = 0; i < items.length; i++) {
            items[i] = Sizes.values()[i].getDescription(getResources());
        }

        alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                lastSize = Sizes.values()[item].getSize();
                paintView.setSize(lastSize);
                button_size.setCompoundDrawablesWithIntrinsicBounds(0,
                        Sizes.values()[item].getIcon(), 0, 0);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void doUndo(View view) {
        paintView.undo(1);
        updateUndoButton();
    }

    private void exit() {
        finish();
        System.exit(0);
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
        button_undo = (Button) findViewById(R.id.button_undo);

        updateUndoButton();
        paintView.setButtonUndo(button_undo);

        paintView.setTolerance(0);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            button_fill.setCompoundDrawablesWithIntrinsicBounds(R.drawable.fill_low, 0, 0, 0);
        } else {
            button_fill.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.fill_low, 0, 0);
        }

        lastColor = Colors.RED.getColor();
        lastSize = Sizes.MEDIUM.getSize();

        button_color.setTextColor(lastColor);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            button_size.setCompoundDrawablesWithIntrinsicBounds(Sizes.MEDIUM.getIcon(), 0, 0, 0);
        } else {
            button_size.setCompoundDrawablesWithIntrinsicBounds(0, Sizes.MEDIUM.getIcon(), 0, 0);
        }

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
            case R.id.menu_exit:
                exit();
                break;
        }

        return true;
    }

    private void showMessage(String message) {
        String versionName = "";

        try {
            PackageInfo pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = pinfo.versionName;
        } catch (NameNotFoundException e) {
        }

        AlertDialog ad = new AlertDialog.Builder(this).create();
        ad.setCancelable(false);
        ad.setMessage(message);
        ad.setIcon(R.drawable.ic_launcher);
        ad.setTitle(getResources().getText(R.string.app_name) + " " + versionName);
        ad.setButton(getResources().getText(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        ad.show();
    }

    private void updateUndoButton() {
        button_undo.setText(getResources().getText(R.string.undo) + " ("
                + paintView.getHistorySteps() + ")");
    }
}
