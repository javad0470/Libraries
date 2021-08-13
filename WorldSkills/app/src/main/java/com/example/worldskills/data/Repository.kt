package com.example.worldskills.data

import com.example.worldskills.data.model.CenterData
import com.example.worldskills.data.model.CenterListResponse
import com.example.worldskills.data.model.CenterListResponseItem
import com.example.worldskills.data.network.KtorClient
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(private val ktorClient: KtorClient) {

    // Api Client

    suspend fun sendSms(phone: String): Flow<String> = flow {
        emit(ktorClient.sendSMS(phone))
    }.flowOn(Dispatchers.IO)

    suspend fun getCode(phone: String): Flow<String> = flow {
        emit(ktorClient.getCode(phone))
    }.flowOn(Dispatchers.IO)

    suspend fun login(phone: String, code: String): Flow<LoginResponse> = flow {
        emit(ktorClient.login(phone, code))
    }.flowOn(Dispatchers.IO)

    suspend fun getUserData(phone: String): Flow<UserResponse> = flow {
        emit(ktorClient.getUserData(phone))
    }.flowOn(Dispatchers.IO)

    suspend fun getCentersList(token: String): Flow<List<CenterListResponseItem>> = flow {
        emit(ktorClient.getCentersList(token))
    }.flowOn(Dispatchers.IO)

    suspend fun getCentersData(token: String, id: Int): Flow<CenterData> = flow {
        emit(ktorClient.getCentersData(token, id))
    }.flowOn(Dispatchers.IO)

}