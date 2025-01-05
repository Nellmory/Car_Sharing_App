package com.example.myapp2.rentViolation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp2.TableAdapter
import com.example.myapp2.RentViolation
import kotlinx.coroutines.launch

class RentViolationListVM: ViewModel() {
    private var _rentViolations = MutableLiveData<List<RentViolation>>()
    var client: LiveData<List<RentViolation>> = _rentViolations

    private var repository = TableAdapter()

    fun updateList() {
        viewModelScope.launch {
            val rentViolations = repository.getRentViolations()
            _rentViolations.value = rentViolations
        }
    }
}