package com.example.trainlivestatus.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trainlivestatus.application.TrainPays
import com.example.trainlivestatus.model.InterstnModel
import com.example.trainlivestatus.model.RouteStationModel
import com.example.trainlivestatus.model.TrainBtwnStnsListItem
import com.example.trainlivestatus.repository.MainRespository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val mainRespository: MainRespository) : ViewModel() {


    var topcalModelList: MutableLiveData<List<TrainBtwnStnsListItem>> =
        MutableLiveData<List<TrainBtwnStnsListItem>>()

    var trainlist = MutableLiveData<InterstnModel>()

    var errorMessage = MutableLiveData<String>()
    var showLoadingProg = MutableLiveData<Boolean>()


    fun safeBreakingNewsCall(from: String?, to: String?, date: String?) {

        if (TrainPays.isNetConnectionAvailable()) {

            showLoadingProg.value = true

            val call = mainRespository.GetFindStation(from, to, date)
            call!!.enqueue(object : retrofit2.Callback<RouteStationModel?> {

                @SuppressLint("NullSafeMutableLiveData")
                override fun onResponse(
                    call: Call<RouteStationModel?>,
                    response: Response<RouteStationModel?>
                ) {

                    showLoadingProg.value = false

                    if (response.isSuccessful) {

                        val routedetails: RouteStationModel? = response.body()

                        if (routedetails?.trainBtwnStnsList != null && routedetails.trainBtwnStnsList.isNotEmpty()) {


                            topcalModelList.postValue(routedetails.trainBtwnStnsList as List<TrainBtwnStnsListItem>?)


                        } else {

                            showLoadingProg.value = false

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
            errorMessage.setValue("Data not Available")
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

                        if (response.body() != null) {

                            trainlist.postValue(response.body())

                        } else {

                            errorMessage.value="Something went Wrong"
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
                    errorMessage.value = "Something went Wrong"
                }
            })
        } else {

            showLoadingProg.value = false
            errorMessage.value = "Data not Available"
        }
    }

}