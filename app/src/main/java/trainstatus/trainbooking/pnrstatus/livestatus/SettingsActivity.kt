package trainstatus.trainbooking.pnrstatus.livestatus

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.thefinestartist.finestwebview.FinestWebView
import trainstatus.trainbooking.pnrstatus.R
import trainstatus.trainbooking.pnrstatus.databinding.ActivitySettingsBinding

class SettingsActivity : BaseClass() {


    lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)

        binding.rvToolbar.setNavigationOnClickListener {

            onBackPressed()
        }

        binding.ivfedd.setOnClickListener {

            sendEmail()
        }

        binding.ivrate.setOnClickListener {

            rate()
        }

        binding.ivshare.setOnClickListener {

            shareapp()
        }

        binding.ivpri.setOnClickListener {

            FinestWebView.Builder(this@SettingsActivity)
                .theme(R.style.FinestWebViewTheme_Fullscreen)
                .showSwipeRefreshLayout(true)
                .webViewBuiltInZoomControls(true)
                .webViewDisplayZoomControls(true)
                .showUrl(false)
                .show("https://kotlinlang.org/")
        }
    }


    fun sendEmail() {

        sendmail()
    }
}