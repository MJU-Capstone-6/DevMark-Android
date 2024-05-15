package com.devmark.devmark.data.repository

import com.devmark.devmark.data.mapper.workspace.WorkSpaceCreateMapper
import com.devmark.devmark.data.remote.RetrofitClient
import com.devmark.devmark.data.remote.api.WorkSpaceService
import com.devmark.devmark.domain.model.user.WorkspaceEntity
import com.devmark.devmark.domain.model.workspace.RequestWorkSpaceCreateEntity
import com.devmark.devmark.domain.repository.WorkSpaceRepository
import org.json.JSONObject
import java.lang.Exception

class WorkSpaceRepositoryImpl : WorkSpaceRepository {
    private val service = RetrofitClient.getInstance().create(WorkSpaceService::class.java)
    override suspend fun createWorkspace(
        accessToken: String,
        body: RequestWorkSpaceCreateEntity
    ): Result<WorkspaceEntity> {
        val response =
            service.createWorkspace(
                "Bearer $accessToken",
                WorkSpaceCreateMapper.mapperToRequestDTO(body)
            )
        return if (response.isSuccessful) {
            Result.success(WorkSpaceCreateMapper.mapperToResponseEntity(response.body()!!))
        } else {
            val errorMsg = JSONObject(response.errorBody()!!.string()).getString("msg")
            Result.failure(Exception(errorMsg))
        }
    }
}