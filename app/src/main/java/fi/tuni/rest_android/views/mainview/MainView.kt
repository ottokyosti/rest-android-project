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
import fi.tuni.rest_android.*

@Composable
fun MainView(addViewState : MutableState<Boolean>,
             client : Models,
             users : Array<User>?,
             isModifyOn: MutableState<Pair<Boolean, User>>
) {
    var usersList by remember {
        mutableStateOf(users)
    }
    Scaffold(
        topBar = { SearchBar(client) { response ->
            usersList = ObjectMapper()
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
        backgroundColor = MaterialTheme.colors.background
    ) { padding ->
        Box(
            modifier = Modifier.padding(padding)
        ) {
            MainContent(usersList, client, addViewState, isModifyOn)
        }
    }
}