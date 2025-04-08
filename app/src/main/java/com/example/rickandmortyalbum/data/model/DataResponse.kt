package com.example.rickandmortyalbum.data.model

data class DataResponse<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): DataResponse<T> {
            return DataResponse(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): DataResponse<T> {
            return DataResponse(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): DataResponse<T> {
            return DataResponse(Status.LOADING, data, null)
        }
    }
}
