package com.devmark.devmark.data.repository

import com.devmark.devmark.data.mapper.user.LoginMapper
import com.devmark.devmark.data.model.user.ResponseLoginDTO
import com.devmark.devmark.data.remote.RetrofitClient
import com.devmark.devmark.data.remote.api.UserService
import com.devmark.devmark.domain.model.user.ResponseLoginEntity
import com.devmark.devmark.domain.repository.UserRepository
import org.json.JSONObject

class UserRepositoryImpl: UserRepository {
    private val service = RetrofitClient.getInstance().create(UserService::class.java)

    override suspend fun login(accessToken: String): Result<ResponseLoginEntity> {
        val res = service.login("Bearer $accessToken")

        return if(res.isSuccessful){
            val data = res.body()!!
            Result.success(LoginMapper.mapperToResponseEntity(ResponseLoginDTO(data.accessToken , data.refreshToken)))
        } else {
            val errorMsg = JSONObject(res.errorBody()!!.string()).getString("msg")
            Result.failure(Exception(errorMsg))
        }
    }
}