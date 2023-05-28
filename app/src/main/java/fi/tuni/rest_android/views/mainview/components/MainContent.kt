package fi.tuni.rest_android.views.mainview.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import fi.tuni.rest_android.tools.Models
import fi.tuni.rest_android.usercomponents.User

/**
 * Composable function for displaying the main content of the application.
 *
 * This composable function renders the main content of the application,
 * which consists of a list of user cards.
 *
 * @param usersList The list of users to display
 * @param client The client object used for making network requests
 * @param addViewState The mutable state to pass down to a children component
 * @param isModifyOn The mutable state to pass down to a children component
 */
@Composable
fun MainContent(usersList : Array<User>?,
                client : Models,
                addViewState : MutableState<Boolean>,
                isModifyOn: MutableState<Pair<Boolean, User>>
) {
    // Component representing the background for list of users
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        LazyColumn {
            items(usersList!!) { user ->
                // Component representing user in a list
                UserCard(user, client, addViewState, isModifyOn)
            }
        }
    }
}