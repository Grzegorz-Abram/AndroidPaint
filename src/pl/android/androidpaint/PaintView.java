
package pl.android.androidpaint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import pl.android.androidpaint.enums.Figures;

import java.util.ArrayList;

import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

public class PaintView extends View {

    private ArrayList<FiguresToDraw> figures;
    private ArrayList<FiguresToDraw> figuresToFlat;
    private ArrayList<FiguresToDraw> figuresBuffer;
    private Paint paint;
    private Paint paintTemp;
    private int color;
    private int size;
    private Figures figure;
    private float startX;
    private float startY;
    private Bitmap bitmap;
    private Path path;
    private boolean drawing = false;
    private final int historySteps = 10;
    private Button button_undo;
    private int tolerance;

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setFocusable(true);

        figures = new ArrayList<FiguresToDraw>();
        figuresToFlat = new ArrayList<FiguresToDraw>();
        figuresBuffer = new ArrayList<FiguresToDraw>();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintTemp = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    private void drawFigures(ArrayList<FiguresToDraw> arrayOfFigures, Canvas canvas) {
        for (FiguresToDraw figure : arrayOfFigures) {
            paint.setColor(figure.getColor());
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(figure.getSize() * 2);
            paint.setStyle(Style.STROKE);

            canvas.drawPath(figure.getPath(), paint);
        }
    }

    public int getHistorySteps() {
        return figures.size();
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
                paintTemp.setStrokeWidth(this.size * 2);
                paintTemp.setStyle(Style.STROKE);

                canvas.drawPath(this.path, paintTemp);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.startX = event.getX();
                this.startY = event.getY();

                path = new Path();

                if (this.figure == Figures.POINT) {
                    path.moveTo(event.getX(), event.getY());
                    path.lineTo(event.getX() + 1, event.getY() + 1);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (this.figure != Figures.POINT) {
                    path.reset();
                    path.moveTo(this.startX, this.startY);
                }

                switch (this.figure) {
                    case POINT:
                        for (int j = 0; j < event.getHistorySize(); j++) {
                            path.lineTo(event.getHistoricalX(j), event.getHistoricalY(j));
                        }
                        path.lineTo(event.getX(), event.getY());
                        break;
                    case LINE:
                        path.lineTo(event.getX(), event.getY());
                        break;
                    case CIRCLE:
                        path.addCircle(
                                this.startX / 2 + event.getX() / 2,
                                this.startY / 2 + event.getY() / 2,
                                (float) sqrt(pow(this.startX / 2 - event.getX() / 2, 2)
                                        + pow(this.startY / 2 - event.getY() / 2, 2)),
                                Direction.CW);
                        break;
                    case RECTANGLE:
                        path.addRect(this.startX, this.startY, event.getX(), event.getY(),
                                Direction.CW);
                        break;
                }

                drawing = true;
                break;
            case MotionEvent.ACTION_UP:
                if (this.figure == Figures.FILL) {
                    while (figures.size() > 0) {
                        figuresToFlat.add(figures.remove(0));
                    }

                    Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                            Bitmap.Config.ARGB_8888);
                    draw(new Canvas(bitmap));
                    this.bitmap = bitmap;

                    figuresToFlat.clear();

                    FloodFiller ff = new FloodFiller(bitmap, this.color, this.tolerance);
                    ff.floodFill(new Point((int) event.getX(), (int) event.getY()));
                    this.bitmap = ff.getBitmap();
                } else {
                    figures.add(new FiguresToDraw(path, this.color, this.size));
                    drawing = false;
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

        updateUndoButton();

        return true;
    }

    public void open(Bitmap bitmap) {
        this.bitmap = bitmap;
        invalidate();
    }

    public void setButtonUndo(Button button_undo) {
        this.button_undo = button_undo;
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

    public void setTolerance(int tolerance) {
        this.tolerance = tolerance;
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

    private void updateUndoButton() {
        if (button_undo != null) {
            button_undo.setText(getResources().getText(R.string.undo) + " (" + getHistorySteps()
                    + ")");
        }
    }
}
