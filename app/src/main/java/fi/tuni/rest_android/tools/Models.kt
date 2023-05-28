package fi.tuni.rest_android.tools

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Class responsible for making HTTP requests to interact with the server.
 */
class Models {
    // Client variable with explicit connection timeout value
    private val client = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .build()

    /**
     * Performs a GET request to the specified URL and invokes
     * the callback function with the response.
     *
     * @param url The URL to send the GET request to.
     * @param callback The callback function to handle the response.
     */
    fun getRequest(url : String, callback : (String) -> Unit) {
        val request = Request.Builder().url(url).build()
        makeRequest(request) {
            callback(it)
        }
    }

    /**
     * Performs a DELETE request to the specified URL and
     * invokes the callback function with a success message.
     *
     * @param url The URL to send the DELETE request to.
     * @param callback The callback function to handle the response.
     */
    fun deleteRequest(url : String, callback: (String) -> Unit) {
        val request = Request.Builder().url(url).delete().build()
        makeRequest(request) {
            callback("User deleted successfully!")
        }
    }

    /**
     * Performs a POST request to add a user with the specified JSON
     * data and invokes the callback function with a success message.
     *
     * @param jsonString The JSON string representing the user data to add.
     * @param callback The callback function to handle the response.
     */
    fun postRequest(jsonString : String, callback: (String) -> Unit) {
        val url = "https://dummyjson.com/users/add"
        val requestBody = jsonString.toRequestBody("application/json".toMediaType())
        val request = Request.Builder().url(url).post(requestBody).build()
        makeRequest(request) {
            callback("User added successfully!")
        }
    }

    /**
     * Performs a PUT request to update a user with the specified JSON
     * data and invokes the callback function with a success message.
     *
     * @param url The URL to send the PUT request to.
     * @param jsonString The JSON string representing the updated user data.
     * @param callback The callback function to handle the response.
     */
    fun putRequest(url : String, jsonString : String, callback: (String) -> Unit) {
        val requestBody = jsonString.toRequestBody("application/json".toMediaType())
        val request = Request.Builder().url(url).put(requestBody).build()
        makeRequest(request) {
            callback("User updated successfully!")
        }
    }

    /**
     * Makes an HTTP request and invokes the callback
     * function with the response.
     *
     * @param request The HTTP request to send.
     * @param callback The callback function to handle the response.
     */
    private fun makeRequest(request : Request, callback: (String) -> Unit) {
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful)
                        throw IOException("Unexpected error happened: $response")
                    callback(response.body!!.string())
                }
            }
        })
    }
}