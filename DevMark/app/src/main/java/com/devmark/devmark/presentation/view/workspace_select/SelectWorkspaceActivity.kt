package com.devmark.devmark.presentation.view.workspace_select

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.devmark.devmark.R
import com.devmark.devmark.databinding.ActivitySelectWorkspaceBinding

class SelectWorkspaceActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectWorkspaceBinding
    private val viewModel: SelectWorkSpaceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_workspace)

        supportFragmentManager.beginTransaction()
            .add(binding.flSelectWorkspace.id, SelectWorkspaceFragment())
            .commit()
    }

    fun moveActivity(p: Activity) {
        val intent = Intent(this, p::class.java)
        startActivity(intent)
    }

    fun replaceFragmentWithBackstack(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(binding.flSelectWorkspace.id, fragment)
            .addToBackStack(null).commit()
    }
}