package fi.tuni.rest_android

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class User (var id : Int? = null,
                 var firstName : String? = null,
                 var lastName : String? = null,
                 var username : String? = null,
                 var age : Int? = null,
                 var email : String? = null,
                 var phone : String? = null,
                 var height : Int? = null,
                 var weight : Double? = null,
                 var image : String? = null) {

    fun updateAttributes(index : Int, value : String) {
        when (index) {
            0 -> {
                this.firstName = value
            }
            1 -> {
                this.lastName = value
            }
            2 -> {
                this.username = value
            }
            3 -> {
                this.age = value.toInt()
            }
            4 -> {
                this.email = value
            }
            5 -> {
                this.phone = value
            }
            6 -> {
                this.height = value.toInt()
            }
            7 -> {
                this.weight = value.toDouble()
            }
        }
    }

    override fun toString() : String {
        return "${this.firstName} ${this.lastName}\n" +
                "Username: ${this.username}\n" +
                "Age: ${this.age}\n" +
                "Email: ${this.email}\n" +
                "Phone: ${this.phone}\n" +
                "Height: ${this.height} cm\n" +
                "Weight: ${this.weight} kg"
    }

    fun getAttributes() : Array<String> {
        return arrayOf(
            "first name",
            "last name",
            "username",
            "age",
            "email",
            "phone",
            "height",
            "weight"
        )
    }

    fun attrToArray() : Array<String> {
        return arrayOf(
            firstName ?: "",
            lastName ?: "",
            username ?: "",
            age.toString(),
            email ?: "",
            phone ?: "",
            height.toString(),
            weight.toString()
        )
    }
}