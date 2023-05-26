package fi.tuni.rest_android.views.addview.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fasterxml.jackson.databind.ObjectMapper
import fi.tuni.rest_android.Models
import fi.tuni.rest_android.User

@Composable
fun NavigationButtons(addViewState : MutableState<Boolean>,
                      modifyState : MutableState<Pair<Boolean, User>>,
                      userToAdd : MutableState<User>,
                      isConfirmEnabled : MutableState<Boolean>,
                      client : Models
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = {
                addViewState.value = false
            },
            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.background),
            border = BorderStroke(1.dp, Color.DarkGray)
        ) {
            Text(
                text = "Cancel",
                color = MaterialTheme.colors.surface
            )
        }
        Spacer(Modifier.width(5.dp))
        Button(
            modifier = Modifier.align(Alignment.CenterEnd),
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
                        println(it)
                    }
                } else {
                    client.postRequest(jsonString) {
                        println(it)
                    }
                }
                addViewState.value = false
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
}