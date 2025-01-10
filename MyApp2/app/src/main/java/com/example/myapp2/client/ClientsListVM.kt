package com.example.myapp2.client

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp2.Client
import com.example.myapp2.ClientsResponse
import com.example.myapp2.TableAdapter
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ClientsListVM: ViewModel() {
    private var _clientsResponse = MutableLiveData<ClientsResponse>()
    var clientsResponse: LiveData<ClientsResponse> = _clientsResponse
    private val _addClientResult = MutableLiveData<Result<String>>()
    val addClientResult: LiveData<Result<String>> = _addClientResult
    private var currentPage = 1

    private var repository = TableAdapter()

    init {
        loadClients()
    }

    fun getClients(page: Int, query: String? = null) {
        loadClients(page,query)
    }

    private fun loadClients(page: Int = currentPage, query: String? = null) {
        viewModelScope.launch {
            _clientsResponse.value = repository.getClients(page,query)
        }
    }

    suspend fun getTotalClientCount(): Int {
        return viewModelScope.async {
            val clientsResponse = repository.getClients(1)
            val totalPages = clientsResponse?.total_pages ?: 0
            var clientCount = 0
            for(i in 1..totalPages){
                val response = repository.getClients(i)
                clientCount += response?.clients?.size ?: 0
            }
            clientCount
        }.await()
    }

    private fun loadClients(page: Int = currentPage) {
        viewModelScope.launch {
            _clientsResponse.value = repository.getClients(page)
        }
    }

    fun reloadClients() {
        currentPage = 1;
        loadClients()
    }

    fun addClient(client: Client) {
        viewModelScope.launch {
            val result = repository.addClient(client)
            _addClientResult.postValue(result)
            if (result.isSuccess) {
                reloadClients()
            }
        }
    }

    fun editClient(id: Int, client: Client) {
        viewModelScope.launch {
            val isEdited = repository.editClient(id,client)
            if (isEdited) {
                reloadClients()
            }
        }
    }

    fun removeClient(id: Int) {
        viewModelScope.launch {
            val isRemoved = repository.removeClient(id)
            if (isRemoved) {
                reloadClients()
            }
        }
    }

}