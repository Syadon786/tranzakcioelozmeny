package com.example.tranzakcioelozmenyek.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tranzakcioelozmenyek.R
import com.example.tranzakcioelozmenyek.model.Tranzaction

class TranzactionAdapter(private val context: Context, private val dataset : List<Tranzaction>) : RecyclerView.Adapter<TranzactionAdapter.TranzactionViewHolder>() {

    class TranzactionViewHolder(private val view : View) : RecyclerView.ViewHolder(view)
    {
        val textView : TextView = view.findViewById(R.id.item_title)
        val listCard : CardView = view.findViewById(R.id.list_card)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TranzactionViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return TranzactionViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: TranzactionViewHolder, position: Int) {
        val tranzaction = dataset[position]
        holder.textView.text = tranzaction.content
        if (tranzaction.isWithdrawing)
        {
            holder.listCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.withdraw))
        }
        else
        {
            holder.listCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.deposit))
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}