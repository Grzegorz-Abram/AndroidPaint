
package pl.android.androidpaint;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class PaintThread extends Thread {

    private SurfaceHolder myThreadSurfaceHolder;
    private PaintView myThreadSurfaceView;
    private boolean myThreadRun = false;

    public PaintThread(SurfaceHolder surfaceHolder, PaintView surfaceView) {
        myThreadSurfaceHolder = surfaceHolder;
        myThreadSurfaceView = surfaceView;
    }

    @Override
    public void run() {
        while (myThreadRun) {
            Canvas c = null;
            try {
                c = myThreadSurfaceHolder.lockCanvas(null);
                synchronized (myThreadSurfaceHolder) {
                    myThreadSurfaceView.draw(c);
                }
            } finally {
                if (c != null) {
                    myThreadSurfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }

    public void setRunning(boolean b) {
        myThreadRun = b;
    }
}
