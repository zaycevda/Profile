package com.example.testformangofzco.utils

import android.app.Activity
import android.content.Intent
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.testformangofzco.R

private const val BLANK = ""
private const val RU = "\uD83C\uDDF7\uD83C\uDDFA"
private const val RU_CODE = "+7"
private const val US = "\uD83C\uDDFA\uD83C\uDDF8"
private const val US_CODE = "+1"
private const val FLAG = "flag"

fun TextView.setCountryCode(code: String) {
    val text = resources.getString(R.string.code, code)
    val regex = FLAG.toRegex()
    val flag = code.getFlag()
    this.text = text.replace(regex, flag)
}

private fun String.getFlag() =
    when (this) {
        RU_CODE -> RU
        US_CODE -> US
        else -> BLANK
    }

fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Activity.replaceActivity(activity: AppCompatActivity) {
    val intent = Intent(this, activity::class.java)
    startActivity(intent)
    this.finish()
}