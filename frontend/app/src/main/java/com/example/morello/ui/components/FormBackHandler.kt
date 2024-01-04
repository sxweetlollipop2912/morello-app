package com.example.morello.ui.components

import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable

class FormBackHandlerState() {
    val state = mutableStateOf<State>(State.Idle)

    companion object {
        val Default = FormBackHandlerState()

        fun Saver(): Saver<FormBackHandlerState, *> = Saver(
            save = { it.state },
            restore = {
                var newState = Default
                newState.updateState(it.value)
                newState
            },
        )
    }

    internal fun updateState(newState: State) {
        state.value = newState
    }

    fun goBack(shouldAskForConfirmation: Boolean = false) {
        if (shouldAskForConfirmation) {
            updateState(State.TryToGoBack)
        } else {
            updateState(State.ConfirmGoBack)
        }
    }

    enum class State {
        Idle,
        TryToGoBack,
        ConfirmGoBack,
    }

}

@Composable
fun rememberFormBackHandlerState(): FormBackHandlerState =
    rememberSaveable(
        saver = FormBackHandlerState.Saver(),
    ) {
        FormBackHandlerState.Default
    }

@Composable
fun FormBackHandler(
    state: FormBackHandlerState = rememberFormBackHandlerState(),
    onConfirmGoBack: () -> Unit,
) {
    if (state.state.value == FormBackHandlerState.State.ConfirmGoBack) {
        onConfirmGoBack()
        state.updateState(FormBackHandlerState.State.Idle)
    }
    if (state.state.value == FormBackHandlerState.State.TryToGoBack) {
        AlertDialog(
            onDismissRequest = { state.updateState(FormBackHandlerState.State.Idle) },
            title = { Text(text = "Discard changes?") },
            text = { Text(text = "Are you sure you want to discard changes?") },
            confirmButton = {
                Button(onClick = {
                    state.updateState(FormBackHandlerState.State.ConfirmGoBack)
                }) {
                    Text(text = "Discard")
                }
            },
            dismissButton = {
                Button(onClick = { state.updateState(FormBackHandlerState.State.Idle) }) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}