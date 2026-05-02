package com.nutrisport.shared.util

import android.content.Context
import android.content.res.Resources

actual fun getScreenWidth(): Float {
    return Resources.getSystem().displayMetrics.widthPixels / Resources.getSystem().displayMetrics.density
}