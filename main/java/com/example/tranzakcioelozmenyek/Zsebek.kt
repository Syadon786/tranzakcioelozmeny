package com.example.tranzakcioelozmenyek

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class Zsebek(context: Context) : Fragment(R.layout.fragment_zsebek) {

    val appContext = context
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val szAddBtn = getView()?.findViewById<Button>(R.id.addBtnSzallas)
        val szRmBtn = getView()?.findViewById<Button>(R.id.rmBtnSzallas)
        val szallasBalance = getView()?.findViewById<TextView>(R.id.szallasegyenleg)

        val vAddBtn = getView()?.findViewById<Button>(R.id.addBtnVendeg)
        val vRmBtn = getView()?.findViewById<Button>(R.id.rmBtnVendeg)
        val vendegBalance = getView()?.findViewById<TextView>(R.id.vendeglatasegyenleg)

        val szbAddBtn = getView()?.findViewById<Button>(R.id.addBtnSzabad)
        val szbRmBtn = getView()?.findViewById<Button>(R.id.rmBtnSzabad)
        val szabadidoBalance = getView()?.findViewById<TextView>(R.id.szabadidoegyenleg)
        
        szAddBtn?.setOnClickListener {
            changeBalance(szallasBalance, false)
        }
        szRmBtn?.setOnClickListener {
            changeBalance(szallasBalance, true)
        }

        vAddBtn?.setOnClickListener {
            changeBalance(vendegBalance, false)
        }
        vRmBtn?.setOnClickListener {
            changeBalance(vendegBalance, true)
        }

        szbAddBtn?.setOnClickListener {
            changeBalance(szabadidoBalance, false)
        }
        szbRmBtn?.setOnClickListener {
            changeBalance(szabadidoBalance, true)
        }
    }

    private fun changeBalance(balanceText : TextView?, isWithdrawing : Boolean)
    {
        Toast.makeText(appContext, "Működik", Toast.LENGTH_SHORT).show()
    }

}



