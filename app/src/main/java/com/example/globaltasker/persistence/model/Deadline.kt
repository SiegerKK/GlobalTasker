package com.example.globaltasker.persistence.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Deadline(
    var isActive: Boolean = false,
    var date: Date = Date()
) : Parcelable {
    companion object{
        const val SHORT_DATE_FORMAT = "dd.MM HH:mm"
        const val DATE_YEAR_FORMAT = "yyyy:MM:dd"
    }

    fun set(date: Date){
        this.date = date
        isActive = true
    }
    fun isOut(): Boolean = date.time < System.currentTimeMillis() && isActive
    fun isLastDay(): Boolean{
        val dayInMillis = 24 * 60 * 60 * 1000L
        return date.time - dayInMillis < System.currentTimeMillis() && isActive
    }

    fun toSimpleString(): String = SimpleDateFormat(SHORT_DATE_FORMAT, Locale.GERMAN).format(date)
}