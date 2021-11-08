package com.example.tranzakcioelozmenyek

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.tranzakcioelozmenyek.adapter.TranzactionAdapter
import com.example.tranzakcioelozmenyek.adapter.TranzactionManager


class TranzakciokFragment(context : Context) : Fragment(R.layout.fragment_tranzakciok) {

    private val appContext : Context = context
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = getView()?.findViewById<RecyclerView>(R.id.tranzactionsView)
        val dWBtn = getView()?.findViewById<Button>(R.id.dWBtn)
        val crWindow = getView()?.findViewById<CardView>(R.id.cr_windowCard)
        val crOk = getView()?.findViewById<Button>(R.id.crBtnOk)
        val crBack = getView()?.findViewById<Button>(R.id.crBtnVissza)


        recyclerView?.adapter = TranzactionAdapter(appContext, TranzactionManager.loadTransactions(appContext))

        dWBtn?.setOnClickListener {
            crWindow?.visibility = View.VISIBLE
        }
        crOk?.setOnClickListener {
            crWindow?.visibility = View.INVISIBLE
            TranzactionManager.deleteTransactionHistory(appContext)
            recyclerView?.adapter = TranzactionAdapter(appContext, TranzactionManager.loadTransactions(appContext))
        }

        crBack?.setOnClickListener {
            crWindow?.visibility = View.INVISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        val recyclerView = getView()?.findViewById<RecyclerView>(R.id.tranzactionsView)
        recyclerView?.adapter = TranzactionAdapter(appContext, TranzactionManager.loadTransactions(appContext))
    }
}