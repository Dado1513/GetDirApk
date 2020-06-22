package com.dave.getdirapk

import android.graphics.drawable.Drawable
import kotlinx.android.parcel.Parcelize

data class App (
    val packageName: String,
    val pathApp: String,
    val icon: Drawable,
    val name:String?
)