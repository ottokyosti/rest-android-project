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

/**
 * The main activity of the application.
 *
 * This activity displays the main view or the
 * add view based on the state of [isAddViewOn].
 * It uses [client] for making a network request
 * to fetch user data and displays it in the main view.
 */
class MainActivity : ComponentActivity() {
    private val client = Models()
    /**
     * Called when the activity is created.
     *
     * @param savedInstanceState The saved instance state bundle
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Variable to hold the fetched user data
        var users : Users
        client.getRequest("https://dummyjson.com/users?limit=10&skip=0") {
            users = ObjectMapper().readValue(it, Users::class.java)
            runOnUiThread {
                setContent {
                    RestAndroidTheme {
                        // State for tracking whether
                        // the add view is currently active
                        val isAddViewOn = remember {
                            mutableStateOf(false)
                        }
                        // State for tracking the modification state
                        // and user in the add view
                        val isModify = remember {
                            mutableStateOf(Pair(false, User()))
                        }
                        if (!isAddViewOn.value) {
                            MainView(isAddViewOn, client, users, isModify)
                        } else {
                            AddView(isAddViewOn, isModify, client)
                            // Component handling the back button behavior
                            // when add view state is active
                            BackHandler {
                                isAddViewOn.value = false
                            }
                        }
                    }
                }
            }
        }
    }
}