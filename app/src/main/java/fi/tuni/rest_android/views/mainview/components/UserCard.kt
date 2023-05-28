package fi.tuni.rest_android.views.mainview.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fi.tuni.rest_android.tools.Models
import fi.tuni.rest_android.usercomponents.User

/**
 * Composable function that represents a card displaying user information.
 *
 * @param user The User object representing the user
 * whose information will be displayed.
 * @param client The Models object used for making network requests.
 * @param addViewState The mutable state to pass down to a children component.
 * @param isModifyOn The mutable state to pass down to a children component.
 */
@Composable
fun UserCard(user : User,
             client : Models,
             addViewState : MutableState<Boolean>,
             isModifyOn : MutableState<Pair<Boolean, User>>
) {
    // Access the current context
    val context = LocalContext.current
    // Component representing the user's image and information
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        // Show user's image
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(user.image)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp, 50.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colors.primary, CircleShape)
                .padding(10.dp)
        )
        Spacer(Modifier.width(10.dp))
        // Component representing user's information
        UserInfo(user, client, addViewState, isModifyOn)
    }
}