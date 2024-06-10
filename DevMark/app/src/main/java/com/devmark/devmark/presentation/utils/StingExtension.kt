package com.devmark.devmark.presentation.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.capitalizeFirstLetter(): String {
    return if (this.isNotEmpty()) {
        this.substring(0, 1).uppercase() + this.substring(1).lowercase()
    } else {
        this
    }
}

fun String.adjustDateTime(): String {
    if (!this.contains('+')) {
        return this
    }

    val dateTimeString = this.substring(0, this.indexOf('+'))
    val offsetString = this.substring(this.indexOf('+'))

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    val dateTime = LocalDateTime.parse(dateTimeString, formatter)
    val hoursOffset = offsetString.split(':')[0].replace("+", "").toLong()

    val newDateTime = dateTime.plusHours(hoursOffset)

    return newDateTime.format(formatter)
}