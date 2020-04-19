package com.alex_malishev.presentation_layer.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private const val API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private const val SHORT_DATE_FORMAT = "dd MMMM"

    fun toShortDate(date: String): String?{
        return SimpleDateFormat(API_DATE_FORMAT, Locale.getDefault()).parse(date)?.let {
            SimpleDateFormat(SHORT_DATE_FORMAT, Locale.getDefault()).format(it)
        }
    }
}