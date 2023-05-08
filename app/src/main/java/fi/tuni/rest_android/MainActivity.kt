package fi.tuni.rest_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fasterxml.jackson.databind.ObjectMapper
import fi.tuni.rest_android.ui.theme.RestAndroidTheme
import kotlin.concurrent.thread

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
                        var usersList by remember { mutableStateOf(users) }
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
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.background,
        shape = RoundedCornerShape(8.dp),
        elevation = 5.dp
    ) {
        Column(
            Modifier.padding(5.dp)
        ) {
            Text(
                text = "${user.firstName} ${user.lastName}",
                fontWeight = FontWeight.Bold
            )
            Text("Age: ${user.age.toString()}")
        }
    }
}
