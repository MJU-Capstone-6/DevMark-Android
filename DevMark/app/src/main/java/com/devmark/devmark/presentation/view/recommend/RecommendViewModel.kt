package com.devmark.devmark.presentation.view.recommend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmark.devmark.data.repository.WorkSpaceRepositoryImpl
import com.devmark.devmark.domain.model.workspace.RealRecommendPost
import com.devmark.devmark.domain.model.workspace.RecommendLink
import com.devmark.devmark.domain.model.workspace.RecommendPostItem
import com.devmark.devmark.presentation.base.GlobalApplication.Companion.app
import com.devmark.devmark.presentation.utils.UiState
import kotlinx.coroutines.launch

class RecommendViewModel: ViewModel() {
    private val repositoryImpl = WorkSpaceRepositoryImpl()

    private var _uiState = MutableLiveData<UiState<List<RealRecommendPost>>>(UiState.Loading)
    val uiState get() = _uiState

    fun fetchData(workspaceId: Int){
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            repositoryImpl.getRecommendPost(app.userPreferences.getAccessToken().getOrNull().orEmpty(), workspaceId)
                .onSuccess {
                    val result = mutableListOf<RealRecommendPost>()

                    val tmp = it.listIterator()
                    while(tmp.hasNext()){
                        val postItem = tmp.next()
                        result.addAll(postItem.recommendLinks.map { RealRecommendPost(link=it.link, title=it.title, categoryName = postItem.name) })
                    }

                    _uiState.value = UiState.Success(result)
                }
                .onFailure {
                    _uiState.value = UiState.Failure(it.message)
                }
        }
    }
}