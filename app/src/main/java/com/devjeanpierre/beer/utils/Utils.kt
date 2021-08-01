package com.devjeanpierre.beer.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.devjeanpierre.beer.R
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso

fun ImageView.loadByResourceWithoutCache(resource: String) = Picasso.get()
    .load(resource)
    .error(R.drawable.beer_example_image)
    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
    .into(this)

fun Activity.hideKeyboard() {
    val view = currentFocus
    view?.let { v ->
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }
}