package com.example.tranzakcioelozmenyek

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import android.content.SharedPreferences
import com.example.tranzakcioelozmenyek.adapter.TranzactionManager


class ZsebekFragment(context: Context) : Fragment(R.layout.fragment_zsebek) {

    private val appContext :Context = context
    private var isWithdrawing : Boolean = false
    private var actID : Int = -1
    private var txtBalances = mutableListOf<TextView?>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = this.requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        txtBalances.add(getView()?.findViewById(R.id.szallasegyenleg))
        txtBalances.add(getView()?.findViewById(R.id.vendeglatasegyenleg))
        txtBalances.add(getView()?.findViewById(R.id.szabadidoegyenleg))

        val szAddBtn = getView()?.findViewById<Button>(R.id.addBtnSzallas)
        val szRmBtn = getView()?.findViewById<Button>(R.id.rmBtnSzallas)

        val vAddBtn = getView()?.findViewById<Button>(R.id.addBtnVendeg)
        val vRmBtn = getView()?.findViewById<Button>(R.id.rmBtnVendeg)

        val szbAddBtn = getView()?.findViewById<Button>(R.id.addBtnSzabad)
        val szbRmBtn = getView()?.findViewById<Button>(R.id.rmBtnSzabad)

        val trWindow = getView()?.findViewById<ConstraintLayout>(R.id.tr_Window)
        val trEt = trWindow?.findViewById<EditText>(R.id.trEt)
        val trMegjegyzes = trWindow?.findViewById<EditText>(R.id.trMegjegyzes)
        val trWindowTxt = trWindow?.findViewById<TextView>(R.id.tr_windowText)
        val trbtnOk = trWindow?.findViewById<Button>(R.id.btn_Ok)
        val trbtnVissza = trWindow?.findViewById<Button>(R.id.btn_Vissza)

        setListeners(szAddBtn, szRmBtn, vAddBtn, vRmBtn, szbAddBtn, szbRmBtn, trWindow, trbtnOk, trbtnVissza, trEt, trMegjegyzes, trWindowTxt, editor)

        init(sharedPreferences, txtBalances[0], txtBalances[1], txtBalances[2])

    }

    private fun init(sharedPreferences: SharedPreferences, szB: TextView?, vB: TextView?, szbB: TextView?)
    {
        TranzactionManager.loadBalances(sharedPreferences, TranzactionManager.SZALLAS_ID, szB)
        TranzactionManager.loadBalances(sharedPreferences, TranzactionManager.VENDEGLATAS_ID, vB)
        TranzactionManager.loadBalances(sharedPreferences, TranzactionManager.SZABADIDO_ID, szbB)
    }

    private fun setProperties(isW : Boolean, id: Int, tr_window: ConstraintLayout?, trwTxt: TextView?)
    {
        this.isWithdrawing = isW
        this.actID = id
        tr_window?.visibility = View.VISIBLE
        trwTxt?.text = if(isW) getString(R.string.trw_with) else getString(R.string.trw_dep)
    }

    private fun setListeners(szAddBtn : Button? , szRmBtn : Button?, vAddBtn : Button?, vRmBtn : Button?, szbAddBtn: Button?, szbRmBtn : Button?
    , tr_window : ConstraintLayout?, tr_btnOk: Button?, tr_btnVissza : Button?, trEt : EditText?, trMegjegyzes: EditText?
    , trwTxt : TextView?, editor : SharedPreferences.Editor
    )
    {
        szAddBtn?.setOnClickListener {
            setProperties(false, TranzactionManager.SZALLAS_ID, tr_window, trwTxt)
        }
        szRmBtn?.setOnClickListener {
            setProperties(true, TranzactionManager.SZALLAS_ID, tr_window, trwTxt)
        }

        vAddBtn?.setOnClickListener {
            setProperties(false, TranzactionManager.VENDEGLATAS_ID, tr_window, trwTxt)
        }
        vRmBtn?.setOnClickListener {
            setProperties(true, TranzactionManager.VENDEGLATAS_ID, tr_window, trwTxt)
        }

        szbAddBtn?.setOnClickListener {
            setProperties(false, TranzactionManager.SZABADIDO_ID, tr_window, trwTxt)
        }
        szbRmBtn?.setOnClickListener {
            setProperties(true, TranzactionManager.SZABADIDO_ID, tr_window, trwTxt)
        }


        tr_btnVissza?.setOnClickListener {
            trEt?.text?.clear()
            tr_window?.visibility = View.INVISIBLE
            closeKeyBoard()
        }
        tr_btnOk?.setOnClickListener {
                if(TranzactionManager.changeBalance(appContext, trEt?.text.toString(), trMegjegyzes?.text.toString(), isWithdrawing, actID, editor))
                {
                    trEt?.text?.clear()
                    val txt = "Egyenleg: ${TranzactionManager.getBalance(actID)} Ft"
                    txtBalances[actID]?.text = txt
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



