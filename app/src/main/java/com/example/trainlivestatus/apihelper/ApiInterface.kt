package com.example.trainlivestatus.apihelper

import com.example.trainlivestatus.model.InterstnModel
import com.example.trainlivestatus.model.RouteStationModel
import com.example.trainlivestatus.utils.WsClients
import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @FormUrlEncoded
    @POST("station_list?")
    fun FindStations(
        @Field(WsClients.app_version) app_version: String?,
        @Field(WsClients.api_key) api_key: String?
    ): Call<JsonArray?>?


    @GET("/api/platform/trainbooking/tatwnstns")
    fun RouteStationCall(
        @Query(WsClients.fromStnCode) fromStnCode: String?,
        @Query(WsClients.destStnCode) destination: String?,
        @Query(WsClients.doj) doj: String?,
        @Query(WsClients.token) token: String?,
        @Query(WsClients.quota) quota: String?,
        @Query(WsClients.locale) locale: String?,
        @Query(WsClients.androidid) androidid: String?,
        @Query(WsClients.appVersion) appVersion: String?,
        @Query(WsClients.email) email: String?
    ): Call<RouteStationModel?>?


    @GET("api/trains/schedulewithintermediatestn")
    fun getTrainDetails(@Query(WsClients.trainNo) trainNo: String?): Call<InterstnModel?>

}