package com.devmark.devmark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.devmark.devmark.databinding.ActivityMainBinding
import com.devmark.devmark.presentation.Recommend.RecommendFragment
import com.devmark.devmark.presentation.Remind.RemindFragment
import com.devmark.devmark.presentation.Workspace.WorkspaceFragment
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

        // 첫 화면 workspace뜨게 하기
        bn.selectedItemId = R.id.menu_workspace
        supportFragmentManager.beginTransaction().add(binding.frameLayout.id, WorkspaceFragment()).commit()
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
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(binding.frameLayout.id, fragment).commit()
    }

}
