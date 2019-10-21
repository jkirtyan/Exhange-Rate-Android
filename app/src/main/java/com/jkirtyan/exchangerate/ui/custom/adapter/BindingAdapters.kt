package com.jkirtyan.exchangerate.ui.custom.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import java.util.*

@BindingAdapter("currencyCode")
fun loadFlag(view: ImageView, currencyCode: String) {
    view.setImageResource(
        view.resources.getIdentifier(
            "@drawable/flag_${currencyCode.toLowerCase(Locale.getDefault())}",
            "drawable",
            view.context
                .packageName
        )
    )
}