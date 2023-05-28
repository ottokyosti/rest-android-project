package fi.tuni.rest_android.views.mainview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.fasterxml.jackson.databind.ObjectMapper
import fi.tuni.rest_android.usercomponents.User
import fi.tuni.rest_android.tools.Models
import fi.tuni.rest_android.usercomponents.Users
import fi.tuni.rest_android.views.mainview.components.*
import kotlin.concurrent.thread

/**
 * The main view of the application.
 *
 * This composable function displays the main content of
 * the application, including a search bar, a floating action
 * button for adding users, and a pagination row for navigating between pages.
 *
 * @param addViewState The state to pass down to a component
 * so that the component is able to update it and trigger a recomposition
 * @param client The client object used for making network requests
 * @param users The initial list of users to display
 * @param isModifyOn The state to pass down to a component
 * so that the component is able to update it and trigger a recomposition
 */
@Composable
fun MainView(addViewState : MutableState<Boolean>,
             client : Models,
             users : Users,
             isModifyOn: MutableState<Pair<Boolean, User>>
) {
    // State for tracking the list of users
    val usersList = remember {
        mutableStateOf(users.users)
    }
    // Component representing main view's layout with top bar,
    // floating action button and bottom bar
    Scaffold(
        topBar = {
            // Component representing a search bar
            SearchBar(client) { response ->
                usersList.value = ObjectMapper()
                    .readValue(response, Users::class.java)
                    .users
            }
        },
        floatingActionButton = {
            // Floating action button component for adding a user
            FloatingActionButton(
                onClick = {
                    addViewState.value = true
                    isModifyOn.value = Pair(false, User())
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = null
                )
            }
        },
        bottomBar = {
            // Component representing buttons for pagination
            PaginationButtons(users, client) {
                thread {
                    usersList.value = ObjectMapper()
                        .readValue(it, Users::class.java)
                        .users
                }
            }
        },
        backgroundColor = MaterialTheme.colors.background
    ) { padding ->
        Box(
            modifier = Modifier.padding(padding)
        ) {
            // Component representing main content
            // of the view consisting of users
            MainContent(usersList.value, client, addViewState, isModifyOn)
        }
    }
}