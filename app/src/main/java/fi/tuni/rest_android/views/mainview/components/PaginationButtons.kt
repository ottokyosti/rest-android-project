package fi.tuni.rest_android.views.mainview.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fi.tuni.rest_android.tools.Models
import fi.tuni.rest_android.usercomponents.User
import fi.tuni.rest_android.usercomponents.Users

@Composable
fun PaginationButtons(users : Users,
                      client : Models,
                      callback: (String) -> Unit
) {
    val totalPages = users.total / 10
    val currentPage = remember {
        mutableStateOf(1)
    }
    val isInitialComposition = remember {
        mutableStateOf(true)
    }
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 5.dp, start = 2.dp, end = 2.dp)
    ) {
        IconButton(
            onClick = {
                currentPage.value -= 1
            },
            modifier = Modifier
                .padding(end = 1.dp)
                .size(50.dp, 40.dp),
            enabled = currentPage.value > 1
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Previous page",
                modifier = Modifier.padding(0.dp)
            )
        }
        Button(
            onClick = {
                currentPage.value = 1
            },
            modifier = Modifier
                .padding(horizontal = 1.dp)
                .size(50.dp, 40.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background
            ),
            border = BorderStroke(
                1.dp, if (currentPage.value == 1) {
                    MaterialTheme.colors.primary
                } else {
                    Color.Transparent
                }
            )
        ) {
            Text(
                text = "1",
                fontSize = 13.sp
            )
        }
        val visiblePageNumbers = if (currentPage.value == 1) {
            currentPage.value + 1
        } else {
            minOf(currentPage.value, totalPages - 3)
        }
        for (page in visiblePageNumbers until visiblePageNumbers + 3) {
            Button(
                onClick = {
                    currentPage.value = page
                },
                modifier = Modifier
                    .padding(horizontal = 1.dp)
                    .size(50.dp, 40.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.background
                ),
                border = BorderStroke(
                    1.dp, if (currentPage.value == page) {
                        MaterialTheme.colors.primary
                    } else {
                        Color.Transparent
                    }
                )
            ) {
                Text(
                    text = page.toString(),
                    fontSize = 13.sp
                )
            }
        }
        Button(
            onClick = {
                currentPage.value = totalPages
            },
            modifier = Modifier
                .padding(horizontal = 1.dp)
                .size(50.dp, 40.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background
            ),
            border = BorderStroke(
                1.dp, if (currentPage.value == totalPages) {
                    MaterialTheme.colors.primary
                } else {
                    Color.Transparent
                }
            )
        ) {
            Text(
                text = totalPages.toString(),
                fontSize = 13.sp,
            )
        }
        IconButton(
            onClick = {
                currentPage.value += 1
            },
            modifier = Modifier
                .padding(start = 1.dp)
                .size(50.dp, 40.dp),
            enabled = currentPage.value < totalPages
        ) {
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = "Next page"
            )
        }
        LaunchedEffect(currentPage.value) {
            if (!isInitialComposition.value) {
                client.getRequest(
                    "https://dummyjson.com/users?limit=10&skip=${
                        10 * (currentPage.value - 1)
                    }"
                ) {
                    callback(it)
                }
            } else {
                isInitialComposition.value = false
            }
        }
    }
}