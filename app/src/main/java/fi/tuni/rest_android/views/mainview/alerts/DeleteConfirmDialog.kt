package fi.tuni.rest_android.views.mainview.alerts

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import fi.tuni.rest_android.ui.theme.containerColor

/**
 * Composable function that displays a delete confirmation dialog.
 *
 * @param callback The callback function to be invoked with the
 * user's confirmation choice.
 */
@Composable
fun DeleteConfirmDialog(callback: (Boolean) -> Unit) {
    // Component representing the dialog showing delete action confirmation
    AlertDialog(
        onDismissRequest = { callback(false) },
        title = {
            Text(
                text = "Do you want to delete this user?",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        },
        text = {
            Text(
                text = "Please confirm your decision",
            )
        },
        dismissButton = {
            // Cancel button
            Button(
                onClick = { callback(false) },
                colors = ButtonDefaults.buttonColors(containerColor())
            ) {
                Text(
                    text = "Cancel",
                    fontWeight = FontWeight.Bold,
                )
            }
        },
        confirmButton = {
            // Confirm button
            Button(
                onClick = { callback(true) },
                colors = ButtonDefaults.buttonColors(containerColor())
            ) {
                Text(
                    text = "Confirm",
                    fontWeight = FontWeight.Bold,
                )
            }
        },
        backgroundColor = containerColor()
    )
}