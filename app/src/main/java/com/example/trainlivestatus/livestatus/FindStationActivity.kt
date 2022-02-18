package com.example.trainlivestatus.livestatus

import android.app.SearchManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainlivestatus.R
import com.example.trainlivestatus.adapter.StationAdapter
import com.example.trainlivestatus.apihelper.ApiInterface
import com.example.trainlivestatus.databinding.ActivityFindStationBinding
import com.example.trainlivestatus.model.FindStationModel
import com.example.trainlivestatus.repository.MainRespository
import com.example.trainlivestatus.utils.CommonUtil
import com.example.trainlivestatus.utils.ModelFactory
import com.example.trainlivestatus.viewmodel.MainViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.JsonArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class FindStationActivity : BaseClass() {

    lateinit var binding: ActivityFindStationBinding
    var searchView: SearchView? = null
    var arraylist = ArrayList<FindStationModel>()
    lateinit var mainViewModel: MainViewModel
    val base = "http://www.hgapis.com/railapi/v1/"
    val apiInterface: ApiInterface by lazy {

        getclient(base).create(ApiInterface::class.java)
    }
    val adapter = StationAdapter(this@FindStationActivity)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_find_station)

        setSupportActionBar(binding.appBarLayout)

        if (supportActionBar != null) {

            supportActionBar?.title = null
            /* binding.progressCircular.visibility = View.VISIBLE*/

        }


        binding.rv.layoutManager = LinearLayoutManager(this@FindStationActivity)

        binding.rv.adapter = adapter

      /*  mainViewModel =
            ViewModelProvider(
                this,
                ModelFactory(MainRespository(apiInterface))
            )[MainViewModel::class.java]


        lifecycleScope.launchWhenCreated {


            mainViewModel.ar.observe(this@FindStationActivity, Observer {

                adapter.setdata(it)
            })
        }

        mainViewModel.getfindstationcall()*/


    }

    /* @RequiresApi(Build.VERSION_CODES.M)
     private fun findstationcall() {

         if (TrainPays.isNetConnectionAvailable()) {

             val apiInterface: ApiInterface = getClient().create(ApiInterface::class.java)

             val call = apiInterface.FindStations(CommonUtil.app_version, CommonUtil.api_key)

             call?.enqueue(object : Callback<JsonArray?> {

                 @SuppressLint("NotifyDataSetChanged")
                 override fun onResponse(call: Call<JsonArray?>, response: Response<JsonArray?>) {

                     handler.postDelayed({ //Do something after 5000ms
                         binding.progressCircular.visibility = View.GONE }, 2000)


                     if (response.body() != null && response.body()!!.size() > 0) {

                         for (i in 0 until response.body()!!.size()) {

                             val name = response.body()!![i].asJsonArray
                             val Citycode = name[0].toString()
                             val Cityname = name[1].toString()
                             val Citylocale = name[2].toString()
                             val stationModel = FindStationModel(Citycode, Cityname, Citylocale)

                             arraylist.add(stationModel)

                             val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
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
                 Toast.makeText(
                     this@FindStationActivity,
                     R.string.please_internet,
                     Toast.LENGTH_SHORT
                 )
                     .show()
             }, 2000)
         }
     }*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        searchView = menu!!.findItem(R.id.action_search).actionView as SearchView
        searchView!!.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView!!.maxWidth = Int.MAX_VALUE

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                // filter recycler view when text is changed
                if (query.isNotEmpty()) {

                    adapter.filter.filter(query)

                } else {


                }
                return false
            }
        })


        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        return if (id == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)
    }

    suspend fun findstationcall(): Flow<List<JsonArray?>?> {
        return flow{
            val comment = apiInterface.FindStations(CommonUtil.app_version, CommonUtil.api_key)
            emit(comment)
        }.flowOn(Dispatchers.IO)
    }

}




