package com.devmark.devmark.presentation.view.recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmark.devmark.data.utils.LoggerUtils
import com.devmark.devmark.presentation.view.MainActivity
import com.devmark.devmark.domain.model.Recommend
import com.devmark.devmark.domain.model.TopCategory
import com.devmark.devmark.databinding.FragmentRecommendBinding
import com.devmark.devmark.domain.model.workspace.RecommendLink
import com.devmark.devmark.presentation.utils.UiState
import com.devmark.devmark.presentation.view.MainViewModel

class RecommendFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: RecommendViewModel by viewModels()
    private val topCategoryList = ArrayList<TopCategory>()
    private lateinit var binding: FragmentRecommendBinding
    private lateinit var recommendAdapter: RecommendRvAdapter
    private lateinit var topCategoryAdapter: TopCategoryLvAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as MainActivity).changeNaviVisibility(true)
        binding = FragmentRecommendBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
        setRecommendPostRv()
        setTopCategoryPostRv()
        viewModel.fetchData(mainViewModel.workspaceId)
    }

    private fun observer(){
        viewModel.uiState.observe(viewLifecycleOwner){
            when (it) {
                is UiState.Failure -> showErrorToast("북마크 정보 조회 실패: ${it.error}")
                is UiState.Loading -> {}
                is UiState.Success -> {
                    recommendAdapter.setData(it.data)
                }
            }
        }
    }

    private fun showErrorToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        LoggerUtils.error(message)
    }

    private fun setRecommendPostRv(){
        recommendAdapter = RecommendRvAdapter()
        recommendAdapter.setData(listOf())

        binding.rvRecommend.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recommendAdapter
        }
    }

    private fun setTopCategoryPostRv(){
        topCategoryList.add(TopCategory("Android", 22))
        topCategoryList.add(TopCategory("Kotlin", 10))
        topCategoryList.add(TopCategory("Golang", 6))
        topCategoryAdapter = TopCategoryLvAdapter(requireContext(), topCategoryList)
        binding.lvTopCategory.apply {
            adapter = topCategoryAdapter
        }
    }
}