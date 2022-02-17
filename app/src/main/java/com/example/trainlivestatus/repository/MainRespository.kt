package com.example.trainlivestatus.repository


import com.example.trainlivestatus.apihelper.ApiInterface
import com.example.trainlivestatus.model.InterstnModel
import com.example.trainlivestatus.model.LiveStatusModel
import com.example.trainlivestatus.model.NameOrCodeModelItem
import com.example.trainlivestatus.model.RouteStationModel
import com.example.trainlivestatus.trainavaimodel.SeatAvailabilityModel
import com.example.trainlivestatus.trainavaimodel.TopCalModel
import com.example.trainlivestatus.utils.CommonUtil
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call

class MainRespository constructor(val api: ApiInterface) {

    fun GetFindStation(from: String?, to: String?, date: String?): Call<RouteStationModel?>? {

        return api.RouteStationCall(
            from,
            to,
            date,
            CommonUtil.tokan,
            CommonUtil.quota,
            CommonUtil.locale,
            "4c266f54-988a-477d-bd6c-4981c124a80a",
            CommonUtil.appVersion,
            CommonUtil.EMAIL
        )
    }

    fun getallIntermediatestn(trainId: String?): Call<InterstnModel?> {
        return api.getTrainDetails(trainId)
    }

    fun livestatus(num: String?, doj: String?): Call<LiveStatusModel?> {

        return api.trainlivestatus(num, doj)
    }

    fun topcalanderstatus(
        source: String?,
        den: String?,
        doj: String?,
        travelcalss: String?,
        email: String?,
        ptran: Boolean,
        local: String?,
        showclass: Boolean
    ): Call<TopCalModel?>? {

        return api.topcalanderdata(source, den, doj, travelcalss, email, ptran, local, showclass)
    }

    fun avilabletrain(
        trainNo: String?,
        source: String?,
        destination: String?,
        doj: String?,
        travelClasses: String?,
        quota: String,
        email: String?
    ): Call<List<SeatAvailabilityModel?>?> {

        return api.getTrainCalender(trainNo, source, destination, doj, travelClasses, quota, email)
    }

    fun finalcallstation(jsonObject: JsonObject): Call<JsonObject?>? {

        return api.finalstation(jsonObject)
    }

    suspend fun fetchnamecode(id: String): Flow<List<NameOrCodeModelItem>> {
        return flow {
            val comment = api.nameorcode(id, "getTrainByNameOrCode")
            emit(comment)
        }.flowOn(Dispatchers.IO)
    }


}