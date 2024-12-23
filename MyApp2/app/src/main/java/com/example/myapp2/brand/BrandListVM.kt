package com.example.myapp2.brand

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp2.Brand
import com.example.myapp2.TableAdapter
import kotlinx.coroutines.launch

class BrandListVM: ViewModel() {
    private var _brands = MutableLiveData<List<Brand>>()
    var client: LiveData<List<Brand>> = _brands

    private var repository = TableAdapter()

    fun updateList() {
        viewModelScope.launch {
            val brands = repository.getBrands()
            _brands.value = brands
        }
    }
}