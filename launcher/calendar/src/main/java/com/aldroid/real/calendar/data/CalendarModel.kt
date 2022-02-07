package com.aldroid.real.calendar.data

import android.provider.CalendarContract

data class CalendarItem(
    val id: Long,
    val name: String?,
    val displayName: String?,
    val color: Int?,
    val visible: Boolean?,
    val syncEvents: Boolean?,
    val accountName: String?,
    val accountType: String?,
    val events : List<EventItem> =  emptyList(),
)

data class EventItem(
    val id: Long,
    val title: String?,
    val eventLocation: String?,
    val status: Int?,
    val dtStart: Long?,
    val dtEnd: Long?,
    val duration: String?,
    val allDay: Boolean?,
    val availability: Int?,
    val rRule: String?,
    val displayColor: Int?,
    val visible: Boolean?,
)

fun getEventStatusText(status: Int?): String {
    return when (status) {
        CalendarContract.Events.STATUS_TENTATIVE -> "Tentative"
        CalendarContract.Events.STATUS_CONFIRMED -> "Confirmed"
        CalendarContract.Events.STATUS_CANCELED -> "Canceled"
        else -> "Unknown"
    }
}

fun getEventAvailabilityText(availability: Int?): String {
    return when (availability) {
        CalendarContract.Events.AVAILABILITY_BUSY -> "Busy"
        CalendarContract.Events.AVAILABILITY_FREE -> "Free"
        CalendarContract.Events.AVAILABILITY_TENTATIVE -> "Tentative"
        else -> "Unknown"
    }
}
