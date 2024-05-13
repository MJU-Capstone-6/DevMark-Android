package com.devmark.devmark

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.devmark.devmark.databinding.ActivityMainBinding
import com.devmark.devmark.presentation.view.Recommend.RecommendFragment
import com.devmark.devmark.presentation.view.Remind.RemindFragment
import com.devmark.devmark.presentation.view.Workspace.WorkspaceFragment
import com.devmark.devmark.presentation.view.WorkspaceSelect.SelectWorkspaceActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val bn: BottomNavigationView by lazy {
        binding.bottomNav
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding 세팅
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // 첫 화면 workspace 표시
        bn.selectedItemId = R.id.menu_workspace
        supportFragmentManager.beginTransaction().add(binding.flMain.id, WorkspaceFragment())
            .commit()
        bn.setOnItemSelectedListener { item ->
            replaceFragment(
                when (item.itemId) {
                    R.id.menu_recommend -> RecommendFragment()
                    R.id.menu_remind -> RemindFragment()
                    else -> WorkspaceFragment()
                }
            )
            true
        }

    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(binding.flMain.id, fragment).commit()
    }

    fun replaceFragmentWithBackstack(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(binding.flMain.id, fragment)
            .addToBackStack(null).commit()
    }

    fun changeNaviVisibility(p: Boolean) {
        if (p) {
            binding.bottomNav.visibility = View.VISIBLE
        } else {
            binding.bottomNav.visibility = View.GONE
        }
    }

    fun moveActivity(p: Activity) {
        val intent = Intent(this, p::class.java)
        startActivity(intent)
    }

    fun backToSelectWorkspaceActivity() {
        val intent = Intent(this, SelectWorkspaceActivity::class.java)
        startActivity(intent)
    }

}
