package com.example.myapp2.rent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp2.Rent
import com.example.myapp2.RentsResponse
import com.example.myapp2.TableAdapter
import kotlinx.coroutines.launch

class RentListVM: ViewModel() {
    private var _rentsResponse = MutableLiveData<RentsResponse>()
    var rentsResponse: LiveData<RentsResponse> = _rentsResponse
    private val _addRentResult = MutableLiveData<Result<String>>()
    val addRentResult: LiveData<Result<String>> = _addRentResult
    private var currentPage = 1

    private var repository = TableAdapter()

    init {
        loadRents()
    }

    fun getRents(page: Int, query: String? = null, startDate: String? = null, finishDate: String? = null) {
        loadRents(page, query, startDate, finishDate)
    }

    private fun loadRents(page: Int = currentPage, query: String? = null, startDate: String? = null, finishDate: String? = null) {
        viewModelScope.launch {
            _rentsResponse.value = repository.getRents(page, query, startDate, finishDate)
        }
    }

    fun reloadRents() {
        currentPage = 1;
        loadRents()
    }

    fun addRent(rent: Rent) {
        viewModelScope.launch {
            val result = repository.addRent(rent)
            _addRentResult.postValue(result)
            if (result.isSuccess) {
                reloadRents()
            }
        }
    }

    fun editRent(id: Int, rent: Rent) {
        viewModelScope.launch {
            val isEdited = repository.editRent(id,rent)
            if (isEdited) {
                reloadRents()
            }
        }
    }

    fun removeRent(id: Int) {
        viewModelScope.launch {
            val isRemoved = repository.removeRent(id)
            if (isRemoved) {
                reloadRents()
            }
        }
    }
}