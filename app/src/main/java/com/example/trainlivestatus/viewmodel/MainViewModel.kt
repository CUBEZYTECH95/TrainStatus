package com.example.trainlivestatus.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainlivestatus.R
import com.example.trainlivestatus.application.TrainPays
import com.example.trainlivestatus.model.*
import com.example.trainlivestatus.repository.MainRespository
import com.example.trainlivestatus.trainavaimodel.SeatAvailabilityModel
import com.example.trainlivestatus.trainavaimodel.TopCalModel
import com.example.trainlivestatus.trainavaimodel.TrainsItem
import com.google.gson.JsonObject
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val mainRespository: MainRespository) : ViewModel() {

    var topcalmodellist: MutableLiveData<List<TrainBtwnStnsListItem>> =
        MutableLiveData<List<TrainBtwnStnsListItem>>()
    var liveStatusModel = MutableLiveData<LiveStatusModel?>()
    var trainlist = MutableLiveData<InterstnModel>()
    var errorMessage = MutableLiveData<String>()
    var calandermessage = MutableLiveData<String>()
    var no_available_errormessage = MutableLiveData<String>()
    var calanderLoadingProg = MutableLiveData<Boolean>()
    var showLoadingProg = MutableLiveData<Boolean>()
    var trainliveornot = MutableLiveData<Boolean>()
    var trainname = MutableLiveData<String>()
    var TopcalModelList: MutableLiveData<List<TrainsItem>> = MutableLiveData<List<TrainsItem>>()
    var monthlyAvaModel: MutableLiveData<List<SeatAvailabilityModel>> =
        MutableLiveData<List<SeatAvailabilityModel>>()

    var objects: MutableLiveData<Any?> = MutableLiveData<Any?>()

    val postData: MutableLiveData<List<NameOrCodeModelItem>> = MutableLiveData()
    val articles: MutableList<FindStationModel> = mutableListOf()
    val ar= MutableLiveData<List<FindStationModel>>()
    var mon = MutableLiveData<Int>()
    var tue = MutableLiveData<Int>()
    var wed = MutableLiveData<Int>()
    var th = MutableLiveData<Int>()
    var fri = MutableLiveData<Int>()
    var sat = MutableLiveData<Int>()
    var sun = MutableLiveData<Int>()
    var secpnd: String? = null
    var first: String? = null

    var recyclerview_flag = MutableLiveData<Boolean>()

    companion object {

        var TrainName: String? = null
        var TrainNumber: String? = null
        var ScheduleArr: String? = null
        var ExpectedArr: String? = null
        var ExpectedArrColor: String? = null
        var DelayArr: String? = null
        var DelayArrColor: String? = null
        var ScheduleDep: String? = null
        var ExpectedDep: String? = null
        var ExpectedDepColor: String? = null
        var DelayDep: String? = null
        var DelayDepColor: String? = null
        var ExpPF: String? = null

    }


    fun safeBreakingNewsCall(from: String?, to: String?, date: String?) {

        if (TrainPays.isNetConnectionAvailable()) {

            showLoadingProg.value = true

            val call = mainRespository.GetFindStation(from, to, date)

            call!!.enqueue(object : Callback<RouteStationModel?> {

                @SuppressLint("NullSafeMutableLiveData")
                override fun onResponse(
                    call: Call<RouteStationModel?>,
                    response: Response<RouteStationModel?>
                ) {

                    showLoadingProg.value = false

                    if (response.isSuccessful) {

                        val routedetails: RouteStationModel? = response.body()

                        if (routedetails?.trainBtwnStnsList != null && routedetails.trainBtwnStnsList.isNotEmpty()) {

                            topcalmodellist.postValue(routedetails.trainBtwnStnsList as List<TrainBtwnStnsListItem>?)

                        } else {


                            showLoadingProg.value = false
                            no_available_errormessage.value = routedetails?.errorMessage.toString()

                        }

                    } else {

                        when (response.code()) {

                            404 -> errorMessage.setValue("404 not found")
                            500 -> errorMessage.setValue("500 server broken")
                            else -> errorMessage.setValue("unknown error")
                        }
                    }

                }

                override fun onFailure(call: Call<RouteStationModel?>, t: Throwable) {

                    showLoadingProg.value = false

                    errorMessage.value = "Network failure, Please Try Again"

                }
            })

        } else {

            showLoadingProg.value = false
            errorMessage.setValue("Check your Internet Connection")
        }

    }

    fun schedulewithintermediatestn(train: String?) {

        if (TrainPays.isNetConnectionAvailable()) {

            showLoadingProg.value = true

            val call: Call<InterstnModel?> = mainRespository.getallIntermediatestn(train)
            call.enqueue(object : Callback<InterstnModel?> {
                override fun onResponse(
                    call: Call<InterstnModel?>,
                    response: Response<InterstnModel?>
                ) {

                    showLoadingProg.value = false

                    if (response.isSuccessful) {

                        if (response.body() != null && response.body()?.schedule != null) {

                            trainlist.postValue(response.body())

                        } else {

                            showLoadingProg.value = false
                            errorMessage.value = "No trains route Available"
                        }


                    } else {

                        when (response.code()) {

                            404 -> errorMessage.setValue("404 not found")
                            500 -> errorMessage.setValue("500 server broken")
                            else -> errorMessage.setValue("unknown error")
                        }
                    }

                }

                override fun onFailure(call: Call<InterstnModel?>, t: Throwable) {

                    showLoadingProg.value = false
                    errorMessage.value = "Network failure, Please Try Again"
                }
            })

        } else {

            showLoadingProg.value = false
            errorMessage.value = "Check your Internet Connection"
        }
    }

    fun getlivestatus(num: String?, doj: String?) {

        if (TrainPays.isNetConnectionAvailable()) {

            showLoadingProg.value = true
            trainliveornot.value = false


            val call: Call<LiveStatusModel?> = mainRespository.livestatus(num, doj)
            call.enqueue(object : Callback<LiveStatusModel?> {

                override fun onResponse(
                    call: Call<LiveStatusModel?>,
                    response: Response<LiveStatusModel?>
                ) {

                    showLoadingProg.value = false

                    if (response.isSuccessful) {

                        val liveStatusResponse = response.body()

                        if (liveStatusResponse?.trainDataFound != null) {

                            trainname.postValue(liveStatusResponse.trainNo.toString() + ":" + liveStatusResponse.trainName)
                            liveStatusModel.postValue(liveStatusResponse)

                        } else {

                            trainliveornot.postValue(true)

                            Log.e("=========", "onResponse: ${"no train live"}")

                            //no train live
                            /*LiveStatusModel.postValue(null);*/
                        }
                    } else {

                        when (response.code()) {

                            404 -> errorMessage.value = "404 not found"
                            500 -> errorMessage.value = "500 server broken"
                            else -> errorMessage.value = "unknown error"
                        }
                    }

                }

                override fun onFailure(call: Call<LiveStatusModel?>, t: Throwable) {

                    showLoadingProg.value = false
                    errorMessage.value = "Network failure, Please Try Again"
                }

            })

        } else {

            showLoadingProg.value = false
            errorMessage.value = "Check your Internet Connection"

        }
    }

    fun topseatcalander(
        source: String?,
        den: String?,
        doj: String?,
        travelcalss: String?,
        email: String?,
        ptran: Boolean,
        local: String?,
        showclass: Boolean
    ) {
        if (TrainPays.isNetConnectionAvailable()) {

            recyclerview_flag.value = false
            showLoadingProg.value = true

            val call: Call<TopCalModel?>? = mainRespository.topcalanderstatus(
                source,
                den,
                doj,
                travelcalss,
                email,
                ptran,
                local,
                showclass
            )

            call?.enqueue(object : Callback<TopCalModel?> {
                override fun onResponse(
                    call: Call<TopCalModel?>,
                    response: Response<TopCalModel?>
                ) {

                    recyclerview_flag.value = true
                    showLoadingProg.value = false

                    if (response.isSuccessful) {

                        val liveStatusResponse: TopCalModel? = response.body()

                        if (liveStatusResponse?.trains != null && liveStatusResponse.trains.isNotEmpty()) {

                            for (i in liveStatusResponse.trains.indices) {

                                /*if (i == 0) {

                                    trainname.postValue(liveStatusResponse.trains[i]?.trainNo.toString() + "-" + liveStatusResponse.trains[i]?.trainName)
                                }*/

                                if (i == 0) {

                                    if (!liveStatusResponse.trains[i]?.daysOfRun?.mon!!) {

                                        mon.postValue(R.color.colorGray)

                                    } else {

                                        mon.postValue(R.color.colore_d)
                                    }
                                    if (!liveStatusResponse.trains[i]?.daysOfRun?.tue!!) {

                                        tue.postValue(R.color.colorGray)

                                    } else {

                                        tue.postValue(R.color.colore_d)
                                    }
                                    if (!liveStatusResponse.trains[i]?.daysOfRun?.wed!!) {
                                        wed.postValue(R.color.colorGray)
                                    } else {
                                        wed.postValue(R.color.colore_d)
                                    }
                                    if (!liveStatusResponse.trains[i]?.daysOfRun?.thu!!) {
                                        th.postValue(R.color.colorGray)
                                    } else {
                                        th.postValue(R.color.colore_d)
                                    }
                                    if (!liveStatusResponse.trains[i]?.daysOfRun?.fri!!) {
                                        fri.postValue(R.color.colorGray)
                                    } else {
                                        fri.postValue(R.color.colore_d)
                                    }
                                    if (!liveStatusResponse.trains[i]?.daysOfRun?.sat!!) {
                                        sat.postValue(R.color.colorGray)
                                    } else {
                                        sat.postValue(R.color.colore_d)
                                    }
                                    if (!liveStatusResponse.trains[i]?.daysOfRun?.sun!!) {
                                        sun.postValue(R.color.colorGray)
                                    } else {
                                        sun.postValue(R.color.colore_d)
                                    }
                                }
                            }
                            TopcalModelList.postValue(liveStatusResponse.trains as List<TrainsItem>?)

                        } else {

                            /**
                             * No trains available between the inputted stations
                             */

                            recyclerview_flag.value = false
                            showLoadingProg.value = false
                            errorMessage.value = "No trains available between the inputted stations"

                        }

                    } else {

                        recyclerview_flag.value = false

                        when (response.code()) {

                            404 -> errorMessage.setValue("404 not found")
                            500 -> errorMessage.setValue("500 server broken")
                            else -> errorMessage.setValue("unknown error")
                        }
                    }
                }

                override fun onFailure(call: Call<TopCalModel?>, t: Throwable) {

                    recyclerview_flag.value = false
                    showLoadingProg.value = false
                    errorMessage.value = "Network failure, Please Try Again"
                }
            })

        } else {

            recyclerview_flag.value = false
            showLoadingProg.value = false
            errorMessage.value = "Check your Internet Connection"
        }
    }


    fun tarintimecalander(
        trainNo: String?,
        source: String?,
        destination: String?,
        doj: String?,
        travelClasses: String?,
        quota: String,
        email: String?
    ) {

        if (TrainPays.isNetConnectionAvailable()) {

            calanderLoadingProg.value = true

            val call: Call<List<SeatAvailabilityModel?>?> = mainRespository.avilabletrain(
                trainNo,
                source,
                destination,
                doj,
                travelClasses,
                quota,
                email
            )

            call.enqueue(object : Callback<List<SeatAvailabilityModel?>?> {

                override fun onResponse(
                    call: Call<List<SeatAvailabilityModel?>?>,
                    response: Response<List<SeatAvailabilityModel?>?>
                ) {

                    calanderLoadingProg.value = false

                    if (response.isSuccessful) {

                        val liveStatusResponse: List<SeatAvailabilityModel?>? = response.body()

                        if (liveStatusResponse != null && liveStatusResponse.isNotEmpty()) {

                            monthlyAvaModel.postValue(liveStatusResponse as List<SeatAvailabilityModel>)

                        } else {


                        }


                    } else {

                        when (response.code()) {

                            404 -> calandermessage.value = "404 not found"
                            500 -> calandermessage.value = "500 server broken"
                            else -> calandermessage.value = "unknown error"
                        }
                    }

                }

                override fun onFailure(call: Call<List<SeatAvailabilityModel?>?>, t: Throwable) {

                    calanderLoadingProg.value = false

                }

            })

        } else {

            calanderLoadingProg.value = false
            calandermessage.value = "Check your Internet Connection"

        }

    }

    fun laststioncall(NextHours: String?, code: String?, cityname: String?, jsonIn1: String?) {

        if (TrainPays.isNetConnectionAvailable()) {

            showLoadingProg.value = true

            val jsonObject = JsonObject()
            jsonObject.addProperty("NextHours", NextHours)
            jsonObject.addProperty("StationFromCode", code)
            jsonObject.addProperty("fromUrl", "https://enquiry.indianrail.gov.in/crisns/AppServAnd")
            jsonObject.addProperty("FromStation", cityname)
            val obj = JsonObject()
            obj.addProperty("jsonIn", jsonIn1)
            jsonObject.addProperty("responseString", obj.toString())
            jsonObject.addProperty("key_version", "pnr_ios_key_v1")

            val call = mainRespository.finalcallstation(jsonObject)

            call!!.enqueue(object : Callback<JsonObject?> {

                override fun onResponse(call: Call<JsonObject?>, response: Response<JsonObject?>) {

                    if (response.isSuccessful) {

                        val jsonObject = response.body()

                        if (jsonObject != null) {

                            val jsonObject1 = jsonObject.getAsJsonObject("pair")

                            if (jsonObject1 != null) {

                                first =
                                    if (jsonObject1["first"].isJsonNull) null else jsonObject1["first"].asString

                                secpnd =
                                    if (jsonObject1["second"].isJsonNull) null else jsonObject1["second"].asString

                                try {

                                    val jsonObject2: JSONObject

                                    if (secpnd != null && secpnd!!.isNotEmpty()) {

                                        jsonObject2 = JSONObject(secpnd)

                                        val jsonArray =
                                            jsonObject2.getJSONArray("liveStationModels")

                                        for (i in 0 until jsonArray.length()) {

                                            TrainName =
                                                jsonArray.getJSONObject(i).getString("TrainName")
                                            TrainNumber =
                                                jsonArray.getJSONObject(i).getString("TrainNumber")
                                            ScheduleArr =
                                                jsonArray.getJSONObject(i).getString("ScheduleArr")
                                            ExpectedArr =
                                                jsonArray.getJSONObject(i).getString("ExpectedArr")
                                            ExpectedArrColor = jsonArray.getJSONObject(i)
                                                .getString("ExpectedArrColor")
                                            DelayArr =
                                                jsonArray.getJSONObject(i).getString("DelayArr")
                                            DelayArrColor = jsonArray.getJSONObject(i)
                                                .getString("DelayArrColor")
                                            ScheduleDep =
                                                jsonArray.getJSONObject(i).getString("ScheduleDep")
                                            ExpectedDep =
                                                jsonArray.getJSONObject(i).getString("ExpectedDep")
                                            ExpectedDepColor = jsonArray.getJSONObject(i)
                                                .getString("ExpectedDepColor")
                                            DelayDep =
                                                jsonArray.getJSONObject(i).getString("DelayDep")
                                            DelayDepColor = jsonArray.getJSONObject(i)
                                                .getString("DelayDepColor")
                                            ExpPF = jsonArray.getJSONObject(i).getString("ExpPF")

                                            val model = LiveModel(
                                                TrainName,
                                                TrainNumber,
                                                ScheduleArr,
                                                ExpectedArr,
                                                ExpectedArrColor,
                                                DelayArr,
                                                DelayArrColor,
                                                ScheduleDep,
                                                ExpectedDep,
                                                ExpectedDepColor,
                                                DelayDep,
                                                DelayDepColor,
                                                ExpPF
                                            )


                                            objects.value = model

                                        }

                                        /*mes = jsonObject2.getString("TitleMessage")
                                        binding.text.setVisibility(View.VISIBLE)
                                        binding.tvErrro.setVisibility(View.GONE)
                                        binding.text.setText(mes)*/

                                    } else {

                                        /* binding.progressBar.setVisibility(View.GONE)
                                         binding.text.setVisibility(View.GONE)
                                         binding.tvErrro.setVisibility(View.VISIBLE)
                                         binding.tvErrro.setText(first)*/
                                    }
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }


                            } else {


                            }

                        } else {

                        }

                    } else {

                        when (response.code()) {

                            404 -> errorMessage.setValue("404 not found")
                            500 -> errorMessage.setValue("500 server broken")
                            else -> errorMessage.setValue("unknown error")
                        }
                    }

                }

                override fun onFailure(call: Call<JsonObject?>, t: Throwable) {

                }


            })

        } else {

            showLoadingProg.value = false
            errorMessage.setValue("Data not Available")
        }

    }

    fun getPost(name: String) {

        viewModelScope.launch {

            mainRespository.fetchnamecode(name)

                .catch { e ->

                    Log.d("main", "getPost: ${e.message}")
                }
                .collect {

                    postData.value = it
                }

        }
    }

    @OptIn(FlowPreview::class)
    fun getfindstationcall() {

        viewModelScope.launch {

            mainRespository.findstationcall().debounce(600).catch {e -> Log.d("main", "getPost: ${e.message}") }.distinctUntilChanged().collect {

                it?.forEach { it ->

                    val name = it?.asJsonArray
                    val citycode = name?.get(0)?.toString()
                    val cityname = name?.get(1)?.toString()
                    val citylocale = name?.get(2)?.toString()

                    val stationModel = FindStationModel(citycode, cityname, citylocale)

                    articles.add(stationModel)

                    ar.value=articles

                }
            }

           /* mainRespository.findstationcall()

                .catch { e ->

                    Log.d("main", "getPost: ${e.message}")
                }
                .collect {

                    it?.forEach { it ->

                        val name = it?.asJsonArray
                        val citycode = name?.get(0)?.toString()
                        val cityname = name?.get(1)?.toString()
                        val citylocale = name?.get(2)?.toString()

                        val stationModel = FindStationModel(citycode, cityname, citylocale)

                       articles.add(stationModel)

                        ar.value=articles

                    }

                }*/

        }
    }

}