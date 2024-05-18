package com.devmark.devmark.data.repository

import com.devmark.devmark.data.mapper.workspace.InviteCodeMapper
import com.devmark.devmark.data.mapper.workspace.JoinWorkspaceMapper
import com.devmark.devmark.data.mapper.workspace.WorkSpaceCreateMapper
import com.devmark.devmark.data.remote.RetrofitClient
import com.devmark.devmark.data.remote.api.WorkSpaceService
import com.devmark.devmark.domain.model.user.WorkspaceEntity
import com.devmark.devmark.domain.model.workspace.RequestWorkSpaceCreateEntity
import com.devmark.devmark.domain.model.workspace.ResponseInviteCodeEntity
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

    override suspend fun getInviteCode(
        accessToken: String,
        workspaceId: Int
    ): Result<ResponseInviteCodeEntity> {
        val response =
            service.getInviteCode(
                "Bearer $accessToken",
                InviteCodeMapper.mapperToRequestDTO(workspaceId)
            )
        return if (response.isSuccessful) {
            Result.success(InviteCodeMapper.mapperToResponseEntity(response.body()!!))
        } else {
            val errorMsg = JSONObject(response.errorBody()!!.string()).getString("msg")
            Result.failure(Exception(errorMsg))
        }
    }

    override suspend fun joinWorkspace(
        accessToken: String,
        inviteCode: String
    ): Result<WorkspaceEntity> {
        val response =
            service.joinWorkspace(
                "Bearer $accessToken",
                JoinWorkspaceMapper.mapperToRequestDTO(inviteCode)
            )
        return if (response.isSuccessful) {
            if(response.body() == null) Result.failure(Exception("null data"))
            else {
                Result.success(JoinWorkspaceMapper.mapperToResponseEntity(response.body()!!))
            }
        } else {
            val errorMsg = JSONObject(response.errorBody()!!.string()).getString("msg")
            Result.failure(Exception(errorMsg))
        }
    }
}