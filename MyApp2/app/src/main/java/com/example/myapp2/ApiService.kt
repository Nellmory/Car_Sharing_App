package com.example.myapp2

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //ПОЛУЧЕНИЕ

    @GET("brands")
    suspend fun getBrands(): List<Brand>

    @GET("clients")
    suspend fun getClients(@Query("page") page: Int, @Query("query") query: String?): Response<ClientsResponse>

    @GET("tariffs")
    suspend fun getTariffs(): List<Tariff>

    @GET("cars")
    suspend fun getCars(): List<Car>

    @GET("rents")
    suspend fun getRents(@Query("page") page: Int, @Query("query") query: String?): Response<RentsResponse>

    @GET("violations")
    suspend fun getViolations(): List<Violation>

    @GET("rent_violation")
    suspend fun getRentViolations(): List<RentViolation>

    //ДОБАВЛЕНИЕ

    @POST("add_client")
    suspend fun addClient(@Body client: Client): Response<ResponseBody>

    @POST("add_rent")
    suspend fun addRent(@Body rent: Rent): Response<ResponseBody>

    //УДАЛЕНИЕ

    @DELETE("delete_rent/{id}")
    suspend fun deleteRent(@Path("id") id: Int): Response<ResponseBody>

    @DELETE("/delete_client/{id}")
    suspend fun deleteClient(@Path("id") id: Int): Response<ResponseBody>

    //ОБНОВЛЕНИЕ

    @PUT("edit_rent/{id}")
    suspend fun editRent(@Path("id") id: Int, @Body rent: Rent): Response<ResponseBody>

    @PUT("edit_client/{id}")
    suspend fun editClient(@Path("id") id: Int, @Body client: Client): Response<ResponseBody>
}