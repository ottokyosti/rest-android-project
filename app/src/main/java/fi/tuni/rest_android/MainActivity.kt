package fi.tuni.rest_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fasterxml.jackson.databind.ObjectMapper
import fi.tuni.rest_android.ui.theme.RestAndroidTheme
import fi.tuni.rest_android.ui.theme.containerColor
import java.util.*
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
                        val isAddViewOn = remember {
                            mutableStateOf(false)
                        }
                        val isModify = remember {
                            mutableStateOf(Pair(false, User()))
                        }
                        if (!isAddViewOn.value) {
                            MyScreen(isAddViewOn, client, users, isModify)
                        } else {
                            AddView(isAddViewOn, isModify)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddView(addViewState : MutableState<Boolean>, modifyState : MutableState<Pair<Boolean, User>>) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        val userToAdd = remember { mutableStateOf(modifyState.value.second) }
        LazyColumn {
            itemsIndexed(modifyState.value.second.getAttributes()) { index, attribute ->
                Row {
                    Text(
                        text = "${attribute.replaceFirstChar { it.uppercaseChar() }}:"
                    )
                    Spacer(Modifier.height(10.dp))
                    MyTextField(index, modifyState.value, attribute) {
                        userToAdd.value.updateAttributes(index, it)
                    }
                }
            }
            items(1) {
                Button(
                    onClick = {
                        println(userToAdd.value)
                        addViewState.value = false
                    }
                ) {
                    Text( "Confirm")
                }
            }
        }
    }
}

@Composable
fun MyTextField(index : Int,
                toModify : Pair<Boolean, User>,
                attribute : String,
                onUpdate : (String) -> Unit
) {
    val fieldValue = if (toModify.first)
        remember { mutableStateOf(toModify.second.attrToArray()[index]) }
    else
        remember { mutableStateOf("") }
    TextField(
        value = fieldValue.value,
        onValueChange = { value ->
            fieldValue.value = value
            onUpdate(value)
        },
        placeholder = {
            Text("Enter your $attribute")
        }
    )
}

@Composable
fun MyScreen(addViewState : MutableState<Boolean>,
             client : Models,
             users : Array<User>?,
             isModifyOn: MutableState<Pair<Boolean, User>>
) {
    var usersList by remember {
        mutableStateOf(users)
    }
    Scaffold(
        topBar = { SearchBar(client) { response ->
            usersList = ObjectMapper().readValue(response, Users::class.java).users
        }},
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
fun UserCard(user : User,
             client : Models,
             addViewState : MutableState<Boolean>,
             isModifyOn : MutableState<Pair<Boolean, User>>
) {
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
        UserInfo(user, client, addViewState, isModifyOn)
    }
}

@Composable
fun UserInfo(user : User,
             client : Models,
             addViewState : MutableState<Boolean>,
             modify : MutableState<Pair<Boolean, User>>
) {
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
                        onClick = {
                            modify.value = Pair(true, user)
                            addViewState.value = true
                        }
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