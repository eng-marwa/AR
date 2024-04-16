package com.marwa.ar.utils.extensions

import java.text.SimpleDateFormat
import java.util.Locale

fun String.toDate(): String {
    val timestamp = this
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val date = parser.parse(timestamp) ?: return "Invalid Date" // Parsing the string to Date object
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    return formatter.format(date)
}