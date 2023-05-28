package fi.tuni.rest_android.views.addview.alerts

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import fi.tuni.rest_android.ui.theme.containerColor

@Composable
fun AddUpdateSuccessAlert(responseMessage : MutableState<String>,
                          callback: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            responseMessage.value = ""
            callback()
        },
        title = {
            Text(responseMessage.value)
        },
        confirmButton = {
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