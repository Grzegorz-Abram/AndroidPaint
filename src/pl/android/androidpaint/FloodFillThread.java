package pl.android.androidpaint;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;

public class FloodFillThread extends Thread {
    ProgressDialog mProgressDialog;
    Bitmap mBitmap;
    int mNewColor;
    Point mPoint;
    Runnable mCallback;

    public FloodFillThread(ProgressDialog pd, Runnable callback, Bitmap bitmap, Point pt, int newColor) {
        mBitmap = bitmap;
        mPoint = pt;
        mNewColor = newColor;
        mProgressDialog = pd;
        mCallback = callback;
    }

    @Override
    public void run() {
        FloodFiller ff = new FloodFiller(mBitmap, mNewColor, 0);
        ff.floodFill(new Point(mPoint.x, mPoint.y));

        handler.sendEmptyMessage(0);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mProgressDialog.dismiss();
            mCallback.run();
        }
    };
}