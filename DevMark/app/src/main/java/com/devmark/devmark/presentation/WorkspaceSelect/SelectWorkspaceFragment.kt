package com.devmark.devmark.presentation.WorkspaceSelect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmark.devmark.MainActivity
import com.devmark.devmark.data.WorkSpace
import com.devmark.devmark.databinding.FragmentSelectWorkspaceBinding
import com.devmark.devmark.presentation.Setting.SettingFragment

class SelectWorkspaceFragment : Fragment() {
    private lateinit var workSpaceSelectRvAdapter: WorkSpaceSelectRvAdapter
    private lateinit var binding: FragmentSelectWorkspaceBinding
    private val workspaceList = ArrayList<WorkSpace>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectWorkspaceBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        workspaceList.add(WorkSpace("컴퓨터 아키텍처 품앗이", "컴퓨터 아키텍처에 대해", 18, 4))
        workspaceList.add(WorkSpace("Django 부수기", "Django 심화 스터디", 22, 5))
        workspaceList.add(WorkSpace("디자인 관련", "디자인 관련 사이트 모음", 39, 8))
        workspaceList.add(WorkSpace("Spring 모음", "백엔드 스터디", 21, 4))

        // recyclerview adapter
        workSpaceSelectRvAdapter = WorkSpaceSelectRvAdapter()

        // set up recyclerview
        binding.rvWorkspace.apply {
            setHasFixedSize(true)
            this.layoutManager =
                LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            adapter = workSpaceSelectRvAdapter

        }
        // set data
        workSpaceSelectRvAdapter.setData(list = workspaceList)
    }

}