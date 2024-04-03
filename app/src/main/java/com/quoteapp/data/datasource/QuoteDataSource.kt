package com.quoteapp.data.datasource

import com.quoteapp.data.source.QuoteApiServices
import com.quoteapp.data.source.network.model.QuoteResponse

interface  QuoteDataSource{
    suspend fun getRandomQuotes(): List<QuoteResponse>
}

class QuoteApiDataSource(private val service : QuoteApiServices) : QuoteDataSource{
    override suspend fun getRandomQuotes(): List<QuoteResponse> {
        return service.getRandomQuotes()
    }
}
