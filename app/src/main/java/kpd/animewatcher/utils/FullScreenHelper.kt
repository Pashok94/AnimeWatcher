package kpd.animewatcher.utils

import android.app.Activity
import android.view.View


class FullScreenHelper(private val context: Activity, private vararg val views: View) {

    /**
     * call this method to enter full screen
     */
    fun enterFullScreen() {
        val decorView: View = context.window.decorView
        hideSystemUi(decorView)
        for (view: View in views) {
            view.visibility = View.GONE
            view.invalidate()
        }
    }

    /**
     * call this method to exit full screen
     */
    fun exitFullScreen() {
        val decorView: View = context.window.decorView
        showSystemUi(decorView)
        for (view: View in views) {
            view.visibility = View.VISIBLE
            view.invalidate()
        }
    }

    private fun hideSystemUi(mDecorView: View) {
        mDecorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    private fun showSystemUi(mDecorView: View) {
        mDecorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
}