package com.limeunju.android_netflix.data.util

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResponseAdapterFactory: CallAdapter.Factory() {
    //파라미터로 받은 returnType과 동일한 타입을 반환하는 어댑터 생성
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (Call::class.java != getRawType(returnType)) return null
        check(returnType is ParameterizedType)

        val responseType = getParameterUpperBound(0, returnType)
        if (getRawType(responseType) != Result::class.java) return null
        check(responseType is ParameterizedType)

        val successType = getParameterUpperBound(0, responseType)
        return ResponseAdapter<Any>(successType)
    }
}