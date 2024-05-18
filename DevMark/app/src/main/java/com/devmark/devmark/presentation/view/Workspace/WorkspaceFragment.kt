package com.devmark.devmark.presentation.view.workspace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmark.devmark.presentation.view.MainActivity
import com.devmark.devmark.R
import com.devmark.devmark.data.utils.LoggerUtils
import com.devmark.devmark.domain.model.BookMark
import com.devmark.devmark.databinding.FragmentWorkspaceBinding
import com.devmark.devmark.presentation.utils.UiState
import com.devmark.devmark.presentation.view.MainViewModel
import com.devmark.devmark.presentation.view.bookmark.BookmarkFragment
import com.devmark.devmark.presentation.view.setting.SettingFragment
import com.devmark.devmark.presentation.view.workspace_setting.WorkspaceSettingFragment

class WorkspaceFragment : Fragment() {
    private lateinit var binding: FragmentWorkspaceBinding
    private val viewModel: MainViewModel by activityViewModels()
    private val bookmarkList = ArrayList<BookMark>()
    private val categoryFilterList = mutableListOf<Int>()
    private val memberFilterList = mutableListOf<Int>()
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
        observer()
        initListener()
        return binding.root
    }

    private fun initListener() {
        binding.btnWorkspaceSetting.setOnClickListener {
            (requireActivity() as MainActivity).replaceFragmentWithBackstack(
                WorkspaceSettingFragment(1) // 진짜 값 넣어야 함
            )
        }

        binding.ibSetting.setOnClickListener {
            (requireActivity() as MainActivity).replaceFragmentWithBackstack(
                SettingFragment()
            )
        }

        binding.ibBackWorkspace.setOnClickListener {
            (requireActivity() as MainActivity).backToSelectWorkspaceActivity()
            requireActivity().finish()
        }

        binding.tvFilterSort.setOnClickListener {
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
            memberRvAdapter.setData(viewModel.memberList)
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
            categoryRvAdapter.setData(viewModel.categoryList)
        }
    }

    private fun observer() {
        viewModel.filterState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Failure -> {
                    LoggerUtils.error(it.error.toString())
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                }

                is UiState.Loading -> {}
                is UiState.Success -> {
                    categoryRvAdapter.setData(it.data.first)
                    memberRvAdapter.setData(it.data.second)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFilter.visibility = View.GONE
        binding.rvFilter.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        setBookmarkRv()
        setCategoryRv()
        setMemberRv()
        viewModel.fetchInfo()
    }

    private fun setBookmarkRv(){
        workSpaceRvAdapter = WorkSpaceRvAdapter().apply {
            this.setItemClickListener(object : OnItemClickListener{
                override fun onClick(item: Int) {
                    (requireActivity() as MainActivity).replaceFragmentWithBackstack(BookmarkFragment(item))
                }
            })
        }

        binding.rvBookmark.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = workSpaceRvAdapter
        }

        bookmarkList.add(BookMark("Test", "Test")) // todo 이후 제거 필요

        workSpaceRvAdapter.setData(bookmarkList)
    }

    private fun setCategoryRv() {
        categoryRvAdapter = CategoryRvAdapter(categoryFilterList).apply {
            this.setItemClickListener(object : OnItemClickListener {
                override fun onClick(id: Int) {
                    if (categoryFilterList.contains(id)) {
                        categoryFilterList.remove(id)
                    } else {
                        categoryFilterList.add(id)
                    }
                }
            })
        }
        binding.rvFilter.adapter = categoryRvAdapter
    }

    private fun setMemberRv() {
        memberRvAdapter = MemberRvAdapter(memberFilterList).apply {
            this.setItemClickListener(object : OnItemClickListener {
                override fun onClick(id: Int) {
                    if (memberFilterList.contains(id)) {
                        memberFilterList.remove(id)
                    } else {
                        memberFilterList.add(id)
                    }
                }
            })
        }
        binding.rvFilter.adapter = memberRvAdapter
    }
}
