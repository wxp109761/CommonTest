package com.example.CommonTest.SurfaceViewTest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceViewTest extends SurfaceView implements SurfaceHolder.Callback,Runnable{
   int x=0,y=0,w=40,h=40,ScreenW,ScreenH;
   SurfaceHolder surfaceHolder;
   Thread thread;
   int sleepTime=100;
   boolean flag=false;
   Paint paint;

    public SurfaceViewTest(Context context,int ScreenW,int ScreenH) {
        super(context);
        surfaceHolder=getHolder();
        surfaceHolder.addCallback(this);
        this.ScreenH=ScreenH;
        this.ScreenW=ScreenW;
        paint=new Paint();
        thread=new Thread();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.BLUE);
        paint.setColor(Color.RED);
        canvas.drawRect(x, y, x + w, y + h, paint);
    }

    //逻辑
    public void logic(){
        if(x<=0){
            x=0;
        }
        if(y<=0){
            y=0;
        }
        if(x>=ScreenW-w){
            x=ScreenW-w;
        }
        if(y>=ScreenH-h){
            y=ScreenH-h;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        flag=true;
        if(!thread.isAlive()){
            thread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag=false;
        thread=null;
    }

    @Override
    public void run() {
        Canvas canvas=null;
        while (flag){
            try {


            canvas=surfaceHolder.lockCanvas();
            synchronized (canvas){
                logic();
                draw(canvas);
            }
        }catch (Exception e){
                Log.e("XXX",e.getMessage());
            }finally {
                if(canvas==null){
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            x++;
            y++;
            try {
                Thread.sleep(sleepTime);
            }catch (Exception e){
                Log.e("XXX",e.getMessage());
            }
        }
    }
}
