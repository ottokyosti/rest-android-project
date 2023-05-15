package fi.tuni.rest_android

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class User (var id : Int? = null,
                 var firstName : String? = null,
                 var lastName : String? = null,
                 var username : String? = null,
                 var age : Int? = null,
                 var gender : String? = null,
                 var email : String? = null,
                 var phone : String? = null,
                 var height : String? = null,
                 var weight : String? = null,
                 var image : String? = null) {

    override fun toString() : String {
        return "${this.firstName} ${this.lastName}\n" +
                "Username: ${this.username}\n" +
                "Age: ${this.age}\n" +
                "Gender: ${this.gender}\n" +
                "Email: ${this.email}\n" +
                "Phone: ${this.phone}\n" +
                "Height: ${this.height} cm\n" +
                "Weight: ${this.weight} kg"
    }
}