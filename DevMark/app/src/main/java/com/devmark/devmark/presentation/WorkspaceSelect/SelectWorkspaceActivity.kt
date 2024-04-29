package com.devmark.devmark.presentation.WorkspaceSelect

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmark.devmark.MainActivity
import com.devmark.devmark.R
import com.devmark.devmark.data.WorkSpace
import com.devmark.devmark.databinding.ActivitySelectWorkspaceBinding

class SelectWorkspaceActivity : AppCompatActivity() {
    private lateinit var workSpaceSelectRvAdapter: WorkSpaceSelectRvAdapter
    private lateinit var binding: ActivitySelectWorkspaceBinding
    private val workspaceList = ArrayList<WorkSpace>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_workspace)
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
                    this@SelectWorkspaceActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            adapter = workSpaceSelectRvAdapter

        }
        // set data
        workSpaceSelectRvAdapter.setData(list = workspaceList)

        binding.icNowWorkspace.loItemWorkspace.setOnClickListener {
            moveActivity(MainActivity())
        }
    }
    private fun moveActivity(p: Activity) {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, p::class.java)
            startActivity(intent)
        }, 10)
    }
}