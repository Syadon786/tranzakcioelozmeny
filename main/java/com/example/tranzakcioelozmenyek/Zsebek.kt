package com.example.tranzakcioelozmenyek

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

class Zsebek(context: Context) : Fragment(R.layout.fragment_zsebek) {

    private val appContext :Context = context
    private var isWithdrawing : Boolean = false
    private var actID :Byte = -1

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

        val tr_window = getView()?.findViewById<ConstraintLayout>(R.id.tr_Window)
        val trEt = tr_window?.findViewById<EditText>(R.id.trEt)
        val tr_btnOk = tr_window?.findViewById<Button>(R.id.btn_Ok)
        val tr_btnVissza = tr_window?.findViewById<Button>(R.id.btn_Vissza)

        szAddBtn?.setOnClickListener {
            setProperties(false, TranzactionManager.SZALLAS_ID)
            if (tr_window != null) {
                tr_window.visibility = View.VISIBLE
            }
        }
        szRmBtn?.setOnClickListener {
            setProperties(true, TranzactionManager.SZALLAS_ID)
            if (tr_window != null) {
                tr_window.visibility = View.VISIBLE
            }
        }

        vAddBtn?.setOnClickListener {
            setProperties(false, TranzactionManager.VENDEGLATAS_ID)
            if (tr_window != null) {
                tr_window.visibility = View.VISIBLE
            }
        }
        vRmBtn?.setOnClickListener {
            setProperties(true, TranzactionManager.VENDEGLATAS_ID)
            if (tr_window != null) {
                tr_window.visibility = View.VISIBLE
            }
        }

        szbAddBtn?.setOnClickListener {
            setProperties(false, TranzactionManager.SZABADIDO_ID)
            if (tr_window != null) {
                tr_window.visibility = View.VISIBLE
            }
        }
        szbRmBtn?.setOnClickListener {
            setProperties(true, TranzactionManager.SZABADIDO_ID)
            if (tr_window != null) {
                tr_window.visibility = View.VISIBLE
            }
        }


        tr_btnVissza?.setOnClickListener {
            trEt?.text?.clear()
            tr_window?.visibility = View.INVISIBLE
        }

        tr_btnOk?.setOnClickListener {
            if(trEt?.text.toString() == "")
            {
                Toast.makeText(appContext, "Nem adott meg összeget", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setProperties(isW : Boolean, id: Byte)
    {
        this.isWithdrawing = isW
        this.actID = id

    }
}



