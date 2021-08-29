package com.macrosystems.beerfinder.core.ex

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.macrosystems.beerfinder.R

fun View.lostConnectionSnackBar(){
    val snackBar = Snackbar.make(this, context.getString(R.string.lost_connection_snack_bar_placeholder), Snackbar.LENGTH_INDEFINITE)
    val snackBarView = snackBar.view
    snackBarView.setBackgroundColor(Color.RED)
    snackBar.show()
}

fun View.reconnectedSnackBar(){
    val snackBar = Snackbar.make(this, context.getString(R.string.reconnected_snack_bar_placeholder), Snackbar.LENGTH_LONG)
    val snackBarView = snackBar.view
    snackBarView.setBackgroundColor(Color.GREEN)
    val snackBarTextView: TextView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text)
    snackBarTextView.setTextColor(Color.BLACK)
    snackBar.show()
}


fun View.dismissKeyboard(completed: () -> Unit ={}){
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val wasItOpened = inputMethodManager.hideSoftInputFromWindow(windowToken, 0)

    if (!wasItOpened) completed()
}