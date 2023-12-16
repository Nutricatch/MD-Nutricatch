package com.nutricatch.dev.data


/*
* File ini digunakan untuk handle result dari api
* */
sealed class ResultState<out R> private constructor() {
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error(val error: String, val errorCode: Int? = null) : ResultState<Nothing>()
    data object Loading : ResultState<Nothing>()
}
