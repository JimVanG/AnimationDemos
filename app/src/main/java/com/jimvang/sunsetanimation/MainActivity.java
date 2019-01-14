package com.jimvang.sunsetanimation;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

public class MainActivity extends AppCompatActivity
{

    View sceneView;
    View sunView;
    View skyView;
    View seaView;

    int blueSkyColor;
    int sunsetSkyColor;
    int nightSkyColor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources resources = getResources();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            blueSkyColor = resources.getColor(R.color.sky_blue, getTheme());
            sunsetSkyColor = resources.getColor(R.color.sky_sunset, getTheme());
            nightSkyColor = resources.getColor(R.color.sky_night, getTheme());
        }
        else
        {
            blueSkyColor = resources.getColor(R.color.sky_blue);
            sunsetSkyColor = resources.getColor(R.color.sky_sunset);
            nightSkyColor = resources.getColor(R.color.sky_night);
        }

        sceneView = (ViewGroup) ((ViewGroup)
                findViewById(android.R.id.content)).getChildAt(0);
        sunView = findViewById(R.id.sun);
        skyView = findViewById(R.id.sky);
        seaView = findViewById(R.id.sea);

        // run the animation every time the screen is touched
        sceneView.setOnClickListener(new View.OnClickListener()
        {
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
        // we might want to have the sun stop half through itself so it looks like the sunsetting.
        float sunYEnd = seaView.getTop() - (sunView.getHeight() / 2);

        // Takes care of the interpolation of the object being animated
        ObjectAnimator heightAnimator = ObjectAnimator.
                ofFloat(sunView, "y", sunYStart, sunYEnd).
                setDuration(3000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());

        // Takes care of the color change in the sky during the process
        ObjectAnimator colorAnimator = ObjectAnimator.
                ofInt(skyView, "backgroundColor", blueSkyColor, sunsetSkyColor).
                setDuration(3000);
        colorAnimator.setEvaluator(new ArgbEvaluator());

        // Turns the skyView into a night sky at the end of the horizon animation.
        ObjectAnimator nightSkyAnimator = ObjectAnimator.
                ofInt(skyView, "backgroundColor", sunsetSkyColor, nightSkyColor).
                setDuration(1500);
        nightSkyAnimator.setEvaluator(new ArgbEvaluator());

        // Unused since we are using the AnimatorSet to sync animations.
        //
        //        heightAnimator.start();
        //        colorAnimator.start();


        // We need an AnimatorSet to play our animations in sync together
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet
                .play(heightAnimator)
                .with(colorAnimator)
                .before(nightSkyAnimator);
        animatorSet.start();
    }
}
