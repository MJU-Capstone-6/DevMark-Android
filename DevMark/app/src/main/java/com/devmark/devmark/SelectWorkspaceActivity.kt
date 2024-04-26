package com.devmark.devmark

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmark.devmark.data.WorkSpace
import com.devmark.devmark.databinding.ActivitySelectWorkspaceBinding

class SelectWorkspaceActivity : AppCompatActivity() {
    private lateinit var multiViewAdapter: MultiviewAdapter
    private lateinit var binding: ActivitySelectWorkspaceBinding
    private val workspaceList = ArrayList<WorkSpace>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_workspace)
        binding.icNowWorkspace.tvWorkspaceName.text = "안드로이드 스터디0"
        binding.icNowWorkspace.tvWorkspaceDetail.text = "안드로이드 북마크설명"
        binding.icNowWorkspace.tvTotalBookmarkNum.text = "2873"
        binding.icNowWorkspace.tvTotalPeopleNum.text = "123"

        workspaceList.add(WorkSpace("안드로이드 스터디", "안드로이드 스터디1", 120, 4))
        workspaceList.add(WorkSpace("안드로이드 스터디", "안드로이드 스터디2", 121, 5))
        workspaceList.add(WorkSpace("안드로이드 스터디", "안드로이드 스터디3", 122, 6))
        workspaceList.add(WorkSpace("안드로이드 스터디", "안드로이드 스터디4", 123, 6))
        workspaceList.add(WorkSpace("안드로이드 스터디", "안드로이드 스터디5", 124, 6))
        workspaceList.add(WorkSpace("안드로이드 스터디", "안드로이드 스터디6", 125, 6))

        // recyclerview adapter
        multiViewAdapter = MultiviewAdapter()

        // set up recyclerview
        binding.rvWorkspace.apply {
            setHasFixedSize(true)
            this.layoutManager =
                LinearLayoutManager(
                    this@SelectWorkspaceActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            adapter = multiViewAdapter

        }
        // set data
        multiViewAdapter.setData(list = workspaceList)
    }
}