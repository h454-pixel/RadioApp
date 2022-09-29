package com.example.radioapp.Repository

import com.example.radioapp.Model.ListCountry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.example.radioapp.util.NetworkResult
import com.example.radioapp.Model.ListRadio
import com.example.radioapp.Model.ListRecommed
import com.example.radioapp.util.BaseApiResponse
import kotlinx.coroutines.flow.flowOn


class RadioRepository @Inject constructor(private val remoteSource: RemoteSource): BaseApiResponse() {

    suspend fun getRadiolist(
        cc_key: String,
        lc: String,
        c_code: String,
        curentpage: String,
    ): Flow<NetworkResult<ListRadio>> {
        return flow {
            emit(NetworkResult.Loading())
            emit(safeApiCall {
               remoteSource.getRadiolist(
                   cc_key,
                   lc,
                   c_code,
                   curentpage,
                )
            })
        }.flowOn(Dispatchers.IO)
    }


    suspend fun getcountrylist(
        cc: String,
        lc: String,
        ): Flow<NetworkResult<ListCountry>> {
        return flow {
            emit(NetworkResult.Loading())
            emit(safeApiCall {
                remoteSource.getcountrylist(
                    cc,
                    lc,
                    )
            })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getlistrecommed(): Flow<NetworkResult<ListRecommed>> {
        return flow {
            emit(NetworkResult.Loading())
            emit(safeApiCall {
                remoteSource.getlistrecommed()
            })
        }.flowOn(Dispatchers.IO)
    }
}