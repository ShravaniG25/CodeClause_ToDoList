package com.example.todolist;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class firstScreen extends AppCompatActivity {

    LottieAnimationView lottie;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_first_screen);

        lottie = findViewById(R.id.lottie);
        name = findViewById(R.id.textView);

        name.animate().translationY(2600).setDuration(1000).setStartDelay(4000);
        lottie.animate().translationY(2600).setDuration(1000).setStartDelay(4000).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                lottie.setAnimation(R.raw.list);
                lottie.playAnimation();
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onAnimationEnd(Animator animation) {
                lottie.pauseAnimation();
                lottie.setVisibility(View.VISIBLE);
                Intent intent = new Intent(firstScreen.this, MainActivity.class);
                startActivity(intent);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
            
        });

    }
}