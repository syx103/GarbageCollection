package com.example.utils

import android.view.View
import android.widget.TextView

fun View?.setViewVisibility(visible: Int) {
    this?.let {
        visibility = visible
    }
}

fun TextView?.setTextViewText(textStr: String) {
    this?.let {
        text = textStr
    }
}