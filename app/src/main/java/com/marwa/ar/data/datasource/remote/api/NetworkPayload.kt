package com.marwa.ar.data.datasource.remote.api

class NetworkPayload<T> private constructor() {
    var data: T? = null

    companion object {
        fun <T> create(data: T?): NetworkPayload<T> = NetworkPayload<T>().apply {
            this.data = data
        }
    }
}