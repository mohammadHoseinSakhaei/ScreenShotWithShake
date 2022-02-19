package com.example.screenshot

import android.graphics.Bitmap
import android.view.View


class ScreenShot {


    companion object {
        fun takescreenshot(v: View): Bitmap? {
            v.isDrawingCacheEnabled = true
            v.buildDrawingCache(true)
            val b: Bitmap = Bitmap.createBitmap(v.getDrawingCache())
            v.setDrawingCacheEnabled(false)
            return b
        }

        fun takescreenshotOfRootView(v: View): Bitmap? {
            return takescreenshot(v.rootView)
        }
    }
}