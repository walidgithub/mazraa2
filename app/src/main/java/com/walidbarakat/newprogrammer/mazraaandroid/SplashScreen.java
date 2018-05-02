package com.walidbarakat.newprogrammer.mazraaandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.miguelcatalan.materialsearchview.utils.AnimationUtil;

public class SplashScreen extends AppCompatActivity {
    ImageView img, img2, imgdot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        img = (ImageView) findViewById(R.id.ImgAnimated);
        img2 = (ImageView) findViewById(R.id.ImgAnimated2);
        imgdot = (ImageView) findViewById(R.id.ImgDot);

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
/*
                    anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_animation);
                    img.setAnimation(anim);
                    anim.setRepeatCount(2);
                    anim.setFillAfter(true);
                    img.setVisibility(0);
*/
                    AnimationSet animationSet = new AnimationSet(true);
                    AnimationSet animationSet2 = new AnimationSet(true);
                    AnimationSet animationSet3 = new AnimationSet(true);

                    TranslateAnimation animation = new TranslateAnimation(0, 50, -0, -50);
                    animation.setDuration(2000);
                    animation.setRepeatCount(7);
                    animation.setFillAfter(true);

                    TranslateAnimation animation3 = new TranslateAnimation(0, 50, -0, -50);
                    animation3.setDuration(2000);
                    animation3.setRepeatCount(7);
                    animation3.setFillAfter(true);

                    ScaleAnimation animation2 = new ScaleAnimation(1, 2, 1, 2, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    //animation2.setStartOffset(4000);
                    animation2.setDuration(2000);
                    animation2.setRepeatCount(7);
                    animation2.setFillAfter(true);

                    ScaleAnimation animation4 = new ScaleAnimation(1, 2, 1, 2, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    //animation2.setStartOffset(4000);
                    animation4.setDuration(2000);
                    animation4.setRepeatCount(7);
                    animation4.setFillAfter(true);

                    AlphaAnimation animation5 = new AlphaAnimation(1, 0);
                    //animation2.setStartOffset(4000);
                    animation5.setDuration(2000);
                    animation5.setRepeatCount(7);
                    animation5.setFillAfter(true);

                    AlphaAnimation animation6 = new AlphaAnimation(0, 1);
                    //animation2.setStartOffset(4000);
                    animation6.setDuration(2000);
                    animation6.setRepeatCount(7);
                    animation6.setFillAfter(true);

                    AlphaAnimation animation7 = new AlphaAnimation(1, 0);
                    //animation2.setStartOffset(4000);
                    animation7.setDuration(2000);
                    animation7.setRepeatCount(7);
                    animation7.setFillAfter(true);

                    animationSet2.addAnimation(animation3);
                    animationSet2.addAnimation(animation4);
                    animationSet2.addAnimation(animation5);

                    animationSet.addAnimation(animation);
                    animationSet.addAnimation(animation2);
                    animationSet.addAnimation(animation6);

                    animationSet3.addAnimation(animation7);

                    img.setAnimation(animationSet);
                    img.setVisibility(0);

                    img2.setAnimation(animationSet2);
                    img2.setVisibility(0);

                    imgdot.setAnimation(animationSet3);
                    imgdot.setVisibility(0);


                    sleep(7000);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }
}
