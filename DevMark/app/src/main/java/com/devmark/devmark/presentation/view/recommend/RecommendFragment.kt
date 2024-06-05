package com.devmark.devmark.presentation.view.recommend

import android.content.Intent
import android.net.Uri
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
import com.devmark.devmark.databinding.FragmentRecommendBinding
import com.devmark.devmark.domain.model.workspace.RealRecommendPost
import com.devmark.devmark.presentation.utils.UiState
import com.devmark.devmark.presentation.view.MainViewModel

class RecommendFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: RecommendViewModel by viewModels()
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
        viewModel.fetchTopCategoryData(mainViewModel.workspaceId)
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

        viewModel.categoryState.observe(viewLifecycleOwner){
            when (it) {
                is UiState.Failure -> showErrorToast("북마크 정보 조회 실패: ${it.error}")
                is UiState.Loading -> {}
                is UiState.Success -> {
                    topCategoryAdapter.setData(it.data)
                }
            }
        }
    }

    private fun showErrorToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        LoggerUtils.error(message)
    }

    private fun setRecommendPostRv(){
        recommendAdapter = RecommendRvAdapter().apply {
            setRecommendPostClickListener(object : RecommendRvAdapter.OnRecommendClickListener{
                override fun onClick(item: RealRecommendPost) {
                    if(item.link.isNotEmpty()) moveInternet(item.link)
                }
            })
        }
        recommendAdapter.setData(listOf())

        binding.rvRecommend.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recommendAdapter
        }
    }

    private fun setTopCategoryPostRv(){
        topCategoryAdapter = TopCategoryLvAdapter(requireContext(), listOf())
        binding.lvTopCategory.apply {
            adapter = topCategoryAdapter
        }
    }

    private fun moveInternet(link: String){
        val address = Uri.parse(link)
        val intent = Intent(Intent.ACTION_VIEW, address)
        startActivity(intent)
    }
}