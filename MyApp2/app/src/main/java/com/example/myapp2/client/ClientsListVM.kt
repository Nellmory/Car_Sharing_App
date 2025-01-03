package com.example.myapp2.client

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp2.Client
import com.example.myapp2.ClientsResponse
import com.example.myapp2.TableAdapter
import kotlinx.coroutines.launch

class ClientsListVM: ViewModel() {
    private var _clients = MutableLiveData<List<Client>>()
    var client: LiveData<List<Client>> = _clients

    private var repository = TableAdapter()

    /*fun updateList() {
        viewModelScope.launch {
            val clients = repository.getClients()
            _clients.value = clients
        }
    }

    fun addClient(client: Client) {
        viewModelScope.launch {
            val isAdded = repository.addClient(client)
            if (isAdded) {
                updateList()
            }
        }
    }

    fun editClient(id: Int, client: Client) {
        viewModelScope.launch {
            val isEdited = repository.editClient(id,client)
            if (isEdited) {
                updateList()
            }
        }
    }

    fun removeClient(id: Int) {
        viewModelScope.launch {
            val isRemoved = repository.removeClient(id)
            if (isRemoved) {
                updateList()
            }
        }
    }*/

    fun getClients(page: Int): LiveData<ClientsResponse> {
        val clientsResponse = MutableLiveData<ClientsResponse>()
        viewModelScope.launch {
            val response = repository.getClients(page)
            clientsResponse.value = response
        }
        return clientsResponse
    }

    fun addClient(client: Client) {
        viewModelScope.launch {
            val isAdded = repository.addClient(client)
        }
    }

    fun editClient(id: Int, client: Client) {
        viewModelScope.launch {
            val isEdited = repository.editClient(id,client)
        }
    }

    fun removeClient(id: Int) {
        viewModelScope.launch {
            val isRemoved = repository.removeClient(id)
        }
    }

}