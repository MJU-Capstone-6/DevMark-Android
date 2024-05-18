package com.devmark.devmark.presentation.view.workspace_select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmark.devmark.data.utils.LoggerUtils
import com.devmark.devmark.presentation.view.MainActivity
import com.devmark.devmark.databinding.FragmentSelectWorkspaceBinding
import com.devmark.devmark.presentation.utils.UiState
import com.devmark.devmark.presentation.view.setting.SettingFragment


class SelectWorkspaceFragment : Fragment() {
    private lateinit var workSpaceSelectRvAdapter: WorkSpaceSelectRvAdapter
    private var _binding: FragmentSelectWorkspaceBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SelectWorkSpaceViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectWorkspaceBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        initView()
    }

    private fun initView() {
        viewModel.fetchData()

        binding.icNowWorkspace.loItemWorkspace.setOnClickListener {
            (requireActivity() as SelectWorkspaceActivity).moveActivity(MainActivity())
        }

        binding.ibSetting.setOnClickListener {
            (requireActivity() as SelectWorkspaceActivity).replaceFragmentWithBackstack(
                SettingFragment()
            )
        }

        binding.icNowWorkspace.tvWorkspaceName.text = "안드로이드 스터디"
        binding.icNowWorkspace.tvWorkspaceDetail.text = "안드로이드 북마크 모음"
        binding.icNowWorkspace.tvTotalBookmarkNum.text = "2873"
        binding.icNowWorkspace.tvTotalPeopleNum.text = "123"

        workSpaceSelectRvAdapter = WorkSpaceSelectRvAdapter().apply {
            this.setItemClickListener(object : OnMethodClickListener {
                override fun onClickJoin() {
                    WorkspaceJoinDialog().apply {
                        setButtonClickListener(object :
                            WorkspaceJoinDialog.OnButtonClickListener {
                            override fun onButton1Clicked(code: String) {
                                viewModel.workspaceJoin(code)
                            }
                        })
                    }.show(parentFragmentManager, "Workspace Join Dialog")
                }

                override fun onClickCreate() {
                    WorkspaceCreateDialog().apply {
                        setButtonClickListener(object :
                            WorkspaceCreateDialog.OnButtonClickListener {
                            override fun onButton1Clicked(title: String, desc: String) {
                                viewModel.workspaceCreate(title, desc)
                            }
                        })
                    }.show(parentFragmentManager, "Workspace Create Dialog")
                }
            })
        }

        binding.rvWorkspace.apply {
            setHasFixedSize(true)
            this.layoutManager =
                LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
            adapter = workSpaceSelectRvAdapter
        }
    }

    private fun observer() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            when(it) {
                is UiState.Failure -> {
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    LoggerUtils.error("워크스페이스 조회 실패: ${it.error}")
                }

                is UiState.Loading -> {}
                is UiState.Success -> {
                    workSpaceSelectRvAdapter.setData(it.data)
                }
            }
        }

        viewModel.createState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Failure -> {
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    LoggerUtils.error("워크스페이스 생성 실패: ${it.error}")
                }
                is UiState.Loading -> {}
                is UiState.Success -> {
                    workSpaceSelectRvAdapter.addItem(item = it.data)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}