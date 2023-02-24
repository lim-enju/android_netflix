package com.limeunju.android_netflix.data.util

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ResponseAdapter<T>(private val successType: Type): CallAdapter<T, Call<Result<T>>> {
    //반환값으로 지정할 타입
    //Call<ResponseSearchMovie>가 인터페이스 리턴값이면 responseType() 은 ResponseSearchMovie를 반환함
    override fun responseType(): Type = successType
    override fun adapt(call: Call<T>): Call<Result<T>> {
        return ResponseCall(call)
    }
}