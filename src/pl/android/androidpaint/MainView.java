package pl.android.androidpaint;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainView extends SurfaceView implements SurfaceHolder.Callback {

	private ArrayList<FiguresToDraw> figures;
	private Paint paint;
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

	public MainView(Context context, AttributeSet attrs) {
		super(context, attrs);
		figures = new ArrayList<FiguresToDraw>();
		paint = new Paint();

		color = Colors.BLACK.getColor();
		size = Sizes.MEDIUM.getSize();
		figure = Figures.POINT;
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (this.figure) {
		case POINT:
			RectF oval = new RectF(event.getX() - this.size, event.getY() - this.size, event.getX() + this.size, event.getY() + this.size);

			figures.add(new FiguresToDraw(oval, this.color));

			break;
		case LINE:
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				this.lineStartX = event.getX();
				this.lineStartY = event.getY();
			}

			if (event.getAction() == MotionEvent.ACTION_UP) {
				this.lineStopX = event.getX();
				this.lineStopY = event.getY();

				figures.add(new FiguresToDraw(new float[] { this.lineStartX, this.lineStartY, this.lineStopX, this.lineStopY }, this.color, this.size));
			}

			break;
		case CIRCLE:
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				this.circleStartX = event.getX();
				this.circleStartY = event.getY();
			}

			if (event.getAction() == MotionEvent.ACTION_UP) {
				this.circleStopX = event.getX();
				this.circleStopY = event.getY();

				this.circleX = this.circleStartX + (this.circleStopX - this.circleStartX) / 2;
				this.circleY = this.circleStartY + (this.circleStopY - this.circleStartY) / 2;
				this.circleRadius = (float) Math.sqrt(Math.pow(Math.abs(this.circleX - event.getX()), 2) + Math.pow(Math.abs(this.circleY - event.getY()), 2));

				figures.add(new FiguresToDraw(this.circleX, this.circleY, this.circleRadius, this.color, this.size));
			}

			break;
		case RECTANGLE:
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				this.rectLeft = event.getX();
				this.rectTop = event.getY();
			}

			if (event.getAction() == MotionEvent.ACTION_UP) {
				this.rectRight = event.getX();
				this.rectBottom = event.getY();

				figures.add(new FiguresToDraw(this.rectLeft, this.rectTop, this.rectRight, this.rectBottom, this.color, this.size));
			}

			break;
		}
		invalidate();
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		for (FiguresToDraw figure : figures) {
			paint.setColor(figure.getColor());

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
				paint.setStyle(Style.FILL);
				canvas.drawOval(figure.getBounds(), paint);
				break;
			}
		}
	}

	public void undo(int steps) {
		try {
			if (steps == Integer.MAX_VALUE) {
				figures.clear();
			} else {
				figures.remove(figures.size() - 1);
			}

			invalidate();
		} catch (IndexOutOfBoundsException e) {
		}
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setFigure(Figures figure) {
		this.figure = figure;
	}
}
