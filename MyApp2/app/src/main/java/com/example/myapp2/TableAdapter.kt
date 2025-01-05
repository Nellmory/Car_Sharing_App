package com.example.myapp2

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
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

    suspend fun getViolations(): List<Violation> {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getViolations()
            } catch (e: IOException) {
                println("IOException, you might want to handle it: \${e.message}")
                emptyList()
            } catch (e: HttpException) {
                println("HttpException, unexpected response: \${e.message}")
                emptyList()
            }
        }
    }

    suspend fun getRentViolations(): List<RentViolation> {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getRentViolations()
            } catch (e: IOException) {
                println("IOException, you might want to handle it: \${e.message}")
                emptyList()
            } catch (e: HttpException) {
                println("HttpException, unexpected response: \${e.message}")
                emptyList()
            }
        }
    }

    suspend fun getClients(page: Int): ClientsResponse? {
        return try {
            val response: Response<ClientsResponse> = apiService.getClients(page)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("TableAdapter", "Error response: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("TableAdapter", "Exception: ${e.message}")
            e.printStackTrace()
            null
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
        return try {
            val response = apiService.addClient(client)
            if (response.isSuccessful) {
                Log.d("TableAdapter", "Client added successfully: ${response.code()}")
                true
            } else {
                val errorMessage = response.errorBody()?.string()
                Log.e("TableAdapter", "Failed to add client: ${response.code()} $errorMessage")
                false
            }
        } catch (e: Exception) {
            Log.e("TableAdapter", "Exception during add client: ${e.message}")
            false
        }
    }

    suspend fun editClient(id: Int, client: Client): Boolean {
        return try {
            val response = apiService.editClient(id, client)
            if (response.isSuccessful) {
                Log.d("TableAdapter", "Client edited successfully: ${response.code()}")
                true
            } else {
                val errorMessage = response.errorBody()?.string()
                Log.e("TableAdapter", "Failed to edit client: ${response.code()} $errorMessage")
                false
            }
        } catch (e: Exception) {
            Log.e("TableAdapter", "Exception during edit client: ${e.message}")
            false
        }
    }

    suspend fun removeClient(id: Int): Boolean {
        return try {
            val response = apiService.deleteClient(id)
            if (response.isSuccessful) {
                Log.d("TableAdapter", "Client removed successfully: ${response.code()}")
                true
            } else {
                val errorMessage = response.errorBody()?.string()
                Log.e("TableAdapter", "Failed to remove client: ${response.code()} $errorMessage")
                false
            }
        } catch (e: Exception) {
            Log.e("TableAdapter", "Exception during remove client: ${e.message}")
            false
        }
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