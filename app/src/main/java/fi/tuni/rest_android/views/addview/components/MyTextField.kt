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
import fi.tuni.rest_android.User
import fi.tuni.rest_android.Validator

@Composable
fun MyTextField(index : Int,
                toModify : Pair<Boolean, User>,
                attribute : String,
                isConfirmEnabled : MutableState<Boolean>,
                onUpdate : (String) -> Unit
) {
    val fieldValue = if (toModify.first)
        remember { mutableStateOf(toModify.second.attrToArray()[index]) }
    else
        remember { mutableStateOf("") }
    val isValid = remember {
        mutableStateOf(Validator.validate(attribute, fieldValue.value))
    }
    val outlineColor = if (isValid.value) Color.Green else Color.Red
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