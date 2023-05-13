package fi.tuni.rest_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fasterxml.jackson.databind.ObjectMapper
import fi.tuni.rest_android.ui.theme.RestAndroidTheme

class MainActivity : ComponentActivity() {
    private val client = Models()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var users : Array<User>?
        client.getAll("https://dummyjson.com/users") {
            users = ObjectMapper().readValue(it, Users::class.java).users
            runOnUiThread {
                setContent {
                    RestAndroidTheme {
                        val usersList by remember { mutableStateOf(users) }
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colors.background
                        ) {
                            LazyColumn {
                                items(usersList!!) { user ->
                                    UserCard(user)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserCard(user : User) {
    val context = LocalContext.current
    Row (
         modifier = Modifier
             .fillMaxWidth()
             .padding(10.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(user.image)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp, 50.dp)
                .clip(CircleShape)
                .border(1.dp, MaterialTheme.colors.primary, CircleShape)
                .padding(10.dp)
        )
        Spacer(Modifier.width(10.dp))
        UserInfo(user)
    }
}

@Composable
fun UserInfo(user : User) {
    var isExpanded by remember { mutableStateOf(false) }
    var openDeleteConfirm by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.background,
        shape = RoundedCornerShape(8.dp),
        elevation = 5.dp
    ) {
        Box {
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(5.dp)
            ) {
                Text(
                    text = user.toString(),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 2
                )
            }
            Column(
                Modifier.align(Alignment.TopEnd)
            ) {
                if (isExpanded) {
                    IconButton(
                        onClick = { openDeleteConfirm = !openDeleteConfirm }
                    ) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "Delete user"
                        )
                    }
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "Edit user"
                        )
                    }
                }
                IconButton(
                    onClick = { isExpanded = !isExpanded }
                ) {
                    Icon(
                        if (isExpanded) {
                            Icons.Filled.ExpandLess
                        } else {
                            Icons.Filled.ExpandMore
                        },
                        contentDescription = "Expand user information"
                    )
                }
            }
        }
        if (openDeleteConfirm) {
            MyDeleteConfirmDialog {
                openDeleteConfirm = !openDeleteConfirm
            }
        }
    }
}

@Composable
fun MyDeleteConfirmDialog(callback: () -> Unit) {
    AlertDialog(
        onDismissRequest = { callback() },
        title = {
            Text(
                text = "Do you want to delete this user?",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        },
        text = {
            Text(
                text = "Please confirm your decision",
            )
        },
        dismissButton = {
            OutlinedButton(
                onClick = { callback() },
                border = BorderStroke(0.dp, Color.Transparent)
            ) {
                Text(
                    text = "Cancel",
                    fontWeight = FontWeight.Bold,
                )
            }
        },
        confirmButton = {
            OutlinedButton(
                onClick = { callback() },
                border = BorderStroke(0.dp, Color.Transparent)
            ) {
                Text(
                    text = "Confirm",
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    )
}