package com.example.testformangofzco.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.example.testformangofzco.R

private const val BLANK = ""
private const val RU = "\uD83C\uDDF7\uD83C\uDDFA"
private const val RU_CODE = "+7"
private const val US = "\uD83C\uDDFA\uD83C\uDDF8"
private const val US_CODE = "+1"
private const val FLAG = "flag"
private const val TAG = "funs"

private const val JANUARY = "01"
private const val FEBRUARY = "02"
private const val MARCH = "03"
private const val APRIL = "04"
private const val MAY = "05"
private const val JUNE = "06"
private const val JULY = "07"
private const val AUGUST = "08"
private const val SEPTEMBER = "09"
private const val OCTOBER = "10"
private const val NOVEMBER = "11"


private fun String.getFlag() =
    when (this) {
        RU_CODE -> RU
        US_CODE -> US
        else -> BLANK
    }

fun NavController.safelyNavigate(resId: Int, args: Bundle? = null) {
    try {
        this.navigate(resId, args)
    } catch (e: Exception) {
        Log.d(TAG, "safelyNavigate: ${e.message}")
    }
}

fun TextView.setCountryCode(code: String) {
    val text = resources.getString(R.string.code, code)
    val regex = FLAG.toRegex()
    val flag = code.getFlag()
    this.text = text.replace(regex, flag)
}

fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Activity.replaceActivity(activity: AppCompatActivity) {
    val intent = Intent(this, activity::class.java)
    startActivity(intent)
    this.finish()
}

fun TextView.setZodiacSign(month: String, dayOfMonth: Int) {
    text = resources.getString(
        R.string.zodiac_sign,
        resources.getString(
            when (month) {
                JANUARY -> if (dayOfMonth >= 20) R.string.aquarius else R.string.capricorn
                FEBRUARY -> if (dayOfMonth >= 19) R.string.pisces else R.string.aquarius
                MARCH -> if (dayOfMonth >= 21) R.string.aries else R.string.pisces
                APRIL -> if (dayOfMonth >= 20) R.string.taurus else R.string.aries
                MAY -> if (dayOfMonth >= 21) R.string.gemini else R.string.taurus
                JUNE -> if (dayOfMonth >= 21) R.string.cancer else R.string.gemini
                JULY -> if (dayOfMonth >= 23) R.string.leo else R.string.cancer
                AUGUST -> if (dayOfMonth >= 23) R.string.virgo else R.string.leo
                SEPTEMBER -> if (dayOfMonth >= 23) R.string.libra else R.string.virgo
                OCTOBER -> if (dayOfMonth >= 23) R.string.scorpio else R.string.libra
                NOVEMBER -> if (dayOfMonth >= 22) R.string.sagittarius else R.string.scorpio
                else -> if (dayOfMonth >= 22) R.string.capricorn else R.string.sagittarius
            }
        )
    )
}