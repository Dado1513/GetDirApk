package com.dave.getdirapk

import android.graphics.drawable.Drawable

data class App (
    val packageName: String,
    val pathApp: String,
    val icon: Drawable,
    val name:String?
)