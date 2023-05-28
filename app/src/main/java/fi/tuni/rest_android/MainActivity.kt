package fi.tuni.rest_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.fasterxml.jackson.databind.ObjectMapper
import fi.tuni.rest_android.tools.Models
import fi.tuni.rest_android.ui.theme.RestAndroidTheme
import fi.tuni.rest_android.usercomponents.User
import fi.tuni.rest_android.usercomponents.Users
import fi.tuni.rest_android.views.addview.AddView
import fi.tuni.rest_android.views.mainview.MainView
import androidx.activity.compose.BackHandler

class MainActivity : ComponentActivity() {
    private val client = Models()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var users : Users
        client.getRequest("https://dummyjson.com/users?limit=10&skip=0") {
            users = ObjectMapper().readValue(it, Users::class.java)
            runOnUiThread {
                setContent {
                    RestAndroidTheme {
                        val isAddViewOn = remember {
                            mutableStateOf(false)
                        }
                        val isModify = remember {
                            mutableStateOf(Pair(false, User()))
                        }
                        if (isAddViewOn.value) {
                            BackHandler {
                                isAddViewOn.value = false
                            }
                        }
                        if (!isAddViewOn.value) {
                            MainView(isAddViewOn, client, users, isModify)
                        } else {
                            AddView(isAddViewOn, isModify, client)
                        }
                    }
                }
            }
        }
    }
}