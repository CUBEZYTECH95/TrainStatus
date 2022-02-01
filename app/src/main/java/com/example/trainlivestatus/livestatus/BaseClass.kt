package com.example.trainlivestatus.livestatus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.trainlivestatus.R
import com.example.trainlivestatus.model.CategoryModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

open class BaseClass : AppCompatActivity() {

    var Image_array = intArrayOf(
        R.drawable.ic_fare_in,
        R.drawable.ic_search_train,
        R.drawable.ic_train_schedule,
        R.drawable.ic_live_train,
        R.drawable.ic_fare_in,
        R.drawable.ic_fare_in
    )
    var list = ArrayList<CategoryModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    // This could be moved into an abstract BaseActivity
    // class for being re-used by several instances
    protected fun setFragment(containerId: FrameLayout, fragment: Fragment?) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(containerId.id, fragment!!)
        fragmentTransaction.commit()
    }

    fun openActivity(context: Context?, aClass: Class<*>?) {
        startActivity(
            Intent(
                context,
                aClass
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )
    }

    fun moveNextActivity(context: Context?, aClass: Class<*>?) {
        startActivity(Intent(context, aClass))
    }

    fun getclient(url: String): Retrofit {

        val client = OkHttpClient().newBuilder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS).build();
        return Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

}