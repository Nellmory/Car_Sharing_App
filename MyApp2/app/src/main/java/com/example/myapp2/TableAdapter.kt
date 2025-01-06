package com.example.myapp2

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
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


    suspend fun getRents(page: Int): RentsResponse? {
        return try {
            val response: Response<RentsResponse> = apiService.getRents(page)
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

    suspend fun addClient(client: Client): Result<String> {
        return try {
            val response: Response<ResponseBody> = apiService.addClient(client)
            if (response.isSuccessful) {
                Log.d("TableAdapter", "Client added successfully: ${response.code()}")
                Result.success("Client added successfully")
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = when (response.code()){
                    400 ->  "Bad request: $errorBody"
                    500 -> "Server error: $errorBody"
                    else -> "Unknown error: ${response.code()} $errorBody"
                }

                Log.e("TableAdapter", "Failed to add client: $errorMessage")
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Log.e("TableAdapter", "Exception during add client: ${e.message}")
            Result.failure(e)
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

    suspend fun addRent(rent: Rent): Result<String> {
        return try {
            val response: Response<ResponseBody> = apiService.addRent(rent)
            if (response.isSuccessful) {
                Log.d("TableAdapter", "Rent added successfully: ${response.code()}")
                Result.success("Rent added successfully")
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = when (response.code()){
                    400 ->  "Bad request: $errorBody"
                    500 -> "Server error: $errorBody"
                    else -> "Unknown error: ${response.code()} $errorBody"
                }

                Log.e("TableAdapter", "Failed to add rent: $errorMessage")
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Log.e("TableAdapter", "Exception during add rent: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun editRent(id: Int, rent: Rent): Boolean {
        return try {
            val response = apiService.editRent(id, rent)
            if (response.isSuccessful) {
                Log.d("TableAdapter", "Rent edited successfully: ${response.code()}")
                true
            } else {
                val errorMessage = response.errorBody()?.string()
                Log.e("TableAdapter", "Failed to edit rent: ${response.code()} $errorMessage")
                false
            }
        } catch (e: Exception) {
            Log.e("TableAdapter", "Exception during edit rent: ${e.message}")
            false
        }
    }

    suspend fun removeRent(id: Int): Boolean {
        return try {
            val response = apiService.deleteRent(id)
            if (response.isSuccessful) {
                Log.d("TableAdapter", "Rent removed successfully: ${response.code()}")
                true
            } else {
                val errorMessage = response.errorBody()?.string()
                Log.e("TableAdapter", "Failed to remove rent: ${response.code()} $errorMessage")
                false
            }
        } catch (e: Exception) {
            Log.e("TableAdapter", "Exception during remove rent: ${e.message}")
            false
        }
    }
}