package com.example.myapp2.tariff

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp2.TableAdapter
import com.example.myapp2.Tariff
import kotlinx.coroutines.launch

class TariffListVM: ViewModel() {
    private var _tariffs = MutableLiveData<List<Tariff>>()
    var client: LiveData<List<Tariff>> = _tariffs

    private var repository = TableAdapter()

    fun updateList() {
        viewModelScope.launch {
            val tariffs = repository.getTariffs()
            _tariffs.value = tariffs
        }
    }
}