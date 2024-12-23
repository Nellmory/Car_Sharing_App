package com.example.myapp2.car

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp2.TableAdapter
import com.example.myapp2.Car
import kotlinx.coroutines.launch

class CarListVM: ViewModel() {
    private var _cars = MutableLiveData<List<Car>>()
    var client: LiveData<List<Car>> = _cars

    private var repository = TableAdapter()

    fun updateList() {
        viewModelScope.launch {
            val cars = repository.getCars()
            _cars.value = cars
        }
    }
}