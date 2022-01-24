package com.aldroid.real.clock

import android.app.Application
import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDateTime
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

class ClockViewModel(application: Application) : AndroidViewModel(application) {

    @RequiresApi(VERSION_CODES.O)
    @OptIn(ExperimentalTime::class)
    val time : StateFlow<Time> = tickerFlow(1.seconds)
    .map {
        val time = LocalDateTime.now()
        Time(
            hour = if(time.hour > 12) (time.hour - 12).toFloat() else time.hour.toFloat(),
            minute = time.minute.toFloat(),
            second = time.second.toFloat()
        )
    }
//    .distinctUntilChanged { old, new ->
//        old.minute == new.minute
//    }
    .stateIn(viewModelScope, SharingStarted.Eagerly, Time(0f,0f,0f))

}


@OptIn(ExperimentalTime::class)
fun tickerFlow(period: Duration, initialDelay: Duration = Duration.ZERO) = flow {
    delay(initialDelay)
    while (true) {
        emit(Unit)
        delay(period)
    }
}
