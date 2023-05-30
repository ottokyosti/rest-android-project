package fi.tuni.rest_android.views.mainview.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import fi.tuni.rest_android.tools.Models
import kotlin.concurrent.thread

/**
 * Composable function that represents the search bar component.
 *
 * @param client The Models object used for making network requests.
 * @param callback The callback function to be invoked
 * when the search is performed.
 */
@Composable
fun SearchBar(client : Models, callback : (String) -> Unit) {
    // Mutable state to track the search word
    var searchWord by remember { mutableStateOf("") }
    // URL for performing the search
    val url = remember {
        mutableStateOf("https://dummyjson.com/users/search?q=$searchWord")
    }
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background
    ) {
        // TextField representing a searchbar containing
        // search icon, and appearing clear button
        TextField(
            value = searchWord,
            onValueChange = { value ->
                searchWord = value
                url.value = "https://dummyjson.com/users/search?q=$value"
                client.getRequest(url.value) {
                    thread {
                        callback(it)
                    }
                }
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (searchWord != "") {
                    IconButton(
                        onClick = {
                            searchWord = ""
                            url.value =
                                "https://dummyjson.com/users?limit=10&skip=0"
                            client.getRequest(url.value) {
                                thread {
                                    callback(it)
                                }
                            }
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                }
            },
            placeholder = {
                Text("Search")
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                textColor = MaterialTheme.colors.surface,
                trailingIconColor = MaterialTheme.colors.surface,
                leadingIconColor = MaterialTheme.colors.surface,
                cursorColor = MaterialTheme.colors.surface,
                placeholderColor = Color.Gray
            )
        )
    }
}