package com.example.CommonTest.animation;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.example.CommonTest.R;

public class AnimationActivity extends Activity implements TypeEvaluator {
    Point currentPoint;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ImageView myView=findViewById(R.id.image_01);
//       // imageView.setImageResource(R.drawable.animation_list);
//        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(myView, "alpha", 1.0f, 0.5f, 0.8f, 1.0f);
//        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(myView, "scaleX", 0.0f, 1.0f);
//        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(myView, "scaleY", 0.0f, 2.0f);
//        ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(myView, "rotation", 0, 360);
//        ObjectAnimator transXAnim = ObjectAnimator.ofFloat(myView, "translationX", 100, 400);
//        ObjectAnimator transYAnim = ObjectAnimator.ofFloat(myView, "tranlsationY", 100, 750);
//        AnimatorSet set = new AnimatorSet();
//        set.playTogether(alphaAnim, scaleXAnim, scaleYAnim, rotateAnim, transXAnim, transYAnim);
////                set.playSequentially(alphaAnim, scaleXAnim, scaleYAnim, rotateAnim, transXAnim, transYAnim);
//        set.setDuration(3000);
//        set.start();

        //AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
        //animationDrawable.start();
        @SuppressLint("ResourceType") AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this,
                R.anim.property_animator);
        set.setTarget(myView);
        set.start();

    }

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint= (Point) startValue;
        Point endPoint= (Point) endValue;
        float x= startPoint.x+fraction*(endPoint.x-startPoint.x);
        float y= (float)(Math.sin(x*Math.PI/180)*100)+endPoint.y/2;
        PointF point=new PointF(x,y);
        return point;
    }
}
