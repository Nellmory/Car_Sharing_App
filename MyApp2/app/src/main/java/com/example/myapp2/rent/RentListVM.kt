package com.example.myapp2.rent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp2.Rent
import com.example.myapp2.TableAdapter
import kotlinx.coroutines.launch

class RentListVM: ViewModel() {
    private var _rents = MutableLiveData<List<Rent>>()
    var client: LiveData<List<Rent>> = _rents

    private var repository = TableAdapter()

    fun updateList() {
        viewModelScope.launch {
            val rents = repository.getRents()
            _rents.value = rents
        }
    }

    fun addRent(rent: Rent) {
        viewModelScope.launch {
            val isAdded = repository.addRent(rent)
            if (isAdded) {
                updateList()
            }
        }
    }

    fun editRent(id: Int, rent: Rent) {
        viewModelScope.launch {
            val isEdited = repository.editRent(id,rent)
            if (isEdited) {
                updateList()
            }
        }
    }

    fun removeRent(id: Int) {
        viewModelScope.launch {
            val isRemoved = repository.removeRent(id)
            if (isRemoved) {
                updateList()
            }
        }
    }
}