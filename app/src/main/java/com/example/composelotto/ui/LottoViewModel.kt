package com.example.composelotto.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LottoViewModel: ViewModel() {
    private val _items = MutableStateFlow(lottoList)
    val items = _items.asStateFlow()

    private val _selectedItems = MutableStateFlow<List<String>>(emptyList())
    val selectedItems = _selectedItems.asStateFlow()

    private val _duplicateEvent = MutableSharedFlow<String>()
    val duplicateEvent = _duplicateEvent.asSharedFlow()

    fun selectedItems(item: String) {
        _selectedItems.value =
            if (!selectedItems.value.contains(item) && selectedItems.value.size < 6) {
                (selectedItems.value + item)
                    .map { it.toInt() }
                    .sorted()
                    .map { it.toString() }
            } else {
                viewModelScope.launch {
                    _duplicateEvent.emit("중복된 숫자 입니다.")
                }
                selectedItems.value
            }

    }

    fun autoGenerate() {
        _selectedItems.value = (selectedItems.value + _items.value.filter {
            selectedItems.value.contains(it).not()
        }.shuffled().take(6 - selectedItems.value.size))
            .map { it.toInt() }
            .sorted()
            .map { it.toString() }
    }

    fun resetItems() {
        _selectedItems.value = emptyList()
    }
}