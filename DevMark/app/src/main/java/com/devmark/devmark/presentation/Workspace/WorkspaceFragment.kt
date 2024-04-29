package com.devmark.devmark.presentation.Workspace

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmark.devmark.R
import com.devmark.devmark.data.BookMark
import com.devmark.devmark.data.Category
import com.devmark.devmark.data.Member
import com.devmark.devmark.databinding.FragmentWorkspaceBinding

class WorkspaceFragment : Fragment() {
    private lateinit var binding: FragmentWorkspaceBinding
    private val bookmarkList = ArrayList<BookMark>()
    private val categoryList = ArrayList<Category>()
    private val memberList = ArrayList<Member>()
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
        binding = FragmentWorkspaceBinding.inflate(layoutInflater)
        binding.btnWorkspaceSetting.setOnClickListener{
            Log.d("TAG", "btn click")
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
                "안드로이드 개발자 로드맵 - Part1: The Android Platform Hello World! Lorem Ipsum",
                "Android"
            )
        )
        bookmarkList.add(BookMark("기업들은 왜 코틀린을 선택하였을까?", "Kotlin"))

        memberList.add(Member("함범준"))
        memberList.add(Member("이성진"))
        memberList.add(Member("문장훈"))
        memberList.add(Member("최건호"))


        categoryList.add(Category("Kotlin"))
        categoryList.add(Category("Android"))
        categoryList.add(Category("Golang"))
        categoryList.add(Category("Python"))
        categoryList.add(Category("부트캠프"))
        categoryList.add(Category("Flutter"))
        categoryList.add(Category("Algorithm"))

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
