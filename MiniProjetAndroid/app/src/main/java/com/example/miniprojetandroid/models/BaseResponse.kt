package com.example.miniprojetandroid.models

class BaseResponse<T> {


    var errorCode = 0
    var errorMsg: String? = null
    var data: T? = null
        private set

    fun setData(data: T) {
        this.data = data
    }
}