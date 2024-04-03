package com.quoteapp.data.repository

import com.quoteapp.data.datasource.QuoteDataSource
import com.quoteapp.data.model.Quote
import com.quoteapp.data.model.mapper.toQuotes
import com.quoteapp.utils.ResultWrapper
import com.quoteapp.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface QuoteRepository{
    fun getRandomQuotes(): Flow<ResultWrapper<List<Quote>>>

}

class QuoteRepositoryImpl(private val dataSource  : QuoteDataSource): QuoteRepository{
    override fun getRandomQuotes(): Flow<ResultWrapper<List<Quote>>> {
        return proceedFlow {dataSource.getRandomQuotes().toQuotes()}
    }
}