package com.example.trainlivestatus.livestatus

import android.annotation.SuppressLint
import android.app.SearchManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trainbooking.util.CommonUtil
import com.example.trainlivestatus.R
import com.example.trainlivestatus.adapter.FindStationAdapter
import com.example.trainlivestatus.apihelper.ApiInterface
import com.example.trainlivestatus.application.TrainPays
import com.example.trainlivestatus.databinding.ActivityFindStationBinding
import com.example.trainlivestatus.livestatus.FindStationActivity.Companion.BASE
import com.example.trainlivestatus.model.FindStationModel
import com.google.gson.JsonArray
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class FindStationActivity : AppCompatActivity() {

    lateinit var binding: ActivityFindStationBinding
    var searchView: SearchView? = null
    var handler = Handler(Looper.getMainLooper())
    var adapter: FindStationAdapter? = null
    var arraylist = ArrayList<FindStationModel>()


    companion object {

        const val BASE = "http://www.hgapis.com/railapi/v1/"
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_find_station)


        setSupportActionBar(binding.appBarLayout)

        if (supportActionBar != null) {

            supportActionBar?.title = null
            binding.progressCircular.visibility = View.VISIBLE
            findstationcall()
        }


    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun findstationcall() {

        if (TrainPays.isNetConnectionAvailable()) {

            val apiInterface: ApiInterface = getClient().create(ApiInterface::class.java)

            val call = apiInterface.FindStations(CommonUtil.app_version, CommonUtil.api_key)

            call?.enqueue(object : Callback<JsonArray?> {

                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(call: Call<JsonArray?>, response: Response<JsonArray?>) {


                    handler.postDelayed({ //Do something after 5000ms
                        binding.progressCircular.visibility = View.GONE
                    }, 2000)


                    if (response.body() != null && response.body()!!.size() > 0) {

                        for (i in 0 until response.body()!!.size()) {
                            val name = response.body()!![i].asJsonArray
                            val Citycode = name[0].toString()
                            val Cityname = name[1].toString()
                            val Citylocale = name[2].toString()
                            val stationModel = FindStationModel(Citycode, Cityname, Citylocale)

                            arraylist.add(stationModel)

                            val mLayoutManager: RecyclerView.LayoutManager =
                                LinearLayoutManager(applicationContext)
                            adapter = FindStationAdapter(this@FindStationActivity, arraylist)
                            binding.rv.layoutManager = mLayoutManager
                            binding.rv.adapter = adapter
                            adapter!!.notifyDataSetChanged()
                            binding.rv.isNestedScrollingEnabled = false
                        }
                    } else {

                        handler.postDelayed({ //Do something after 5000ms
                            binding.progressCircular.visibility = View.GONE
                            Toast.makeText(
                                this@FindStationActivity,
                                R.string.data_availabele,
                                Toast.LENGTH_SHORT
                            ).show()
                        }, 2000)
                    }

                }

                override fun onFailure(call: Call<JsonArray?>, t: Throwable) {

                }
            })

        } else {

            handler.postDelayed({ //Do something after 5000ms
                binding.progressCircular.visibility = View.GONE
                Toast.makeText(this@FindStationActivity, R.string.internet_not, Toast.LENGTH_SHORT)
                    .show()
            }, 2000)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        searchView = menu!!.findItem(R.id.action_search)
            .actionView as SearchView
        searchView!!.setSearchableInfo(
            searchManager
                .getSearchableInfo(componentName)
        )
        searchView!!.maxWidth = Int.MAX_VALUE


        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // filter recycler view when query submitted

                /*adapter.getFilter().filter(query);*/return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                // filter recycler view when text is changed
                if (query.length > 1) {
                    adapter!!.filter.filter(query)
                }
                return false
            }
        })


        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)
    }


}


fun getClient(): Retrofit {

    val client = OkHttpClient().newBuilder()
        .connectTimeout(100, TimeUnit.SECONDS)
        .readTimeout(100, TimeUnit.SECONDS).build();
    return Retrofit.Builder().baseUrl(BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
}


