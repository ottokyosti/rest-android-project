package fi.tuni.rest_android.tools

import android.util.Patterns
import fi.tuni.rest_android.usercomponents.User

object Validator {
    fun checkAllFieldsValid(user : User) : Boolean {
        return ((0 until user.getAttributes().size).all { index ->
            validate(user.getAttributes()[index], user.attrToArray()[index])
        })
    }

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

    private fun validateName(name : String) : Boolean {
        val regex = Regex("^[A-Za-z\\-']{1,25}+$")
        return name.matches(regex)
    }

    private fun validateAge(age : String) : Boolean {
        val ageInt = age.toIntOrNull() ?: return false
        return (ageInt in 1..149)
    }

    private fun validateEmail(email : String) : Boolean {
        return (Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }

    private fun validatePhone(phone : String) : Boolean {
        return (Patterns.PHONE.matcher(phone).matches())
    }

    private fun validateHeight(height : String) : Boolean {
        val heightInt = height.toIntOrNull() ?: return false
        return (heightInt in 40..300)
    }

    private fun validateWeight(weight : String) : Boolean {
        val weightDouble = weight.toDoubleOrNull() ?: return false
        return (weightDouble in 3.0..700.0)
    }
}