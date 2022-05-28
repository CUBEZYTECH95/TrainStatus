package trainstatus.trainbooking.pnrstatus.livestatus

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import trainstatus.trainbooking.pnrstatus.utils.SharedPref
import trainstatus.trainbooking.pnrstatus.utils.SharedPref.Companion.user_id
import com.ncorti.slidetoact.SlideToActView
import trainstatus.trainbooking.pnrstatus.R


@SuppressLint("CustomSplashScreen")
class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_intro)

        val slide = findViewById<SlideToActView>(R.id.li_start)

        slide.animDuration = 100
        slide.onSlideCompleteListener = object : SlideToActView.OnSlideCompleteListener {
            override fun onSlideComplete(view: SlideToActView) {

                SharedPref.screensave(user_id, true)
                startActivity(Intent(this@IntroActivity, CategoryActivity::class.java))
                finish()

            }


        }

        /* slide.onSlideCompleteListener {

             SharedPref.screensave(user_id, true)
             startActivity(Intent(this@IntroActivity, CategoryActivity::class.java))
             finish()

         }*/

    }
}