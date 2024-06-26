package com.e_book

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.e_book.DashBoard.DashBoardActivity
import com.e_book.R

abstract class BaseActivity : AppCompatActivity() {

    private val PREFS_NAME = "MyPrefs"
    private val THEME_KEY = "theme"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyTheme()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar_menu, menu)

        // Show the theme options menu items
        setOptionsMenuVisibility(menu)

        // Hide notification and logout icons if not on DashBoardActivity
       /* if (this !is DashBoardActivity) {
            setMenuItemVisibility(menu, R.id.notification, false)
            setMenuItemVisibility(menu, R.id.logout, false)
        }*/

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.light_mode -> {
                setThemeAndSaveMode(AppCompatDelegate.MODE_NIGHT_NO)
                return true
            }
            R.id.dark_mode -> {
                setThemeAndSaveMode(AppCompatDelegate.MODE_NIGHT_YES)
                return true
            }
            R.id.system_default_mode -> {
                setThemeAndSaveMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Set the theme to light mode when back button is pressed
        setThemeAndSaveMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun applyTheme() {
        val savedTheme = getSavedTheme()
        AppCompatDelegate.setDefaultNightMode(savedTheme)
    }

    private fun getSavedTheme(): Int {
        val sharedPreferences: SharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        return sharedPreferences.getInt(THEME_KEY, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    private fun setThemeAndSaveMode(themeMode: Int) {
        AppCompatDelegate.setDefaultNightMode(themeMode)
        saveTheme(themeMode)
        recreate()
    }

    private fun saveTheme(themeMode: Int) {
        val sharedPreferences: SharedPreferences.Editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit()
        sharedPreferences.putInt(THEME_KEY, themeMode)
        sharedPreferences.apply()
    }

    // Method to show or hide the theme options menu items
    private fun setOptionsMenuVisibility(menu: Menu?) {
        menu?.findItem(R.id.light_mode)?.isVisible = true
        menu?.findItem(R.id.dark_mode)?.isVisible = true
        menu?.findItem(R.id.system_default_mode)?.isVisible = true
    }

    // Method to set visibility of a specific menu item
    private fun setMenuItemVisibility(menu: Menu?, menuItemId: Int, isVisible: Boolean) {
        menu?.findItem(menuItemId)?.isVisible = isVisible
    }
}
