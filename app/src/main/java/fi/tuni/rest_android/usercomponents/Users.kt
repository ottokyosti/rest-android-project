package fi.tuni.rest_android.usercomponents

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import fi.tuni.rest_android.usercomponents.User

/**
 * Data class representing a collection of users.
 *
 * @property users An array of User objects representing
 * the users in the collection.
 * @property total The total number of users present in the HTTP response.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class Users (var users : Array<User>? = null, var total : Int = 0)