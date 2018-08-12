package eu.gitcode.musicmap.common.extensions

import android.content.Context
import android.support.v4.app.FragmentActivity
import android.view.inputmethod.InputMethodManager

fun FragmentActivity.hideKeyboard() {
    val view = currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}