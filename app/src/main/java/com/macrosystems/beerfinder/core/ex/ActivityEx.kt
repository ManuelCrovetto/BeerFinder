package com.macrosystems.beerfinder.core.ex

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.macrosystems.beerfinder.R

fun Activity.showToast(message: String, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, message, duration).show()
}

fun Context.span(name: String, alcoholGrades: String): SpannableStringBuilder {
    val context: Context = this
    val completedText = SpannableStringBuilder("$name $alcoholGrades")
    val textSize = 20

    completedText.apply {
        setSpan(
            StyleSpan(Typeface.ITALIC),
            name.length,
            (name + alcoholGrades).length +1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    completedText.apply {
        setSpan(
            AbsoluteSizeSpan(textSize),
            name.length,
            (name + alcoholGrades).length +1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    completedText.apply {
        setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.black)),
            name.length,
            (name + alcoholGrades).length + 1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return completedText
}