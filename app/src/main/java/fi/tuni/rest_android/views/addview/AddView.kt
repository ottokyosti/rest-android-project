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
import fi.tuni.rest_android.tools.Models
import fi.tuni.rest_android.usercomponents.User
import fi.tuni.rest_android.tools.Validator
import fi.tuni.rest_android.views.addview.components.*

/**
 * Composable function that displays the add or edit view for a user
 * based on modify state.
 *
 * @param addViewState The state of add view to pass down to NavigationButtons.
 * @param modifyState The state of modify parameter to pass down to
 * NavigationButtons and list of TextFields
 * @param client The client to pass down to NavigationButtons.
 */
@Composable
fun AddView(addViewState : MutableState<Boolean>,
            modifyState : MutableState<Pair<Boolean, User>>,
            client : Models,
) {
    // Component representing background for the add view
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        // Store the user to add/edit in a mutable state
        val userToAdd = remember {
            mutableStateOf(modifyState.value.second)
        }
        // Enable/disable the confirm button
        // based on whether all fields are valid
        val isConfirmEnabled = remember {
            mutableStateOf(Validator.checkAllFieldsValid(userToAdd.value))
        }
        LazyColumn(
            modifier = Modifier.padding(10.dp)
        ) {
            // Display user attributes in a vertical list
            itemsIndexed(modifyState
                .value
                .second
                .getAttributes()
            ) { index, attribute ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 5.dp)
                ) {
                    // Dynamic TextField for each of the user's
                    // attributes
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
            item {
                // Navigation buttons for saving and
                // canceling the add/edit operation
                NavigationButtons(
                    addViewState,
                    modifyState,
                    userToAdd,
                    isConfirmEnabled,
                    client
                )
            }
        }
    }
}