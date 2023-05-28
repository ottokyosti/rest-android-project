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

@Composable
fun MainContent(usersList : Array<User>?,
                client : Models,
                addViewState : MutableState<Boolean>,
                isModifyOn: MutableState<Pair<Boolean, User>>
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        LazyColumn {
            items(usersList!!) { user ->
                UserCard(user, client, addViewState, isModifyOn)
            }
        }
    }
}