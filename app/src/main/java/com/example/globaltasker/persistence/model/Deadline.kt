package com.example.globaltasker.persistence.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Deadline(
    var isActive: Boolean = false,
    var date: Date = Date()
) : Parcelable {
    fun set(date: Date){
        this.date = date
        isActive = true
    }
    fun isOut(): Boolean{
        return date.time < System.currentTimeMillis()
    }
    fun isLastDay(): Boolean{
        val dayInMillis = 24 * 60 * 60 * 1000L
        return date.time - dayInMillis < System.currentTimeMillis()
    }
}