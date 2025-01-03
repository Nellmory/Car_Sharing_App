package com.example.myapp2

import retrofit2.Call
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

    @GET("/test")
    fun hello(): Call<TestResponse>

    @GET("brands")
    suspend fun getBrands(): List<Brand>

    @GET("clients")
    //suspend fun getClients(): List<Client>
    suspend fun getClients(@Query("page") page: Int, @Query("per_page") perPage: Int = 10): Response<ClientsResponse>

    @GET("tariffs")
    suspend fun getTariffs(): List<Tariff>

    @GET("cars")
    suspend fun getCars(): List<Car>

    @GET("rents")
    suspend fun getRents(): List<Rent>

    //ДОБАВЛЕНИЕ

    @POST("add_client")
    //suspend fun addClient(@Body client: Client): Response<Client>
    fun addClient(@Body client: Client): Call<Void>

    @POST("add_rent")
    suspend fun addRent(@Body rent: Rent): Response<Rent>

    //УДАЛЕНИЕ

    @DELETE("delete_rent/{id}")
    suspend fun deleteRent(@Path("id") id: Int): Response<Void>

    @DELETE("/delete_client/{id}")
    //suspend fun deleteClient(@Path("id") id: Int): Response<Void>
    fun deleteClient(@Path("id") id: Int): Call<Void>

    //ОБНОВЛЕНИЕ

    @PUT("edit_rent/{id}")
    suspend fun updateRent(@Path("id") id: Int, @Body rent: Rent): Response<Rent>

    @PUT("edit_client/{id}")
    //suspend fun updateClient(@Path("id") id: Int, @Body client: Client): Response<Client>
    fun editClient(@Path("id") id: Int, @Body client: Client): Call<Void>
}