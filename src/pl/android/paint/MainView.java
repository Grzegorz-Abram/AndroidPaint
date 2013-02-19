package pl.android.paint;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainView extends SurfaceView implements SurfaceHolder.Callback {

	private ArrayList<PointsToDraw> points;
	private ArrayList<LinesToDraw> lines;
	private ArrayList<CirclesToDraw> circles;
	private ArrayList<RectanglesToDraw> rectangles;
	private Paint paint;
	private int color;
	private int size;
	private int shape;
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
		points = new ArrayList<PointsToDraw>();
		lines = new ArrayList<LinesToDraw>();
		circles = new ArrayList<CirclesToDraw>();
		rectangles = new ArrayList<RectanglesToDraw>();
		paint = new Paint();
		color = Color.RED;
		size = 1;
		shape = 1;
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
		switch (this.shape) {
		case 1:
			RectF oval = new RectF(event.getX() - this.size, event.getY() - this.size, event.getX() + this.size, event.getY() + this.size);

			points.add(new PointsToDraw(oval, this.color));

			break;
		case 2:
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				this.lineStartX = event.getX();
				this.lineStartY = event.getY();
			}

			if (event.getAction() == MotionEvent.ACTION_UP) {
				this.lineStopX = event.getX();
				this.lineStopY = event.getY();

				lines.add(new LinesToDraw(new float[] { this.lineStartX, this.lineStartY, this.lineStopX, this.lineStopY }, this.color, this.size));
			}

			break;
		case 3:
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

				circles.add(new CirclesToDraw(this.circleX, this.circleY, this.circleRadius, this.color, this.size));
			}

			break;
		case 4:
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				this.rectLeft = event.getX();
				this.rectTop = event.getY();
			}

			if (event.getAction() == MotionEvent.ACTION_UP) {
				this.rectRight = event.getX();
				this.rectBottom = event.getY();

				rectangles.add(new RectanglesToDraw(this.rectLeft, this.rectTop, this.rectRight, this.rectBottom, this.color, this.size));
			}

			break;
		}
		invalidate();
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		for (RectanglesToDraw rectangle : rectangles) {
			paint.setColor(rectangle.getColor());
			paint.setStrokeWidth(rectangle.getSize() * 2);
			paint.setStyle(Style.STROKE);
			canvas.drawRect(rectangle.getLeft(), rectangle.getTop(), rectangle.getRight(), rectangle.getBottom(), paint);
		}

		for (CirclesToDraw circle : circles) {
			paint.setColor(circle.getColor());
			paint.setStrokeWidth(circle.getSize() * 2);
			paint.setStyle(Style.STROKE);
			canvas.drawCircle(circle.getCircleX(), circle.getCircleY(), circle.getCircleRadius(), paint);
		}

		for (LinesToDraw line : lines) {
			paint.setColor(line.getColor());
			paint.setStrokeWidth(line.getSize() * 2);
			paint.setStyle(Style.FILL);
			canvas.drawLines(line.getPts(), paint);
		}

		for (PointsToDraw point : points) {
			paint.setColor(point.getColor());
			paint.setStyle(Style.FILL);
			canvas.drawOval(point.getFigure(), paint);
		}
	}

	public void setPencilColor(int color) {
		this.color = color;
	}

	public void setPencilSize(int size) {
		this.size = size;
	}

	public void setShape(int shape) {
		this.shape = shape;
	}
}
