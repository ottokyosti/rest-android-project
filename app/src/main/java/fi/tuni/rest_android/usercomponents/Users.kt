package fi.tuni.rest_android.usercomponents

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import fi.tuni.rest_android.usercomponents.User

@JsonIgnoreProperties(ignoreUnknown = true)
data class Users (var users : Array<User>? = null, var total : Int = 0)