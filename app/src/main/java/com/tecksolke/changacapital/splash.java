package com.tecksolke.changacapital;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class splash extends AppCompatActivity {

    ImageView imageView;
    TextView textView,thope;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView = findViewById(R.id.splash_image);
        textView = findViewById(R.id.tecksolke);
        thope = findViewById(R.id.hope);
        spinner = findViewById(R.id.progressBar);

        //set color to progressbar
        spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#303F9F"), android.graphics.PorterDuff.Mode.MULTIPLY);

        //create animation
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.capitaltransition);
        //start animation with the variables
        imageView.startAnimation(animation);
        textView.startAnimation(animation);
        thope.startAnimation(animation);
        spinner.startAnimation(animation);

        //textview
        //textView.setMovementMethod(LinkMovementMethod.getInstance());
//        textView.setText("http://tecksolke.com/");
//        Linkify.addLinks(textView, Linkify.WEB_URLS);

        //start a new activity after splash screen
        final Intent intent = new Intent(this, ChangaCapital.class);

        //start a thread to give a counter
        Thread timer = new Thread() {
            public void run() {
                try {
                    //give your delay timer here
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        //start the timer
        timer.start();
    }
}
