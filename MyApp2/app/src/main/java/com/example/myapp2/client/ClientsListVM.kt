package com.example.myapp2.client

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp2.Client
import com.example.myapp2.ClientsResponse
import com.example.myapp2.TableAdapter
import kotlinx.coroutines.launch

class ClientsListVM: ViewModel() {
    private var _clientsResponse = MutableLiveData<ClientsResponse>()
    var clientsResponse: LiveData<ClientsResponse> = _clientsResponse
    private var currentPage = 1

    private var repository = TableAdapter()

    init {
        loadClients()
    }

    fun getClients(page: Int) {
        loadClients(page)
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
            val isAdded = repository.addClient(client)
            if (isAdded) {
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