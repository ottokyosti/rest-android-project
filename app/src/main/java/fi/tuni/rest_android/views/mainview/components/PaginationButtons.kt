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
import fi.tuni.rest_android.usercomponents.Users

/**
 * Composable function that represents the pagination buttons component.
 *
 * @param users The Users object containing information about the users.
 * @param client The Models object used for making network requests.
 * @param callback The callback function to be invoked when
 * current page changes.
 */
@Composable
fun PaginationButtons(users : Users,
                      client : Models,
                      callback: (String) -> Unit
) {
    // Total number of pages based on the number of users.
    val totalPages = users.total / 10
    // Mutable state to track the current page.
    val currentPage = remember {
        mutableStateOf(1)
    }
    // Mutable state to track the initial composition.
    val isInitialComposition = remember {
        mutableStateOf(true)
    }
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 5.dp, start = 2.dp, end = 2.dp)
    ) {
        // Previous page button
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
        // First page button
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
                2.dp, if (currentPage.value == 1) {
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
        // Get first visible page number after 1
        val visiblePageNumber = if (currentPage.value == 1) {
            currentPage.value + 1
        } else {
            minOf(currentPage.value, totalPages - 3)
        }
        // Render buttons for visible page numbers
        for (page in visiblePageNumber until visiblePageNumber + 3) {
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
                    2.dp, if (currentPage.value == page) {
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
        // Last page button
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
                2.dp, if (currentPage.value == totalPages) {
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
        // Next page button
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
        // Invoke the callback function when the current page changes
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