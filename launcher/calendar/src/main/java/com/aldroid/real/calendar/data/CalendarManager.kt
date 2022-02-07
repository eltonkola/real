package com.aldroid.real.calendar.data

import android.content.Context
import android.database.Cursor
import android.os.Build.VERSION_CODES
import android.provider.CalendarContract
import android.provider.CalendarContract.Events
import androidx.annotation.RequiresApi
import androidx.core.database.getIntOrNull
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import java.util.*

class CalendarManager(private val context: Context) {
    companion object {
        @RequiresApi(VERSION_CODES.ICE_CREAM_SANDWICH)
        private val CALENDAR_EVENT_PROJECTION = arrayOf(
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
            CalendarContract.Calendars.NAME,
            CalendarContract.Calendars.CALENDAR_COLOR,
            CalendarContract.Calendars.VISIBLE,
            CalendarContract.Calendars.SYNC_EVENTS,
            CalendarContract.Calendars.ACCOUNT_NAME,
            CalendarContract.Calendars.ACCOUNT_TYPE,
        )
        private const val CALENDAR_PROJECTION_ID_INDEX = 0
        private const val CALENDAR_PROJECTION_DISPLAY_NAME_INDEX = 1
        private const val CALENDAR_PROJECTION_NAME_INDEX = 2
        private const val CALENDAR_PROJECTION_CALENDAR_COLOR_INDEX = 3
        private const val CALENDAR_PROJECTION_VISIBLE_INDEX = 4
        private const val CALENDAR_PROJECTION_SYNC_EVENTS_INDEX = 5
        private const val CALENDAR_PROJECTION_ACCOUNT_NAME_INDEX = 6
        private const val CALENDAR_PROJECTION_ACCOUNT_TYPE_INDEX = 7


        @RequiresApi(VERSION_CODES.ICE_CREAM_SANDWICH)
        private val EVENT_PROJECTION = arrayOf(
            CalendarContract.Events._ID,
            CalendarContract.Events.TITLE,
            CalendarContract.Events.EVENT_LOCATION,
            CalendarContract.Events.STATUS,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DTEND,
            CalendarContract.Events.DURATION,
            CalendarContract.Events.ALL_DAY,
            CalendarContract.Events.AVAILABILITY,
            CalendarContract.Events.RRULE,
            CalendarContract.Events.DISPLAY_COLOR,
            CalendarContract.Events.VISIBLE,
        )
        private const val PROJECTION_ID_INDEX = 0
        private const val PROJECTION_TITLE_INDEX = 1
        private const val PROJECTION_EVENT_LOCATION_INDEX = 2
        private const val PROJECTION_STATUS_INDEX = 3
        private const val PROJECTION_DTSTART_INDEX = 4
        private const val PROJECTION_DTEND_INDEX = 5
        private const val PROJECTION_DURATION_INDEX = 6
        private const val PROJECTION_ALL_DAY_INDEX = 7
        private const val PROJECTION_AVAILABILITY_INDEX = 8
        private const val PROJECTION_RRULE_INDEX = 9
        private const val PROJECTION_DISPLAY_COLOR_INDEX = 10
        private const val PROJECTION_VISIBLE_INDEX = 11
    }

    @RequiresApi(VERSION_CODES.ICE_CREAM_SANDWICH)
    fun getCalendars(): List<CalendarItem> {
        val calendars = mutableListOf<CalendarItem>()
        val uri = CalendarContract.Calendars.CONTENT_URI
        val selection = ""
        val selectionArgs = emptyArray<String>()
        val cur = context.contentResolver.query(
            uri,
            CALENDAR_EVENT_PROJECTION,
            selection, selectionArgs,
            null,
        )
        while (cur?.moveToNext() == true) {
            val calId = cur.getLong(CALENDAR_PROJECTION_ID_INDEX)
            val displayName = cur.getString(CALENDAR_PROJECTION_DISPLAY_NAME_INDEX)
            val name = cur.getString(CALENDAR_PROJECTION_NAME_INDEX)
            val color = cur.getInt(CALENDAR_PROJECTION_CALENDAR_COLOR_INDEX)
            val visible = cur.getInt(CALENDAR_PROJECTION_VISIBLE_INDEX)
            val syncEvents = cur.getInt(CALENDAR_PROJECTION_SYNC_EVENTS_INDEX)
            val accountName = cur.getString(CALENDAR_PROJECTION_ACCOUNT_NAME_INDEX)
            val accountType = cur.getString(CALENDAR_PROJECTION_ACCOUNT_TYPE_INDEX)
            calendars.add(
                CalendarItem(
                    id = calId,
                    name = name,
                    displayName = displayName,
                    color = color,
                    visible = visible == 1,
                    syncEvents = syncEvents == 1,
                    accountName = accountName,
                    accountType = accountType,
                )
            )
        }
        cur?.close()




        return calendars.map {
            it.copy(
                events = getEvents(it.id.toString())
            )
        }.filter { it.events.isNotEmpty() }
    }


    @RequiresApi(VERSION_CODES.ICE_CREAM_SANDWICH)
    private fun getEvents(calendarId: String): List<EventItem> {
        val eventItems = mutableListOf<EventItem>()
        val uri = CalendarContract.Events.CONTENT_URI

        val selectionArgs = arrayOf(calendarId)


        val startTime = Calendar.getInstance()

        startTime.set(Calendar.HOUR_OF_DAY, 0)
        startTime.set(Calendar.MINUTE, 0)
        startTime.set(Calendar.SECOND, 0)

        val endTime: Calendar = Calendar.getInstance()
        endTime.add(Calendar.DATE, 1)



        val selection = "(${Events.CALENDAR_ID} = ?) " +
                "AND (" + Events.DTSTART + " >= " + startTime.timeInMillis + " ) " +
             //   "AND ( " + Events.DTSTART + " <= " + endTime.timeInMillis + " ) " +
                "AND ( deleted != 1 )"

        val cur = context.contentResolver.query(
            uri,
            EVENT_PROJECTION,
            selection, selectionArgs,
            null,
        )

        while (cur?.moveToNext() == true) {
            val eventId = cur.getLong(PROJECTION_ID_INDEX)
            val title = cur.getStringOrNull(PROJECTION_TITLE_INDEX)
            val eventLocation = cur.getStringOrNull(PROJECTION_EVENT_LOCATION_INDEX)
            val status = cur.getIntOrNull(PROJECTION_STATUS_INDEX)
            val dtStart = cur.getLongOrNull(PROJECTION_DTSTART_INDEX)
            val dtEnd = cur.getLongOrNull(PROJECTION_DTEND_INDEX)
            val duration = cur.getStringOrNull(PROJECTION_DURATION_INDEX)
            val allDay = cur.getIntOrNull(PROJECTION_ALL_DAY_INDEX) == 1
            val availability = cur.getIntOrNull(PROJECTION_AVAILABILITY_INDEX)
            val rRule = cur.getStringOrNull(PROJECTION_RRULE_INDEX)
            val displayColor = cur.getIntOrNull(PROJECTION_DISPLAY_COLOR_INDEX)
            val visible = cur.getIntOrNull(PROJECTION_VISIBLE_INDEX) == 1

            eventItems.add(
                EventItem(
                    id = eventId,
                    title = title,
                    eventLocation = eventLocation,
                    status = status,
                    dtStart = dtStart,
                    dtEnd = dtEnd,
                    duration = duration,
                    allDay = allDay,
                    availability = availability,
                    rRule = rRule,
                    displayColor = displayColor,
                    visible = visible,
                )
            )
        }
        cur?.close()
        return eventItems
    }

}
