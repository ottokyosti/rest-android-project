package fi.tuni.rest_android

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class User (var firstName : String? = null,
                 var lastName : String? = null,
                 var age : Int? = null,
                 var image : String? = null) {

    override fun toString() : String {
        return "name: $firstName $lastName"
    }
}