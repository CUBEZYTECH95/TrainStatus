package com.example.trainlivestatus.apihelper

import com.example.trainlivestatus.model.InterstnModel
import com.example.trainlivestatus.model.LiveStatusModel
import com.example.trainlivestatus.model.RouteStationModel
import com.example.trainlivestatus.trainavaimodel.SeatAvailabilityModel
import com.example.trainlivestatus.trainavaimodel.TopCalModel
import com.example.trainlivestatus.utils.WsClients
import com.google.gson.JsonArray
import com.google.gson.JsonObject
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


    @GET("api/trains/livestatusall")
    fun trainlivestatus(
        @Query(WsClients.trainno) trainNo: String?,
        @Query(WsClients.doj) doj: String?
    ): Call<LiveStatusModel?>

    @GET("api/trains/{trainno}/monthlyavailability")

    fun getTrainCalender(
        @Path(WsClients.trainno) trainNo: String?,
        @Query(WsClients.source) source: String?,
        @Query(WsClients.destination) destination: String?,
        @Query(WsClients.doj) doj: String?,
        @Query(WsClients.travelclasses) travelClasses: String?,
        @Query(WsClients.quota) quota: String?,
        @Query(WsClients.email) email: String?
    ): Call<List<SeatAvailabilityModel?>?>

    @GET("api/trains/latest")
    fun topcalanderdata(
        @Query(WsClients.source) source: String?,
        @Query(WsClients.destination) destination: String?,
        @Query(WsClients.doj) doj: String?,
        @Query(WsClients.travelClass) travelClass: String?,
        @Query(WsClients.email) email: String?,
        @Query(WsClients.passengerTrains) passengerTrains: Boolean?,
        @Query(WsClients.locale) locale: String?,
        @Query(WsClients.showEcClass) showEcClass: Boolean?
    ): Call<TopCalModel?>?

    @Headers(
        "Authorization: Token dab50e0656db2e9383bc9269d35615a6e93aef15"
    )
    @POST("railapi/getlivestation")
    fun livestation1(
        @Body action: JsonObject?
    ): Call<JsonObject?>?

    @Headers(
        "Authorization: Token dab50e0656db2e9383bc9269d35615a6e93aef15",
        "Content-Type: application/x-www-form-urlencoded",
        "Accept-Path: true",
        "User-Agent: Mozilla/5.0 (Linux; Android 7.0; SM-G930V Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.125 Mobile Safari/537.36"
    )
    @POST("/crisns/AppServAnd")
    fun livestation2(@Body action: JsonObject?): Call<JsonObject?>?


    @Headers("Authorization: Token dab50e0656db2e9383bc9269d35615a6e93aef15")
    @POST("railapi/getlivestation")
    fun finalstation(
        @Body action: JsonObject?
    ): Call<JsonObject?>?
}