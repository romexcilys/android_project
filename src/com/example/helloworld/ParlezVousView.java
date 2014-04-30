package com.example.helloworld;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.example.domain.Cercle;
import com.example.domain.Pointeur;

public class ParlezVousView extends SurfaceView {

	Paint paint = new Paint();
	float x;
	float y;
	List<Cercle> cercles;
	float lastime;
	boolean double_click;
	Cercle cercle;

	final float DOUBLE_CLICK = 200;

	public ParlezVousView(Context context, AttributeSet attrs) {
		super(context, attrs);
		cercles = new ArrayList<Cercle>();
		lastime = System.nanoTime();
		double_click = false;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		paint.setColor(Color.RED);
		// paint.setStyle(Style.FILL);
		// Dessiner ici !

		for (Cercle cercle : cercles) {
			canvas.drawCircle(cercle.getX(), cercle.getY(), cercle.getRadius(), paint);
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		x = event.getX();
		y = event.getY();

		Cercle cercle_temp = new Cercle(x, y, 60);
		System.out.println("Nombre de doigt sur ecran : "+event.getPointerCount());

		// Gérer les actions ici !
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		
		case MotionEvent.ACTION_UP:
			// relâchement du doigt
			break;
		case MotionEvent.ACTION_DOWN:

			// Get current time in nano seconds. Buttom press
			float pressTime = System.nanoTime();

			// Double click
			if ((pressTime - lastime) / 1000000 < DOUBLE_CLICK) {
				if (cercles.contains(cercle_temp))
					cercles.remove(cercle_temp);
			} else // Simple click
			{
				if (cercles.contains(cercle_temp)) {
					cercle = cercles.get(cercles.indexOf(cercle_temp));
					cercle.setX(x);
					cercle.setY(y);
				} else {
					cercles.add(cercle_temp);
					cercle = cercle_temp;
				}
			}

			lastime = pressTime;

			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			
			//if(event.getPointerCount() == 2)
			{
				Pointeur pointeur1 = new Pointeur(event.getX(), event.getY());
				Pointeur pointeur2 = new Pointeur(event.getX(event.getActionIndex()), event.getY(event.getActionIndex()));
				
				
				System.out.println("Distance entre les deux doigts : "+ pointeur1.getDistance(pointeur2));
				
				cercle_temp.setRadius(pointeur1.getDistance(pointeur2)/2);
				cercle_temp.setX((pointeur2.getX()+pointeur1.getX())/2);
				cercle_temp.setX((pointeur2.getY()+pointeur1.getY())/2);
				cercles.add(cercle_temp);
			}
			
			break;
		case MotionEvent.ACTION_MOVE:
			// double_click = true;
			cercle.setX(x);
			cercle.setY(y);

			break;
		
		}

		invalidate();

		return true;
	}

}