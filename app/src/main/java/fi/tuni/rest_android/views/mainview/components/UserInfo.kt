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

@Composable
fun UserInfo(user : User,
             client : Models,
             addViewState : MutableState<Boolean>,
             modify : MutableState<Pair<Boolean, User>>
) {
    var isExpanded by remember { mutableStateOf(false) }
    var openDeleteConfirm by remember { mutableStateOf(false) }
    var deleteSuccessful by remember { mutableStateOf("") }
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
            Column(
                Modifier.align(Alignment.TopEnd)
            ) {
                if (isExpanded) {
                    IconButton(
                        onClick = { openDeleteConfirm = !openDeleteConfirm }
                    ) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "Delete user"
                        )
                    }
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