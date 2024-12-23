package com.example.myapp2

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    //ПОЛУЧЕНИЕ

    @GET("/test")
    fun hello(): Call<TestResponse>

    @GET("brands")
    suspend fun getBrands(): List<Brand>

    @GET("clients")
    suspend fun getClients(): List<Client>

    @GET("tariffs")
    suspend fun getTariffs(): List<Tariff>

    @GET("cars")
    suspend fun getCars(): List<Car>

    @GET("rents")
    suspend fun getRents(): List<Rent>

    //ДОБАВЛЕНИЕ

    @POST("add_client")
    suspend fun addClient(@Body client: Client): Response<Client>

    @POST("add_rent")
    suspend fun addRent(@Body rent: Rent): Response<Rent>

    //УДАЛЕНИЕ

    @DELETE("delete_rent/{id}")
    suspend fun deleteRent(@Path("id") id: Int): Response<Void>

    @DELETE("/delete_client/{id}")
    suspend fun deleteClient(@Path("id") id: Int): Response<Void>

    //ОБНОВЛЕНИЕ

    @PUT("edit_rent/{id}")
    suspend fun updateRent(@Path("id") id: Int, @Body rent: Rent): Response<Rent>

    @PUT("edit_client/{id}")
    suspend fun updateClient(@Path("id") id: Int, @Body client: Client): Response<Client>
}