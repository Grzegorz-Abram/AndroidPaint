package pl.android.androidpaint;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class PaintView extends SurfaceView implements SurfaceHolder.Callback {

    private ArrayList<FiguresToDraw> figures;
    private ArrayList<FiguresToDraw> figuresToFlat;
    private ArrayList<FiguresToDraw> figuresBuffer;
    private Paint paint;
    private Paint paintTemp;
    private int color;
    private int size;
    private Figures figure;
    private float lineStartX;
    private float lineStartY;
    private float lineStopX;
    private float lineStopY;
    private float circleX;
    private float circleY;
    private float circleRadius;
    private float rectLeft;
    private float rectTop;
    private float rectRight;
    private float rectBottom;
    private float circleStartX;
    private float circleStartY;
    private float circleStopX;
    private float circleStopY;
    private Bitmap bitmap;
    private Path path;
    private boolean drawing = false;
    private PaintThread thread;
    private final int historySteps = 10;

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);

        getHolder().addCallback(this);
        thread = new PaintThread(getHolder(), this);
        setFocusable(true);

        figures = new ArrayList<FiguresToDraw>();
        figuresToFlat = new ArrayList<FiguresToDraw>();
        figuresBuffer = new ArrayList<FiguresToDraw>();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintTemp = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    private int detectColor(int x, int y) {
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        this.draw(new Canvas(bitmap));

        // saveImage();

        return bitmap.getPixel(x, y);
    }

    private void drawFigures(ArrayList<FiguresToDraw> arrayOfFigures, Canvas canvas) {
        for (FiguresToDraw figure : arrayOfFigures) {
            paint.setColor(figure.getColor());
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeJoin(Paint.Join.ROUND);

            switch (figure.getFigure()) {
            case RECTANGLE:
                paint.setStrokeWidth(figure.getSize() * 2);
                paint.setStyle(Style.STROKE);
                canvas.drawRect(figure.getLeft(), figure.getTop(), figure.getRight(), figure.getBottom(), paint);
                break;
            case CIRCLE:
                paint.setStrokeWidth(figure.getSize() * 2);
                paint.setStyle(Style.STROKE);
                canvas.drawCircle(figure.getCircleX(), figure.getCircleY(), figure.getCircleRadius(), paint);
                break;
            case LINE:
                paint.setStrokeWidth(figure.getSize() * 2);
                paint.setStyle(Style.FILL);
                canvas.drawLines(figure.getPts(), paint);
                break;
            case POINT:
                paint.setStrokeWidth(figure.getSize() * 2);
                paint.setStyle(Style.STROKE);
                canvas.drawPath(figure.getPath(), paint);
                break;
            }
        }
    }

    private void floodFillScanline(int x, int y, int newColor, int oldColor) {
        if (oldColor == newColor)
            return;
        if (detectColor(x, y) != oldColor)
            return;

        int y1;

        // saveImage();

        // draw current scanline from start position to the top
        y1 = y;
        while (y1 < getHeight() && detectColor(x, y1) == oldColor) {
            bitmap.setPixel(x, y1, newColor);
            y1++;
        }

        // saveImage();

        // draw current scanline from start position to the bottom
        y1 = y - 1;
        while (y1 >= 0 && detectColor(x, y1) == oldColor) {
            bitmap.setPixel(x, y1, newColor);
            y1--;
        }

        // saveImage();

        // test for new scanlines to the left
        y1 = y;
        while (y1 < getHeight() && detectColor(x, y1) == newColor) {
            if (x > 0 && detectColor(x - 1, y1) == oldColor) {
                floodFillScanline(x - 1, y1, newColor, oldColor);
            }
            y1++;
        }
        y1 = y - 1;
        while (y1 >= 0 && detectColor(x, y1) == newColor) {
            if (x > 0 && detectColor(x - 1, y1) == oldColor) {
                floodFillScanline(x - 1, y1, newColor, oldColor);
            }
            y1--;
        }

        // test for new scanlines to the right
        y1 = y;
        while (y1 < getHeight() && detectColor(x, y1) == newColor) {
            if (x < getWidth() - 1 && detectColor(x + 1, y1) == oldColor) {
                floodFillScanline(x + 1, y1, newColor, oldColor);
            }
            y1++;
        }
        y1 = y - 1;
        while (y1 >= 0 && detectColor(x, y1) == newColor) {
            if (x < getWidth() - 1 && detectColor(x + 1, y1) == oldColor) {
                floodFillScanline(x + 1, y1, newColor, oldColor);
            }
            y1--;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, 0, 0, null);
            }

            if (figuresToFlat.size() > 0) {
                drawFigures(figuresToFlat, canvas);
            }

            if (figures.size() > 0) {
                drawFigures(figures, canvas);
            }

            if (drawing) {
                paintTemp.setColor(this.color);
                paintTemp.setStrokeCap(Paint.Cap.ROUND);
                paintTemp.setStrokeJoin(Paint.Join.ROUND);

                switch (this.figure) {
                case RECTANGLE:
                    paintTemp.setStrokeWidth(this.size * 2);
                    paintTemp.setStyle(Style.STROKE);
                    canvas.drawRect(this.rectLeft, this.rectTop, this.rectRight, this.rectBottom, paintTemp);
                    break;
                case CIRCLE:
                    paintTemp.setStrokeWidth(this.size * 2);
                    paintTemp.setStyle(Style.STROKE);
                    canvas.drawCircle(this.circleX, this.circleY, this.circleRadius, paintTemp);
                    break;
                case LINE:
                    paintTemp.setStrokeWidth(this.size * 2);
                    paintTemp.setStyle(Style.FILL);
                    canvas.drawLines(new float[] { this.lineStartX, this.lineStartY, this.lineStopX, this.lineStopY }, paintTemp);
                    break;
                case POINT:
                    paintTemp.setStrokeWidth(this.size * 2);
                    paintTemp.setStyle(Style.STROKE);
                    canvas.drawPath(this.path, paintTemp);
                    break;
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (this.figure) {
        case POINT:
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                path = new Path();
                path.moveTo(event.getX(), event.getY());
                path.lineTo(event.getX() + 1, event.getY() + 1);
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                path.lineTo(event.getX(), event.getY());

                drawing = true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                path.lineTo(event.getX(), event.getY());

                figures.add(new FiguresToDraw(path, this.color, this.size));

                drawing = false;
            }

            break;
        case LINE:
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                this.lineStartX = event.getX();
                this.lineStartY = event.getY();
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                this.lineStopX = event.getX();
                this.lineStopY = event.getY();

                drawing = true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                this.lineStopX = event.getX();
                this.lineStopY = event.getY();

                figures.add(new FiguresToDraw(new float[] { this.lineStartX, this.lineStartY, this.lineStopX, this.lineStopY }, this.color, this.size));

                drawing = false;
            }

            break;
        case CIRCLE:
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                this.circleStartX = event.getX();
                this.circleStartY = event.getY();
                this.circleRadius = 1;
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                this.circleStopX = event.getX();
                this.circleStopY = event.getY();
                this.circleX = this.circleStartX + (this.circleStopX - this.circleStartX) / 2;
                this.circleY = this.circleStartY + (this.circleStopY - this.circleStartY) / 2;
                this.circleRadius = (float) Math.sqrt(Math.pow(this.circleX - event.getX(), 2) + Math.pow(this.circleY - event.getY(), 2));

                drawing = true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                this.circleStopX = event.getX();
                this.circleStopY = event.getY();
                this.circleX = this.circleStartX + (this.circleStopX - this.circleStartX) / 2;
                this.circleY = this.circleStartY + (this.circleStopY - this.circleStartY) / 2;
                this.circleRadius = (float) Math.sqrt(Math.pow(this.circleX - event.getX(), 2) + Math.pow(this.circleY - event.getY(), 2));

                figures.add(new FiguresToDraw(this.circleX, this.circleY, this.circleRadius, this.color, this.size));

                drawing = false;
            }

            break;
        case RECTANGLE:
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                this.rectLeft = event.getX();
                this.rectTop = event.getY();
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                this.rectRight = event.getX();
                this.rectBottom = event.getY();

                drawing = true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                this.rectRight = event.getX();
                this.rectBottom = event.getY();

                figures.add(new FiguresToDraw(this.rectLeft, this.rectTop, this.rectRight, this.rectBottom, this.color, this.size));

                drawing = false;
            }

            break;
        case FILL:
            if (event.getAction() == MotionEvent.ACTION_UP) {
                int oldColor = detectColor((int) event.getX(), (int) event.getY());
                floodFillScanline((int) event.getX(), (int) event.getY(), this.color, oldColor);

                if (oldColor == Color.RED) {
                    Log.d("COLOR", "RED");
                } else {
                    Log.d("COLOR", "OTHER");
                }
            }

            break;
        }

        if (figures.size() > historySteps) {
            figuresToFlat.add(figures.remove(0));

            while (figures.size() > 0) {
                figuresBuffer.add(figures.remove(0));
            }

            Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            draw(new Canvas(bitmap));
            this.bitmap = bitmap;

            while (figuresBuffer.size() > 0) {
                figures.add(figuresBuffer.remove(0));
            }

            figuresToFlat.clear();
        }

        invalidate();

        return true;
    }

    public void open(Bitmap bitmap) {
        this.bitmap = bitmap;
        invalidate();
    }

    private void openImage() {
        String path = Environment.getExternalStorageDirectory() + "/AndroidPaint.jpg";
        Bitmap bitmap = BitmapFactory.decodeFile(path);

        try {
            bitmap = Bitmap.createScaledBitmap(bitmap, getWidth(), getHeight(), true);
        } catch (Exception e) {
        }

        open(bitmap);
    }

    private void saveImage() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        draw(new Canvas(bitmap));

        String path = Environment.getExternalStorageDirectory() + "/AndroidPaint.jpg";
        File file = new File(path);

        try {
            file.createNewFile();
            OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (IOException e) {
        }
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setFigure(Figures figure) {
        this.figure = figure;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    public void undo(int steps) {
        try {
            if (steps == Integer.MAX_VALUE) {
                figures.clear();
                figuresBuffer.clear();
                figuresToFlat.clear();
                this.bitmap = null;
            } else {
                figures.remove(figures.size() - 1);
            }

            invalidate();
        } catch (IndexOutOfBoundsException e) {
        }
    }
}
