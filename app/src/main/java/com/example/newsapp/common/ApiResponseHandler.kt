package com.example.newsapp.common

import com.example.newsapp.common.Configurations.NETWORK_ERROR


abstract class ApiResponseHandler<State, Data>(
    private val response: ApiResult<Data?>,
) {
    private val TAG: String = "AppDebug"

    suspend fun getResult(): Resource<State> {
        return when (response) {
            is ApiResult.GenericError -> {
                Resource.Error(
                    null,
                    UIMessage(" Error: ${response.errorMessage}", UIMessageType.SNACKBAR)
                )
            }

            is ApiResult.NetworkError -> {
                Resource.Error(
                    null,
                    UIMessage(" Error: $NETWORK_ERROR", UIMessageType.SNACKBAR)
                )
            }

            is ApiResult.Success -> {
                if (response.value == null) {
                    Resource.Error(
                        null,
                        UIMessage(" Error: Data is NULL", UIMessageType.SNACKBAR)
                    )
                } else {
                    handleSuccess(resultObj = response.value)
                }
            }
        }
    }

    abstract suspend fun handleSuccess(resultObj: Data): Resource<State>
}
