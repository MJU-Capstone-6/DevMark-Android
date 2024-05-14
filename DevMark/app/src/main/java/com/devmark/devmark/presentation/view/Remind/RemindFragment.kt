package com.devmark.devmark.presentation.view.remind

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmark.devmark.presentation.view.MainActivity
import com.devmark.devmark.domain.model.Remind
import com.devmark.devmark.databinding.FragmentRemindBinding

class RemindFragment : Fragment() {
    private lateinit var binding: FragmentRemindBinding
    private val remindList = ArrayList<Remind>()
    private lateinit var remindRvAdapter: RemindRvAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // BottomNav 출력
        (requireActivity() as MainActivity).changeNaviVisibility(true)
        binding = FragmentRemindBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        remindList.add(Remind("안드로이드 개발자 로드맵 - Part1: The Android Platform", "Android", "방금"))
        remindList.add(Remind("[Android] NestedScrollView에 대해 알아보자!", "Android", "11분 전"))
        remindList.add(Remind("Kotlin의 미래에 대한 10가지 질문과 답변","Kotlin","1시간 전"))

        remindRvAdapter = RemindRvAdapter()

        binding.rvRemind.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = remindRvAdapter
        }

        // set data
        remindRvAdapter.setData(remindList)
    }
}