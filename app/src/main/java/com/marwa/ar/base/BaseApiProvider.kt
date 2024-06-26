package com.marwa.ar.base


import com.marwa.ar.data.datasource.remote.api.NetworkPayload
import com.marwa.ar.data.datasource.remote.api.NetworkResource
import com.marwa.ar.utils.extensions.toApiErrorBody
import com.marwa.ar.data.datasource.remote.api.NetworkStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

open class BaseApiProvider {
    fun <T> apiRequest(apiCall: suspend () -> Response<T>): Flow<NetworkResource<T>> {
        return flow {
            emit(NetworkResource(status = NetworkStatus.Loading))

            val response = apiCall()

            val networkResource = if (response.isSuccessful && response.code() == 200) {
                NetworkResource(
                    status = NetworkStatus.Success,
                    payload = NetworkPayload.create(response.body())
                )
            } else {
                try {
                    NetworkResource(
                        status = NetworkStatus.Failure,
                        error = BaseException(
                            statusCode = response.code(),
                            message = response.errorBody()?.toApiErrorBody()?.message
                        )

                    )
                } catch (ex: Exception) {
                    NetworkResource(
                        status = NetworkStatus.Failure, error =
                        BaseException(
                            statusCode = response.code(),
                            message = ex.message ?: ""
                        )
                    )
                }
            }

            emit(networkResource)
        }.flowOn(Dispatchers.IO)
    }
}