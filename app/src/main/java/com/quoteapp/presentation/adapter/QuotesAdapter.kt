package com.quoteapp.presentation.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quoteapp.R
import com.quoteapp.data.model.Quote

class QuotesAdapter(private val quotes: List<Quote>) : RecyclerView.Adapter<QuotesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val quoteTextView: TextView = itemView.findViewById(R.id.quoteTextView)
        private val animeTextView: TextView = itemView.findViewById(R.id.animeTextView)
        private val characterTextView: TextView = itemView.findViewById(R.id.characterTextView)

        fun bind(quote: Quote) {
            quoteTextView.text = quote.quote
            animeTextView.text = quote.anime
            characterTextView.text = quote.character
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_quote, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(quotes[position])
    }

    override fun getItemCount(): Int {
        return quotes.size
    }
}
