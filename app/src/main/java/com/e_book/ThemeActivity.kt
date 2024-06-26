package com.e_book

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.e_book.DashBoard.DashBoardActivity

open class ThemeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        val themeMode = sharedPref.getInt("theme_mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        AppCompatDelegate.setDefaultNightMode(themeMode)
    }

    protected fun setAppTheme(themeMode: Int) {
        val sharedPref = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putInt("theme_mode", themeMode)
            apply()
        }
        AppCompatDelegate.setDefaultNightMode(themeMode)
        recreate()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar_menu, menu)

        // Hide notification and logout icons if not on DashBoardActivity
        if (this !is DashBoardActivity) {
            setMenuItemVisibility(menu, R.id.notification, false)
            setMenuItemVisibility(menu, R.id.logout, false)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.light_mode -> {
                setAppTheme(AppCompatDelegate.MODE_NIGHT_NO)
                true
            }
            R.id.dark_mode -> {
                setAppTheme(AppCompatDelegate.MODE_NIGHT_YES)
                true
            }
            R.id.system_default_mode -> {
                setAppTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Set the theme to light mode when back button is pressed
        setAppTheme(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun setMenuItemVisibility(menu: Menu?, itemId: Int, isVisible: Boolean) {
        val item = menu?.findItem(itemId)
        item?.isVisible = isVisible
    }
}
