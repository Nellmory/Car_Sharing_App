package com.example.myapp2.violation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp2.TableAdapter
import com.example.myapp2.Violation
import kotlinx.coroutines.launch

class ViolationListVM: ViewModel() {
    private var _violations = MutableLiveData<List<Violation>>()
    var client: LiveData<List<Violation>> = _violations

    private var repository = TableAdapter()

    fun updateList() {
        viewModelScope.launch {
            val violations = repository.getViolations()
            _violations.value = violations
        }
    }
}