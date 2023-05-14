package fi.tuni.rest_android

import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

class Models {
    private val client = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .build()

    fun getData(url : String, callback : (String) -> Unit) {
        val request = Request.Builder().url(url).build()
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