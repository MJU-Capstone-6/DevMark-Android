package com.devmark.devmark.presentation.Workspace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmark.devmark.MainActivity
import com.devmark.devmark.R
import com.devmark.devmark.data.BookMark
import com.devmark.devmark.databinding.FragmentWorkspaceBinding
import com.devmark.devmark.presentation.WorkspaceSetting.WorkspaceSettingFragment

class WorkspaceFragment : Fragment() {
    private lateinit var binding: FragmentWorkspaceBinding
    private val bookmarkList = ArrayList<BookMark>()
    private val categoryList = ArrayList<String>()
    private val memberList = ArrayList<String>()
    private val categoryFilterList = mutableListOf<String>()
    private val memberFilterList = mutableListOf<String>()
    private lateinit var workSpaceRvAdapter: WorkSpaceRvAdapter
    private lateinit var categoryRvAdapter: CategoryRvAdapter
    private lateinit var memberRvAdapter: MemberRvAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as MainActivity).changeNaviVisibility(true)
        binding = FragmentWorkspaceBinding.inflate(layoutInflater)

        binding.btnWorkspaceSetting.setOnClickListener {
            // 워크스페이스 관리 페이지로
            (requireActivity() as MainActivity).replaceFragmentWithBackstack(
                WorkspaceSettingFragment()
            )
        }
        binding.ibBackWorkspace.setOnClickListener {
            // 이전 화면으로 뒤로가기
            (requireActivity() as MainActivity).backToSelectWorkspaceActivity()
            requireActivity().finish()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFilter.visibility = View.GONE
        binding.rvFilter.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // 데이터 추가
        bookmarkList.add(
            BookMark(
                "안드로이드 개발자 로드맵 - Part1: The Android Platform Hello World! 안드로이드 마스터를 해보자~",
                "Android"
            )
        )
        bookmarkList.add(BookMark("기업들은 왜 코틀린을 선택하였을까?", "Kotlin"))

        memberList.add("문장훈")
        memberList.add("이성진")
        memberList.add("최건호")
        memberList.add("함범준")

        categoryList.add("Kotlin")
        categoryList.add("Android")
        categoryList.add("Golang")
        categoryList.add("Python")
        categoryList.add("부트캠프")
        categoryList.add("Flutter")
        categoryList.add("Algorithm")

        // recyclerview adapter
        workSpaceRvAdapter = WorkSpaceRvAdapter()

        // set up recyclerview
        binding.rvBookmark.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = workSpaceRvAdapter
        }

        // set data
        workSpaceRvAdapter.setData(bookmarkList)

        binding.tvFilterSort.setOnClickListener {
            // 최신순 정렬 아이콘 변경
            binding.tvFilterSort.isSelected = !binding.tvFilterSort.isSelected
        }

        binding.tvFilterMember.setOnClickListener {
            val newColor =
                if (!binding.tvFilterMember.isSelected) {
                    binding.tvFilterCategory.isSelected = false
                    binding.tvFilterCategory.setTextColor(requireContext().getColor(R.color.rust))

                    binding.rvFilter.visibility = View.VISIBLE
                    requireContext().getColor(R.color.alabaster)
                } else {
                    binding.rvFilter.visibility = View.GONE
                    requireContext().getColor(R.color.rust)
                }
            binding.tvFilterMember.isSelected = !binding.tvFilterMember.isSelected
            binding.tvFilterMember.setTextColor(newColor)

            setMemberRv()
            memberRvAdapter.setData(memberList)
        }

        binding.tvFilterCategory.setOnClickListener {
            val newColor =
                if (!binding.tvFilterCategory.isSelected) {
                    binding.tvFilterMember.isSelected = false
                    binding.tvFilterMember.setTextColor(requireContext().getColor(R.color.rust))

                    binding.rvFilter.visibility = View.VISIBLE
                    requireContext().getColor(R.color.alabaster)
                } else {
                    binding.rvFilter.visibility = View.GONE
                    requireContext().getColor(R.color.rust)
                }
            binding.tvFilterCategory.isSelected = !binding.tvFilterCategory.isSelected
            binding.tvFilterCategory.setTextColor(newColor)

            setCategoryRv()
            categoryRvAdapter.setData(categoryList)
        }
    }

    private fun setCategoryRv() {
        categoryRvAdapter = CategoryRvAdapter(categoryFilterList).apply {
            this.setItemClickListener(object : OnItemClickListener {
                override fun onClick(name: String) {
                    if (categoryFilterList.contains(name)) {
                        categoryFilterList.remove(name)
                    } else {
                        categoryFilterList.add(name)
                    }
                }
            })
        }
        binding.rvFilter.adapter = categoryRvAdapter
    }

    private fun setMemberRv() {
        memberRvAdapter = MemberRvAdapter(memberFilterList).apply {
            this.setItemClickListener(object : OnItemClickListener {
                override fun onClick(name: String) {
                    if (memberFilterList.contains(name)) {
                        memberFilterList.remove(name)
                    } else {
                        memberFilterList.add(name)
                    }
                }
            })
        }
        binding.rvFilter.adapter = memberRvAdapter
    }

}
