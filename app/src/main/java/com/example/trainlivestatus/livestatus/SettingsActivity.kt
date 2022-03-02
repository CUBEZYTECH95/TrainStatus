package com.example.trainlivestatus.livestatus

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.trainlivestatus.BuildConfig
import com.example.trainlivestatus.R
import com.example.trainlivestatus.databinding.ActivitySettingsBinding
import com.thefinestartist.finestwebview.FinestWebView

class SettingsActivity : AppCompatActivity() {


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

            try {
                val uri = Uri.parse("market://details?id=$packageName")
                val goMarket = Intent(Intent.ACTION_VIEW, uri)
                startActivity(goMarket)
            } catch (e: ActivityNotFoundException) {
                val uri = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                val goMarket = Intent(Intent.ACTION_VIEW, uri)
                startActivity(goMarket)
            }
        }

        binding.ivshare.setOnClickListener {

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
            val share = "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID + "\n\n"}"
            shareIntent.putExtra(Intent.EXTRA_TEXT, share)
            startActivity(Intent.createChooser(shareIntent, "choose one"))
        }

        binding.ivpri.setOnClickListener {

            FinestWebView.Builder(this@SettingsActivity).
                theme(R.style.FinestWebViewTheme_Fullscreen)
                .showSwipeRefreshLayout(true)
                .webViewBuiltInZoomControls(true)
                .webViewDisplayZoomControls(true)
                .showUrl(false)
                .show("https://kotlinlang.org/")
        }
    }


    fun sendEmail() {

        val emailIntent = Intent(
            Intent.ACTION_SENDTO,
            Uri.parse("mailto:" + Uri.encode(resources.getString(R.string.mail_id)))
        )
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback")
        try {
            startActivity(Intent.createChooser(emailIntent, "Send email via..."))
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                applicationContext,
                "There are no email clients installed.", Toast.LENGTH_SHORT
            )
                .show()
        }
    }
}