package com.devmark.devmark.data.repository

import com.devmark.devmark.data.mapper.user.LoginMapper
import com.devmark.devmark.data.mapper.user.NotificationMapper
import com.devmark.devmark.data.mapper.user.WorkSpaceListMapper
import com.devmark.devmark.data.mapper.workspace.RecommendPostMapper
import com.devmark.devmark.data.model.user.RequestLoginDTO
import com.devmark.devmark.data.remote.RetrofitClient
import com.devmark.devmark.data.remote.api.UserService
import com.devmark.devmark.data.utils.LoggerUtils
import com.devmark.devmark.domain.model.user.NotificationEntity
import com.devmark.devmark.domain.model.user.ResponseLoginEntity
import com.devmark.devmark.domain.model.user.ResponseWorkSpaceListEntity
import com.devmark.devmark.domain.model.workspace.RecommendPostItem
import com.devmark.devmark.domain.repository.UserRepository
import org.json.JSONObject

class UserRepositoryImpl : UserRepository {
    private val service = RetrofitClient.getInstance().create(UserService::class.java)

    override suspend fun login(
        accessToken: String,
        registrationToken: String
    ): Result<ResponseLoginEntity> {
        val res = service.login("Bearer $accessToken", RequestLoginDTO(registrationToken))

        return if (res.isSuccessful) {
            val data = res.body()!!
            Result.success(LoginMapper.mapperToResponseEntity(data))
        } else {
            val errorMsg = JSONObject(res.errorBody()!!.string()).getString("msg")
            Result.failure(Exception(errorMsg))
        }
    }

    override suspend fun getWorkspaceList(accessToken: String): Result<ResponseWorkSpaceListEntity> {
        val res = service.getWorkspaceList("Bearer $accessToken")
        return if (res.isSuccessful) {
            val data = res.body()
            if (data != null) Result.success(WorkSpaceListMapper.mapperToResponseEntity(data))
            else Result.success(ResponseWorkSpaceListEntity(-1, listOf()))
        } else {
            val errorMsg = JSONObject(res.errorBody()!!.string()).getString("msg")
            Result.failure(Exception(errorMsg))
        }
    }

    override suspend fun getNotificationList(accessToken: String): Result<List<NotificationEntity>> {
        val res = service.getNotificationList("Bearer $accessToken")
        return if (res.isSuccessful) {
            if (res.body() == null) Result.success(listOf())
            else Result.success(NotificationMapper.mapperToResponseEntity(res.body()!!))
        } else {
            val errorMsg = JSONObject(res.errorBody()!!.string()).getString("msg")
            Result.failure(java.lang.Exception(errorMsg))
        }
    }
}