package fi.tuni.rest_android.tools

import android.util.Patterns
import fi.tuni.rest_android.usercomponents.User

/**
 * Utility object containing validation functions for user attributes.
 */
object Validator {
    /**
     * Checks if all fields of a user are valid.
     *
     * @param user The User object to validate.
     * @return `true` if all fields are valid, `false` otherwise.
     */
    fun checkAllFieldsValid(user : User) : Boolean {
        return ((0 until user.getAttributes().size).all { index ->
            validate(user.getAttributes()[index], user.attrToArray()[index])
        })
    }

    /**
     * Validates a specific user attribute based on attribute name.
     *
     * @param indicator The indicator or label of the attribute to validate.
     * @param value The value of the attribute to validate.
     * @return `true` if the attribute is valid, `false` otherwise.
     */
    fun validate(indicator : String, value : String) : Boolean {
        when(indicator) {
            "first name", "last name" -> {
                return validateName(value)
            }
            "username" -> {
                return (value != "")
            }
            "age" -> {
                return validateAge(value)
            }
            "email" -> {
                return validateEmail(value)
            }
            "phone" -> {
                return validatePhone(value)
            }
            "height" -> {
                return validateHeight(value)
            }
            "weight" -> {
                return validateWeight(value)
            }
        }
        return false
    }

    /**
     * Validates a name attribute using regular expression.
     *
     * @param name The name value to validate.
     * @return `true` if the name is valid, `false` otherwise.
     */
    private fun validateName(name : String) : Boolean {
        val regex = Regex("^[A-Za-z\\-']{1,25}+$")
        return name.matches(regex)
    }

    /**
     * Validates an age attribute based on specific range.
     *
     * @param age The age value to validate.
     * @return `true` if the age is valid, `false` otherwise.
     */
    private fun validateAge(age : String) : Boolean {
        val ageInt = age.toIntOrNull() ?: return false
        return (ageInt in 1..149)
    }

    /**
     * Validates an email attribute based on Android's own email pattern.
     *
     * @param email The email value to validate.
     * @return `true` if the email is valid, `false` otherwise.
     */
    private fun validateEmail(email : String) : Boolean {
        return (Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }

    /**
     * Validates a phone attribute based on Android's own phone pattern.
     *
     * @param phone The phone value to validate.
     * @return `true` if the phone is valid, `false` otherwise.
     */
    private fun validatePhone(phone : String) : Boolean {
        return (Patterns.PHONE.matcher(phone).matches())
    }

    /**
     * Validates a height attribute based on specific range.
     *
     * @param height The height value to validate.
     * @return `true` if the height is valid, `false` otherwise.
     */
    private fun validateHeight(height : String) : Boolean {
        val heightInt = height.toIntOrNull() ?: return false
        return (heightInt in 40..300)
    }

    /**
     * Validates a weight attribute based on specific range.
     *
     * @param weight The weight value to validate.
     * @return `true` if the weight is valid, `false` otherwise.
     */
    private fun validateWeight(weight : String) : Boolean {
        val weightDouble = weight.toDoubleOrNull() ?: return false
        return (weightDouble in 3.0..700.0)
    }
}