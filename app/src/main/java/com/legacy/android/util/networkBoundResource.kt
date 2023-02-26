/*
 * Copyright © 2020-2023 Proxidize. All Rights Reserved.
 */
package com.legacy.android.util


/**
 * @param <ResultType>
 * @param <RequestType>
</RequestType></ResultType> */


import androidx.annotation.MainThread
import androidx.lifecycle.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import java.util.concurrent.CancellationException


fun <ResultType, RequestType> networkBoundResource(
    saveCallResult: suspend (RequestType) -> Unit,
    shouldFetch: (ResultType) -> Boolean = { true },
    loadFromDb: () -> LiveData<ResultType>,
    fetch: suspend () -> ApiResponse<RequestType>,
    processResponse: (suspend (ApiSuccessResponse<RequestType>) -> RequestType) = { it.body },
    onFetchFailed: ((ApiErrorResponse<RequestType>) -> Unit)? = null
): LiveData<Resource<ResultType>> {
    return CoroutineNetworkBoundResource(
        saveCallResult = saveCallResult,
        shouldFetch = shouldFetch,
        loadFromDb = loadFromDb,
        fetch = fetch,
        processResponse = processResponse,
        onFetchFailed = onFetchFailed
    ).asLiveData().distinctUntilChanged()
}

/**
 * A [NetworkBoundResource] implementation in corotuines
 */
private class CoroutineNetworkBoundResource<ResultType, RequestType>
@MainThread constructor(
    private val saveCallResult: suspend (RequestType) -> Unit,
    private val shouldFetch: (ResultType) -> Boolean = { true },
    private val loadFromDb: () -> LiveData<ResultType>,
    private val fetch: suspend () -> ApiResponse<RequestType>,
    private val processResponse: (suspend (ApiSuccessResponse<RequestType>) -> RequestType),
    private val onFetchFailed: ((ApiErrorResponse<RequestType>) -> Unit)?
) {
    @ExperimentalCoroutinesApi
    private val result = liveData<Resource<ResultType>> {
        if (latestValue?.status != Status.SUCCESS) {
            emit(Resource.loading(latestValue?.data))
        }
        val dbSource = loadFromDb()
        val initialValue = dbSource.await()
        val willFetch = initialValue == null || shouldFetch(initialValue)
        if (!willFetch) {
            emitSource(dbSource.map {
                Resource.success(it)
            })
        } else {
            doFetch(dbSource, this)
        }
    }

    private suspend fun doFetch(
        dbSource: LiveData<ResultType>,
        liveDataScope: LiveDataScope<Resource<ResultType>>
    ) {
        val initialSource = liveDataScope.emitSource(dbSource.map {
            Resource.loading(it)
        })
        when (val response = fetchCatching()) {
            is ApiSuccessResponse, is ApiEmptyResponse -> {
                if (response is ApiSuccessResponse) {
                    val processed = processResponse(response)
                    initialSource.dispose()
                    saveCallResult(processed)
                }
                liveDataScope.emitSource(loadFromDb().map {
                    Resource.success(it)
                })
            }
            is ApiErrorResponse -> {
                onFetchFailed?.invoke(response)
                liveDataScope.emitSource(dbSource.map {
                    Resource.error(response.errorMessage, it)
                })
            }
        }
    }

    fun asLiveData() = result

    private suspend fun fetchCatching(): ApiResponse<RequestType> {
        return try {
            fetch()
        } catch (ex: CancellationException) {
            throw ex
        } catch (ex: Throwable) {
            ApiResponse.create(ex)
        }
    }

    private suspend fun <T> LiveData<T>.await() = withContext(Dispatchers.Main) {
        val receivedValue = CompletableDeferred<T?>()
        val observer = Observer<T> {
            if (receivedValue.isActive) {
                receivedValue.complete(it)
            }
        }
        try {
            observeForever(observer)
            return@withContext receivedValue.await()
        } finally {
            removeObserver(observer)
        }
    }
}