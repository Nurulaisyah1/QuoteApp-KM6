package com.quoteapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quoteapp.data.model.Quote
import com.quoteapp.data.repository.QuoteRepository
import com.quoteapp.utils.ResultWrapper
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class MainViewModel(private val repository: QuoteRepository) : ViewModel() {

    private val _quotes: MutableLiveData<ResultWrapper<List<Quote>>> = MutableLiveData()
    val quotes: LiveData<ResultWrapper<List<Quote>>> get() = _quotes

    init {
        fetchRandomQuotes()
    }

    // Fetch random quotes from repository
    private fun fetchRandomQuotes() {
        viewModelScope.launch {
            _quotes.value = ResultWrapper.Loading()
            repository.getRandomQuotes().collect { result ->
                _quotes.value = result // Update the value of _quotes with the Flow result
            }
        }
    }
}
