package fi.tuni.rest_android

import okhttp3.*
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