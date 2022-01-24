package com.aldroid.real.ui.desktop

import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aldroid.real.AppViewModel
import com.aldroid.real.MainActivity
import com.aldroid.real.databinding.WidgetHostBinding
import com.aldroid.real.ui.desktop.clock.AnalogClock
import com.aldroid.real.widgets.WidgetHostFragment
import java.lang.Exception


@Composable
fun MainDesktop(
    viewModel: AppViewModel = viewModel(),
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.size(20.dp))

        AnalogClock()

        //TODO - if we ever decide we want android widgets
//        AndroidViewBinding(WidgetHostBinding::inflate) {
//
//        }

    }

}