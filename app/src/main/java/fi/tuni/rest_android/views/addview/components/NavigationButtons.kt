package fi.tuni.rest_android.views.addview.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fasterxml.jackson.databind.ObjectMapper
import fi.tuni.rest_android.tools.Models
import fi.tuni.rest_android.usercomponents.User
import fi.tuni.rest_android.views.addview.alerts.AddUpdateSuccessAlert

/**
 * Composable function that displays navigation buttons canceling the
 * add/modify actions and saving the added/modified user.
 *
 * @param addViewState The state variable for component to disable add view
 * @param modifyState The state variable used in client request.
 * @param userToAdd The state holding the user being added or modified.
 * @param isConfirmEnabled The state indicating whether the confirm
 * button should be enabled.
 * @param client The client used for making HTTP requests to the server.
 */
@Composable
fun NavigationButtons(addViewState : MutableState<Boolean>,
                      modifyState : MutableState<Pair<Boolean, User>>,
                      userToAdd : MutableState<User>,
                      isConfirmEnabled : MutableState<Boolean>,
                      client : Models
) {
    // State for displaying the response
    // message after saving or updating a user
    val responseMessage = remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        // Back button
        Button(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(75.dp, 40.dp),
            onClick = {
                addViewState.value = false
            },
            colors = ButtonDefaults.buttonColors(
                MaterialTheme.colors.background
            ),
        ) {
            Text(
                text = "Back",
                color = MaterialTheme.colors.surface
            )
        }
        Spacer(Modifier.width(5.dp))
        // Save/Update button
        Button(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(75.dp, 40.dp),
            onClick = {
                val jsonString = ObjectMapper()
                    .writeValueAsString(userToAdd.value)
                if (modifyState.value.first) {
                    // Update existing user
                    client.putRequest(
                        "https://dummyjson.com/users/${modifyState
                            .value
                            .second
                            .id
                        }",
                        jsonString
                    ) {
                        responseMessage.value = it
                    }
                } else {
                    // Add new user
                    client.postRequest(jsonString) {
                        responseMessage.value = it
                    }
                }
            },
            enabled = isConfirmEnabled.value,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                disabledBackgroundColor =
                    MaterialTheme.colors.primary.copy(alpha = 0.25f),
                contentColor = MaterialTheme.colors.background,
                disabledContentColor =
                    MaterialTheme.colors.surface.copy(alpha = 0.25f)
            )
        ) {
            Text(text = "Save")
        }
    }
    // Display a success alert if a response message is present
    if (responseMessage.value != "")
        // Component representing alert to be shown with the message
        AddUpdateSuccessAlert(responseMessage) {
            addViewState.value = false
        }
}