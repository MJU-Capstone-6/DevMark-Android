package com.devmark.devmark.presentation.view.Recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmark.devmark.presentation.view.MainActivity
import com.devmark.devmark.domain.model.Recommend
import com.devmark.devmark.domain.model.TopCategory
import com.devmark.devmark.databinding.FragmentRecommendBinding

class RecommendFragment : Fragment() {
    private val recommendList = ArrayList<Recommend>()
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

        topCategoryList.add(TopCategory("Android", 22))
        topCategoryList.add(TopCategory("Kotlin", 10))
        topCategoryList.add(TopCategory("Golang", 6))
        topCategoryAdapter = TopCategoryLvAdapter(requireContext(), topCategoryList)
        binding.lvTopCategory.apply {
            adapter = topCategoryAdapter
        }

        // 데이터 추가
        recommendList.add(
            Recommend(
                "[Android] 깔쌈하게 MVVM 패턴과 AAC 알아보기",
                "Android"
            )
        )
        recommendList.add(Recommend("메타는 왜 안드로이드 앱을 코틀린으로 다시 짜나", "Android"))
        recommendList.add(Recommend("우아한테크세미나 코프링 정리", "Kotlin"))

        // recyclerview adapter
        recommendAdapter = RecommendRvAdapter()

        // set up recyclerview
        binding.rvRecommend.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recommendAdapter
        }

        // set data
        recommendAdapter.setData(recommendList)
    }
}