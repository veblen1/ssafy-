package com.ssafyb109.bangrang.view

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(date: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    return try {
        val parsedDate = inputFormat.parse(date)
        outputFormat.format(parsedDate!!)
    } catch (e: Exception) {
        date
    }
}


fun formatDateToKorean(dateStr: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("M월 d일", Locale.KOREAN)
    return try {
        val parsedDate = inputFormat.parse(dateStr)
        outputFormat.format(parsedDate!!)
    } catch (e: Exception) {
        dateStr
    }
}


fun formatExchangeRate(number: Double): String {
    val symbols = DecimalFormatSymbols(Locale.getDefault())
    symbols.groupingSeparator = ','
    val df = DecimalFormat("#,##0.00", symbols)
    return df.format(number)
}

