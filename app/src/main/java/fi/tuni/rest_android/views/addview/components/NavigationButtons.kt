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

@Composable
fun NavigationButtons(addViewState : MutableState<Boolean>,
                      modifyState : MutableState<Pair<Boolean, User>>,
                      userToAdd : MutableState<User>,
                      isConfirmEnabled : MutableState<Boolean>,
                      client : Models
) {
    val responseMessage = remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
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
        Button(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(75.dp, 40.dp),
            onClick = {
                val jsonString = ObjectMapper()
                    .writeValueAsString(userToAdd.value)
                if (modifyState.value.first) {
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
    if (responseMessage.value != "")
        AddUpdateSuccessAlert(responseMessage) {
            addViewState.value = false
        }
}