package fi.tuni.rest_android.views.mainview.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fi.tuni.rest_android.tools.Models
import fi.tuni.rest_android.views.mainview.alerts.DeleteConfirmDialog
import fi.tuni.rest_android.usercomponents.User
import fi.tuni.rest_android.ui.theme.containerColor

/**
 * Composable function that represents the user information display.
 *
 * @param user The User object representing the user information to display.
 * @param client The Models object used for making network
 * requests passed down to a child component.
 * @param addViewState The mutable state representing the add view
 * state to pass down to a child component.
 * @param modify The mutable state representing the modify state
 * state to pass down to a child component.
 */
@Composable
fun UserInfo(user : User,
             client : Models,
             addViewState : MutableState<Boolean>,
             modify : MutableState<Pair<Boolean, User>>
) {
    // Mutable state to track whether the user information is expanded
    var isExpanded by remember { mutableStateOf(false) }
    // Mutable state to track the confirmation dialog for deleting the user
    var openDeleteConfirm by remember { mutableStateOf(false) }
    // Mutable state to track the delete operation's success message
    var deleteSuccessful by remember { mutableStateOf("") }
    // Component representing the style of information's background
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                isExpanded = !isExpanded
            },
        color = if (MaterialTheme.colors.isLight) {
            MaterialTheme.colors.background
        } else {
            Color(0xFF1A1A1A)
        },
        shape = RoundedCornerShape(8.dp),
        elevation = 5.dp,
    ) {
        Box {
            // Component representing information in text format
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(7.dp)
            ) {
                Text(
                    text = user.toString(),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 2
                )
            }
            // Component representing action icons
            Column(
                Modifier.align(Alignment.TopEnd)
            ) {
                if (isExpanded) {
                    // Delete user button
                    IconButton(
                        onClick = { openDeleteConfirm = !openDeleteConfirm }
                    ) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "Delete user"
                        )
                    }
                    // Modify user button
                    IconButton(
                        onClick = {
                            modify.value = Pair(true, user)
                            addViewState.value = true
                        }
                    ) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "Edit user"
                        )
                    }
                }
                // Expand info button
                IconButton(
                    onClick = { isExpanded = !isExpanded }
                ) {
                    Icon(
                        if (isExpanded) {
                            Icons.Filled.ExpandLess
                        } else {
                            Icons.Filled.ExpandMore
                        },
                        contentDescription = "Expand user information"
                    )
                }
            }
        }
        if (openDeleteConfirm) {
            // Dialog for confirming delete action
            DeleteConfirmDialog {
                if (it) {
                    val url = "https://dummyjson.com/users/${user.id}"
                    client.deleteRequest(url) { response ->
                        deleteSuccessful = response
                    }
                }
                openDeleteConfirm = !openDeleteConfirm
            }
        }
        if (deleteSuccessful != "") {
            // Dialog for alerting user of successful delete action
            AlertDialog(
                onDismissRequest = { deleteSuccessful = "" },
                title = {
                    Text(deleteSuccessful)
                },
                confirmButton = {
                    Button(
                        onClick = {
                            deleteSuccessful = ""
                        },
                        colors = ButtonDefaults.buttonColors(containerColor())
                    ) {
                        Text("Ok")
                    }
                },
                backgroundColor = containerColor()
            )
        }
    }
}