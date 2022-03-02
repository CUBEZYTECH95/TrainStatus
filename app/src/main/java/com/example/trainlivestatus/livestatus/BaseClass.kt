package com.example.trainlivestatus.livestatus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.trainlivestatus.R
import com.example.trainlivestatus.model.CategoryModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

open class BaseClass : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

    fun TextInputEditText.getQueryTextChangeStateFlow(): StateFlow<String> {
        val query = MutableStateFlow("")

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // beforeTextChanged
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


                query.value = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                // afterTextChanged
            }
        })

        return query
    }

    suspend fun ProgressBar.setVisible() {
        withContext(Dispatchers.Main) {
            visibility = View.VISIBLE

        }
    }

    suspend fun ProgressBar.setInvisible(scope: (() -> Unit)? = null) {
        withContext(Dispatchers.Main) {

            visibility = View.GONE
            scope?.invoke()
        }
    }



}