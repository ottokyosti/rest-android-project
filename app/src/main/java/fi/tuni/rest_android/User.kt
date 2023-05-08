package fi.tuni.rest_android

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class User (var id : Int? = null,
                 var firstName : String? = null,
                 var lastName : String? = null) {

    override fun toString() : String {
        return "Id: $id, name: $firstName $lastName"
    }
}