package com.mutsuddi_s.stockmarket.util

sealed class Resource<T>(val data:T?=null,val message:String?=null) {
    class Success<T>(data: T?): Resource<T>(data = data)
    class Error<T>(msg: String,data: T?=null): Resource<T>(data,message=msg)
    class Loading<T>(val isLoading:Boolean=true): Resource<T>(null)
}