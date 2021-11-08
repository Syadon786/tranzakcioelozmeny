package com.example.tranzakcioelozmenyek

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.tranzakcioelozmenyek.adapter.TranzactionAdapter
import com.example.tranzakcioelozmenyek.adapter.TranzactionManager


class TranzakciokFragment(context : Context) : Fragment(R.layout.fragment_tranzakciok) {

    private val appContext : Context = context
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = getView()?.findViewById<RecyclerView>(R.id.tranzactionsView)
        recyclerView?.adapter = TranzactionAdapter(appContext, TranzactionManager.loadTransactions(appContext))
    }

    override fun onResume() {
        super.onResume()
        val recyclerView = getView()?.findViewById<RecyclerView>(R.id.tranzactionsView)
        recyclerView?.adapter = TranzactionAdapter(appContext, TranzactionManager.loadTransactions(appContext))
    }
}