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

@Composable
fun MainView(addViewState : MutableState<Boolean>,
             client : Models,
             users : Users,
             isModifyOn: MutableState<Pair<Boolean, User>>
) {
    val usersList = remember {
        mutableStateOf(users.users)
    }
    Scaffold(
        topBar = { SearchBar(client) { response ->
            usersList.value = ObjectMapper()
                .readValue(response, Users::class.java)
                .users
            }
        },
        floatingActionButton = {
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
            MainContent(usersList.value, client, addViewState, isModifyOn)
        }
    }
}