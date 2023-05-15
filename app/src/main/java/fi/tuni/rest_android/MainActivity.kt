package fi.tuni.rest_android

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fasterxml.jackson.databind.ObjectMapper
import fi.tuni.rest_android.ui.theme.RestAndroidTheme
import fi.tuni.rest_android.ui.theme.containerColor
import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {
    private val client = Models()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var users : Array<User>?
        client.getRequest("https://dummyjson.com/users") {
            users = ObjectMapper().readValue(it, Users::class.java).users
            runOnUiThread {
                setContent {
                    RestAndroidTheme {
                        var usersList by remember { mutableStateOf(users) }
                        Scaffold(
                            topBar = { SearchBar(client) { response ->
                                usersList = ObjectMapper().readValue(response, Users::class.java).users
                            }},
                            backgroundColor = MaterialTheme.colors.background
                        ) { padding ->
                            Box(
                                modifier = Modifier.padding(padding)
                            ) {
                                MainContent(usersList, client)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainContent(usersList : Array<User>?, client : Models) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        LazyColumn {
            items(usersList!!) { user ->
                UserCard(user, client)
            }
        }
    }
}

@Composable
fun SearchBar(client : Models, callback : (String) -> Unit) {
    var searchWord by remember { mutableStateOf("") }
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background
    ) {
        TextField(
            value = searchWord,
            onValueChange = { value ->
                searchWord = value
                val url = "https://dummyjson.com/users/search?q=$searchWord"
                client.getRequest(url) {
                    thread {
                        callback(it)
                    }
                }
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (searchWord != "") {
                    IconButton(
                        onClick = {
                            searchWord = ""
                            val url = "https://dummyjson.com/users/search?q=$searchWord"
                            client.getRequest(url) {
                                thread {
                                    callback(it)
                                }
                            }
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                }
            },
            placeholder = {
                Text("Search")
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                textColor = MaterialTheme.colors.surface,
                trailingIconColor = MaterialTheme.colors.surface,
                leadingIconColor = MaterialTheme.colors.surface,
                cursorColor = MaterialTheme.colors.surface,
                placeholderColor = Color.Gray
            )
        )
    }
}

@Composable
fun UserCard(user : User, client : Models) {
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
        UserInfo(user, client)
    }
}

@Composable
fun UserInfo(user : User, client : Models) {
    var isExpanded by remember { mutableStateOf(false) }
    var openDeleteConfirm by remember { mutableStateOf(false) }
    var deleteSuccessful by remember { mutableStateOf("") }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                isExpanded = !isExpanded
            },
        color = if (MaterialTheme.colors.isLight) {
            MaterialTheme.colors.background
        } else {
            Color(0xFF1A1A1A)
        },
        shape = RoundedCornerShape(8.dp),
        elevation = 5.dp,
    ) {
        Box {
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(7.dp)
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
                if (it) {
                    val url = "https://dummyjson.com/users/${user.id}"
                    client.deleteRequest(url) { response ->
                        deleteSuccessful = response
                    }
                }
                openDeleteConfirm = !openDeleteConfirm
            }
        }
        if (deleteSuccessful != "") {
            AlertDialog(
                onDismissRequest = { deleteSuccessful = "" },
                title = {
                    Text(deleteSuccessful)
                },
                confirmButton = {
                    Button(
                        onClick = {
                            deleteSuccessful = ""
                        },
                        colors = ButtonDefaults.buttonColors(containerColor())
                    ) {
                        Text("Ok")
                    }
                },
                backgroundColor = containerColor()
            )
        }
    }
}

@Composable
fun MyDeleteConfirmDialog(callback: (Boolean) -> Unit) {
    AlertDialog(
        onDismissRequest = { callback(false) },
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
            Button(
                onClick = { callback(false) },
                colors = ButtonDefaults.buttonColors(containerColor())
            ) {
                Text(
                    text = "Cancel",
                    fontWeight = FontWeight.Bold,
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { callback(true) },
                colors = ButtonDefaults.buttonColors(containerColor())
            ) {
                Text(
                    text = "Confirm",
                    fontWeight = FontWeight.Bold,
                )
            }
        },
        backgroundColor = containerColor()
    )
}