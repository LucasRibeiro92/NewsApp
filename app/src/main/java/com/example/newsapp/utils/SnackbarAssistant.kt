package com.example.newsapp.utils

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.newsapp.R
import com.google.android.material.snackbar.Snackbar

object SnackbarAssistant {
    fun showSnackbar(view: View, message: String, actionText: String? = null, action: (() -> Unit)? = null) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        if (actionText != null && action != null) {
            snackbar.setAction(actionText) { action() }
            snackbar.setActionTextColor(Color.YELLOW)
        }

        // Customize the background color
        snackbar.view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.mygray))

        // Customize the text color
        val textView = snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.setTextColor(ContextCompat.getColor(view.context, R.color.mygreen))
        textView.textSize = 18f

        // Customize the action button color
        val actionView = snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)
        actionView.setTextColor(ContextCompat.getColor(view.context, R.color.mygreen))

        snackbar.show()
    }
}