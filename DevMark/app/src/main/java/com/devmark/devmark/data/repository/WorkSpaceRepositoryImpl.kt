package com.devmark.devmark.data.repository

import com.devmark.devmark.data.mapper.bookmark.BookmarkCodeMapper
import com.devmark.devmark.data.mapper.workspace.InviteCodeMapper
import com.devmark.devmark.data.mapper.workspace.JoinWorkspaceMapper
import com.devmark.devmark.data.mapper.workspace.RecommendPostMapper
import com.devmark.devmark.data.mapper.workspace.WorkSpaceCreateMapper
import com.devmark.devmark.data.mapper.workspace.WorkSpaceInfoMapper
import com.devmark.devmark.data.mapper.workspace.WorkSpaceSettingInfoMapper
import com.devmark.devmark.data.remote.RetrofitClient
import com.devmark.devmark.data.remote.api.WorkSpaceService
import com.devmark.devmark.domain.model.bookmark.ResponseBookmarkCodeEntity
import com.devmark.devmark.domain.model.user.WorkspaceEntity
import com.devmark.devmark.domain.model.workspace.RecommendPostItem
import com.devmark.devmark.domain.model.workspace.RequestWorkSpaceCreateEntity
import com.devmark.devmark.domain.model.workspace.ResponseInviteCodeEntity
import com.devmark.devmark.domain.model.workspace.ResponseWorkspaceSettingInfoEntity
import com.devmark.devmark.domain.model.workspace.WorkSpaceInfoEntity
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
            if (response.body() != null) Result.success(
                WorkSpaceCreateMapper.mapperToResponseEntity(
                    response.body()!!
                )
            )
            else Result.failure(Exception("null data"))
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
    ): Result<Boolean> {
        val response =
            service.joinWorkspace(
                "Bearer $accessToken",
                JoinWorkspaceMapper.mapperToRequestDTO(inviteCode)
            )
        return if (response.isSuccessful) {
            if (response.body() == null) {
                Result.failure(Exception("null data"))
            } else {
                Result.success(response.body()!!.ok)
            }
        } else {
            val errorMsg = JSONObject(response.errorBody()!!.string()).getString("msg")
            Result.failure(Exception(errorMsg))
        }
    }

    override suspend fun getWorkspaceInfo(
        accessToken: String,
        workspaceId: Int
    ): Result<WorkSpaceInfoEntity> {
        val response =
            service.getWorkspaceInfo(
                "Bearer $accessToken",
                workspaceId
            )
        return if (response.isSuccessful) {
            if (response.body() != null) Result.success(
                WorkSpaceInfoMapper.mapperToResponseEntity(
                    response.body()!!
                )
            )
            else Result.failure(Exception("null data"))
        } else {
            val errorMsg = JSONObject(response.errorBody()!!.string()).getString("msg")
            Result.failure(Exception(errorMsg))
        }
    }

    override suspend fun deleteWorkspace(accessToken: String, workspaceId: Int): Result<Boolean> {
        val response =
            service.deleteWorkspace(
                "Bearer $accessToken",
                workspaceId
            )
        return if (response.isSuccessful) {
            Result.success(true)
        } else {
            val errorMsg = JSONObject(response.errorBody()!!.string()).getString("msg")
            Result.failure(Exception(errorMsg))
        }
    }

    override suspend fun getBookmarkCode(
        accessToken: String,
        workspaceId: Int
    ): Result<ResponseBookmarkCodeEntity> {
        val response =
            service.getBookmarkCode(
                "Bearer $accessToken",
                workspaceId
            )
        return if (response.isSuccessful) {
            Result.success(BookmarkCodeMapper.mapperToResponseEntity(response.body()!!))
        } else {
            val errorMsg = JSONObject(response.errorBody()!!.string()).getString("msg")
            Result.failure(Exception(errorMsg))
        }
    }

    override suspend fun getWorkspaceSettingInfo(
        accessToken: String,
        workspaceId: Int
    ): Result<ResponseWorkspaceSettingInfoEntity> {
        val response =
            service.getWorkspaceSettingInfo(
                "Bearer $accessToken",
                workspaceId
            )
        return if (response.isSuccessful) {
            Result.success(WorkSpaceSettingInfoMapper.mapperToResponseEntity(response.body()!!))
        } else {
            val errorMsg = JSONObject(response.errorBody()!!.string()).getString("msg")
            Result.failure(Exception(errorMsg))
        }
    }

    override suspend fun getRecommendPost(
        accessToken: String,
        workspaceId: Int
    ): Result<List<RecommendPostItem>> {
        val response =
            service.getRecommendPost(
                "Bearer $accessToken",
                workspaceId
            )
        return if (response.isSuccessful) {
            if(response.body() == null) Result.success(listOf())
            else Result.success(RecommendPostMapper.mapperToResponseEntity(response.body()!!))
        } else {
            val errorMsg = JSONObject(response.errorBody()!!.string()).getString("msg")
            Result.failure(Exception(errorMsg))
        }
    }
}