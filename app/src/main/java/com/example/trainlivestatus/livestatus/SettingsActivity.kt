package com.example.trainlivestatus.livestatus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.trainlivestatus.R
import com.example.trainlivestatus.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {


    lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)

        binding.rvToolbar.setNavigationOnClickListener {

            onBackPressed()
        }
    }
}