package com.example.tranzakcioelozmenyek

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.tranzakcioelozmenyek.adapter.TranzactionManager

class BankkartyaFragment(context: Context) : Fragment(R.layout.fragment_bankkartya) {

    private val appContext : Context = context
    private var isWithdrawing : Boolean = false
    private var actID : Int = -1
    private var txtBalances = mutableListOf<TextView?>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtBalances.add(getView()?.findViewById(R.id.kartyaegyenleg))
        txtBalances.add(getView()?.findViewById(R.id.keszpenzegyenleg))
        val cdAddBtn = getView()?.findViewById<Button>(R.id.addBtnKartya)
        val cdRmBtn = getView()?.findViewById<Button>(R.id.rmBtnKartya)
        val chAddBtn = getView()?.findViewById<Button>(R.id.addBtnKeszpenz)
        val chRmBtn = getView()?.findViewById<Button>(R.id.rmBtnKeszpenz)

        val sharedPreferences = this.requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val trWindow = getView()?.findViewById<ConstraintLayout>(R.id.tr_Window)
        val trEt = trWindow?.findViewById<EditText>(R.id.trEt)
        val trMegjegyzes = trWindow?.findViewById<EditText>(R.id.trMegjegyzes)
        val trWindowTxt = trWindow?.findViewById<TextView>(R.id.tr_windowText)
        val trbtnOk = trWindow?.findViewById<Button>(R.id.btn_Ok)
        val trbtnVissza = trWindow?.findViewById<Button>(R.id.crBtnVissza)


        setListeners(cdAddBtn, cdRmBtn, chAddBtn, chRmBtn, trWindow, trbtnOk, trbtnVissza, trEt, trMegjegyzes, trWindowTxt, editor)

        init(sharedPreferences, txtBalances[0], txtBalances[1])
    }

    private fun init(sharedPreferences: SharedPreferences, cdB: TextView?, chB: TextView?)
    {
        TranzactionManager.loadBalances(sharedPreferences, TranzactionManager.KARTYA_ID, cdB)
        TranzactionManager.loadBalances(sharedPreferences, TranzactionManager.KESZPENZ_ID, chB)
    }

    private fun setProperties(isW : Boolean, id: Int, tr_window: ConstraintLayout?, trwTxt: TextView?)
    {
        this.isWithdrawing = isW
        this.actID = id
        tr_window?.visibility = View.VISIBLE
        trwTxt?.text = if(isW) getString(R.string.trw_with) else getString(R.string.trw_dep)
    }


    private fun setListeners(cdAddBtn : Button? , cdRmBtn : Button?, chAddBtn : Button?, chRmBtn : Button?
                             , tr_window : ConstraintLayout?, tr_btnOk: Button?, tr_btnVissza : Button?, trEt : EditText?, trMegjegyzes: EditText?
                             , trwTxt : TextView?, editor : SharedPreferences.Editor
    )
    {
        cdAddBtn?.setOnClickListener {
            setProperties(false, TranzactionManager.KARTYA_ID, tr_window, trwTxt)
        }
        cdRmBtn?.setOnClickListener {
            setProperties(true, TranzactionManager.KARTYA_ID, tr_window, trwTxt)
        }

        chAddBtn?.setOnClickListener {
            setProperties(false, TranzactionManager.KESZPENZ_ID, tr_window, trwTxt)
        }
        chRmBtn?.setOnClickListener {
            setProperties(true, TranzactionManager.KESZPENZ_ID, tr_window, trwTxt)
        }

        tr_btnVissza?.setOnClickListener {
            trEt?.text?.clear()
            trMegjegyzes?.text?.clear()
            tr_window?.visibility = View.INVISIBLE
            closeKeyBoard()
        }
        tr_btnOk?.setOnClickListener {
            if(TranzactionManager.changeBalance(appContext, trEt?.text.toString(), trMegjegyzes?.text.toString(), isWithdrawing, actID, editor))
            {
                trEt?.text?.clear()
                trMegjegyzes?.text?.clear()
                val txt = "Egyenleg: ${TranzactionManager.getBalance(actID)} Ft"
                txtBalances[actID - 3]?.text = txt
                tr_window?.visibility = View.INVISIBLE
                closeKeyBoard()
            }
        }
    }

    private fun closeKeyBoard()
    {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}