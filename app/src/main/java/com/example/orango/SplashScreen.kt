package com.example.orango

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // This is used to hide the status bar and make
        // the splash screen as a full screen activity.
        @Suppress("DEPRECATION")
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.screen_splash)

        supportActionBar?.hide()

        val topLogo = findViewById<ImageView>(R.id.top_logo)
        val logo = findViewById<ImageView>(R.id.logo)
        val qoute = findViewById<TextView>(R.id.qoute)

        // HERE WE ARE TAKING THE REFERENCE OF OUR IMAGE
        // SO THAT WE CAN PERFORM ANIMATION USING THAT IMAGE
        val topAnim = AnimationUtils.loadAnimation(this, R.anim.up_to_down_animation)
        topLogo.startAnimation(topAnim)
        val fadedAnim = AnimationUtils.loadAnimation(this, R.anim.faded_animation)
        logo.startAnimation(fadedAnim)
        qoute.startAnimation(fadedAnim)

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        @Suppress("DEPRECATION")
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000) // 4000 is the delayed time in milliseconds.


    }
}