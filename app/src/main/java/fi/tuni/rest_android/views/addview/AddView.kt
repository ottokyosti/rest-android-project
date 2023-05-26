package fi.tuni.rest_android.views.addview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuni.rest_android.Models
import fi.tuni.rest_android.User
import fi.tuni.rest_android.Validator
import fi.tuni.rest_android.views.addview.components.*

@Composable
fun AddView(addViewState : MutableState<Boolean>,
            modifyState : MutableState<Pair<Boolean, User>>,
            client : Models,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        val userToAdd = remember {
            mutableStateOf(modifyState.value.second)
        }
        val isConfirmEnabled = remember {
            mutableStateOf(Validator.checkAllFieldsValid(userToAdd.value))
        }
        LazyColumn(
            modifier = Modifier.padding(10.dp)
        ) {
            item {
                NavigationButtons(
                    addViewState,
                    modifyState,
                    userToAdd,
                    isConfirmEnabled,
                    client
                )
            }
            itemsIndexed(modifyState
                .value
                .second
                .getAttributes()
            ) { index, attribute ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 5.dp)
                ) {
                    MyTextField(
                        index,
                        modifyState.value,
                        attribute,
                        isConfirmEnabled
                    ) {
                        userToAdd.value.updateAttributes(index, it)
                    }
                }
            }
        }
    }
}