package fi.tuni.rest_android.tools

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit

class Models {
    private val client = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .build()

    fun getRequest(url : String, callback : (String) -> Unit) {
        val request = Request.Builder().url(url).build()
        makeRequest(request) {
            callback(it)
        }
    }
    fun deleteRequest(url : String, callback: (String) -> Unit) {
        val request = Request.Builder().url(url).delete().build()
        makeRequest(request) {
            callback("User deleted successfully!")
        }
    }

    fun postRequest(jsonString : String, callback: (String) -> Unit) {
        val url = "https://dummyjson.com/users/add"
        val requestBody = jsonString.toRequestBody("application/json".toMediaType())
        val request = Request.Builder().url(url).post(requestBody).build()
        makeRequest(request) {
            callback("User added successfully!")
        }
    }

    fun putRequest(url : String, jsonString : String, callback: (String) -> Unit) {
        val requestBody = jsonString.toRequestBody("application/json".toMediaType())
        val request = Request.Builder().url(url).put(requestBody).build()
        makeRequest(request) {
            callback("User updated successfully!")
        }
    }

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