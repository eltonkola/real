package com.aldroid.real.ui.desktop

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aldroid.real.AppViewModel

@Composable
fun NoteDesktop(
    viewModel: AppViewModel = viewModel(),
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val note by viewModel.note.collectAsState()

        TextField(
            modifier = Modifier.fillMaxSize(),
            value = note,
            onValueChange = { viewModel.updateNote(it) },
            label = { Text("Quick note") }
        )
    }
}