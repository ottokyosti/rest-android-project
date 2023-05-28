package fi.tuni.rest_android.views.addview.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import fi.tuni.rest_android.usercomponents.User
import fi.tuni.rest_android.tools.Validator

/**
 * Composable function that represents a custom text field
 * used for inputting user attributes.
 *
 * @param index The index of the attribute in the list of attributes.
 * @param toModify The pair indicating the modification
 * mode and the user to modify.
 * @param attribute The name of the attribute being edited.
 * @param isConfirmEnabled The state indicating whether the
 * confirm button should be enabled.
 * @param onUpdate The callback function invoked when the text
 * value of the field changes.
 */
@Composable
fun MyTextField(index : Int,
                toModify : Pair<Boolean, User>,
                attribute : String,
                isConfirmEnabled : MutableState<Boolean>,
                onUpdate : (String) -> Unit
) {
    // Determine the initial field value based on the modification mode
    val fieldValue = if (toModify.first)
        remember { mutableStateOf(toModify.second.attrToArray()[index]) }
    else
        remember { mutableStateOf("") }
    // Check the validity of the field value
    val isValid = remember {
        mutableStateOf(Validator.validate(attribute, fieldValue.value))
    }
    // Determine the outline color based on validity
    val outlineColor = if (isValid.value) Color.Green else Color.Red
    // TextField component for each of the user's attributes with
    // label, placeholder text, icon when value is valid
    // and keyboard options for different attributes
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = fieldValue.value,
        isError = !isValid.value,
        onValueChange = { value ->
            fieldValue.value = value
            isValid.value = Validator.validate(attribute, value)
            onUpdate(value)
            isConfirmEnabled.value = Validator.checkAllFieldsValid(toModify.second)
        },
        label = {
            Text(attribute.replaceFirstChar { it.uppercaseChar() })
        },
        placeholder = {
            Text("Enter your $attribute")
        },
        trailingIcon = {
            if (isValid.value) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = null
                )
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = when(attribute) {
                "age", "height", "weight" -> KeyboardType.Number
                "email" -> KeyboardType.Email
                "phone" -> KeyboardType.Phone
                else -> KeyboardType.Text
            },
            imeAction = ImeAction.Next
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = outlineColor,
            unfocusedBorderColor = outlineColor,
            errorBorderColor = outlineColor,
            focusedLabelColor = outlineColor,
            errorLabelColor = outlineColor,
            backgroundColor = MaterialTheme.colors.background,
            errorCursorColor = MaterialTheme.colors.surface,
            cursorColor = MaterialTheme.colors.surface,
        )
    )
}