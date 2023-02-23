package com.limeunju.android_netflix.data.util

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResponseCall<T> (private val callDelegate: Call<T>): Call<Result<T>> {
    override fun enqueue(callback: Callback<Result<T>>)  = callDelegate.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            response.body()?.let {
                when (response.code()) {
                    in 200..299 -> {
                        callback.onResponse(this@ResponseCall, Response.success(Result.success(it)))
                    }
                    in 400..409 -> {
                        callback.onResponse(this@ResponseCall, Response.success(Result.failure(IllegalArgumentException("cannot find"))))
                    }
                }
            } ?: callback.onResponse(this@ResponseCall, Response.success(Result.failure(IllegalArgumentException("no body"))))
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            callback.onResponse(this@ResponseCall, Response.success(Result.failure(t)))
            call.cancel()
        }

    })

    override fun cancel() = callDelegate.cancel()
    override fun clone(): Call<Result<T>> = ResponseCall(callDelegate.clone())
    override fun execute(): Response<Result<T>> {
        throw UnsupportedOperationException("unsupported operation")
    }

    override fun isCanceled(): Boolean = callDelegate.isCanceled
    override fun isExecuted(): Boolean = callDelegate.isExecuted
    override fun request(): Request = callDelegate.request()
    override fun timeout(): Timeout = callDelegate.timeout()
}