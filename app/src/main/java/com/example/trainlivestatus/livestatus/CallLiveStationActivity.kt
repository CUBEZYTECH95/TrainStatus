package com.example.trainlivestatus.livestatus

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainlivestatus.R
import com.example.trainlivestatus.adapter.LiveStationAdapter
import com.example.trainlivestatus.apihelper.ApiInterface
import com.example.trainlivestatus.application.TrainPays
import com.example.trainlivestatus.databinding.ActivityCallLiveStationBinding
import com.example.trainlivestatus.model.LiveModel
import com.example.trainlivestatus.repository.MainRespository
import com.example.trainlivestatus.utils.CommonUtil
import com.example.trainlivestatus.utils.ModelFactory
import com.example.trainlivestatus.viewmodel.MainViewModel
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallLiveStationActivity : BaseClass() {


    lateinit var binding: ActivityCallLiveStationBinding
    var mes: String? = null
    var secpnd: String? = null
    var first: String? = null
    var code: String? = null
    var NextHours: String? = null
    var cityname: String? = null
    var mainViewModel: MainViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_call_live_station)


        code = intent.getStringExtra("citycode")
        cityname = intent.getStringExtra("cityname")
        NextHours = intent.getStringExtra("NextHours")

        livestaioninfo()

    }

    private fun livestaioninfo() {

        if (TrainPays.isNetConnectionAvailable()) {

            val apiInterface: ApiInterface =
                getclient(CommonUtil.API_URL1).create(ApiInterface::class.java)

            val jsonob = JsonObject()
            jsonob.addProperty("NextHours", NextHours)
            jsonob.addProperty("StationFromCode", code)
            jsonob.addProperty("FromStation", cityname)
            jsonob.addProperty("key_version", "pnr_ios_key_v1")

            val call: Call<JsonObject?>? = apiInterface.livestation1(jsonob)
            call?.enqueue(object : Callback<JsonObject?> {

                override fun onResponse(call: Call<JsonObject?>, response: Response<JsonObject?>) {

                    if (response.isSuccessful) {

                        val jsonObject = response.body()

                        if (jsonObject != null) {

                            val jsonObject1 = jsonObject.getAsJsonObject("parameters")
                            val jsonin = jsonObject1["jsonIn"].asString
                            getlivestation1(jsonin)

                        } else {


                        }

                    } else {

                        when (response.code()) {

                            404 -> Toast.makeText(
                                this@CallLiveStationActivity,
                                "404 not found",
                                Toast.LENGTH_SHORT
                            ).show()
                            500 -> Toast.makeText(
                                this@CallLiveStationActivity,
                                "500 server broken",
                                Toast.LENGTH_SHORT
                            ).show()
                            else -> Toast.makeText(
                                this@CallLiveStationActivity,
                                "unknown error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }

                override fun onFailure(call: Call<JsonObject?>, t: Throwable) {

                    Toast.makeText(
                        this@CallLiveStationActivity,
                        "Network failure, Please Try Again",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })


        } else {

            Toast.makeText(
                this@CallLiveStationActivity,
                R.string.No_Train_Available,
                Toast.LENGTH_SHORT
            ).show()
        }


    }

    private fun getlivestation1(jsonin: String?) {

        if (TrainPays.isNetConnectionAvailable()) {

            val apiInterface: ApiInterface =
                getclient(CommonUtil.API_URL2).create(ApiInterface::class.java)

            val jsonob = JsonObject()
            jsonob.addProperty("jsonIn", jsonin)

            val call: Call<JsonObject?>? = apiInterface.livestation2(jsonob)
            call?.enqueue(object : Callback<JsonObject?> {

                override fun onResponse(call: Call<JsonObject?>, response: Response<JsonObject?>) {
                    if (response.isSuccessful) {

                        val jsonObject = response.body()

                        if (jsonObject != null) {

                            val jsonIn1 = jsonObject["jsonIn"].asString
                            getlivestation2(jsonIn1)
                        }
                    }
                }

                override fun onFailure(call: Call<JsonObject?>, t: Throwable) {

                }

            })


        } else {

        }
    }

    private fun getlivestation2(jsonIn1: String?) {

        val apiInterface: ApiInterface = getclient(CommonUtil.API_URL1).create(ApiInterface::class.java)

        mainViewModel = ViewModelProvider(this, ModelFactory(MainRespository(apiInterface)))[MainViewModel::class.java]

        mainViewModel?.laststioncall(NextHours, code, cityname, jsonIn1)

        mainViewModel?.objects?.observe(this, {

            binding.rvLiveStation.layoutManager = LinearLayoutManager(this@CallLiveStationActivity)
            binding.rvLiveStation.adapter = LiveStationAdapter(this@CallLiveStationActivity, listOf(it))

        })


    }


}