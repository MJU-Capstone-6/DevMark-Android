package com.devmark.devmark.presentation.view.workspace_select

import android.content.Intent
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
import com.devmark.devmark.presentation.base.GlobalApplication.Companion.app
import com.devmark.devmark.presentation.utils.UiState
import com.devmark.devmark.presentation.view.setting.SettingFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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
        initListener()
        initView()
    }

    private fun initView() {
        viewModel.fetchData()

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
            this.setWorkspaceClickListener(object : OnWorkspaceClickListener {
                override fun onWorkspaceClick(id: Int, name: String, description: String) {
                    CoroutineScope(Dispatchers.IO).launch {
                        app.userPreferences.setCurrentWorkspace(
                            id
                        )
                    }

                    val intent = Intent(requireContext(), MainActivity::class.java).apply {
                        putExtra("WORKSPACE_ID", id)
                        putExtra("WORKSPACE_NAME", name)
                        putExtra("WORKSPACE_DESCRIPTION", description)
                    }
                    startActivity(intent)
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

    private fun initListener() {
        binding.icNowWorkspace.loItemWorkspace.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java).apply {
                putExtra("WORKSPACE_ID", viewModel.currentWorkspace.value?.id)
                putExtra("WORKSPACE_NAME", viewModel.currentWorkspace.value?.name)
                putExtra("WORKSPACE_DESCRIPTION", viewModel.currentWorkspace.value?.description)
            }
            startActivity(intent)
        }

        binding.ibSetting.setOnClickListener {
            (requireActivity() as SelectWorkspaceActivity).replaceFragmentWithBackstack(
                SettingFragment()
            )
        }
    }

    private fun observer() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Failure -> {
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    LoggerUtils.error("워크스페이스 조회 실패: ${it.error}")
                }

                is UiState.Loading -> {}
                is UiState.Success -> {
                    binding.icNowWorkspace.root.visibility =
                        if (viewModel.currentWorkspace.value == null) View.INVISIBLE else View.VISIBLE
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

        viewModel.currentWorkspace.observe(viewLifecycleOwner) {
            if (it.id != -1) {
                binding.icNowWorkspace.root.visibility = View.VISIBLE
                with(binding.icNowWorkspace) {
                    tvWorkspaceName.text = it.name
                    tvWorkspaceDetail.text = it.description
                    tvTotalPeopleNum.text = it.userCount.toString()
                    tvTotalBookmarkNum.text = it.bookmarkCount.toString()
                }
            } else {
                binding.icNowWorkspace.root.visibility = View.INVISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}