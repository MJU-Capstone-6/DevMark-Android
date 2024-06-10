package com.devmark.devmark.presentation.view.workspace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmark.devmark.presentation.view.MainActivity
import com.devmark.devmark.R
import com.devmark.devmark.data.utils.LoggerUtils
import com.devmark.devmark.databinding.FragmentWorkspaceBinding
import com.devmark.devmark.presentation.utils.UiState
import com.devmark.devmark.presentation.view.MainViewModel
import com.devmark.devmark.presentation.view.bookmark.BookmarkFragment
import com.devmark.devmark.presentation.view.setting.SettingFragment
import com.devmark.devmark.presentation.view.workspace_setting.WorkspaceSettingFragment

class WorkspaceFragment : Fragment() {
    private lateinit var binding: FragmentWorkspaceBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: WorkspaceViewModel by viewModels()
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
        val workspaceName = requireActivity().intent.getStringExtra("WORKSPACE_NAME")
        binding.tbWorkspaceTitle.text = workspaceName.toString()

        observer()
        initListener()
        return binding.root
    }

    private fun initListener() {
        binding.btnWorkspaceSetting.setOnClickListener {
            (requireActivity() as MainActivity).replaceFragmentWithBackstack(
                WorkspaceSettingFragment(mainViewModel.workspaceId)
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
            workSpaceRvAdapter.reverseList()
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
            memberRvAdapter.setData(mainViewModel.memberList)
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
            categoryRvAdapter.setData(mainViewModel.categoryList)
        }
    }

    private fun observer() {
        mainViewModel.filterState.observe(viewLifecycleOwner) {
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
        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Failure -> {
                    LoggerUtils.error(it.error.toString())
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                }

                is UiState.Loading -> {}
                is UiState.Success -> {
                    workSpaceRvAdapter.setData(it.data)
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
        mainViewModel.fetchInfo()
        viewModel.fetchData(memberFilterList, categoryFilterList)
    }

    private fun setBookmarkRv() {
        workSpaceRvAdapter = WorkSpaceRvAdapter().apply {
            this.setItemClickListener(object : OnItemClickListener {
                override fun onClick(id: Int) {
                    (requireActivity() as MainActivity).replaceFragmentWithBackstack(
                        BookmarkFragment(id)
                    )
                }
            })
        }

        binding.rvBookmark.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = workSpaceRvAdapter
        }

        workSpaceRvAdapter.setData(listOf())
    }

    private fun setCategoryRv() {
        categoryRvAdapter = CategoryRvAdapter(categoryFilterList).apply {
            this.setItemClickListener(object : OnItemClickListener {
                override fun onClick(item: Int) {
                    if (categoryFilterList.contains(item)) {
                        categoryFilterList.remove(item)
                    } else {
                        categoryFilterList.add(item)
                    }
                    viewModel.fetchData(memberFilterList, categoryFilterList)
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
                    viewModel.fetchData(memberFilterList, categoryFilterList)
                }
            })
        }
        binding.rvFilter.adapter = memberRvAdapter
    }
}