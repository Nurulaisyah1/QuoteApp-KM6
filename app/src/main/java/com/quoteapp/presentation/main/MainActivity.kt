package com.quoteapp.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.quoteapp.data.datasource.QuoteApiDataSource
import com.quoteapp.data.datasource.QuoteDataSource
import com.quoteapp.data.model.Quote
import com.quoteapp.data.repository.QuoteRepository
import com.quoteapp.data.repository.QuoteRepositoryImpl
import com.quoteapp.data.source.QuoteApiServices
import com.quoteapp.databinding.ActivityMainBinding
import com.quoteapp.presentation.adapter.QuotesAdapter
import com.quoteapp.presentation.main.MainViewModel
import com.quoteapp.utils.GenericViewModelFactory
import com.quoteapp.utils.ResultWrapper
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels {
        val s = QuoteApiServices.invoke()
        val ds: QuoteDataSource = QuoteApiDataSource(s)
        val rp: QuoteRepository = QuoteRepositoryImpl(ds)
        GenericViewModelFactory.create(MainViewModel(rp))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Panggil fungsi observeQuotes() untuk mengamati perubahan pada quotes dari MainViewModel
        observeQuotes()
    }

    private fun observeQuotes() {
        // Amati perubahan pada quotes dari MainViewModel
        viewModel.quotes.observe(this) { result: ResultWrapper<List<Quote>> ->
            when (result) {
                is ResultWrapper.Loading -> {
                    // Menampilkan ProgressBar saat loading
                    binding.progressBar.visibility = View.VISIBLE
                    binding.errorTextView.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                }
                is ResultWrapper.Success -> {
                    // Menampilkan RecyclerView dan menyembunyikan ProgressBar saat berhasil memuat data
                    binding.progressBar.visibility = View.GONE
                    binding.errorTextView.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE

                    // Update RecyclerView dengan data baru
                    val quotesAdapter = QuotesAdapter(result.payload ?: emptyList())
                    binding.recyclerView.adapter = quotesAdapter
                }
                is ResultWrapper.Error -> {
                    // Menampilkan pesan error dan menyembunyikan RecyclerView dan ProgressBar saat terjadi error
                    binding.progressBar.visibility = View.GONE
                    binding.errorTextView.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE

                    // Menampilkan pesan error
                    binding.errorTextView.text = result.message ?: "Failed to fetch data"
                }
                else -> {
                    // Menyembunyikan semua tampilan saat state lainnya
                    binding.progressBar.visibility = View.GONE
                    binding.errorTextView.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                }
            }
        }
    }
}
