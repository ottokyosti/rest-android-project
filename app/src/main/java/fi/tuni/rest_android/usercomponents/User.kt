package fi.tuni.rest_android.usercomponents

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * Data class representing a User.
 *
 * @property id The unique identifier of the user.
 * @property firstName The first name of the user.
 * @property lastName The last name of the user.
 * @property username The username of the user.
 * @property age The age of the user.
 * @property email The email address of the user.
 * @property phone The phone number of the user.
 * @property height The height of the user in centimeters.
 * @property weight The weight of the user in kilograms.
 * @property image The image of the user.
 */
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


    /**
     * Updates the attribute of the user based on the provided index and value.
     *
     * @param index The index of the attribute to update.
     * @param value The new value for the attribute.
     */
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
                this.age = value.toIntOrNull()
            }
            4 -> {
                this.email = value
            }
            5 -> {
                this.phone = value
            }
            6 -> {
                this.height = value.toIntOrNull()
            }
            7 -> {
                this.weight = value.toDoubleOrNull()
            }
        }
    }

    /**
     * Returns a string representation of the user.
     *
     * @return A string representing the user's information.
     */
    override fun toString() : String {
        return "${this.firstName} ${this.lastName}\n" +
                "Username: ${this.username}\n" +
                "Age: ${this.age}\n" +
                "Email: ${this.email}\n" +
                "Phone: ${this.phone}\n" +
                "Height: ${this.height} cm\n" +
                "Weight: ${this.weight} kg"
    }

    /**
     * Returns an array of attribute names for the user.
     *
     * @return An array of attribute names.
     */
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

    /**
     * Returns an array of attribute values for the user.
     *
     * @return An array of attribute values.
     */
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