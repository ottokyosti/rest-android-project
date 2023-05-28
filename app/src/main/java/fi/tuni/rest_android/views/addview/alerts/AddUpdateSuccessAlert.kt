package fi.tuni.rest_android.views.addview.alerts

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import fi.tuni.rest_android.ui.theme.containerColor

/**
 * Composable function that displays an alert dialog to
 * indicate a successful addition or update operation.
 *
 * @param responseMessage The state holding the response message to display.
 * @param callback The callback function to execute
 * when the alert dialog is dismissed.
 */
@Composable
fun AddUpdateSuccessAlert(responseMessage : MutableState<String>,
                          callback: () -> Unit
) {
    // Dialog component with button for acknowledging action
    AlertDialog(
        onDismissRequest = {
            responseMessage.value = ""
            callback()
        },
        title = {
            Text(responseMessage.value)
        },
        confirmButton = {
            // Confirm button
            Button(
                onClick = {
                    responseMessage.value = ""
                    callback()
                },
                colors = ButtonDefaults.buttonColors(containerColor())
            ) {
                Text("Ok")
            }
        },
        backgroundColor = containerColor()
    )
}