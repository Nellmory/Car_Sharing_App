package com.example.myapp2

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.await
import retrofit2.awaitResponse
import java.io.IOException

class TableAdapter {
    private val apiService = RetrofitClient.api

    suspend fun getBrands(): List<Brand> {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getBrands()
            } catch (e: IOException) {
                println("IOException, you might want to handle it: \${e.message}")
                emptyList()
            } catch (e: HttpException) {
                println("HttpException, unexpected response: \${e.message}")
                emptyList()
            }
        }
    }

    suspend fun getTariffs(): List<Tariff> {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getTariffs()
            } catch (e: IOException) {
                println("IOException, you might want to handle it: \${e.message}")
                emptyList()
            } catch (e: HttpException) {
                println("HttpException, unexpected response: \${e.message}")
                emptyList()
            }
        }
    }

    suspend fun getClients(): List<Client> {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getClients()
            } catch (e: IOException) {
                println("IOException, you might want to handle it: ${e.message}")
                emptyList()
            } catch (e: HttpException) {
                println("HttpException, unexpected response: ${e.message}")
                emptyList()
            }
        }
    }

    suspend fun getRents(): List<Rent> {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getRents()
            } catch (e: IOException) {
                println("IOException, you might want to handle it: ${e.message}")
                emptyList()
            } catch (e: HttpException) {
                println("HttpException, unexpected response: ${e.message}")
                emptyList()
            }
        }
    }

    suspend fun getCars(): List<Car> {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getCars()
            } catch (e: IOException) {
                println("IOException, you might want to handle it: \${e.message}")
                emptyList()
            } catch (e: HttpException) {
                println("HttpException, unexpected response: \${e.message}")
                emptyList()
            }
        }
    }

    suspend fun addClient(client: Client): Boolean {
        val result = apiService.addClient(client)
        return result.code() in 200..299
    }

    suspend fun editClient(id: Int, client: Client): Boolean {
        val result = apiService.updateClient(id, client)
        return result.code() in 200..299
    }

    suspend fun removeClient(id: Int): Boolean {
        val result = apiService.deleteClient(id)
        return result.code() in 200..299
    }

    suspend fun addRent(rent: Rent): Boolean {
        val result = apiService.addRent(rent)
        return result.code() in 200..299
    }

    suspend fun editRent(id: Int, rent: Rent): Boolean {
        val result = apiService.updateRent(id, rent)
        return result.code() in 200..299
    }

    suspend fun removeRent(id: Int): Boolean {
        val result = apiService.deleteRent(id)
        return result.code() in 200..299
    }
}