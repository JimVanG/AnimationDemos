package com.jimvang.sunsetanimation;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity
{

    View sceneView;
    View sunView;
    View skyView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sceneView = (ViewGroup) ((ViewGroup)
                findViewById(android.R.id.content)).getChildAt(0);
        sunView = findViewById(R.id.sun);
        skyView = findViewById(R.id.sky);

        // run the animation every time the screen is touched
        sceneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startAnimation();
            }
        });
    }

    void startAnimation()
    {
        float sunYStart = sunView.getTop();
        float sunYEnd = skyView.getHeight();

        ObjectAnimator heightAnimator = ObjectAnimator.
                ofFloat(sunView, "y", sunYStart, sunYEnd).
                setDuration(3000);

        heightAnimator.start();
    }


}
