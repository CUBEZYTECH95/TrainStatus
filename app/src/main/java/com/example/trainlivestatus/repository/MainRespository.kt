package com.example.trainlivestatus.repository


import com.example.trainlivestatus.apihelper.ApiInterface
import com.example.trainlivestatus.model.InterstnModel
import com.example.trainlivestatus.model.RouteStationModel
import retrofit2.Call

class MainRespository constructor(val api: ApiInterface) {

    fun GetFindStation(from: String?, to: String?, date: String?): Call<RouteStationModel?>? {

        return api.RouteStationCall(
            from,
            to,
            date,
            "AE07DF23B773F913C5470EC3454568B641391A532B93A77BED83FB1329DF244F",
            "GN",
            "en",
            "4c266f54-988a-477d-bd6c-4981c124a80a",
            "315",
            "4c266f54-988a-477d-bd6c-4981c124a80a"
        )
    }

    fun getallIntermediatestn(trainId: String?): Call<InterstnModel?> {
        return api.getTrainDetails(trainId)
    }

}