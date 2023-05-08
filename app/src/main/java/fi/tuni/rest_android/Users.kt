package fi.tuni.rest_android

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Users (var users : Array<User>? = null)