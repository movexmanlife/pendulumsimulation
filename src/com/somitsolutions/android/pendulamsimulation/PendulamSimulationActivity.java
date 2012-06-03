package com.somitsolutions.android.pendulamsimulation;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class PendulamSimulationActivity extends Activity {
	
	//private Paint mPaint;
	private boolean leftToRightMovement = true;
	private boolean rightToLeftMovement = false;
	private boolean atTheMiddlePositionWhileLeftToRight = false;
	private boolean atTheMiddlePositionWhileRightToLeft = false;
	private boolean firstHalf = true;
	private boolean secondHalf = false;
	private volatile double ballX = 0;
	private volatile double ballY = 0;
	
	//private boolean theCenterBeingDrawn = false;
	
	double angleAccel = 0.0;
	double angleVelocity = 0;
	double dt = 0.15;
	
	private boolean flag = false;
	
	boolean flagCondition = false;
	
	private int anchorX;
	private int anchorY;
	public static double initialAngle = Math.PI/4;
	public static double angle = Math.PI/4;
	double angleInThePreviousStep = Math.PI/4;
	
	private static final int length = 150;
	
	private Paint mPaint;
	
	private Paint mRefreshPaint;
	
	//SurfaceHolder surfaceHolder;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        Panel p = new Panel(this);
       
        mPaint = new Paint();
		mPaint.setColor(Color.YELLOW);
		
		mRefreshPaint = new Paint();
		mRefreshPaint.setColor(Color.BLACK);
		
        setContentView(p);
        
    }
    
    class Panel extends SurfaceView implements SurfaceHolder.Callback{
    	
    	
    	public PendulamThread _thread;
    	
    	public Panel(Context context) {
    		super(context);
    		 
    		
    		getHolder().addCallback(this);
    		_thread = new PendulamThread(getHolder(), this);
    	}

    	public void surfaceChanged(SurfaceHolder holder, int format, int width,
    			int height) {
    		// TODO Auto-generated method stub
    		
    	}

    	public void surfaceCreated(SurfaceHolder holder) {
    		// TODO Auto-generated method stub
    		
    		 _thread.setRunning(true);
             _thread.start();
    		
    	}

    	public void surfaceDestroyed(SurfaceHolder holder) {
    		// TODO Auto-generated method stub
    		
    		 _thread.setRunning(false);
    		
    	}
    	
    	@Override
        public void onDraw(Canvas canvas) {
    		
    			canvas.drawColor(Color.BLACK);
	    		anchorX = getWidth()/2;
	    		
	    		anchorY = getHeight()/4;
	    		
	    		//if(!theCenterBeingDrawn){
	    		canvas.drawCircle(anchorX - 3, anchorY - 4, 7, mPaint);
	    			
	    		
	    		
	    		//First Half ... Left To Right
	    		if(leftToRightMovement == true && rightToLeftMovement ==false && atTheMiddlePositionWhileLeftToRight == false && atTheMiddlePositionWhileRightToLeft == false && firstHalf == true && secondHalf == false){
	    		
	    			angleInThePreviousStep = angle;
	        		
	                angle = angle - dt/Math.sqrt(length/9.81);
	                
	                
	        		
	                if(angle >0.01){
		                ballX = anchorX - (int)length*(Math.sin(angle));
		        		
		        		ballY = anchorY + (int)length*(Math.cos(angle));
		                
		        		canvas.drawLine(anchorX, anchorY,(float)ballX,(float)ballY,mPaint);
		                
		                canvas.drawCircle((float)ballX , (float)ballY , 14, mPaint );
	                }
	                
	                else{
	                	atTheMiddlePositionWhileLeftToRight = true;
	                	atTheMiddlePositionWhileRightToLeft = false;
	                	leftToRightMovement = true;
	                	rightToLeftMovement = false;
	                	firstHalf = false;
	                	secondHalf = false;
	                }
		                
	             
	                
	                return;
	                
	    		}
	    		//First Half Left To Right end
	    		
	    		//AtTheMiddle while Left To right
	    		if(atTheMiddlePositionWhileLeftToRight == true && leftToRightMovement == true && rightToLeftMovement == false && atTheMiddlePositionWhileRightToLeft  == false && firstHalf ==false && secondHalf == false){
	    		
	    			angle = 0;
	    			angleInThePreviousStep = 0;
	    			flag = true;
	    			angleAccel = 0;
	    			angleVelocity = (Math.sqrt(2*9.81*length));
	    			ballX = anchorX;
	    			ballY = anchorY + length;
	    			canvas.drawLine(anchorX, anchorY,(float)ballX,(float)ballY,mPaint);
	                
	                canvas.drawCircle((float)ballX ,(float)ballY , 14, mPaint );
	                
	                atTheMiddlePositionWhileLeftToRight = false;
	                leftToRightMovement = true;
	                rightToLeftMovement = false;
	                atTheMiddlePositionWhileRightToLeft = false;
	                firstHalf = false;
	                secondHalf = true;
	                
	               
	                return;
	    		}
	    		//at the middle while left to right end
	    		
	    		//Left to Right second half
	    		if(leftToRightMovement == true && rightToLeftMovement == false && atTheMiddlePositionWhileLeftToRight == false && atTheMiddlePositionWhileRightToLeft ==false && firstHalf == false && secondHalf == true){
	    			
	    			double velocityAtTheBeginning = angleVelocity;//not sure if doing the right thing... forgot mechanics
	    				    			
	    			
	    			angle += dt/(Math.sqrt(length/9.81));
	    			
	    				
	    			if((initialAngle- angle)>0.01){
	    			
	        		ballX = anchorX + (int) (Math.sin(angle) * length); //greater than anchorX
	        		ballY = anchorY + (int) (Math.cos(angle) * length);//less than anchorY
	        			
	        		canvas.drawLine(anchorX, anchorY,(float)ballX,(float)ballY,mPaint);
	                    
	                canvas.drawCircle((float)ballX , (float)ballY , 14, mPaint );
	                
	    			}
	    			else{
	    				atTheMiddlePositionWhileLeftToRight = false;
		                leftToRightMovement = false;
		                rightToLeftMovement = true;
		                atTheMiddlePositionWhileRightToLeft = false;
		                firstHalf = true;
		                secondHalf = false;
		                angle = initialAngle;
	    			}
	                return;
	    		}
	    		
	    		//left to right second half end
	    		
	    		////right to left first half
	    		if(leftToRightMovement == false && rightToLeftMovement ==true && atTheMiddlePositionWhileLeftToRight == false && atTheMiddlePositionWhileRightToLeft == false && firstHalf == true && secondHalf == false){
		    		
	    			angleInThePreviousStep = angle;
	        		
	                angle = angle - dt/Math.sqrt(length/9.81);
	                
	                
	        		
	                if(angle >0.01){
		                ballX = anchorX + (int)length*(Math.sin(angle));
		        		
		        		ballY = anchorY + (int)length*(Math.cos(angle));
		                
		        		canvas.drawLine(anchorX, anchorY,(float)ballX,(float)ballY,mPaint);
		                
		                canvas.drawCircle((float)ballX , (float)ballY , 14, mPaint );
	                }
	                
	                else{
	                	atTheMiddlePositionWhileLeftToRight = false;
	                	atTheMiddlePositionWhileRightToLeft = true;
	                	leftToRightMovement = false;
	                	rightToLeftMovement = true;
	                	firstHalf = false;
	                	secondHalf = false;
	                }
		                
	                return;
	                
	    		}
	    		
	    		//Right to left first half end
	    		    		
	    		
	    		///at the middle while right to left
	    		if(atTheMiddlePositionWhileLeftToRight == false && leftToRightMovement == false && rightToLeftMovement == true && atTheMiddlePositionWhileRightToLeft  == true && firstHalf ==false && secondHalf == false){
		    		
	    			angle = 0;
	    			//angleInThePreviousStep = 0;
	    			//flag = true;
	    			angleAccel = 0;
	    			angleVelocity = (Math.sqrt(2*9.81*length));
	    			ballX = anchorX;
	    			ballY = anchorY + length;
	    			canvas.drawLine(anchorX, anchorY,(float)ballX,(float)ballY,mPaint);
	                
	                canvas.drawCircle((float)ballX ,(float)ballY , 14, mPaint );
	                
	                atTheMiddlePositionWhileLeftToRight = false;
	                leftToRightMovement = false;
	                rightToLeftMovement = true;
	                atTheMiddlePositionWhileRightToLeft = false;
	                firstHalf = false;
	                secondHalf = true;
	                
	               
	                return;
	    		}
	    		//at the middle while right to left end
	    		
	    		
	    		
	    	
	    		///Right to left second half
	    		if(leftToRightMovement == false && rightToLeftMovement == true && atTheMiddlePositionWhileLeftToRight == false && atTheMiddlePositionWhileRightToLeft ==false && firstHalf == false && secondHalf == true){
	    			
	    			double velocityAtTheBeginning = angleVelocity;//not sure if doing the right thing... forgot mechanics
	    				    			
	    			
	    			angle += dt/(Math.sqrt(length/9.81));
	    			
	    				
	    			if((initialAngle - angle)>0.01){
	    			
	        		ballX = anchorX - (int) (Math.sin(angle) * length); //greater than anchorX
	        		ballY = anchorY + (int) (Math.cos(angle) * length);//less than anchorY
	        			
	        		canvas.drawLine(anchorX, anchorY,(float)ballX,(float)ballY,mPaint);
	                    
	                canvas.drawCircle((float)ballX , (float)ballY , 14, mPaint );
	                
	    			}
	    			else{
	    				atTheMiddlePositionWhileLeftToRight = false;
		                leftToRightMovement = true;
		                rightToLeftMovement = false;
		                atTheMiddlePositionWhileRightToLeft = false;
		                firstHalf = true;
		                secondHalf = false;
		                angle = initialAngle;
	    			}
	                return;
	    		}        
	    }
    	
    }//onDraw
    	

    /////
    class PendulamThread extends Thread {
        private SurfaceHolder _surfaceHolder;
        private Panel _panel;
        private boolean _run = false;

        public PendulamThread(SurfaceHolder surfaceHolder, Panel panel) {
            _surfaceHolder = surfaceHolder;
            _panel = panel;
        }

        public void setRunning(boolean run) {
            _run = run;
        }

        public SurfaceHolder getSurfaceHolder() {
            return _surfaceHolder;
        }

        @Override
        public void run() {
            Canvas c;
            while (_run) {
                c = null;
                try {
                    c = _surfaceHolder.lockCanvas(null);
                    synchronized (_surfaceHolder) {
                        _panel.onDraw(c);
                        Thread.sleep(50);
                       //c.drawColor(Color.BLACK);
                       // _panel.postInvalidateDelayed(10);
                    }
                }
                 catch(InterruptedException e){
                        	
                 }
                     
                finally {
                }
                    // do this in a finally so that if an exception is thrown
                    // during the above, we don't leave the Surface in an
                    // inconsistent state
                    if (c != null) {
                        _surfaceHolder.unlockCanvasAndPost(c);
                    	}
                	}
            	}
        	}
    
    }
    
    
    /////

