package com.example.testformangofzco.utils

import android.text.Editable
import android.text.TextWatcher

private const val BLANK = ""
private const val DASH = "-"
private const val HASH = '#'
private const val MASK_PATTERN = "###-###-##-##"
private const val START = 0

object MaskTextWatcher : TextWatcher {

    private var isFormatting = false

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun afterTextChanged(editable: Editable) {
        if (isFormatting) return

        isFormatting = true

        val phone = editable.toString()
        val regex = DASH.toRegex()
        val dashes = phone.replace(regex, BLANK)
        val formattedText = StringBuilder()
        var index = START

        for (char in MASK_PATTERN)
            if (index < dashes.length)
                if (char == HASH) {
                    formattedText.append(dashes[index])
                    index++
                } else {
                    formattedText.append(char)
                    if (dashes[index].toString() == char.toString()) {
                        formattedText.append(dashes[index])
                        index++
                    }
                } else break

        editable.replace(START, editable.length, formattedText.toString())

        isFormatting = false
    }
}