package com.example.trainlivestatus.livestatus

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.trainlivestatus.R
import com.example.trainlivestatus.utils.SharedPref
import com.example.trainlivestatus.utils.SharedPref.Companion.instagram_url
import com.example.trainlivestatus.utils.SharedPref.Companion.twitter_url
import com.example.trainlivestatus.utils.SharedPref.Companion.user_id
import com.ncorti.slidetoact.SlideToActView

@SuppressLint("CustomSplashScreen")
class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_intro)

        val slide = findViewById<SlideToActView>(R.id.li_start)

        slide.onSlideCompleteListener = object : SlideToActView.OnSlideCompleteListener {

            override fun onSlideComplete(view: SlideToActView) {

                startActivity(Intent(this@IntroActivity, CategoryActivity::class.java))
                SharedPref.screensave(user_id,true)

            }

        }

    }
}